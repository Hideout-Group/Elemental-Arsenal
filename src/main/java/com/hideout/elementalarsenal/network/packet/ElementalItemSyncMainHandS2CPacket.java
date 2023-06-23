package com.hideout.elementalarsenal.network.packet;

import com.hideout.elementalarsenal.item.custom.interfaces.IMultiElementItem;
import com.hideout.elementalarsenal.mixin.InGameHudAccessor;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

@SuppressWarnings("unused")
public class ElementalItemSyncMainHandS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        ClientPlayerEntity player = client.player;
        assert player != null;
        ItemStack stack = player.getMainHandStack();
        if (stack.getItem() instanceof IMultiElementItem item) {
            boolean valid = buf.readInt() > 0 ? item.incrementType(stack) : item.decrementType(stack);

            if (valid) {
                ((InGameHudAccessor) client.inGameHud).setHeldItemTooltipFade((int)(40.0 * client.options.getNotificationDisplayTime().getValue()));
            }
        }
    }
}
