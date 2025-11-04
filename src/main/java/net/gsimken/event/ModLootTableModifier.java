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
            
            // Obtener probabilidades desde la configuración
            Map<String, Float> probabilities = TicketOfEternalKeep.configManager.getConfig().getLootTableProbabilities();
            Float genericProbability = TicketOfEternalKeep.configManager.getConfig().getGenericChestProbability();
            
            // Verificar si esta loot table tiene una probabilidad configurada
            if (probabilities != null && probabilities.containsKey(idString)) {
                Float probability = probabilities.get(idString);
                if (probability != null && probability > 0.0f) {
                    tableBuilder.pool(addTicketToPool(TicketUtils.createTicket(), probability));
                }
            }
            // Agregar probabilidad genérica a otros cofres si está configurada
            else if (id.getPath().startsWith("chests/") && genericProbability != null && genericProbability > 0.0f) {
                tableBuilder.pool(addTicketToPool(TicketUtils.createTicket(), genericProbability));
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


