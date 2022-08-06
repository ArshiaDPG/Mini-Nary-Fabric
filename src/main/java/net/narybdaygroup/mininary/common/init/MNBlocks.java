package net.narybdaygroup.mininary.common.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.narybdaygroup.mininary.MiniNary;
import net.narybdaygroup.mininary.common.blocks.NaryCakeBlock;

import java.util.Set;
import java.util.stream.Collectors;

public class MNBlocks {
    private static final Set<Block> CANDLES = Registry.BLOCK.stream()
            .filter((block) -> block instanceof CandleBlock)
            .collect(Collectors.toSet());


    public static BlockItem createBlockItem(String blockID, Block block, ItemGroup group){
        return Registry.register(Registry.ITEM, new Identifier(MiniNary.MOD_ID, blockID), new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static Block createBlockWithItem(String blockID, Block block, ItemGroup group){
        createBlockItem(blockID, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(MiniNary.MOD_ID, blockID), block);
    }
    public static Block createBlockWithoutItem(String blockID, Block block){
        return Registry.register(Registry.BLOCK, new Identifier(MiniNary.MOD_ID, blockID), block);
    }
    
    public static final Block NARY_CAKE = createBlockWithItem("nary_cake", new NaryCakeBlock(AbstractBlock.Settings.of(Material.CAKE).strength(0.5F).sounds(BlockSoundGroup.WOOL)), ItemGroup.FOOD);

    public static void init(){
//        CANDLES.forEach(block ->
//                createBlockWithoutItem(block.getTranslationKey().split("\\.")[2] + "_chocolate_cake",
//                        new CandleChocolateCakeBlock(block, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE))));
    }
}
