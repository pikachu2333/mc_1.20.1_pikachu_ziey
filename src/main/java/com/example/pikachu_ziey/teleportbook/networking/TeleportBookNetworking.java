package com.example.pikachu_ziey.teleportbook.networking;

import com.example.pikachu_ziey.teleportbook.util.Waypoint;
import com.example.pikachu_ziey.teleportbook.util.WaypointStorage;
import com.example.pikachu_ziey.teleportbook.item.TeleportBookItem;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public interface TeleportBookNetworking {
    Identifier DELETE = new Identifier("teleportbook", "delete_waypoint");
    Identifier ADD = new Identifier("teleportbook", "add_waypoint");
    Identifier SYNC_LIST = new Identifier("teleportbook", "sync_list");
    Identifier REFRESH = new Identifier("teleportbook", "refresh_ui");

    static void register() {
        ServerPlayNetworking.registerGlobalReceiver(DELETE, (server, player, handler, buf, responseSender) -> {
            int index = buf.readInt();
            server.execute(() -> {
                ItemStack book = player.getMainHandStack();
                if (!(book.getItem() instanceof TeleportBookItem)) return;

                List<Waypoint> list = WaypointStorage.read(book);
                if (index < 0 || index >= list.size()) return;

                list.remove(index);
                WaypointStorage.write(book, list);

                // 关键：把最新列表直接回发给客户端
                PacketByteBuf out = PacketByteBufs.create();
                out.writeCollection(list, (b, w) -> b.writeNbt(w.toNbt()));
                ServerPlayNetworking.send(player, SYNC_LIST, out);
            });
        });
        // 新增包
        ServerPlayNetworking.registerGlobalReceiver(ADD, (server, player, handler, buf, responseSender) -> {
            String name = buf.readString(32);
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            String dimId = buf.readString();
            RegistryKey<World> dim = RegistryKey.of(RegistryKeys.WORLD, new Identifier(dimId));

            server.execute(() -> {
                ItemStack book = player.getMainHandStack();
                if (!(book.getItem() instanceof TeleportBookItem)) return;
                Waypoint w = new Waypoint(name, x, y, z, dim);
                WaypointStorage.add(book, w);
            });
        });




    }
}