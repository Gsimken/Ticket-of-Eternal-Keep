package net.gsimken.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.gsimken.TicketOfEternalKeep;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String DEBUG_DROPS_PROPERTY = "toek.debugDrops";
    private ModConfig config;

    public void loadConfig() {
        try {
            Path configPath = configPath();
            createConfigDirectoryIfNeeded(configPath);
            if (!Files.exists(configPath)) {
                saveDefaultConfig();
            }
            try (FileReader reader = new FileReader(configPath.toFile())) {
                config = GSON.fromJson(reader, ModConfig.class);
            }

            boolean configUpdated = applyMissingDefaults(config);
            if (configUpdated) {
                saveConfigFile();
            }
            applyDebugDropsIfEnabled();
            applyFormatting(config);

            try {
                Identifier itemId = Identifier.of(config.getItem());
                TicketOfEternalKeep.ticketItem = Registries.ITEM.get(itemId);
            } catch (Error e) {
                TicketOfEternalKeep.ticketItem = Items.PAPER;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDefaultConfig() {
        try {
            createConfigDirectoryIfNeeded(configPath());
            config = createDefaultConfig();
            saveConfigFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ModConfig getConfig() {
        return config;
    }

    static ModConfig createDefaultConfig() {
        ModConfig config = new ModConfig();
        config.setItem("minecraft:paper");
        config.setName("&6Ticket of Eternal Keeping");
        config.setLore(List.of("&bThis ticket allows whoever carries it", "&bin the inventory to keep their items when they die.", "", "&4&lIt is consumed at death"));
        config.setCustomModelDataNumber(506);
        config.setLootTableProbabilities(defaultChestLootTableProbabilities());
        config.setMobLootTableProbabilities(defaultMobLootTableProbabilities());
        config.setGenericChestProbability(0.005f);
        config.setGenericMobProbability(0.0f);
        return config;
    }

    static boolean applyMissingDefaults(ModConfig config) {
        boolean configUpdated = false;
        if (config.getLootTableProbabilities() == null) {
            config.setLootTableProbabilities(defaultChestLootTableProbabilities());
            configUpdated = true;
        }
        if (config.getMobLootTableProbabilities() == null) {
            config.setMobLootTableProbabilities(defaultMobLootTableProbabilities());
            configUpdated = true;
        }
        if (config.getGenericChestProbability() == null) {
            config.setGenericChestProbability(0.005f);
            configUpdated = true;
        }
        if (config.getGenericMobProbability() == null) {
            config.setGenericMobProbability(0.0f);
            configUpdated = true;
        }
        return configUpdated;
    }

    static void applyFormatting(ModConfig config) {
        config.setName(replaceFormatSymbols(config.getName()));
        List<String> updatedLore = config.getLore().stream()
                .map(ConfigManager::replaceFormatSymbols)
                .collect(Collectors.toList());
        config.setLore(updatedLore);
    }

    private void applyDebugDropsIfEnabled() {
        if (!Boolean.getBoolean(DEBUG_DROPS_PROPERTY)) {
            return;
        }
        config.getLootTableProbabilities().put(LootTables.ANCIENT_CITY_CHEST.getValue().toString(), 1.0f);
        config.getMobLootTableProbabilities().put("minecraft:entities/zombie", 1.0f);
        TicketOfEternalKeep.LOGGER.warn("ToEK debug drops enabled: ancient_city chests and zombies drop tickets at 100%.");
    }

    private static String replaceFormatSymbols(String text) {
        return text.replace("&", "\u00A7");
    }

    private Path configPath() {
        return Paths.get(FabricLoader.getInstance().getGameDir().toFile().getPath() + "/config/ToEK.json");
    }

    private void createConfigDirectoryIfNeeded(Path configPath) throws IOException {
        Path configDir = configPath.getParent();
        if (!Files.exists(configDir)) {
            Files.createDirectories(configDir);
        }
    }

    private void saveConfigFile() {
        try (FileWriter writer = new FileWriter(configPath().toFile())) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Float> defaultChestLootTableProbabilities() {
        Map<String, Float> defaultProbabilities = new HashMap<>();
        defaultProbabilities.put(LootTables.ANCIENT_CITY_CHEST.getValue().toString(), 0.1f);
        defaultProbabilities.put(LootTables.ABANDONED_MINESHAFT_CHEST.getValue().toString(), 0.03f);
        defaultProbabilities.put(LootTables.STRONGHOLD_LIBRARY_CHEST.getValue().toString(), 0.05f);
        defaultProbabilities.put(LootTables.STRONGHOLD_CORRIDOR_CHEST.getValue().toString(), 0.05f);
        defaultProbabilities.put(LootTables.STRONGHOLD_CROSSING_CHEST.getValue().toString(), 0.05f);
        defaultProbabilities.put(LootTables.END_CITY_TREASURE_CHEST.getValue().toString(), 0.05f);
        defaultProbabilities.put(LootTables.BASTION_BRIDGE_CHEST.getValue().toString(), 0.15f);
        defaultProbabilities.put(LootTables.BASTION_HOGLIN_STABLE_CHEST.getValue().toString(), 0.15f);
        defaultProbabilities.put(LootTables.BASTION_OTHER_CHEST.getValue().toString(), 0.15f);
        defaultProbabilities.put(LootTables.BASTION_TREASURE_CHEST.getValue().toString(), 0.15f);
        defaultProbabilities.put(LootTables.NETHER_BRIDGE_CHEST.getValue().toString(), 0.05f);
        return defaultProbabilities;
    }

    private static Map<String, Float> defaultMobLootTableProbabilities() {
        Map<String, Float> defaultProbabilities = new HashMap<>();
        defaultProbabilities.put("minecraft:entities/zombie", 0.0f);
        defaultProbabilities.put("minecraft:entities/skeleton", 0.0f);
        defaultProbabilities.put("minecraft:entities/creeper", 0.0f);
        defaultProbabilities.put("minecraft:entities/spider", 0.0f);
        defaultProbabilities.put("minecraft:entities/enderman", 0.0f);
        defaultProbabilities.put("minecraft:entities/blaze", 0.0f);
        defaultProbabilities.put("minecraft:entities/wither_skeleton", 0.0f);
        return defaultProbabilities;
    }
}
