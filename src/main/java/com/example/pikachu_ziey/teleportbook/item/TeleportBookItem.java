package com.example.pikachu_ziey.teleportbook.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class TeleportBookItem extends Item {
    public TeleportBookItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        // 仅在客户端打开 GUI
        if (world.isClient()) {
            if (user.isSneaking()) {
                com.example.pikachu_ziey.teleportbook.client.util.ClientUtil.openNamingScreen(stack);
            }  // 正常记录（发消息由服务器端处理）

            return TypedActionResult.success(stack);
        }


        return TypedActionResult.success(stack);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack stack = context.getStack();
        System.out.println("[DEBUG] useOnBlock stack = " + stack);
        PlayerEntity player = context.getPlayer();
        if (player != null && context.getWorld().isClient() && !(player.isSneaking())) {
            // 客户端打开 GUI
            com.example.pikachu_ziey.teleportbook.client.util.ClientUtil.openWaypointScreen(stack);
        }
        return ActionResult.SUCCESS;
    }
}