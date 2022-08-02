package net.narybdaygroup.mininary.common.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;
import java.util.function.Predicate;

public class MiniNaryEntity extends TameableEntity implements Flutterer, IAnimatable, IAnimationTickable, Angerable {

    public static final int field_28637 = MathHelper.ceil(2.4166098F);
    private AnimationFactory factory = new AnimationFactory(this);
    public static final Predicate<LivingEntity> FOLLOW_TAMED_PREDICATE;
    private static final UniformIntProvider ANGER_TIME_RANGE;
    private final String TEXTURE;
    @Nullable
    private UUID angryAt;
    private static final TrackedData<Integer> ANGER_TIME;



    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25D));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(4, new TemptGoal(this, 1.2D, Ingredient.ofItems(Items.COCOA_BEANS), false));
//        this.goalSelector.add(5, new MiniNaryEntity.FlyRandomlyGoal(this));
        this.goalSelector.add(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
        this.targetSelector.add(3, (new RevengeGoal(this, new Class[0])).setGroupRevenge(new Class[0]));
//        this.targetSelector.add(4, new ActiveTargetGoal(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.targetSelector.add(5, new UntamedActiveTargetGoal(this, AnimalEntity.class, false, FOLLOW_TAMED_PREDICATE));
        this.targetSelector.add(6, new UntamedActiveTargetGoal(this, TurtleEntity.class, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
        this.targetSelector.add(7, new ActiveTargetGoal(this, AbstractSkeletonEntity.class, false));
        this.targetSelector.add(8, new UniversalAngerGoal(this, true));
    }


    public MiniNaryEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super((EntityType<? extends TameableEntity>) entityType, world);
        this.experiencePoints = 7;
        this.moveControl = new MiniNaryEntity.MiniNaryMoveControl(this);
        TEXTURE = Types.randomizeExtension(world.getRandom());

    }

    public String getExtension() {
        return this.TEXTURE;
    }
    public boolean canAvoidTraps() {
        return true;
    }
    public boolean hasWings() {
        return this.age % field_28637 == 0;
    }

    protected void initDataTracker() {
        super.initDataTracker();
    }
    public static DefaultAttributeContainer.Builder createMiniNaryAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6000000238418579D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30000001192092896D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0D);
    }
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.5F;
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


    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }
    @Override
    public boolean isInAir() {
        return !this.onGround;
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.CAKE);
    }
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (this.world.isClient) {
            boolean bl = this.isOwner(player) || this.isTamed() || itemStack.isOf(Items.BONE) && !this.isTamed() && !this.hasAngerTime();
            return bl ? ActionResult.CONSUME : ActionResult.PASS;
        } else {
            if (this.isTamed()) {
                if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                    if (!player.getAbilities().creativeMode) {
                        itemStack.decrement(1);
                    }

                    this.heal((float)item.getFoodComponent().getHunger());
                    return ActionResult.SUCCESS;
                }

            } else if (itemStack.isOf(Items.CAKE) && !this.hasAngerTime()) {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                if (this.random.nextInt(3) == 0) {
                    this.setOwner(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setSitting(true);
                    this.world.sendEntityStatus(this, (byte)7);
                } else {
                    this.world.sendEntityStatus(this, (byte)6);
                }

                return ActionResult.SUCCESS;
            }

            return super.interactMob(player, hand);
        }
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

    public boolean tryAttack(Entity target) {
        boolean bl = target.damage(DamageSource.mob(this), (float)((int)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
        if (bl) {
            this.applyDamageEffects(this, target);
        }

        return bl;
    }

    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(8.0D);
        }

        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(4.0D);
    }
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public int getAngerTime() {
        return (Integer)this.dataTracker.get(ANGER_TIME);
    }

    public void setAngerTime(int angerTime) {
        this.dataTracker.set(ANGER_TIME, angerTime);
    }

    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    @Nullable
    public UUID getAngryAt() {
        return this.angryAt;
    }

    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
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


    static {
        ANGER_TIME = DataTracker.registerData(MiniNaryEntity.class, TrackedDataHandlerRegistry.INTEGER);
        FOLLOW_TAMED_PREDICATE = (entity) -> {
            EntityType<?> entityType = entity.getType();
            return entityType == EntityType.SHEEP || entityType == EntityType.RABBIT || entityType == EntityType.FOX;
        };
        ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    }
}
enum Types {
    NARY("nary"),
    TALON("talon");


    private final String identifier;
    Types(String identifier){
        this.identifier = identifier;
    }

    public static String getName(Types types){
        return types.identifier;
    }



    public static String randomizeExtension(Random random){
        Types[] types = values();
        return types[random.nextInt(types.length)].identifier;
    }

}
