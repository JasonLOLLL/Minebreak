package com.jasonjat.minebreak;

import com.jasonjat.minebreak.networking.ModPacketsC2S;
import com.jasonjat.minebreak.registry.ModEntities;
import net.fabricmc.api.ModInitializer;
import software.bernie.geckolib3.GeckoLib;

public class Minebreak implements ModInitializer {

    public static final String MODID = "minebreak";

    @Override
    public void onInitialize() {
        GeckoLib.initialize();
        ModEntities.init();
        ModPacketsC2S.init();
    }
}
