package com.example.client;

import com.example.FovConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting; // Добавлен импорт для цвета
import org.lwjgl.glfw.GLFW;

public class FovConfigScreen extends Screen {
    private final Screen parent;
    private TextFieldWidget stepField;

    public FovConfigScreen(Screen parent) {
        super(Text.translatable("fovchanger.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = this.height / 2 - 60;

        this.addDrawableChild(ButtonWidget.builder(getKeyText("fovchanger.config.increase", FovConfig.keyIncrease), button -> {
            this.client.setScreen(new KeybindScreen(this, true));
        }).dimensions(centerX - 100, startY, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(getKeyText("fovchanger.config.decrease", FovConfig.keyDecrease), button -> {
            this.client.setScreen(new KeybindScreen(this, false));
        }).dimensions(centerX - 100, startY + 25, 200, 20).build());

        stepField = new TextFieldWidget(this.textRenderer, centerX - 100, startY + 55, 90, 20, Text.translatable("fovchanger.config.step"));
        stepField.setMaxLength(10);
        stepField.setText(String.valueOf(FovConfig.fovStep));
        stepField.setChangedListener(text -> {
            try {
                double parsed = Double.parseDouble(text);
                if (parsed > 0) FovConfig.fovStep = parsed;
            } catch (NumberFormatException ignored) {}
        });
        this.addDrawableChild(stepField);

        CheckboxWidget unlimitedCheckbox = new CheckboxWidget(centerX - 5, startY + 55, 105, 20, Text.translatable("fovchanger.config.unlimited"), FovConfig.unlimitedFov) {
            @Override
            public void onPress() {
                super.onPress();
                FovConfig.unlimitedFov = this.isChecked();
            }
        };
        this.addDrawableChild(unlimitedCheckbox);

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.done"), button -> {
            try {
                FovConfig.fovStep = Double.parseDouble(stepField.getText());
            } catch (NumberFormatException ignored) {}
            FovConfig.save();
            if (this.client != null) {
                this.client.setScreen(this.parent);
            }
        }).dimensions(centerX - 100, startY + 90, 200, 20).build());
    }

    private Text getKeyText(String translationKey, int keyCode) {
        String keyName = InputUtil.fromKeyCode(keyCode, 0).getLocalizedText().getString();
        return Text.translatable(translationKey).append(": " + keyName);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        
        // --- ДОБАВЛЕНА СТРОКА АВТОРА ---
        context.drawCenteredTextWithShadow(
            this.textRenderer, 
            Text.literal("Created by jkisl1879-star").formatted(Formatting.GRAY), 
            this.width / 2, 
            40, 
            0xAAAAAA
        );
        // --------------------------------

        int centerX = this.width / 2;
        int startY = this.height / 2 - 60;
        
        context.drawTextWithShadow(this.textRenderer, Text.translatable("fovchanger.config.step_label"), centerX - 100, startY + 45, 0xAAAAAA);
        context.drawTextWithShadow(this.textRenderer, Text.translatable("fovchanger.config.mode_label"), centerX - 5, startY + 45, 0xAAAAAA);

        super.render(context, mouseX, mouseY, delta);
    }

    private class KeybindScreen extends Screen {
        private final Screen parent;
        private final boolean isIncrease;

        protected KeybindScreen(Screen parent, boolean isIncrease) {
            super(Text.translatable("fovchanger.config.press_key"));
            this.parent = parent;
            this.isIncrease = isIncrease;
        }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                this.client.setScreen(this.parent);
                return true;
            }
            if (isIncrease) FovConfig.keyIncrease = keyCode;
            else FovConfig.keyDecrease = keyCode;
            FovConfig.save();
            this.client.setScreen(this.parent);
            return true;
        }

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, float delta) {
            this.renderBackground(context);
            context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2, 0xFFFFFF);
            super.render(context, mouseX, mouseY, delta);
        }
    }
}