package com.example.pikachu_ziey.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMountedOffsetMixin {

    /* 1.20.1 统一入口：getMountedHeightOffset */
    @Inject(method = "getMountedHeightOffset", at = @At("RETURN"), cancellable = true)
    private void piggyback$raiseRider(CallbackInfoReturnable<Double> cir) {
        Entity self = (Entity) (Object) this;
        cir.setReturnValue(cir.getReturnValue() + 0.2);
        /* 只对“被骑”的玩家生效 */
        if (self instanceof PlayerEntity && self.getCommandTags().contains("piggyback_base")) {
            double vanilla = cir.getReturnValue();
            cir.setReturnValue(vanilla + 0.2);   // 再抬 1.0 格
        }
    }
}