package com.hideout.elementalarsenal.network;

import com.hideout.elementalarsenal.ElementalArsenal;
import com.hideout.elementalarsenal.network.packet.ElementalItemSwitchC2SPacket;
import com.hideout.elementalarsenal.network.packet.ElementalItemSyncMainHandS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier ELEMENTAL_ITEM_SWITCH = new Identifier(ElementalArsenal.MOD_ID, "elemental_item_switch");
    public static final Identifier ELEMENTAL_ITEM_SYNC = new Identifier(ElementalArsenal.MOD_ID, "elemental_item_sync");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(ELEMENTAL_ITEM_SWITCH, ElementalItemSwitchC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ELEMENTAL_ITEM_SYNC, ElementalItemSyncMainHandS2CPacket::receive);
    }
}
