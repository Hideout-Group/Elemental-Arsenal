package com.hideout.elementalarsenal.network.packet;

import com.hideout.elementalarsenal.item.custom.interfaces.IMultiElementItem;
import com.hideout.elementalarsenal.network.ModMessages;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

@SuppressWarnings("unused")
public class ElementalItemSwitchC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        ItemStack stack = buf.readItemStack();
        int direction = buf.readInt();

        if (stack.getItem() instanceof IMultiElementItem item) {
            boolean valid = direction > 0 ? item.incrementType(stack) : item.decrementType(stack);
            PacketByteBuf newBuf = PacketByteBufs.create();
            newBuf.writeNbt(stack.getNbt());

            if (valid) {
                ServerPlayNetworking.send(player, ModMessages.ELEMENTAL_ITEM_SYNC, newBuf);
            }
        }
    }

}
