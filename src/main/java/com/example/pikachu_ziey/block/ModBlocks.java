package com.example.pikachu_ziey.block;


import com.example.pikachu_ziey.Pikachu_ziey;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModBlocks {
    public static final Block PIKACHU_BLOCK = register("pikachu_block", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));
    public static final Block RAW_PIKACHU_BLOCK = register("raw_pikachu_block", new Block(AbstractBlock.Settings.create().strength(0.2f, 0.2f)));

    public static Block register(String id, Block block) {
        registerBlockItems(id,block);
        return Registry.register(Registries.BLOCK, new Identifier(Pikachu_ziey.MOD_ID, id), block);
    }
    public static void registerBlockItems(String id, Block block) {
        Registry.register(Registries.ITEM, new Identifier(Pikachu_ziey.MOD_ID, id),
                new BlockItem(block, new Item.Settings()));
    }
    public static void registerModBlocks() {

    }
}
