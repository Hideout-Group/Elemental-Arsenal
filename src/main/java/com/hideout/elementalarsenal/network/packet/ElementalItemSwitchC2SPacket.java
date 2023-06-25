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
        int slot = buf.readInt();
        NbtCompound nbt = buf.readNbt();

        player.getInventory().getStack(slot).setNbt(nbt);
    }

}
