package net.narybdaygroup.mininary.common.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.narybdaygroup.mininary.MiniNary;
import net.narybdaygroup.mininary.client.MiniNaryClient;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MiniNaryEntityRenderer extends GeoEntityRenderer<MiniNaryEntity> {
    public MiniNaryEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MiniNaryEntityModel());
    }
}