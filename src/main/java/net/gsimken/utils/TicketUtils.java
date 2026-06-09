package net.gsimken.utils;

import net.gsimken.TicketOfEternalKeep;
import net.gsimken.config.ModConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Collections;
import java.util.List;

public class TicketUtils {
    public static void consumeTicket(ServerPlayer player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack.getItem().equals(TicketOfEternalKeep.ticketItem)) {
                CustomData nbt = itemStack.get(DataComponents.CUSTOM_DATA);
                if (nbt != null) {
                    CompoundTag nbtCompound = nbt.copyTag();
                    if (nbtCompound.contains(TicketOfEternalKeep.nbtName) && nbtCompound.getBoolean(TicketOfEternalKeep.nbtName).orElse(false)) {
                        itemStack.shrink(1);
                        break;
                    }
                }
            }
        }
    }

    public static void applyVanishCurse(ServerPlayer player, boolean isInCreativeMode) {
        if(isInCreativeMode){
            return;
        }
        String vanishCurse = Enchantments.VANISHING_CURSE.identifier().toString();
        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack itemStack = player.getInventory().getItem(i);
            for(Holder<Enchantment> enchantment : itemStack.getEnchantments().keySet()){
                if(enchantment.getRegisteredName().equals(vanishCurse)){
                    itemStack.setCount(0);
                }
            }
        }
    }

    public static boolean checkForTicket(ServerPlayer player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack.getItem().equals(TicketOfEternalKeep.ticketItem)) {
                CustomData nbt = itemStack.get(DataComponents.CUSTOM_DATA);
                if (nbt != null) {
                    CompoundTag nbtCompound = nbt.copyTag();
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
        List<Component> loreLines = modConfig.getLore().stream().map(line -> (Component) Component.literal(line)).toList();
        itemStack.set(DataComponents.ITEM_NAME, Component.literal(name));

        ItemLore loreComponent = new ItemLore(loreLines);
        itemStack.set(DataComponents.LORE, loreComponent);

        List<Float> floatList = List.of((float) modConfig.getCustomModelDataNumber());
        List<Boolean> booleanList = Collections.emptyList();
        List<String> stringList = Collections.emptyList();
        List<Integer> integerList = Collections.emptyList();
        CustomModelData customModelDataComponent = new CustomModelData(floatList, booleanList, stringList, integerList);
        itemStack.set(DataComponents.CUSTOM_MODEL_DATA, customModelDataComponent);

        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean(TicketOfEternalKeep.nbtName, true);
        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
        return itemStack;
    }
}
