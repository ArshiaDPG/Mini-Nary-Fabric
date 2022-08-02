package net.narybdaygroup.mininary.common.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.narybdaygroup.mininary.MiniNary;
import net.narybdaygroup.mininary.client.MiniNaryClient;

public class MiniNaryEntityRenderer extends MobEntityRenderer<MiniNaryEntity, MiniNaryEntityModel> {

    public MiniNaryEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new MiniNaryEntityModel(context.getPart(MiniNaryClient.MINI_NARY_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(MiniNaryEntity entity) {
        return new Identifier(MiniNary.MOD_ID, "textures/entity/cube/cube.png");
    }
}