package net.gsimken.config;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigManagerTest {
    @Test
    void defaultConfigKeepsExpectedTicketIdentityAndDropRules() {
        ModConfig config = ConfigManager.createDefaultConfig();

        assertEquals("minecraft:paper", config.getItem());
        assertEquals("&6Ticket of Eternal Keeping", config.getName());
        assertEquals(506, config.getCustomModelDataNumber());
        assertEquals(0.005f, config.getGenericChestProbability());
        assertEquals(0.0f, config.getGenericMobProbability());
        assertEquals(0.1f, config.getLootTableProbabilities().get("minecraft:chests/ancient_city"));
        assertEquals(0.15f, config.getLootTableProbabilities().get("minecraft:chests/bastion_treasure"));
        assertEquals(0.0f, config.getMobLootTableProbabilities().get("minecraft:entities/zombie"));
    }

    @Test
    void missingPostReleaseConfigFieldsAreBackfilled() {
        ModConfig config = new ModConfig();
        config.setItem("minecraft:paper");
        config.setName("&6Ticket");
        config.setLore(List.of("&bLine"));
        config.setCustomModelDataNumber(506);

        boolean updated = ConfigManager.applyMissingDefaults(config);

        assertTrue(updated);
        assertNotNull(config.getLootTableProbabilities());
        assertNotNull(config.getMobLootTableProbabilities());
        assertEquals(0.005f, config.getGenericChestProbability());
        assertEquals(0.0f, config.getGenericMobProbability());
    }

    @Test
    void completeConfigDoesNotGetMarkedAsChanged() {
        ModConfig config = ConfigManager.createDefaultConfig();

        boolean updated = ConfigManager.applyMissingDefaults(config);

        assertFalse(updated);
    }

    @Test
    void ampersandColorCodesAreConvertedForRuntimeText() {
        ModConfig config = ConfigManager.createDefaultConfig();

        ConfigManager.applyFormatting(config);

        assertEquals("\u00A76Ticket of Eternal Keeping", config.getName());
        assertEquals("\u00A7bThis ticket allows whoever carries it", config.getLore().get(0));
        assertEquals("\u00A74\u00A7lIt is consumed at death", config.getLore().get(3));
    }
}
