package com.hideout.elementalarsenal.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InGameHud.class)
public interface InGameHudAccessor {
    @Accessor("heldItemTooltipFade")
    int getHeldItemTooltipFade();
    @Accessor("heldItemTooltipFade")
    void setHeldItemTooltipFade(int value);
}
