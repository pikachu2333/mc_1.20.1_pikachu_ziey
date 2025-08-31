package com.example.pikachu_ziey.teleportbook.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.List;

public class WaypointStorage {
    private static final String KEY = "Waypoints";

    public static List<Waypoint> read(ItemStack stack) {
        System.out.println("[DEBUG] WaypointStorage.read called with stack = " + stack);
        if (stack == null || stack.isEmpty()) {
            System.out.println("[DEBUG] stack null/empty, return empty list");
            return new ArrayList<>();
        }
        List<Waypoint> list = new ArrayList<>();
        if (stack == null || stack.isEmpty()) {
            return new ArrayList<>();   // 返回空列表，避免下面 getOrCreateNbt 炸掉
        }
        NbtCompound tag = stack.getOrCreateNbt();
        System.out.println("[DEBUG] NBT = " + tag);
        if (tag.contains(KEY, 9)) { // 9 = NBT List
            NbtList listTag = tag.getList(KEY, 10); // 10 = NBT Compound
            for (int i = 0; i < listTag.size(); i++) {
                list.add(Waypoint.fromNbt(listTag.getCompound(i)));
            }
        }
        return list;

    }

    public static void write(ItemStack stack, List<Waypoint> waypoints) {
        NbtCompound tag = stack.getOrCreateNbt();
        NbtList listTag = new NbtList();
        for (Waypoint w : waypoints) {
            listTag.add(w.toNbt());
        }
        tag.put(KEY, listTag);
    }

    public static void add(ItemStack stack, Waypoint w) {
        List<Waypoint> waypoints = read(stack);
        waypoints.add(w);
        write(stack, waypoints);
    }


}