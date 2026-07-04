package com.example.visualmod.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import com.example.visualmod.client.gui.VisualConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class FeatureManager {
    private static boolean espEnabled = false;
    private static boolean fogEnabled = false;
    private static boolean freelookActive = false;

    public static void init() {
        // Initialize from config
        espEnabled = Config.instance.espEnabled;
        fogEnabled = Config.instance.fogEnabled;
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (ClientMod.keyToggleESP.wasPressed()) {
                espEnabled = !espEnabled;
                Config.instance.espEnabled = espEnabled;
                Config.save();
            }
            while (ClientMod.keyToggleFog.wasPressed()) {
                fogEnabled = !fogEnabled;
                Config.instance.fogEnabled = fogEnabled;
                Config.save();
            }

            while (ClientMod.keyOpenGui.wasPressed()) {
                client.execute(() -> client.setScreen(new VisualConfigScreen()));
            }

            // FreeLook is a hold key and can be disabled in config
            freelookActive = ClientMod.keyFreeLook.isPressed() && Config.instance.freelookEnabled;

            while (ClientMod.keyAutoSwap.wasPressed()) {
                if (Config.instance.autoswapEnabled) performAutoSwap();
            }
        });
    }

    public static void onHudRender(MatrixStack matrices, float tickDelta) {
        // HUD overlays if needed
    }

    public static void onWorldRender(WorldRenderContext context) {
        if (espEnabled) renderESP(context);
        if (fogEnabled) renderCustomFog(context);
        if (freelookActive) applyFreeLook(context);
    }

    private static void renderESP(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;
        for (PlayerEntity p : client.world.getPlayers()) {
            if (p == client.player) continue;
            // Simple check: only render for players within 50 blocks
            double dx = p.getX() - client.player.getX();
            double dy = p.getY() - client.player.getY();
            double dz = p.getZ() - client.player.getZ();
            double distSq = dx*dx + dy*dy + dz*dz;
            if (distSq > 50*50) continue;

            // Draw a rotating square at player's position (very simplified placeholder)
            // A real implementation would use BufferBuilders and proper matrix transforms.
            // Here we place a simple debug outline using client.textRenderer (placeholder)
            client.textRenderer.drawWithShadow(context.matrixStack(), "[]", (float)p.getX(), (float)(p.getY()+2), 0xFF00FF);
        }
    }

    private static void renderCustomFog(WorldRenderContext context) {
        // Placeholder: a simple colored overlay to emulate sky/fog changes.
        // A production implementation would hook into fog rendering and sky color.
    }

    private static void applyFreeLook(WorldRenderContext context) {
        // Placeholder: For a full freelook you'd intercept camera orientation.
        // This stub keeps state; implementing a robust freelook requires low-level camera control.
    }

    private static void performAutoSwap() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        // Search inventory for matching enabled item names (localized display name)
        int foundSlot = -1;
        for (int i = 0; i < client.player.getInventory().size(); i++) {
            ItemStack stack = client.player.getInventory().getStack(i);
            if (stack == null) continue;
            if (stack.isEmpty()) continue;
            String display = stack.getName().getString().toLowerCase();
            for (String name : Config.instance.enabledItemNames.keySet()) {
                if (!Config.instance.enabledItemNames.get(name)) continue;
                if (display.contains(name)) {
                    foundSlot = i;
                    break;
                }
            }
            if (foundSlot >= 0) break;
        }
        if (foundSlot >= 0) {
            // Swap foundSlot with selected hotbar slot
            int hotbarIndex = client.player.getInventory().selectedSlot; // 0..8
            ItemStack a = client.player.getInventory().getStack(foundSlot);
            ItemStack b = client.player.getInventory().getStack(hotbarIndex);
            client.player.getInventory().setStack(hotbarIndex, a.copy());
            client.player.getInventory().setStack(foundSlot, b.copy());
        }
    }
}
