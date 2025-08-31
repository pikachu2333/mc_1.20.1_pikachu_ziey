package com.example.pikachu_ziey.teleportbook.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public record Waypoint(String name, double x, double y, double z, RegistryKey<World> dimension) {
    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Name", name);
        nbt.putDouble("X", x);
        nbt.putDouble("Y", y);
        nbt.putDouble("Z", z);
        nbt.putString("Dimension", dimension.getValue().toString());
        return nbt;
    }

    public static Waypoint fromNbt(NbtCompound nbt) {
        return new Waypoint(
                nbt.getString("Name"),
                nbt.getDouble("X"),
                nbt.getDouble("Y"),
                nbt.getDouble("Z"),
                RegistryKey.of(RegistryKeys.WORLD, new Identifier(nbt.getString("Dimension")))
        );
    }
}