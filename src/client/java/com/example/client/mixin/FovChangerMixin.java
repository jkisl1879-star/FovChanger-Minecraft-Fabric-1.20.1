package com.example.client.mixin;

import com.example.FovConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class FovChangerMixin {

    // 1. Изменение FOV мира (с поддержкой Sodium/Vulkan и зум-модов)
    @Redirect(method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D",
              at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/SimpleOption;getValue()Ljava/lang/Object;"))
    private Object redirectFovOption(SimpleOption<Integer> instance) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options != null && instance == client.options.getFov()) {
            return (int) FovConfig.fovValue;
        }
        return instance.getValue();
    }

    // 2. Отдельное изменение FOV для руки (ViewModel)
    @Inject(method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D", at = @At("RETURN"), cancellable = true)
    private void changeViewModelFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
        // changingFov == false означает, что игра сейчас рассчитывает FOV для руки
        if (!changingFov && FovConfig.enableViewModelFov) {
            cir.setReturnValue(FovConfig.viewModelFovValue);
        }
    }
}