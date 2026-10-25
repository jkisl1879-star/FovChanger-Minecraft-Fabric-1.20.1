package com.example.client;

import com.example.FovConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;

public class FovChangerClient implements ClientModInitializer {

    private boolean initialized = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Синхронизация при заходе в мир (только один раз за сессию)
            if (!initialized && client.player != null) {
                initialized = true;
                // Если в нашем конфиге стоит 90 по умолчанию, берем значение из игры
                if (FovConfig.fovValue == 90.0) {
                    FovConfig.fovValue = client.options.getFov().getValue();
                    FovConfig.save();
                }
            }

            if (client.player == null || client.currentScreen != null) return;

            long window = MinecraftClient.getInstance().getWindow().getHandle();

            if (InputUtil.isKeyPressed(window, FovConfig.keyIncrease)) {
                if (client.player.age % 2 == 0) {
                    FovConfig.fovValue += FovConfig.fovStep;
                    if (!FovConfig.unlimitedFov && FovConfig.fovValue > 120.0) FovConfig.fovValue = 120.0;
                    
                    client.options.getFov().setValue((int) FovConfig.fovValue);
                    FovConfig.save();
                }
            }
            if (InputUtil.isKeyPressed(window, FovConfig.keyDecrease)) {
                if (client.player.age % 2 == 0) {
                    FovConfig.fovValue -= FovConfig.fovStep;
                    if (!FovConfig.unlimitedFov && FovConfig.fovValue < 30.0) FovConfig.fovValue = 30.0;
                    if (FovConfig.fovValue < 0.1) FovConfig.fovValue = 0.1;
                    
                    client.options.getFov().setValue((int) FovConfig.fovValue);
                    FovConfig.save();
                }
            }
        });
    }
}