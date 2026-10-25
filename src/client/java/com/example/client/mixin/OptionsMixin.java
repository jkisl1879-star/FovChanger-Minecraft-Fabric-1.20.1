package com.example.client.mixin;

import com.example.FovConfig;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public class OptionsMixin {
    @Inject(method = "write", at = @At("HEAD"))
    private void onSaveOptions(CallbackInfo ci) {
        int vanillaFov = ((GameOptions)(Object)this).getFov().getValue();
        
        // Если включен режим безлимита и текущий FOV выходит за ванильные рамки (30-110/120),
        // мы игнорируем ванильное значение, чтобы оно не сбросило наш кастомный FOV при сохранении настроек.
        if (FovConfig.unlimitedFov && (FovConfig.fovValue > 110.0 || FovConfig.fovValue < 30.0)) {
            // Ничего не делаем, сохраняем наше текущее значение из мода
        } else {
            FovConfig.fovValue = vanillaFov;
        }
        
        FovConfig.save();
    }
}