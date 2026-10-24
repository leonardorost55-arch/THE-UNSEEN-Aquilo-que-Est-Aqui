package dev.unseen.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;

public final class RaisedOneEntity extends ZombieEntity {
    public RaisedOneEntity(EntityType<? extends ZombieEntity> type, World world) {
        super(type, world);
        this.setCanPickUpLoot(false);
    }

    @Override
    public void tick() {
        super.tick();
        // It grows more dangerous the longer it remains alive.
        if (!getWorld().isClient && age % 1200 == 0) {
            var health = getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            var damage = getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            if (health != null) health.setBaseValue(Math.min(health.getBaseValue() + 4.0, 48.0));
            if (damage != null) damage.setBaseValue(Math.min(damage.getBaseValue() + 1.0, 10.0));
            setHealth(Math.min(getHealth() + 2.0f, getMaxHealth()));
        }
    }

    public static DefaultAttributeContainer.Builder createZombieAttributes() {
        return ZombieEntity.createZombieAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.24)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0);
    }
}
