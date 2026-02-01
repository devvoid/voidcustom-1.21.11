package com.voidcustom.entities;

import com.voidcustom.Voidcustom;
import com.voidcustom.items.ModItems;
import com.voidcustom.items.components.LootBagComponent;
import com.voidcustom.items.components.ModComponents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class Ilix extends PathfinderMob implements NeutralMob, Merchant {
    public static final List<ItemCost> COSTS = List.of(
            new ItemCost(Items.CHICKEN, 1),
            new ItemCost(Items.CHICKEN, 2),
            new ItemCost(Items.CHICKEN, 3),
            new ItemCost(Items.CHICKEN, 4),
            new ItemCost(Items.BEEF, 1),
            new ItemCost(Items.BEEF, 2),
            new ItemCost(Items.BEEF, 3),
            new ItemCost(Items.BEEF, 4),
            new ItemCost(Items.PORKCHOP, 1),
            new ItemCost(Items.PORKCHOP, 2),
            new ItemCost(Items.PORKCHOP, 3),
            new ItemCost(Items.PORKCHOP, 4)
    );

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    @Nullable
    private Player tradingPlayer;

    @Nullable
    private MerchantOffers offers;

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private long persistentAngerEndTime;
    @Nullable
    private EntityReference<@NotNull LivingEntity> persistentAngerTarget;

    protected Ilix(EntityType<? extends @NotNull PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.refreshDimensions();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 1.2F, 32.0F));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.4D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.ARMOR, 4)
                .add(Attributes.ATTACK_KNOCKBACK, 3)
                .add(Attributes.ATTACK_DAMAGE, 12)
                .add(Attributes.SCALE, 1.35);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            this.idleAnimationTimeout -= 1;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.is(Items.BUCKET)) {
            player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            ItemStack itemStack2 = ItemUtils.createFilledResult(itemStack, player, ModItems.ILIX_MILK_BUCKET.getDefaultInstance());
            player.setItemInHand(interactionHand, itemStack2);
            return InteractionResult.SUCCESS;
        } else if (this.isAlive() && !this.isTrading()) {
            if (interactionHand == InteractionHand.MAIN_HAND) {
                player.awardStat(Stats.TALKED_TO_VILLAGER);
            }

            if (!this.level().isClientSide()) {
                if (this.getOffers().isEmpty()) {
                    return InteractionResult.CONSUME;
                }

                this.setTradingPlayer(player);
                this.openTradingScreen(player, this.getDisplayName(), 1);
            }

            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(player, interactionHand);
        }
    }

    //region Neutral mob stuff
    @Override
    public long getPersistentAngerEndTime() {
        return this.persistentAngerEndTime;
    }

    @Override
    public void setPersistentAngerEndTime(long l) {
        this.persistentAngerEndTime = l;
    }

    @Override
    public @Nullable EntityReference<LivingEntity> getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable EntityReference<LivingEntity> entityReference) {
        this.persistentAngerTarget = entityReference;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setTimeToRemainAngry(PERSISTENT_ANGER_TIME.sample(this.random));
    }
    //endregion

    //region Trading stuff
    public boolean isTrading() {
        return this.tradingPlayer != null;
    }

    @Override
    public void setTradingPlayer(@Nullable Player player) {
        tradingPlayer = player;
    }

    @Override
    public @Nullable Player getTradingPlayer() {
        return tradingPlayer;
    }

    @Override
    public @NotNull MerchantOffers getOffers() {
        if (this.level() instanceof ServerLevel serverLevel) {
            if (this.offers == null) {
                this.offers = new MerchantOffers();
                this.updateTrades(serverLevel);
            }
            return offers;
        } else {
            throw new IllegalStateException("Cannot load Villager offers on the client");
        }
    }

    protected void updateTrades(ServerLevel serverLevel) {
        var trades = this.getOffers();

        //Voidcustom.LOGGER.info("OFFER DATA:\n Demand: {}\nMax uses: {}\nPrice multiplier: {}\nXP awarded: {}", o.getDemand(), o.getMaxUses(), o.getPriceMultiplier(), o.getXp());
        for(var i = 0; i < 8; i++) {
            var f = serverLevel.getRandom().nextFloat();

            // By default, a common trade
            var bag = ModItems.LOOT_BAG.getDefaultInstance();

            if (f < 0.15F) {
                bag.set(ModComponents.LOOT_BAG, LootBagComponent.RARE);
            } else if (f < 0.4F) {
                bag.set(ModComponents.LOOT_BAG, LootBagComponent.UNCOMMON);
            }

            var cost = COSTS.get(serverLevel.getRandom().nextInt(COSTS.size()));

            trades.add(new MerchantOffer(
                    cost,
                    bag,
                    1,
                    12,
                    1.0F
            ));
        }
    }

    @Override
    public void overrideOffers(MerchantOffers merchantOffers) {
        offers = merchantOffers;
    }

    @Override
    public void notifyTrade(MerchantOffer merchantOffer) {
        merchantOffer.increaseUses();
        // TODO: More stuff here for sounds and stuff
    }

    @Override
    public void notifyTradeUpdated(ItemStack itemStack) {
        // TODO: More sound here stuff in AbstractVillager
    }

    @Override
    public int getVillagerXp() {
        return 0;
    }

    @Override
    public void overrideXp(int i) {

    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public @NotNull SoundEvent getNotifyTradeSound() {
        return SoundEvents.WANDERING_TRADER_YES;
    }

    @Override
    public boolean isClientSide() {
        return this.level().isClientSide();
    }

    @Override
    public boolean stillValid(Player player) {
        return this.getTradingPlayer() == player && this.isAlive() && player.isWithinEntityInteractionRange(this, 4.0);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        if (!this.level().isClientSide()) {
            MerchantOffers merchantOffers = this.getOffers();
            if (!merchantOffers.isEmpty()) {
                valueOutput.store("Offers", MerchantOffers.CODEC, merchantOffers);
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        this.offers = (MerchantOffers)valueInput.read("Offers", MerchantOffers.CODEC).orElse(null);
    }
    //endregion
}
