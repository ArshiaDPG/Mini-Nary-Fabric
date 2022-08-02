package net.narybdaygroup.mininary.common.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;

public class MiniNaryEntity extends PathAwareEntity implements Flutterer, IAnimatable, IAnimationTickable {

    public static final int field_28637 = MathHelper.ceil(2.4166098F);
    private AnimationFactory factory = new AnimationFactory(this);
    public static String textureExtender;

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25D));
        this.goalSelector.add(4, new TemptGoal(this, 1.2D, Ingredient.ofItems(new ItemConvertible[]{Items.COCOA_BEANS}), false));
        this.goalSelector.add(5, new MiniNaryEntity.FlyRandomlyGoal(this));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    public MiniNaryEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 7;
        this.moveControl = new MiniNaryEntity.MiniNaryMoveControl(this);
        this.textureExtender = Types.randomizeExtension(world.getRandom());

    }

    public String getExtension() {
        return this.textureExtender;
    }
    public boolean canAvoidTraps() {
        return true;
    }
    public boolean hasWings() {
        return this.age % field_28637 == 0;
    }


    public static DefaultAttributeContainer.Builder createMiniNaryAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6000000238418579D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30000001192092896D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0D);
    }
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.6F;
    }
    protected SoundEvent getAmbientSound() {
        return null;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_GENERIC_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GENERIC_DEATH;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }
    @Override
    public boolean isInAir() {
        return !this.onGround;
    }

    boolean isWithinDistance(BlockPos pos, int distance) {
        return pos.isWithinDistance(this.getBlockPos(), distance);
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<MiniNaryEntity>(this, "controller", 0, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mini_nary.idle", true));


        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public int tickTimer() {
        return age;
    }


    static class MiniNaryMoveControl extends MoveControl {
        private final MiniNaryEntity nary;
        private int collisionCheckCooldown;

        public MiniNaryMoveControl(MiniNaryEntity nary) {
            super(nary);
            this.nary = nary;
        }

        public void tick() {
            if (this.state == MoveControl.State.MOVE_TO) {
                if (this.collisionCheckCooldown-- <= 0) {
                    this.collisionCheckCooldown += this.nary.getRandom().nextInt(5) + 2;
                    Vec3d vec3d = new Vec3d(this.targetX - this.nary.getX(), this.targetY - this.nary.getY(), this.targetZ - this.nary.getZ());
                    double d = vec3d.length();
                    vec3d = vec3d.normalize();
                    if (this.willCollide(vec3d, MathHelper.ceil(d))) {
                        this.nary.setVelocity(this.nary.getVelocity().add(vec3d.multiply(0.1)));
                    } else {
                        this.state = MoveControl.State.WAIT;
                    }
                }

            }
        }

        private boolean willCollide(Vec3d direction, int steps) {
            Box box = this.nary.getBoundingBox();

            for(int i = 1; i < steps; ++i) {
                box = box.offset(direction);
                if (!this.nary.world.isSpaceEmpty(this.nary, box)) {
                    return false;
                }
            }

            return true;
        }
    }

    private static class FlyRandomlyGoal extends Goal {
        private final MiniNaryEntity nary;

        public FlyRandomlyGoal(MiniNaryEntity nary) {
            this.nary = nary;
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        public boolean canStart() {
            MoveControl moveControl = this.nary.getMoveControl();
            if (!moveControl.isMoving()) {
                return true;
            } else {
                double d = moveControl.getTargetX() - this.nary.getX();
                double e = moveControl.getTargetY() - this.nary.getY();
                double f = moveControl.getTargetZ() - this.nary.getZ();
                double g = d * d + e * e + f * f;
                return g < 1.0 || g > 3600.0;
            }
        }

        public boolean shouldContinue() {
            return false;
        }

        public void start() {
            Random random = this.nary.getRandom();
            double d = this.nary.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double e = this.nary.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double f = this.nary.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.nary.getMoveControl().moveTo(d, e, f, 1.0);
        }
    }
}
enum Types {
    NARY("nary");


    private final String identifier;
    Types(String identifier){
        this.identifier = identifier;
    }



    public static String randomizeExtension(Random random){
        Types[] directions = values();
        return directions[random.nextInt(directions.length)].identifier;
    }

}
