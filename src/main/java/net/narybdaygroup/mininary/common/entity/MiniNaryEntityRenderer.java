package net.narybdaygroup.mininary.common.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MiniNaryEntityRenderer extends GeoEntityRenderer<MiniNaryEntity> {
    public MiniNaryEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MiniNaryEntityModel());
    }
}