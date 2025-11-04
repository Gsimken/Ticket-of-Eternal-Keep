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
    private static final Path CONFIG_PATH = Paths.get(FabricLoader.getInstance().getGameDir().toFile().getPath() + "/config/ToEK.json");
    private ModConfig config;

    public void loadConfig() {
        try {
            createConfigDirectoryIfNeeded();
            if (!Files.exists(CONFIG_PATH)) {
                saveDefaultConfig();
            }
            try (FileReader reader = new FileReader(CONFIG_PATH.toFile())) {
                config = GSON.fromJson(reader, ModConfig.class);
                
                // Validar y aplicar valores por defecto si faltan
                if (config.getLootTableProbabilities() == null) {
                    Map<String, Float> defaultProbabilities = new HashMap<>();
                    defaultProbabilities.put(LootTables.ANCIENT_CITY_CHEST.getValue().toString(), 0.10f);
                    defaultProbabilities.put(LootTables.ABANDONED_MINESHAFT_CHEST.getValue().toString(), 0.03f);
                    defaultProbabilities.put(LootTables.STRONGHOLD_LIBRARY_CHEST.getValue().toString(), 0.05f);
                    defaultProbabilities.put(LootTables.STRONGHOLD_CORRIDOR_CHEST.getValue().toString(), 0.05f);
                    defaultProbabilities.put(LootTables.STRONGHOLD_CROSSING_CHEST.getValue().toString(), 0.05f);
                    defaultProbabilities.put(LootTables.END_CITY_TREASURE_CHEST.getValue().toString(), 0.05f);
                    defaultProbabilities.put(LootTables.BASTION_BRIDGE_CHEST.getValue().toString(), 0.15f);
                    defaultProbabilities.put(LootTables.BASTION_HOGLIN_STABLE_CHEST.getValue().toString(), 0.15f);
                    defaultProbabilities.put(LootTables.BASTION_OTHER_CHEST.getValue().toString(), 0.15f);
                    defaultProbabilities.put(LootTables.BASTION_TREASURE_CHEST.getValue().toString(), 0.15f);
                    config.setLootTableProbabilities(defaultProbabilities);
                }
                if (config.getGenericChestProbability() == null) {
                    config.setGenericChestProbability(0.005f);
                }
                
                // Reemplazar los símbolos de formato en la configuración
                config.setName(replaceFormatSymbols(config.getName()));
                List<String> updatedLore = config.getLore().stream()
                        .map(this::replaceFormatSymbols)
                        .collect(Collectors.toList());
                config.setLore(updatedLore);

                try{
                    Identifier itemId = Identifier.of(config.getItem());
                    TicketOfEternalKeep.ticketItem = Registries.ITEM.get(itemId);
                }catch (Error e){
                    TicketOfEternalKeep.ticketItem = Items.PAPER;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDefaultConfig() {
        try {
            createConfigDirectoryIfNeeded();
            config = new ModConfig();
            config.setItem("minecraft:paper");
            config.setName("&6Ticket of Eternal Keeping");
            config.setLore(List.of("&bThis ticket allows whoever carries it", "&bin the inventory to keep their items when they die.", "", "&4&lIt is consumed at death"));
            config.setCustomModelDataNumber(506);
            
            // Configurar probabilidades por defecto de las loot tables
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
            config.setLootTableProbabilities(defaultProbabilities);
            config.setGenericChestProbability(0.005f);

            try (FileWriter writer = new FileWriter(CONFIG_PATH.toFile())) {
                GSON.toJson(config, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ModConfig getConfig() {
        return config;
    }
    private String replaceFormatSymbols(String text) {
        return text.replace("&", "\u00A7");
    }
    private void createConfigDirectoryIfNeeded() throws IOException {
        Path configDir = CONFIG_PATH.getParent();
        if (!Files.exists(configDir)) {
            Files.createDirectories(configDir);
        }
    }

}
