package dev.unseen.entity;

import dev.unseen.TheUnseenMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class UnseenEntities {
    public static final EntityType<ObserverEntity> OBSERVER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(TheUnseenMod.MOD_ID, "observer"),
            EntityType.Builder.create(ObserverEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.7f, 2.65f).build("the_unseen:observer"));

    public static final EntityType<RaisedOneEntity> RAISED_ONE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(TheUnseenMod.MOD_ID, "raised_one"),
            EntityType.Builder.create(RaisedOneEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.9f, 1.9f).build("the_unseen:raised_one"));

    public static final EntityType<MuffinEntity> MUFFIN = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(TheUnseenMod.MOD_ID, "muffin"),
            EntityType.Builder.create(MuffinEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.7f, 0.8f).build("the_unseen:muffin"));

    private UnseenEntities() {}

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(OBSERVER, ObserverEntity.createZombieAttributes());
        FabricDefaultAttributeRegistry.register(RAISED_ONE, RaisedOneEntity.createZombieAttributes());
        FabricDefaultAttributeRegistry.register(MUFFIN, MuffinEntity.createWolfAttributes());
    }
}
