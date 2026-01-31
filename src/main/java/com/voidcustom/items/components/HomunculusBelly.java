package com.voidcustom.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public record HomunculusBelly(int fullness) implements TooltipProvider {
    public static final Codec<HomunculusBelly> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(ExtraCodecs.POSITIVE_INT.fieldOf("fullness").forGetter(HomunculusBelly::fullness)).apply(instance, HomunculusBelly::new)
    );
    public static final StreamCodec<@NotNull ByteBuf, @NotNull HomunculusBelly> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.VAR_INT, HomunculusBelly::fullness, HomunculusBelly::new);

    public static final HomunculusBelly EMPTY = new HomunculusBelly(0);

    public static int getOrDefault(ItemStack itemStack, int i) {
        HomunculusBelly belly = itemStack.get(ModComponents.HOMUNCULUS_BELLY);
        return belly != null ? belly.fullness : i;
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        int count = fullness;
        // 255, 191, 127, 63
        var key = "item.voidcustom.homunculus_belly.starving";
        if (count > 191)
        {
            key = "item.voidcustom.homunculus_belly.engorged";
        }
        else if (count > 127)
        {
            key = "item.voidcustom.homunculus_belly.sated";
        }
        else if (count > 63)
        {
            key = "item.voidcustom.homunculus_belly.nourished";
        }
        consumer.accept(Component.literal(String.valueOf(fullness)).withStyle(ChatFormatting.GOLD));
    }

}
