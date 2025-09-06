package com.example.pikachu_ziey.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

	/* 1.20.1 纱映射方法名：shouldRender */
	@Inject(
			method = "shouldRender",   // 纱映射名
			at = @At("HEAD"),
			cancellable = true
	)
	private void piggy$alwaysRenderPassenger(double cameraX, double cameraY, double cameraZ,
											 CallbackInfoReturnable<Boolean> cir) {
		Entity self = (Entity) (Object) this;
		if (self instanceof PlayerEntity) {
			cir.setReturnValue(true);
		}

	}
}