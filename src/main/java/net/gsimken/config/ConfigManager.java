package net.gsimken.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.gsimken.TicketOfEternalKeep;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = Paths.get(FabricLoader.getInstance().getGameDir().toFile().getPath() + "/config/ToEK.json");
    private ModConfig config;

    public void loadConfig() {
        try {
            if (!Files.exists(CONFIG_PATH)) {
                saveDefaultConfig();
            }
            try (FileReader reader = new FileReader(CONFIG_PATH.toFile())) {
                config = GSON.fromJson(reader, ModConfig.class);
                // Reemplazar los símbolos de formato en la configuración
                config.setName(replaceFormatSymbols(config.getName()));
                List<String> updatedLore = config.getLore().stream()
                        .map(this::replaceFormatSymbols)
                        .collect(Collectors.toList());
                config.setLore(updatedLore);

                try{
                    Identifier itemId = new Identifier(config.getItem());
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
        config = new ModConfig();
        config.setItem("minecraft:paper");
        config.setName("&6Ticket of Eternal Keeping");
        config.setLore(List.of("&bThis ticket allows whoever carries it", "&bin the inventory to keep their items when they die.", "", "&4&lIt is consumed at death"));
        config.setCustomModelDataNumber(506);

        try (FileWriter writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(config, writer);
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

}
