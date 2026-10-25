package com.example.client.mixin;

import com.example.FovConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class FovChangerMixin {

    @Inject(method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D", at = @At("RETURN"), cancellable = true)
    private void changeFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.currentScreen == null) {
            cir.setReturnValue(FovConfig.fovValue);
        }
    }
}