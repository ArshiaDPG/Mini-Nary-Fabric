package net.narybdaygroup.mininary;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.narybdaygroup.mininary.common.entity.MiniNaryEntity;
import net.narybdaygroup.mininary.common.init.MNEntities;
import net.narybdaygroup.mininary.common.init.MNItems;

public class MiniNary implements ModInitializer {
    public static final String MOD_ID = "mininary";

    @Override
    public void onInitialize() {
        FabricDefaultAttributeRegistry.register(MNEntities.MINI_NARY, MiniNaryEntity.createMiniNaryAttributes());

        MNEntities.init();
        MNItems.init();
    }
}
