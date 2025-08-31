package com.example.pikachu_ziey.teleportbook.client.gui;

import com.example.pikachu_ziey.teleportbook.networking.TeleportBookNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

public class WaypointNamingScreen extends Screen {
    private TextFieldWidget nameField;

    public WaypointNamingScreen(ItemStack stack) {
        super(Text.literal("输入坐标点名称"));
    }

    @Override
    protected void init() {
        nameField = new TextFieldWidget(textRenderer, width / 2 - 100, height / 2 - 10, 200, 20, Text.literal("名称"));
        nameField.setMaxLength(32);
        nameField.setText("");
        addDrawableChild(nameField);
        setInitialFocus(nameField);

        addDrawableChild(ButtonWidget.builder(Text.literal("保存"), btn -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) name = "Waypoint";

            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeString(name);
            buf.writeDouble(client.player.getX());
            buf.writeDouble(client.player.getY());
            buf.writeDouble(client.player.getZ());
            buf.writeString(client.world.getRegistryKey().getValue().toString());

            ClientPlayNetworking.send(TeleportBookNetworking.ADD, buf);
            client.player.sendMessage(Text.literal("已记录：" + name), false);
            close();
        }).dimensions(width / 2 - 100, height / 2 + 20, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        nameField.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 257) { // Enter
            children().stream()
                    .filter(ButtonWidget.class::isInstance)
                    .map(ButtonWidget.class::cast)
                    .findFirst()
                    .ifPresent(ButtonWidget::onPress);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}