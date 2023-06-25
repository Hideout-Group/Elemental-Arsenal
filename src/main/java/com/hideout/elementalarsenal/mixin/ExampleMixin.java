package com.hideout.elementalarsenal.mixin;

import com.hideout.elementalarsenal.effect.CustomParticleStatusEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(StatusEffectInstance.class)
public abstract class ExampleMixin {
	@Shadow public abstract StatusEffect getEffectType();

	@Inject(at = @At("HEAD"), method = "shouldShowParticles", cancellable = true)
	private void init(CallbackInfoReturnable<Boolean> cir) {
		if (getEffectType() instanceof CustomParticleStatusEffect) {
			cir.setReturnValue(false);
		}
	}
}