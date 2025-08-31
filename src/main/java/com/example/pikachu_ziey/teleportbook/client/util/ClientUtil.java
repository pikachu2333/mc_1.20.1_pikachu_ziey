package com.example.pikachu_ziey.teleportbook.client.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public final class ClientUtil {
    public static void openNamingScreen(ItemStack stack) {
            if (stack == null || stack.isEmpty()) {
                return;   // 静默忽略，或发送一条聊天信息给玩家
            }

        MinecraftClient.getInstance().setScreen(
                new com.example.pikachu_ziey.teleportbook.client.gui.WaypointNamingScreen(stack)
        );
    }

    public static void openWaypointScreen(ItemStack stack) {

        if (stack == null || stack.isEmpty()) {
            return;   // 静默忽略，或发送一条聊天信息给玩家
        }
        MinecraftClient.getInstance().setScreen(
                new com.example.pikachu_ziey.teleportbook.client.gui.WaypointScreen(stack)
        );
    }
}