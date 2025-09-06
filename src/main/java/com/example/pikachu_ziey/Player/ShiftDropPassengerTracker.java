package com.example.pikachu_ziey.Player;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.WeakHashMap;

public class ShiftDropPassengerTracker {
    // 玩家 -> 已持续潜行 tick 数
    private static final WeakHashMap<PlayerEntity, Integer> SHIFT_TIME = new WeakHashMap<>();

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            for (PlayerEntity player : world.getPlayers()) {
                if (player.isSneaking() && !player.getPassengerList().isEmpty()) {
                    int ticks = SHIFT_TIME.getOrDefault(player, 0) + 1;
                    if (ticks == 5) {           // ¼ 秒
                        // 复制避免并发修改
                        for (var passenger : player.getPassengerList().toArray(new net.minecraft.entity.Entity[0])) {
                            passenger.stopRiding();
                        }
                        PiggyBackMod.syncPassengers(player); // 广播
                    }
                    SHIFT_TIME.put(player, ticks);
                } else {
                    SHIFT_TIME.remove(player);   // 没蹲或没乘客就清零
                }
            }
        });
    }
}