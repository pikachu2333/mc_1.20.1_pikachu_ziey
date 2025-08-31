package com.example.pikachu_ziey.teleportbook.client.gui;

import com.example.pikachu_ziey.teleportbook.networking.TeleportBookNetworking;
import com.example.pikachu_ziey.teleportbook.util.Waypoint;
import com.example.pikachu_ziey.teleportbook.util.WaypointStorage;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

import java.util.List;


public class WaypointScreen extends Screen {
    public  ItemStack book;
    private List<Waypoint> waypoints;


    public WaypointScreen(ItemStack book) {
        super(Text.literal("选择传送点"));
        this.book = book;
        reload();
    }

    public void reload() {
        this.waypoints = WaypointStorage.read(this.book);
    }

    @Override
    public void init() {
        // 每帧 rebuild，方便删除后立即刷新
        clearChildren();
        if (waypoints.isEmpty()) {
            addDrawableChild(ButtonWidget.builder(
                            Text.literal("暂无坐标点，潜行+右键空气记录"),
                            btn -> {})
                    .dimensions(width / 2 - 100, height / 2 - 10, 200, 20)
                    .build());
            return;
        }
        int y = 30;
        for (int i = 0; i < waypoints.size(); i++) {
            Waypoint w = waypoints.get(i);
            final int index = i;

            // 左侧传送按钮
            addDrawableChild(
                    ButtonWidget.builder(Text.literal(w.name()), btn -> {
                                assert client != null && client.player != null;
                                // 简单的 /tp 指令实现，也可换成网络包
                                client.player.networkHandler.sendCommand(
                                        "tp " + client.player.getName().getString() +
                                                " " + (int) w.x() + " " + (int) w.y() + " " + (int) w.z());
                                close();
                            })
                            .dimensions(width / 2 - 125, y, 220, 20)
                            .build());

            // 右侧删除按钮
            addDrawableChild(
                    ButtonWidget.builder(Text.literal("✖"), btn -> {
                                PacketByteBuf buf = PacketByteBufs.create();
                                buf.writeInt(index);
                                ClientPlayNetworking.send(TeleportBookNetworking.DELETE, buf);
                            })
                            .dimensions(width / 2 + 100, y, 20, 20)
                            .tooltip(Tooltip.of(Text.literal("删除")))
                            .build());

            y += 24;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
    }

    public void setWaypoints(List<Waypoint> newList) {
        this.waypoints= newList;
    }
}