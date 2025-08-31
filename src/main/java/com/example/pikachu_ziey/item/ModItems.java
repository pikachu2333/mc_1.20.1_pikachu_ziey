package com.example.pikachu_ziey.item;

import com.example.pikachu_ziey.Pikachu_ziey;
import com.example.pikachu_ziey.teleportbook.item.TeleportBookItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item Pikachu = registerItem("pikachu", new Item(new Item.Settings().food(ModFoodComponents.pikachu)));
    public static final Item Pikachuraw = registerItem("pikachu_raw", new Item(new Item.Settings().food(ModFoodComponents.pikachu_raw)));
    public static final Item TELEPORT_BOOK = registerItem("teleport_book", new TeleportBookItem(new Item.Settings()));

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

    private static void addItemToItemGroup(FabricItemGroupEntries entries) {

        entries.add(Pikachu);
        entries.add(Pikachuraw);

    }
    private static void addBookToItemGroup(FabricItemGroupEntries entries) {
        entries.add(TELEPORT_BOOK);

    }


    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(ModItems::addItemToItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addBookToItemGroup);


    }
}
