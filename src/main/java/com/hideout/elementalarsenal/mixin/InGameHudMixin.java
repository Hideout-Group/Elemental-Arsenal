package com.hideout.elementalarsenal.mixin;

import com.hideout.elementalarsenal.ElementalArsenal;
import com.hideout.elementalarsenal.effect.ModStatusEffects;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Unique
    private static final Identifier ELECTRIFIED_HEARTS =
            new Identifier(ElementalArsenal.MOD_ID, "textures/gui/electrified_hearts.png");

    @Inject(method = "drawHeart", at = @At("HEAD"), cancellable = true)
    private void drawCustomHeart(DrawContext context, InGameHud.HeartType type, int x, int y, int v, boolean blinking, boolean halfHeart, CallbackInfo ci) {
        if (!blinking && type == InGameHud.HeartType.NORMAL && MinecraftClient.getInstance().cameraEntity instanceof PlayerEntity player && ModStatusEffects.hasModEffect(player)) {
            if (player.hasStatusEffect(ModStatusEffects.ELECTRIFIED)) {
                context.drawTexture(ELECTRIFIED_HEARTS, x, y, halfHeart ? 9 : 0, v, 9, 9);
            }
            ci.cancel();
        }
    }
}