package com.example.pikachu_ziey.Player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface PlayerMountCallback {
    Event<PlayerMountCallback> EVENT = EventFactory.createArrayBacked(
            PlayerMountCallback.class,
            listeners -> (rider, target) -> {
                for (PlayerMountCallback l : listeners) {
                    ActionResult result = l.interact(rider, target);
                    if (result != ActionResult.PASS) return result;
                }
                return ActionResult.PASS;
            });

    ActionResult interact(PlayerEntity rider, PlayerEntity target);
}