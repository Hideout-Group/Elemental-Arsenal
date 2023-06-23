package com.hideout.elementalarsenal.mixin;

import com.hideout.elementalarsenal.event.custom.MouseScrollCallback;
import net.minecraft.client.Mouse;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;scrollInHotbar(D)V"), method = "onMouseScroll", cancellable = true)
    private void onScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        ActionResult result = MouseScrollCallback.EVENT.invoker().scroll((int) vertical, ci);

        if (result == ActionResult.CONSUME) {
            ci.cancel();
        }
    }
}
