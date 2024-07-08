package net.gsimken.event;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.gsimken.TicketOfEternalKeep;
import net.gsimken.utils.TicketUtils;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.SetComponentsLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ModLootTableModifier {

    private static final Map<Identifier, Float> LOOT_TABLES_WITH_PROBABILITIES = new HashMap<>();

    static {
        // Ancient City
        LOOT_TABLES_WITH_PROBABILITIES.put(LootTables.ANCIENT_CITY_CHEST.getValue(), 0.10f);

        // Mineshaft
        LOOT_TABLES_WITH_PROBABILITIES.put(LootTables.ABANDONED_MINESHAFT_CHEST.getValue(), 0.03f);

        // Stronghold
        LOOT_TABLES_WITH_PROBABILITIES.put(LootTables.STRONGHOLD_LIBRARY_CHEST.getValue(), 0.05f);
        LOOT_TABLES_WITH_PROBABILITIES.put(LootTables.STRONGHOLD_CORRIDOR_CHEST.getValue(), 0.05f);
        LOOT_TABLES_WITH_PROBABILITIES.put(LootTables.STRONGHOLD_CROSSING_CHEST.getValue(), 0.05f);

        // End Cities
        LOOT_TABLES_WITH_PROBABILITIES.put(LootTables.END_CITY_TREASURE_CHEST.getValue(), 0.05f);

        // Bastions
        LOOT_TABLES_WITH_PROBABILITIES.put(LootTables.BASTION_BRIDGE_CHEST.getValue(), 0.15f);
        LOOT_TABLES_WITH_PROBABILITIES.put(LootTables.BASTION_HOGLIN_STABLE_CHEST.getValue(), 0.15f);
        LOOT_TABLES_WITH_PROBABILITIES.put(LootTables.BASTION_OTHER_CHEST.getValue(), 0.15f);
        LOOT_TABLES_WITH_PROBABILITIES.put(LootTables.BASTION_TREASURE_CHEST.getValue(), 0.15f);
    }

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            Identifier id = key.getValue();
            if (LOOT_TABLES_WITH_PROBABILITIES.containsKey(id)) {
                tableBuilder.pool(addTicketToPool(TicketUtils.createTicket(), LOOT_TABLES_WITH_PROBABILITIES.get(id)));
            }
            // Add a small chance to all chests
            else if(id.getPath().startsWith("chests/")) {
                tableBuilder.pool(addTicketToPool(TicketUtils.createTicket(), 0.005f));
            }
        });
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static LootPool.Builder addTicketToPool(ItemStack ticket, float probability) {
        LeafEntry.Builder<?> entryBuilder = ItemEntry.builder(TicketOfEternalKeep.ticketItem)
                .conditionally(RandomChanceLootCondition.builder(probability));
        ticket.getComponents().stream().forEach((component) -> entryBuilder.apply(
                SetComponentsLootFunction.builder(
                        (ComponentType) component.type(),
                        component.value()
                )
        ));
        entryBuilder.conditionally(RandomChanceLootCondition.builder(probability));
        return LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(entryBuilder);
    }
}


