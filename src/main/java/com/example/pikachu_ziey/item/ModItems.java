package com.example.pikachu_ziey.item;

import com.example.pikachu_ziey.Pikachu_ziey;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item Pikachu = registerItem("pikachu", new Item(new Item.Settings()));
    public static final Item Pikachuraw = registerItem("pikachu_raw", new Item(new Item.Settings()));

//    public static Item registerItems(String id, Item item) {
//        return Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier(Pikachu_ziey.MOD_ID, id)),item);
//    }

    public static Item registerItem(String id, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Pikachu_ziey.MOD_ID, id), item);
    }

    public static Item register(String id, Item item) {
        return register(new Identifier(Pikachu_ziey.MOD_ID, id), item);
    }

    public static Item register(Identifier id, Item item) {
        return register(RegistryKey.of(Registries.ITEM.getKey(), id), item);
    }

    public static Item register(RegistryKey<Item> key, Item item) {
        if (item instanceof BlockItem) {
            ((BlockItem) item).appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, key, item);
    }

    public static void registerModItems() {

    }
}
