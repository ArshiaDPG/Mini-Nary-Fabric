package net.narybdaygroup.mininary.common.init;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.narybdaygroup.mininary.MiniNary;
import net.narybdaygroup.mininary.common.entity.MiniNaryEntity;

public class MNEntities {
    public static final EntityType<MiniNaryEntity> MINI_NARY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MiniNary.MOD_ID, "mini_nary"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MiniNaryEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build()
    );

    public static void init(){
//        MiniNaryEntity.createMiniNaryAttributes();
    }
}
