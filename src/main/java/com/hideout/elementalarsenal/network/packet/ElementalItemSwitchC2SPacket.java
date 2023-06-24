package com.hideout.elementalarsenal.network.packet;

import com.hideout.elementalarsenal.item.custom.interfaces.IElementalItem;
import com.hideout.elementalarsenal.util.ElementalType;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

@SuppressWarnings("unused")
public class ElementalItemSwitchC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        NbtCompound nbt = buf.readNbt();
        int slot = buf.readInt();
        System.out.println("Packet called from " + player.getName().getString());

        player.getInventory().getStack(slot).setNbt(nbt);
        System.out.println("Server: " + ElementalType.fromId(player.getInventory().getStack(slot)
                .getOrCreateNbt().getInt(IElementalItem.TYPE)).toString());
    }

}
