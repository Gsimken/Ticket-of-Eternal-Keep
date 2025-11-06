package net.gsimken.utils;

import net.gsimken.TicketOfEternalKeep;
import net.gsimken.config.ModConfig;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collections;
import java.util.List;

public class TicketUtils {
    public static void consumeTicket(ServerPlayerEntity player) {
        for (int i = 0; i < player.getInventory().size(); ++i) {
            ItemStack itemStack = player.getInventory().getStack(i);
            if (itemStack.getItem().equals(TicketOfEternalKeep.ticketItem)) {
                NbtComponent nbt = itemStack.get(DataComponentTypes.CUSTOM_DATA);
                if (nbt != null) {
                    // Use copyNbt() method instead of deprecated getNbt()
                    NbtCompound nbtCompound = nbt.copyNbt();
                    if (nbtCompound.contains(TicketOfEternalKeep.nbtName) && nbtCompound.getBoolean(TicketOfEternalKeep.nbtName).orElse(false)) {
                        itemStack.decrement(1);
                        break;
                    }
                }
            }
        }
    }

    public static void applyVanishCurse(ServerPlayerEntity player, ServerPlayerEntity oldPlayer) {
        if(oldPlayer == null || oldPlayer.isInCreativeMode()){
            return;
        }
        String vanishCurse = Enchantments.VANISHING_CURSE.getValue().toString();
        for (int i = 0; i < player.getInventory().size(); ++i) {
            ItemStack itemStack = player.getInventory().getStack(i);
            for(RegistryEntry<Enchantment> enchantment : itemStack.getEnchantments().getEnchantments()){
                if(enchantment.getIdAsString().equals(vanishCurse)){
                    itemStack.setCount(0);
                }
            }
        }
    }

    public static boolean checkForTicket(ServerPlayerEntity player) {
        for (int i = 0; i < player.getInventory().size(); ++i) {
            ItemStack itemStack = player.getInventory().getStack(i);
            if (itemStack.getItem().equals(TicketOfEternalKeep.ticketItem)) {
                NbtComponent nbt = itemStack.get(DataComponentTypes.CUSTOM_DATA);
                if (nbt != null) {
                    // Use copyNbt() method instead of deprecated getNbt()
                    NbtCompound nbtCompound = nbt.copyNbt();
                    if (nbtCompound.contains(TicketOfEternalKeep.nbtName)) {
                        return nbtCompound.getBoolean(TicketOfEternalKeep.nbtName).orElse(false);
                    }
                }
            }
        }
        return false;
    }
    
    public static ItemStack createTicket() {
        ItemStack itemStack = new ItemStack(TicketOfEternalKeep.ticketItem);
        ModConfig modConfig = TicketOfEternalKeep.configManager.getConfig();
        String name = modConfig.getName();
        List<Text> loreLines = modConfig.getLore().stream().map(Text::of).toList();
        itemStack.set(DataComponentTypes.ITEM_NAME, Text.of(name));

        LoreComponent loreComponent = new LoreComponent(loreLines);
        itemStack.set(DataComponentTypes.LORE, loreComponent);

        List<Float> floatList = List.of((float) modConfig.getCustomModelDataNumber());
        List<Boolean> booleanList = Collections.emptyList();
        List<String> stringList = Collections.emptyList();
        List<Integer> integerList = Collections.emptyList();
        CustomModelDataComponent customModelDataComponent = new CustomModelDataComponent(floatList, booleanList, stringList, integerList);
        itemStack.set(DataComponentTypes.CUSTOM_MODEL_DATA, customModelDataComponent);

        NbtCompound nbt = new NbtCompound();
        nbt.putBoolean(TicketOfEternalKeep.nbtName, true);
        itemStack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
        return itemStack;
    }
}
