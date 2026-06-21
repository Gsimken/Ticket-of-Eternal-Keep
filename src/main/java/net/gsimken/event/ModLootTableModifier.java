package net.gsimken.event;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.gsimken.TicketOfEternalKeep;
import net.gsimken.config.ModConfig;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.ItemLore;

import java.util.Collections;
import java.util.List;
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
            tableBuilder.pool(addTicketToPool(probability).build());
        }
    }

    private static LootPool.Builder addTicketToPool(float probability) {
        LootPoolSingletonContainer.Builder<?> entryBuilder = LootItem.lootTableItem(TicketOfEternalKeep.ticketItem)
                .when(LootItemRandomChanceCondition.randomChance(probability));
        applyTicketComponents(entryBuilder);
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(entryBuilder);
    }

    private static void applyTicketComponents(LootPoolSingletonContainer.Builder<?> entryBuilder) {
        ModConfig modConfig = TicketOfEternalKeep.configManager.getConfig();
        List<Component> loreLines = modConfig.getLore().stream()
                .map(line -> (Component) Component.literal(line))
                .toList();
        CustomModelData customModelData = new CustomModelData(
                List.of((float) modConfig.getCustomModelDataNumber()),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList()
        );
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean(TicketOfEternalKeep.nbtName, true);

        entryBuilder.apply(SetComponentsFunction.setComponent(DataComponents.ITEM_NAME, Component.literal(modConfig.getName())));
        entryBuilder.apply(SetComponentsFunction.setComponent(DataComponents.LORE, new ItemLore(loreLines)));
        entryBuilder.apply(SetComponentsFunction.setComponent(DataComponents.CUSTOM_MODEL_DATA, customModelData));
        entryBuilder.apply(SetComponentsFunction.setComponent(DataComponents.CUSTOM_DATA, CustomData.of(nbt)));
    }
}
