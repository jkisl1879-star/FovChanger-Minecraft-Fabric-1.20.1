package com.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FovConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "fovchanger.json");

    public static double fovValue = 90.0;
    public static double fovStep = 5.0;
    public static boolean unlimitedFov = false;
    
    // Новые настройки для руки
    public static double viewModelFovValue = 70.0;
    public static boolean enableViewModelFov = false;
    
    public static int keyIncrease = 61;
    public static int keyDecrease = 45;

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                ConfigData data = GSON.fromJson(reader, ConfigData.class);
                if (data != null) {
                    fovValue = data.fovValue;
                    fovStep = data.fovStep;
                    unlimitedFov = data.unlimitedFov;
                    keyIncrease = data.keyIncrease;
                    keyDecrease = data.keyDecrease;
                    
                    // Безопасная загрузка (если старый конфиг не имел этих строк)
                    viewModelFovValue = data.viewModelFovValue <= 0.0 ? 70.0 : data.viewModelFovValue;
                    enableViewModelFov = data.enableViewModelFov;
                }
            } catch (IOException e) {
                FovChangerMod.LOGGER.error("Failed to load config", e);
            }
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(new ConfigData(fovValue, fovStep, unlimitedFov, keyIncrease, keyDecrease, viewModelFovValue, enableViewModelFov), writer);
        } catch (IOException e) {
            FovChangerMod.LOGGER.error("Failed to save config", e);
        }
    }

    private static class ConfigData {
        double fovValue;
        double fovStep;
        boolean unlimitedFov;
        int keyIncrease;
        int keyDecrease;
        double viewModelFovValue;
        boolean enableViewModelFov;

        ConfigData(double fovValue, double fovStep, boolean unlimitedFov, int keyIncrease, int keyDecrease, double viewModelFovValue, boolean enableViewModelFov) {
            this.fovValue = fovValue;
            this.fovStep = fovStep;
            this.unlimitedFov = unlimitedFov;
            this.keyIncrease = keyIncrease;
            this.keyDecrease = keyDecrease;
            this.viewModelFovValue = viewModelFovValue;
            this.enableViewModelFov = enableViewModelFov;
        }
    }
}