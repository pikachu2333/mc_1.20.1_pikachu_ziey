package com.example.pikachu_ziey.mixin;

import net.minecraft.entity.Entity;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)   // 方法在 Entity 里
public class EntityMountedOffsetMixin {

    /* 拿到受保护的原方法返回值 */
    @Shadow
    public double getMountedHeightOffset() {
        throw new AssertionError();
    }

    @Inject(method = "getMountedHeightOffset", at = @At("RETURN"), cancellable = true)
    private void piggyback$raiseRider(CallbackInfoReturnable<Double> cir) {
        Entity self = (Entity) (Object) this;
        // 只用 getClass 判断，绕过 Mixin 静态分析
        if (PlayerEntity.class.isAssignableFrom(self.getClass()) &&
                !self.getPassengerList().isEmpty()) {
            cir.setReturnValue(cir.getReturnValue() + 0.7);
        }
    }
}