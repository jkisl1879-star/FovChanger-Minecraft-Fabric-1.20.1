package com.example;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FovChangerMod implements ModInitializer {
    public static final String MOD_ID = "fovchanger";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // Загрузка конфига при старте
        FovConfig.load();
        LOGGER.info("FovChanger initialized!");
    }
}