package com.example.pikachu_ziey;

import com.example.pikachu_ziey.teleportbook.client.gui.WaypointScreen;
import com.example.pikachu_ziey.teleportbook.networking.TeleportBookNetworking;
import com.example.pikachu_ziey.teleportbook.util.Waypoint;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.util.List;
import java.util.Objects;


public class Pikachu_zieyClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(

                TeleportBookNetworking.SYNC_LIST,
                (client, handler, buf, responseSender) -> {
                    List<Waypoint> newList = buf.readList(b -> Waypoint.fromNbt(Objects.requireNonNull(b.readNbt())));
                    client.execute(() -> {
                        if (client.currentScreen instanceof WaypointScreen screen) {
                            screen.setWaypoints(newList); // 直接替换列表
                            screen.init();                // 重建按钮
                        }
                    });
                }

        );

    }
}
