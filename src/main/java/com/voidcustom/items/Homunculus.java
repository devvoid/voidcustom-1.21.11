package com.voidcustom.items;

import com.voidcustom.items.components.HomunculusBelly;
import com.voidcustom.items.components.ModComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class Homunculus extends Item {
    public Homunculus(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, InteractionHand interactionHand)
    {
        player.startUsingItem(interactionHand);
        level.playSound(player, player.blockPosition(), SoundEvents.AXOLOTL_IDLE_AIR, SoundSource.RECORDS, 16.0F, 1.0F);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack stack) { return ItemUseAnimation.NONE; }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) { return 20; }
}
