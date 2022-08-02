package net.narybdaygroup.mininary.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.narybdaygroup.mininary.MiniNary;
import net.narybdaygroup.mininary.common.entity.MiniNaryEntityModel;
import net.narybdaygroup.mininary.common.entity.MiniNaryEntityRenderer;
import net.narybdaygroup.mininary.common.init.MNEntities;

@Environment(EnvType.CLIENT)
public class MiniNaryClient implements ClientModInitializer {
    public static final EntityModelLayer MINI_NARY_LAYER = new EntityModelLayer(new Identifier(MiniNary.MOD_ID, "mini_nary"), "main");
    @Override
    public void onInitializeClient() {
//        // In 1.17, use EntityRendererRegistry.register (seen below) instead of EntityRendererRegistry.INSTANCE.register (seen above)
//        EntityRendererRegistry.register(MNEntities.MINI_NARY, (context) -> {
//            return new MiniNaryEntityRenderer(context);
//        });
//        EntityModelLayerRegistry.registerModelLayer(MINI_NARY_LAYER, MiniNaryEntityModel::getTexturedModelData);


        EntityRendererRegistry.register(MNEntities.MINI_NARY, MiniNaryEntityRenderer::new);

    }
}
