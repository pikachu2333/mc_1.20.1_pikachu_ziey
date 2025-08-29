package com.example.pikachu_ziey.item;

import com.example.pikachu_ziey.Pikachu_ziey;
import com.example.pikachu_ziey.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> PIKACHU_ZiEY_GROUP = register("pikachu_ziey_group");

    private static RegistryKey<ItemGroup> register(String id) {
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(Pikachu_ziey.MOD_ID, id));
    }
    public static final ItemGroup TUTORIAL_GROUP2 = Registry.register(
            Registries.ITEM_GROUP,
            new Identifier(Pikachu_ziey.MOD_ID, "tutorial_group2"),
            ItemGroup.create(null, -1)
                    .displayName(Text.translatable("itemGroup.tutorial_group2"))
                    .icon(() -> new ItemStack(ModItems.Pikachu))
                    .entries((displayContext, entries) -> {
//                        entries.add(ModItems.Pikachu);
//                        entries.add(ModItems.Pikachuraw);
//                        entries.add(ModBlocks.PIKACHU_BLOCK);
//                        entries.add(ModBlocks.RAW_PIKACHU_BLOCK);
                    }).build());

    public static void registerGroups() {
        Registry.register(
                Registries.ITEM_GROUP,
                PIKACHU_ZiEY_GROUP,
                ItemGroup.create(ItemGroup.Row.TOP, 7)
                        .displayName(Text.translatable("itemGroup.Pikachu_ziey_group"))
                        .icon(() -> new ItemStack(ModItems.Pikachuraw))
                        .entries((displayContext, entries) -> {
                            entries.add(ModItems.Pikachu);
                            entries.add(ModItems.Pikachuraw);
                            entries.add(ModBlocks.PIKACHU_BLOCK);
                            entries.add(ModBlocks.RAW_PIKACHU_BLOCK);
                        }).build());
    }

    ;


}
