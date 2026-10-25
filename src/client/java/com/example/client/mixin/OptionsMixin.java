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
        FovConfig.fovValue = ((GameOptions)(Object)this).getFov().getValue();
        FovConfig.save();
    }
}