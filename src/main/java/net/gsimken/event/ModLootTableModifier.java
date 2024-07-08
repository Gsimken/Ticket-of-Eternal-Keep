package net.gsimken.event;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.gsimken.TicketOfEternalKeep;
import net.gsimken.utils.TicketUtils;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCustomDataLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ModLootTableModifier {

    private static final Map<Identifier, Float> LOOT_TABLES_WITH_PROBABILITIES = new HashMap<>();

    static {
        // Ancient City
        LOOT_TABLES_WITH_PROBABILITIES.put(Identifier.of("minecraft", "chests/ancient_city"), 0.10f);

        // Mineshaft
        LOOT_TABLES_WITH_PROBABILITIES.put(Identifier.of("minecraft", "chests/abandoned_mineshaft"), 0.03f);

        // Stronghold
        LOOT_TABLES_WITH_PROBABILITIES.put(Identifier.of("minecraft", "chests/stronghold_library"), 0.05f);
        LOOT_TABLES_WITH_PROBABILITIES.put(Identifier.of("minecraft", "chests/stronghold_corridor"), 0.05f);
        LOOT_TABLES_WITH_PROBABILITIES.put(Identifier.of("minecraft", "chests/stronghold_crossing"), 0.05f);

        // End Cities
        LOOT_TABLES_WITH_PROBABILITIES.put(Identifier.of("minecraft", "chests/end_city_treasure"), 0.05f);

        // Bastions
        LOOT_TABLES_WITH_PROBABILITIES.put(Identifier.of("minecraft", "chests/bastion_bridge"), 0.15f);
        LOOT_TABLES_WITH_PROBABILITIES.put(Identifier.of("minecraft", "chests/bastion_hoglin_stable"), 0.15f);
        LOOT_TABLES_WITH_PROBABILITIES.put(Identifier.of("minecraft", "chests/bastion_other"), 0.15f);
        LOOT_TABLES_WITH_PROBABILITIES.put(Identifier.of("minecraft", "chests/bastion_treasure"), 0.15f);

        // Villages
        String[] villageTypes = new String[]{"armorer", "butcher", "cartographer", "desert_house", "fisher", "fletcher", "mason", "plains_house", "savanna_house", "shepherd", "snowy_house", "taiga_house", "tannery", "temple", "toolsmith", "weaponsmith"};
        for (String type : villageTypes) {
            LOOT_TABLES_WITH_PROBABILITIES.put(Identifier.of("minecraft", "chests/village/village_" + type), 0.005f);
        }

        // Ruined Portals
        LOOT_TABLES_WITH_PROBABILITIES.put(Identifier.of("minecraft", "chests/ruined_portal"), 0.005f);

        // Shipwrecks
        LOOT_TABLES_WITH_PROBABILITIES.put(Identifier.of("minecraft", "chests/shipwreck_map"), 0.005f);
        LOOT_TABLES_WITH_PROBABILITIES.put(Identifier.of("minecraft", "chests/shipwreck_supply"), 0.005f);
        LOOT_TABLES_WITH_PROBABILITIES.put(Identifier.of("minecraft", "chests/shipwreck_treasure"), 0.005f);
    }

        public static void modifyLootTables() {
            LootTableEvents.MODIFY.register((RegistryKey<LootTable> key, LootTable.Builder tableBuilder, LootTableSource source, RegistryWrapper.WrapperLookup registries) -> {

                if (LOOT_TABLES_WITH_PROBABILITIES.containsKey(key.getRegistry())) {

                    Float probability = LOOT_TABLES_WITH_PROBABILITIES.get(key.getRegistry());
                    LootPool pool = LootPool.builder()
                            .rolls(UniformLootNumberProvider.create(0, 1))
                            .with(ItemEntry.builder(TicketOfEternalKeep.ticketItem)
                                    .conditionally(RandomChanceLootCondition.builder(probability))
                                    .apply(SetCustomDataLootFunction.builder(
                                            (NbtCompound) TicketUtils.createTicket(registries).encode(registries)
                                    ))
                            )
                            .build();
                    tableBuilder.pool(pool);
                }
            });
        }

    }


