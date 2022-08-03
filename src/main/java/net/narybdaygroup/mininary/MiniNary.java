package net.narybdaygroup.mininary;

import net.fabricmc.api.ModInitializer;
import net.narybdaygroup.mininary.common.init.MNBlocks;
import net.narybdaygroup.mininary.common.init.MNEntities;
import net.narybdaygroup.mininary.common.init.MNItems;

public class MiniNary implements ModInitializer {
    public static final String MOD_ID = "mininary";

    @Override
    public void onInitialize() {

        MNBlocks.init();
        MNEntities.init();
        MNItems.init();
    }
}
