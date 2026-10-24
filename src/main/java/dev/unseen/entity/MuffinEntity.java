package dev.unseen.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.world.World;

public final class MuffinEntity extends WolfEntity {
    public MuffinEntity(EntityType<? extends WolfEntity> type, World world) {
        super(type, world);
        setPersistent();
        // Unlike a normal wolf, Muffin treats nearby players as prey.
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.25, true));
    }

    @Override
    public void tick() {
        super.tick();
        // Muffin watches its target before closing the distance.
        if (!getWorld().isClient && age % 200 == 0 && getTarget() != null) {
            getLookControl().lookAt(getTarget(), 30.0f, 30.0f);
        }
    }

    public static DefaultAttributeContainer.Builder createWolfAttributes() {
        return WolfEntity.createWolfAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 22.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.34)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0);
    }
}
