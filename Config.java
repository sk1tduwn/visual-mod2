package com.example.visualmod.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class Config {
    public static Config instance = new Config();

    public boolean autoswapEnabled = true;
    public boolean espEnabled = true;
    public boolean fogEnabled = true;
    public boolean freelookEnabled = true;

    public Map<String, Boolean> enabledItemNames = new LinkedHashMap<>();

    private static final Gson GSON = new Gson();

    private Config() {
        // Default items (lowercase)
        String[] items = new String[]{
                "талисман крушителя",
                "талисман карателя",
                "талисман ярости",
                "талисман мрака",
                "талисман демона",
                "сфера титана",
                "сфера гидры",
                "сфера икара",
                "сфера сатира интерфес"
        };
        for (String s : items) enabledItemNames.put(s, true);
    }

    public static Path configPath() {
        return FabricLoader.getInstance().getConfigDir().resolve("visualmod.json");
    }

    public static void load() {
        Path path = configPath();
        if (Files.exists(path)) {
            try {
                String json = Files.readString(path);
                Type type = new TypeToken<Config>(){}.getType();
                Config loaded = GSON.fromJson(json, type);
                if (loaded != null) instance = loaded;
            } catch (IOException ignored) {
            }
        } else {
            save();
        }
    }

    public static void save() {
        Path path = configPath();
        try {
            String json = GSON.toJson(instance);
            Files.createDirectories(path.getParent());
            Files.writeString(path, json);
        } catch (IOException ignored) {
        }
    }
}
