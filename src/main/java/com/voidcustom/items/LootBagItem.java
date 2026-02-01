package com.voidcustom.items;

import com.voidcustom.Voidcustom;
import com.voidcustom.items.components.ModComponents;
import net.fabricmc.fabric.mixin.loot.LootTableAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class LootBagItem extends Item {
    public LootBagItem(Properties properties) {
        super(properties);
    }

    public @NotNull InteractionResult use(Level level, Player player, InteractionHand interactionHand)
    {
        var stack = player.getItemInHand(interactionHand);
        var component = stack.get(ModComponents.LOOT_BAG);

        if (component != null)
        {
            player.startUsingItem(interactionHand);
            level.playSound(player, player.blockPosition(), SoundEvents.BUNDLE_REMOVE_ONE, SoundSource.PLAYERS, 0.8F, 0.8F + level.getRandom().nextFloat() * 0.4F);
            player.awardStat(Stats.ITEM_USED.get(this));

            if (level instanceof ServerLevel serverLevel) {
                var builder = new LootParams.Builder(serverLevel)
                        .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(player.blockPosition()))
                        .withOptionalParameter(LootContextParams.THIS_ENTITY, player);
                var table = level.getServer().reloadableRegistries().getLootTable(component.lootTable());
                var item = table.getRandomItems(builder.create(LootContextParamSets.CHEST), level.getRandom().nextInt()).getFirst();
                return InteractionResult.CONSUME.heldItemTransformedTo(item);
            }
            else {
                return InteractionResult.CONSUME;
            }
        }
        else {
            return InteractionResult.PASS;
        }
        //return InteractionResult.PASS;
    }
}
