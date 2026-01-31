package com.voidcustom.entities;

import io.netty.util.DefaultAttributeMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class MantisEntity extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public MantisEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(1, new BreedGoal(this, 1.150));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.25D, Ingredient.of(Items.STICK), false));

        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.4D));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.35);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.age);
        } else {
            this.idleAnimationTimeout -= 1;
        }
    }

    @Override
    public void aiStep() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Items.STICK);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.MANTIS.create(serverLevel, EntitySpawnReason.BREEDING);
    }
}
