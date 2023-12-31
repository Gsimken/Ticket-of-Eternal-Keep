package net.gsimken.event;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.gsimken.TicketOfEternalKeep;
import net.gsimken.config.ModConfig;
import net.gsimken.utils.TicketUtils;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetNbtLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModLootTableModifier {

    private static final Map<Identifier, Float> LOOT_TABLES_WITH_PROBABILITIES = new HashMap<>();

    static {
        // Ancient City
        LOOT_TABLES_WITH_PROBABILITIES.put(new Identifier("minecraft", "chests/ancient_city"), 0.10f);

        // Mineshaft
        LOOT_TABLES_WITH_PROBABILITIES.put(new Identifier("minecraft", "chests/abandoned_mineshaft"), 0.03f);

        // Stronghold
        LOOT_TABLES_WITH_PROBABILITIES.put(new Identifier("minecraft", "chests/stronghold_library"), 0.05f);
        LOOT_TABLES_WITH_PROBABILITIES.put(new Identifier("minecraft", "chests/stronghold_corridor"), 0.05f);
        LOOT_TABLES_WITH_PROBABILITIES.put(new Identifier("minecraft", "chests/stronghold_crossing"), 0.05f);

        // End Cities
        LOOT_TABLES_WITH_PROBABILITIES.put(new Identifier("minecraft", "chests/end_city_treasure"), 0.05f);

        // Bastions
        LOOT_TABLES_WITH_PROBABILITIES.put(new Identifier("minecraft", "chests/bastion_bridge"), 0.15f);
        LOOT_TABLES_WITH_PROBABILITIES.put(new Identifier("minecraft", "chests/bastion_hoglin_stable"), 0.15f);
        LOOT_TABLES_WITH_PROBABILITIES.put(new Identifier("minecraft", "chests/bastion_other"), 0.15f);
        LOOT_TABLES_WITH_PROBABILITIES.put(new Identifier("minecraft", "chests/bastion_treasure"), 0.15f);

        // Villages
        String[] villageTypes = new String[]{"armorer", "butcher", "cartographer", "desert_house", "fisher", "fletcher", "mason", "plains_house", "savanna_house", "shepherd", "snowy_house", "taiga_house", "tannery", "temple", "toolsmith", "weaponsmith"};
        for (String type : villageTypes) {
            LOOT_TABLES_WITH_PROBABILITIES.put(new Identifier("minecraft", "chests/village/village_" + type), 0.005f);
        }

        // Ruined Portals
        LOOT_TABLES_WITH_PROBABILITIES.put(new Identifier("minecraft", "chests/ruined_portal"), 0.005f);

        // Shipwrecks
        LOOT_TABLES_WITH_PROBABILITIES.put(new Identifier("minecraft", "chests/shipwreck_map"), 0.005f);
        LOOT_TABLES_WITH_PROBABILITIES.put(new Identifier("minecraft", "chests/shipwreck_supply"), 0.005f);
        LOOT_TABLES_WITH_PROBABILITIES.put(new Identifier("minecraft", "chests/shipwreck_treasure"), 0.005f);
    }

        public static void modifyLootTables() {

            LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {

                if (LOOT_TABLES_WITH_PROBABILITIES.containsKey(id)) {

                    Float probability = LOOT_TABLES_WITH_PROBABILITIES.get(id);

                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(UniformLootNumberProvider.create(0, 1))
                            .with(ItemEntry.builder(TicketOfEternalKeep.ticketItem)
                                    .conditionally(RandomChanceLootCondition.builder(probability))
                                    .apply(SetNbtLootFunction.builder(TicketUtils.ticketNBTs())))
                            ;
                    tableBuilder.pool(poolBuilder.build());
                }
            });
        }

    }


