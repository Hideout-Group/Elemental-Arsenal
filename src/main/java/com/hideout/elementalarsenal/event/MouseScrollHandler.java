package com.hideout.elementalarsenal.event;

import com.hideout.elementalarsenal.event.custom.MouseScrollCallback;
import com.hideout.elementalarsenal.item.custom.interfaces.IMultiElementItem;
import com.hideout.elementalarsenal.network.ModMessages;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.ActionResult;

@Environment(EnvType.CLIENT)
public class MouseScrollHandler {
    public static void registerScrollHandlers() {
        MouseScrollCallback.EVENT.register((direction, ci) -> {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null) {
                ItemStack stack = player.getMainHandStack();
                if (stack.getItem() instanceof IMultiElementItem item && player.isSneaking()) {
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeItemStack(stack);
                    buf.writeInt(direction);
                    ClientPlayNetworking.send(ModMessages.ELEMENTAL_ITEM_SWITCH, buf);

                    return item.getAvailableTypes(stack).length > 1 ? ActionResult.CONSUME : ActionResult.PASS;
                }
            }

            return ActionResult.PASS;
        });
    }
}
