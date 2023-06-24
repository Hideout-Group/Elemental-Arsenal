package com.hideout.elementalarsenal.network.packet;

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

        player.getInventory().getStack(slot).setNbt(nbt);
    }

}
