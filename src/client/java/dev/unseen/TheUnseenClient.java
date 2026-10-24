package dev.unseen;

import dev.unseen.entity.UnseenEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.WolfEntityRenderer;
import net.minecraft.client.render.entity.ZombieEntityRenderer;

public final class TheUnseenClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(UnseenEntities.OBSERVER, ZombieEntityRenderer::new);
        EntityRendererRegistry.register(UnseenEntities.RAISED_ONE, ZombieEntityRenderer::new);
        EntityRendererRegistry.register(UnseenEntities.MUFFIN, WolfEntityRenderer::new);
    }
}
