package net.narybdaygroup.mininary.common.init;

import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.narybdaygroup.mininary.MiniNary;
import net.narybdaygroup.mininary.common.items.TalonToolMaterial;
import net.narybdaygroup.mininary.common.items.TalonsTalonItem;


@SuppressWarnings("unused")
public class MNItems {

    public static Item createItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(MiniNary.MOD_ID, name), item);
    }


    public static final Item MINI_NARY_SPAWN_EGG = createItem("mini_nary_spawn_egg", new SpawnEggItem(MNEntities.MINI_NARY,
            2854040, 7026219, new Item.Settings().group(ItemGroup.MISC)));

    public static Item TALONS_TALON = createItem("talons_talon", new TalonsTalonItem(TalonToolMaterial.INSTANCE, 4F,
            new Item.Settings().group(ItemGroup.COMBAT).rarity(Rarity.EPIC).fireproof().recipeRemainder(Items.NETHERITE_SCRAP)));

    public static void init(){}
}
