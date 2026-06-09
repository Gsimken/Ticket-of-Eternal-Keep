package net.gsimken.event;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.gsimken.TicketOfEternalKeep;
import net.gsimken.utils.TicketUtils;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Map;

public class ModLootTableModifier {

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            Identifier id = key.identifier();
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

    private static void addConfiguredTicketPool(net.minecraft.world.level.storage.loot.LootTable.Builder tableBuilder, Float probability) {
        if (probability != null && probability > 0.0f) {
            tableBuilder.pool(addTicketToPool(TicketUtils.createTicket(), probability).build());
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static LootPool.Builder addTicketToPool(ItemStack ticket, float probability) {
        LootPoolSingletonContainer.Builder<?> entryBuilder = LootItem.lootTableItem(TicketOfEternalKeep.ticketItem)
                .when(LootItemRandomChanceCondition.randomChance(probability));
        ticket.getComponents().stream().forEach((component) -> entryBuilder.apply(
                SetComponentsFunction.setComponent(
                        (DataComponentType) component.type(),
                        component.value()
                )
        ));
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(entryBuilder);
    }
}
