package net.narybdaygroup.mininary.common.items;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class TalonToolMaterial implements ToolMaterial {

    public static final TalonToolMaterial INSTANCE = new TalonToolMaterial();

    @Override
    public int getDurability() {
        return 3000;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 2F;
    }

    @Override
    public float getAttackDamage() {
        return 5F;
    }

    @Override
    public int getMiningLevel() {
        return 1;
    }

    @Override
    public int getEnchantability() {
        return 12;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.ANCIENT_DEBRIS);
    }
}
