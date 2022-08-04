package net.memcycraft.memcysmod.entity.sculk_golem;

import net.memcycraft.memcysmod.entity.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;

public class SculkGolem extends Monster implements IAnimatable {

    public static final AttributeSupplier createAttributes(){
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 50)
                .add(Attributes.ATTACK_DAMAGE, 20)
                .add(Attributes.ARMOR, 21)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2).build();
    }
    public static final String CONTROLLER_NAME = "sculk_controller";

    public static final EntityDataAccessor<Boolean> SLEEP = SynchedEntityData.defineId(SculkGolem.class,
            EntityDataSerializers.BOOLEAN);

    private AnimationFactory factory = new AnimationFactory(this);

    public SculkGolem(EntityType<SculkGolem> type, Level world){
        super(type, world);
    }

    protected SculkGolem(Level world){
        this(EntityInit.SCULK_GOLEM.get(), world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.1d, false){
            @Override
            public boolean canUse() {
                return super.canUse() && !isSleeping();
            }
        });
        this.goalSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true){
            @Override
            public boolean canUse() {
                return super.canUse() && !isSleeping();
            }
        });
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this,Player.class, 8f){
            @Override
            public boolean canUse() {
                return super.canUse() && !isSleeping();
            }
        });
        this.goalSelector.addGoal(10, new RandomStrollGoal(this, 1f){
            @Override
            public boolean canUse() {
                return super.canUse() && !isSleeping();
            }
        });
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide){
            if(this.isSleeping()) {
                List<Entity> entities = this.level.getEntities(this, new AABB(this.blockPosition()).inflate(2), e -> e instanceof Player);
                if (entities.size() != 0)
                    this.entityData.set(SLEEP, false);
            }else if(this.getTarget() == null && this.getLastHurtByMob() != null && this.getLastHurtByMob().distanceToSqr(this) < 4){
                this.entityData.set(SLEEP, true);
            }
        }
    }
//we got to do something about the the null thingy cause it's the problem that there's a crash in the minecraft when I wanna spawn..
    @Override
    public boolean hurt(DamageSource source, float damage) {
        if(this.isSleeping() && source.getEntity() != null)
            this.entityData.set(SLEEP, false);
        return super.hurt(source, damage);
    }

    @Override
    public boolean doHurtTarget(Entity opfer) {
        if(super.doHurtTarget(opfer)){
            AnimationController<SculkGolem> controller = GeckoLibUtil.getControllerForID(this.factory, this.getId(), CONTROLLER_NAME);
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.sculk_golem.attack"));
            return true;
        }
        return false;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SLEEP, true);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("sleeping", isSleeping());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(SLEEP, tag.getBoolean("sleeping"));
    }

    private PlayState predicate(AnimationEvent event){
        if(event.getController().getCurrentAnimation() != null && event.getController().getCurrentAnimation().animationName.equals("animation.sculk_golem.attack"))
            return PlayState.CONTINUE;

        if (isSleeping()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.sculk_golem.awake", true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.sculk_golem.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.sculk_golem.idle", true));
        return PlayState.CONTINUE;

        }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, CONTROLLER_NAME, 20, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public boolean isSleeping(){
        return this.entityData.get(SLEEP);
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.CAT_STRAY_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }

}
