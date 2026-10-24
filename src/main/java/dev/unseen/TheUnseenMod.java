package dev.unseen;

import dev.unseen.entity.UnseenEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class TheUnseenMod implements ModInitializer {
    public static final String MOD_ID = "the_unseen";
    private static final Map<UUID, PlayerState> STATES = new HashMap<>();
    private static long ticks;

    @Override
    public void onInitialize() {
        UnseenEntities.registerAttributes();
        System.out.println("[THE UNSEEN] The world is listening.");
        ServerTickEvents.END_SERVER_TICK.register(TheUnseenMod::tick);
    }

    private static void tick(MinecraftServer server) {
        if (++ticks % 20 != 0) return;

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (player.isSpectator() || player.isCreative()) continue;
            World world = player.getWorld();
            PlayerState state = STATES.computeIfAbsent(player.getUuid(), id -> new PlayerState());
            state.secondsAlive++;

            if (state.secondsAlive < 90 || world.isDay() || world.getLightLevel(player.getBlockPos()) > 8) {
                continue;
            }
            if (state.cooldown > 0) {
                state.cooldown--;
                continue;
            }

            int roll = ThreadLocalRandom.current().nextInt(1000);
            if (roll < 4) {
                playDistantSteps(player);
                state.cooldown = 80 + ThreadLocalRandom.current().nextInt(100);
            } else if (roll < 7) {
                playWrongAnimalSound(player);
                state.cooldown = 100 + ThreadLocalRandom.current().nextInt(120);
            } else if (roll == 11) {
                leaveAVisualSign(player);
                state.cooldown = 120 + ThreadLocalRandom.current().nextInt(120);
            } else if (roll == 12 && world instanceof ServerWorld serverWorld) {
                spawnRareEncounter(player, serverWorld);
                state.cooldown = 180 + ThreadLocalRandom.current().nextInt(180);
            }
        }
    }

    private static void spawnRareEncounter(ServerPlayerEntity player, ServerWorld world) {
        // Spawn far enough away to create a sighting, not an unavoidable instant hit.
        double angle = ThreadLocalRandom.current().nextDouble(Math.PI * 2);
        int distance = 12 + ThreadLocalRandom.current().nextInt(9);
        BlockPos pos = player.getBlockPos().add(
                (int) Math.round(Math.cos(angle) * distance), 0,
                (int) Math.round(Math.sin(angle) * distance));
        pos = world.getTopPosition(net.minecraft.world.Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);

        if (!world.getBlockState(pos).isAir() || !world.getBlockState(pos.up()).isAir()) return;
        int type = ThreadLocalRandom.current().nextInt(3);
        if (type == 0) {
            UnseenEntities.OBSERVER.spawn(world, pos, SpawnReason.EVENT);
        } else if (type == 1) {
            UnseenEntities.RAISED_ONE.spawn(world, pos, SpawnReason.EVENT);
        } else {
            UnseenEntities.MUFFIN.spawn(world, pos, SpawnReason.EVENT);
        }
    }

    private static void playDistantSteps(ServerPlayerEntity player) {
        BlockPos p = player.getBlockPos();
        double angle = ThreadLocalRandom.current().nextDouble(Math.PI * 2);
        double distance = 7 + ThreadLocalRandom.current().nextDouble(7);
        double x = p.getX() + Math.cos(angle) * distance;
        double z = p.getZ() + Math.sin(angle) * distance;
        player.getWorld().playSound(null, x, p.getY(), z,
                SoundEvents.BLOCK_GRASS_STEP, SoundCategory.HOSTILE,
                0.65f, 0.55f + ThreadLocalRandom.current().nextFloat() * 0.25f);
    }

    private static void playWrongAnimalSound(ServerPlayerEntity player) {
        BlockPos p = player.getBlockPos();
        double angle = ThreadLocalRandom.current().nextDouble(Math.PI * 2);
        double x = p.getX() + Math.cos(angle) * 5;
        double z = p.getZ() + Math.sin(angle) * 5;
        player.getWorld().playSound(null, x, p.getY(), z,
                SoundEvents.ENTITY_WOLF_WHINE, SoundCategory.HOSTILE, 0.4f, 0.5f);
    }

    private static void leaveAVisualSign(ServerPlayerEntity player) {
        BlockPos pos = player.getBlockPos().offset(player.getHorizontalFacing(), 6);
        if (player.getWorld().getBlockState(pos).isAir()) {
            player.getServerWorld().spawnParticles(ParticleTypes.ASH,
                    pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                    5, 0.2, 0.3, 0.2, 0.003);
        }
    }

    private static final class PlayerState {
        private int secondsAlive;
        private int cooldown;
    }
}
