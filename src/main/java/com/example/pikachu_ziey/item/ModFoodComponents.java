package com.example.pikachu_ziey.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    // 增强版 pikachu - 给附魔金苹果的效果（恢复生命并附加更多增益效果）
    public static final FoodComponent pikachu = new FoodComponent.Builder()
            .hunger(10)  // 恢复10点饥饿
            .saturationModifier(1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 18000, 1), 1.0F)  // 给玩家速度效果（持续更久，并增强）
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 18000, 0), 1.0F)  // 给玩家抗性效果（类似金苹果的抗性）
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 4), 1.0F)  // 给玩家再生效果（让玩家慢慢恢复生命）
            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 18000, 0), 1.0F)  // 给玩家吸收效果（恢复额外的临时生命值）
            .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 18000), 1.0F)  // 给玩家火焰抗性（防止燃烧）
            .statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 18000, 1), 1.0F)  // 给玩家加速效果（提高挖掘速度）
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 4), 1.0F)  // 5秒无敌效果（通过高抗性等级实现）
            .alwaysEdible()
            .build();

    // pikachu_raw - 负面效果的食物（类似原版的危险食物）
    public static final FoodComponent pikachu_raw = new FoodComponent.Builder()
            .hunger(2)  // 恢复2点饥饿
            .saturationModifier(0.5F)
            .statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 200), 1.0F)  // 给玩家虚弱效果
            .statusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 200), 1.0F)  // 给玩家挖掘疲劳
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 200), 1.0F)  // 给玩家中毒效果
            .statusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 200), 1.0F)  // 给玩家失明效果
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 200), 1.0F)  // 给玩家饥饿效果
            .alwaysEdible()
            .build();
}
