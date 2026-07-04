package com.example.visualmod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ClientMod implements ClientModInitializer {
    public static KeyBinding keyToggleESP;
    public static KeyBinding keyToggleFog;
    public static KeyBinding keyFreeLook;
    public static KeyBinding keyAutoSwap;
    public static KeyBinding keyOpenGui;

    @Override
    public void onInitializeClient() {
        keyToggleESP = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.visualmod.toggle_esp",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                "category.visualmod"
        ));

        keyToggleFog = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.visualmod.toggle_fog",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_P,
                "category.visualmod"
        ));

        keyFreeLook = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.visualmod.freelook",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F,
                "category.visualmod"
        ));

        keyAutoSwap = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.visualmod.autoswap",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "category.visualmod"
        ));

        keyOpenGui = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.visualmod.open_gui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.visualmod"
        ));

        Config.load();
        FeatureManager.init();

        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            FeatureManager.onHudRender(matrixStack, tickDelta);
        });

        WorldRenderEvents.END.register(ctx -> {
            FeatureManager.onWorldRender(ctx);
        });
    }
}
