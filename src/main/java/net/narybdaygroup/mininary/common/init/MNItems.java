package net.narybdaygroup.mininary.common.init;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.narybdaygroup.mininary.MiniNary;

public class MNItems {

    public static final Item createItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(MiniNary.MOD_ID, name), item);
    }

    public static final Item MINI_NARY_SPAWN_EGG = createItem("mini_nary_spawn_egg", new SpawnEggItem(MNEntities.MINI_NARY,
            12895428, 11382189, new Item.Settings().group(ItemGroup.MISC)));

    public static void init(){}
}