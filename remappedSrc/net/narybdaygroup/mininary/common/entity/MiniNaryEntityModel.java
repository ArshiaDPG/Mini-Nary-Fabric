package net.narybdaygroup.mininary.common.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.narybdaygroup.mininary.MiniNary;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class MiniNaryEntityModel extends AnimatedTickingGeoModel<MiniNaryEntity> {

    @SuppressWarnings({ "unchecked", "unused", "rawtypes" })
    @Override
    public void setLivingAnimations(MiniNaryEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        LivingEntity entityIn = entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }

    @Override
    public Identifier getModelResource(MiniNaryEntity object) {
        return new Identifier(MiniNary.MOD_ID, "geo/mini_nary.geo.json");
    }

    @Override
    public Identifier getTextureResource(MiniNaryEntity object) {

        return new Identifier(MiniNary.MOD_ID, "textures/model/entity/mini_nary/mini_" + object.getExtension() + ".png");
    }

    @Override
    public Identifier getAnimationResource(MiniNaryEntity animatable) {
        return new Identifier(MiniNary.MOD_ID, "animations/mini_nary.animation.json");
    }
}