package net.gsimken.event;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.gsimken.TicketOfEternalKeep;
import net.gsimken.utils.TicketUtils;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.SetComponentsLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.Map;

public class ModLootTableModifier {

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            Identifier id = key.getValue();
            String idString = id.toString();

            Map<String, Float> probabilities = TicketOfEternalKeep.configManager.getConfig().getLootTableProbabilities();
            Map<String, Float> mobProbabilities = TicketOfEternalKeep.configManager.getConfig().getMobLootTableProbabilities();
            Float genericProbability = TicketOfEternalKeep.configManager.getConfig().getGenericChestProbability();
            Float genericMobProbability = TicketOfEternalKeep.configManager.getConfig().getGenericMobProbability();

            if (probabilities != null && probabilities.containsKey(idString)) {
                addConfiguredTicketPool(tableBuilder, probabilities.get(idString));
            } else if (mobProbabilities != null && mobProbabilities.containsKey(idString)) {
                addConfiguredTicketPool(tableBuilder, mobProbabilities.get(idString));
            } else if (id.getPath().startsWith("chests/")) {
                addConfiguredTicketPool(tableBuilder, genericProbability);
            } else if (id.getPath().startsWith("entities/")) {
                addConfiguredTicketPool(tableBuilder, genericMobProbability);
            }
        });
    }

    private static void addConfiguredTicketPool(net.minecraft.loot.LootTable.Builder tableBuilder, Float probability) {
        if (probability != null && probability > 0.0f) {
            tableBuilder.pool(addTicketToPool(TicketUtils.createTicket(), probability));
        }
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
        return LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(entryBuilder);
    }
}
