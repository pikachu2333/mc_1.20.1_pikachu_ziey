package com.example.pikachu_ziey.network;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class SneakEvent {
    // 回调接口
    public interface Start { void onStart(PlayerEntity player); }
    public interface Stop  { void onStop (PlayerEntity player); }

    private static final Map<PlayerEntity, Boolean> LAST = new HashMap<>();

    public static void register(Start onStart, Stop onStop) {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            for (PlayerEntity p : world.getPlayers()) {
                boolean now   = p.isSneaking();
                boolean last = LAST.getOrDefault(p, false);
                if (!last && now) onStart.onStart(p);   // 刚刚按下
                if (last && !now) onStop.onStop(p);     // 刚刚松开
                LAST.put(p, now);
            }
        });
    }
}