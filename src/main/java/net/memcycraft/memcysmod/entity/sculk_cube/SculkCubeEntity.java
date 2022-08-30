package net.memcycraft.memcysmod.entity.sculk_cube;

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
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
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

public class SculkCubeEntity extends Monster implements IAnimatable {

    public static final AttributeSupplier createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.ATTACK_DAMAGE, 15)
                .add(Attributes.ARMOR, 10)
                .add(Attributes.KNOCKBACK_RESISTANCE, 6)
                .add(Attributes.MOVEMENT_SPEED, 0.25d).build();

    }
    public static final String CONTROLLER_NAME = "sculk_controller";

    public static final EntityDataAccessor<Boolean> SLEEP = SynchedEntityData.defineId(SculkCubeEntity.class,
            EntityDataSerializers.BOOLEAN);

    private AnimationFactory factory = new AnimationFactory(this);

    public SculkCubeEntity(EntityType<SculkCubeEntity> type, Level world){
        super(type, world);
    }

    protected SculkCubeEntity(Level world){
        this(EntityInit.SCULK_CUBE.get(), world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.1d, false){

        });
        this.goalSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true){

        });
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this,Player.class, 8f){

        });
        this.goalSelector.addGoal(10, new RandomStrollGoal(this, 1f){
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
    @Override
    public boolean hurt(DamageSource source, float damage) {
        if(this.isSleeping() && source.getEntity() != null)
            this.entityData.set(SLEEP, false);
        return super.hurt(source, damage);
    }

    @Override
    public boolean doHurtTarget(Entity opfer) {
        if(super.doHurtTarget(opfer)){
            AnimationController<SculkCubeEntity> controller = GeckoLibUtil.getControllerForID(this.factory, this.getId(), CONTROLLER_NAME);
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.sculk_cube.attack"));
            return true;
        }
        return false;
    }

    private PlayState predicate(AnimationEvent event){
        if(event.getController().getCurrentAnimation() != null && event.getController().getCurrentAnimation().animationName.equals("animation.sculk_cube.attack"))
            return PlayState.CONTINUE;

        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.sculk_cube.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.sculk_cube.idle", true));
        return PlayState.CONTINUE;

    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, CONTROLLER_NAME, 14, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
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
