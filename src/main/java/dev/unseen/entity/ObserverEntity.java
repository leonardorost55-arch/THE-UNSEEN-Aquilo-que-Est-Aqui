package dev.unseen.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;

public final class ObserverEntity extends ZombieEntity {
    public ObserverEntity(EntityType<? extends ZombieEntity> type, World world) {
        super(type, world);
        this.setCanPickUpLoot(false);
    }

    @Override
    public void tick() {
        super.tick();
        // The Observer is deliberately more persistent than an ordinary zombie.
        if (!getWorld().isClient && age % 100 == 0) {
            getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.31);
        }
    }

    public static DefaultAttributeContainer.Builder createZombieAttributes() {
        return ZombieEntity.createZombieAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 36.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.29)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0);
    }
}
