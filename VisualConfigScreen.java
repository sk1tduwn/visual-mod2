package com.example.visualmod.client.gui;

import com.example.visualmod.client.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class VisualConfigScreen extends Screen {

    private final List<CheckboxWidget> itemChecks = new ArrayList<>();

    protected VisualConfigScreen() {
        super(Text.of("VisualMod Config"));
    }

    @Override
    protected void init() {
        super.init();
        int w = this.width;
        int h = this.height;

        // Background is drawn in render

        // Feature toggles
        int y = 20;
        CheckboxWidget esp = addDrawableChild(new CheckboxWidget(w/2 - 100, y, 200, 20, Text.of("Enable TargetESP"), Config.instance.espEnabled));
        esp.setChanged(b -> { Config.instance.espEnabled = b; Config.save(); });
        y += 24;
        CheckboxWidget fog = addDrawableChild(new CheckboxWidget(w/2 - 100, y, 200, 20, Text.of("Enable Custom Fog"), Config.instance.fogEnabled));
        fog.setChanged(b -> { Config.instance.fogEnabled = b; Config.save(); });
        y += 24;
        CheckboxWidget freelook = addDrawableChild(new CheckboxWidget(w/2 - 100, y, 200, 20, Text.of("Enable FreeLook"), Config.instance.freelookEnabled));
        freelook.setChanged(b -> { Config.instance.freelookEnabled = b; Config.save(); });
        y += 30;

        // Item toggles
        for (String name : Config.instance.enabledItemNames.keySet()) {
            boolean val = Config.instance.enabledItemNames.get(name);
            CheckboxWidget cb = new CheckboxWidget(w/2 - 100, y, 200, 20, Text.of(name), val);
            String key = name;
            cb.setChanged(b -> { Config.instance.enabledItemNames.put(key, b); Config.save(); });
            this.addDrawableChild(cb);
            itemChecks.add(cb);
            y += 22;
            if (y > h - 40) break;
        }

        // Close button
        this.addDrawableChild(new ButtonWidget(w/2 - 50, h - 30, 100, 20, Text.of("Close"), btn -> {
            Config.save();
            MinecraftClient.getInstance().setScreen(null);
        }));
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        // Semi-transparent blue background
        fill(0, 0, this.width, this.height, 0x88003399);
        super.render(mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() { return false; }
}
