package net.memcycraft.memcysmod.entity.sculk_monstrosity;
import net.memcycraft.memcysmod.entity.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
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
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;

public class Sculk_Monstrosity extends Monster implements IAnimatable {

    protected ServerBossEvent bossBar = (ServerBossEvent) new ServerBossEvent(this.getDisplayName(),

            BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(true);

// What about the speed?
    public static final AttributeSupplier createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 300)
                .add(Attributes.ATTACK_DAMAGE, 25)
                .add(Attributes.ARMOR, 30)
                .add(Attributes.KNOCKBACK_RESISTANCE, 6)
                .add(Attributes.MOVEMENT_SPEED, 0.25d).build();
    }
//speed done
    // nice
    // for the sculk golem too please.
    public static final String CONTROLLER_NAME = "sculk_controller";

    public static final EntityDataAccessor<Boolean> SLEEP = SynchedEntityData.defineId(net.memcycraft.memcysmod.entity.sculk_golem.SculkGolem.class,
            EntityDataSerializers.BOOLEAN);

    private AnimationFactory factory = new AnimationFactory(this);

    public Sculk_Monstrosity(EntityType<net.memcycraft.memcysmod.entity.sculk_monstrosity.Sculk_Monstrosity> type, Level world) {
        super(type, world);
    }

    protected Sculk_Monstrosity(Level world) {
        this(EntityInit.SCULK_MONSTROSITY.get(), world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, .9d, false) {
            @Override
            public boolean canUse() {
                return super.canUse() && !isSleeping();
            }
        });
        this.goalSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true) {
            @Override
            public boolean canUse() {
                return super.canUse() && !isSleeping();
            }
        });
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8f) {
            @Override
            public boolean canUse() {
                return super.canUse() && !isSleeping();
            }
        });
        this.goalSelector.addGoal(10, new RandomStrollGoal(this, 0.7f) {
            @Override
            public boolean canUse() {
                return super.canUse() && !isSleeping();
            }
        });
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            if (this.isSleeping()) {
                List<Entity> entities = this.level.getEntities(this, new AABB(this.blockPosition()).inflate(10), e -> e instanceof Player);
                if (entities.size() != 0)
                    this.entityData.set(SLEEP, false);
            } else if (this.getTarget() == null && this.getLastHurtByMob() != null && this.getLastHurtByMob().distanceToSqr(this) > 9) {
                this.entityData.set(SLEEP, true);
            }
            bossBar.setProgress(this.getHealth() / this.getMaxHealth());
        }
    }

    @Override
    public boolean hurt(DamageSource source, float damage) {
        if (this.isSleeping() && source.getEntity() != null)
            this.entityData.set(SLEEP, false);
        return super.hurt(source, damage);
    }

    @Override
    public boolean doHurtTarget(Entity opfer) {
        if (super.doHurtTarget(opfer)) {
            AnimationController<net.memcycraft.memcysmod.entity.sculk_monstrosity.Sculk_Monstrosity> controller = GeckoLibUtil.getControllerForID(this.factory, this.getId(), CONTROLLER_NAME);
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.sculk_monstrosity.main_attack"));
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
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
            bossBar.setProgress(this.getHealth() / this.getMaxHealth());
        }
    }

    private PlayState predicate(AnimationEvent event) {
        if (event.getController().getCurrentAnimation() != null && event.getController().getCurrentAnimation().animationName.equals("animation.sculk_monstrosity.main_attack"))
            return PlayState.CONTINUE;

        if (isSleeping()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.sculk_monstrosity.sleep", true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.sculk_monstrosity.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.sculk_monstrosity.idle", true));
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

    public boolean isSleeping() {
        return this.entityData.get(SLEEP);
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.CAT_STRAY_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.WITHER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.WITHER_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }

    @Override
    public void startSeenByPlayer(ServerPlayer p31483) {
        super.startSeenByPlayer(p31483);
        this.bossBar.addPlayer(p31483);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer p31488) {
        super.stopSeenByPlayer(p31488);
        this.bossBar.removePlayer(p31488);
    }

    public void setCustomName(@Nullable Component p31476) {
        super.setCustomName(p31476);
        this.bossBar.setName(this.getDisplayName());
    }
}