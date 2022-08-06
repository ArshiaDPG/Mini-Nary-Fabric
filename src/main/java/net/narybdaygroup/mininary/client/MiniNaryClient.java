package net.narybdaygroup.mininary.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.narybdaygroup.mininary.MiniNary;
import net.narybdaygroup.mininary.common.entity.MiniNaryEntityModel;
import net.narybdaygroup.mininary.common.entity.MiniNaryEntityRenderer;
import net.narybdaygroup.mininary.common.init.MNBlocks;
import net.narybdaygroup.mininary.common.init.MNEntities;

@Environment(EnvType.CLIENT)
public class MiniNaryClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(MNBlocks.NARY_CAKE, RenderLayer.getCutout());

        EntityRendererRegistry.register(MNEntities.MINI_NARY, MiniNaryEntityRenderer::new);

    }
}
