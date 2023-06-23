package com.hideout.elementalarsenal.event.custom;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface MouseScrollCallback {
    Event<MouseScrollCallback> EVENT = EventFactory.createArrayBacked(MouseScrollCallback.class,
            (listeners) -> (direction, callBackinfo) -> {
        boolean cancel = false;
        for (MouseScrollCallback callback : listeners) {
            ActionResult result = callback.scroll(direction, callBackinfo);

            if (result == ActionResult.CONSUME) {
                cancel = true;
            }
        }

        return cancel ? ActionResult.CONSUME : ActionResult.PASS;
    });

    ActionResult scroll(int direction, CallbackInfo ci);
}
