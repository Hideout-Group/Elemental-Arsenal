package com.hideout.elementalarsenal.event;

import com.hideout.elementalarsenal.event.custom.MouseScrollCallback;
import com.hideout.elementalarsenal.item.custom.interfaces.MultiElementItem;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
public class MouseScrollHandler {
    public static void registerScrollHandlers() {
        MouseScrollCallback.EVENT.register(MouseScrollHandler::switchElementalType);
    }

    private static ActionResult switchElementalType(int direction, CallbackInfo callbackInfo) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            ItemStack stack = player.getMainHandStack();
            if (stack.getItem() instanceof MultiElementItem item && player.isSneaking()) {

                boolean valid;
                if (direction > 0) {
                    valid = item.incrementType(stack);
                } else {
                    valid = item.decrementType(stack);
                }

                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeNbt(stack.getNbt());
                buf.writeInt(player.getInventory().selectedSlot);
                ClientPlayNetworking.send(ModMessages.ELEMENTAL_ITEM_SWITCH, buf);


                return valid ? ActionResult.CONSUME : ActionResult.PASS;
            }
        }

        return ActionResult.PASS;
    }
}
