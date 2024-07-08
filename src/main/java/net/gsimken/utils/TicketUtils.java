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
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class TicketUtils {
    public static void consumeTicket(ServerPlayerEntity player) {
        for (int i = 0; i < player.getInventory().size(); ++i) {
            ItemStack itemStack = player.getInventory().getStack(i);
            if (itemStack.getItem().equals(TicketOfEternalKeep.ticketItem)) {
                NbtComponent nbt = itemStack.get(DataComponentTypes.CUSTOM_DATA);
                if (nbt != null && nbt.contains(TicketOfEternalKeep.nbtName) && nbt.getNbt().getBoolean(TicketOfEternalKeep.nbtName)) {
                    itemStack.decrement(1);
                    break;
                }
            }
        }
    }

    public static void applyVanishCurse(ServerPlayerEntity player) {
        if(player == null || player.isInCreativeMode()){
            return;
        }
        String vanishCurse = Enchantments.VANISHING_CURSE.getValue().toString();
        for (ItemStack itemStack : player.getInventory().main) {
            for(RegistryEntry<Enchantment> enchantment : itemStack.getEnchantments().getEnchantments()){
                if(enchantment.getIdAsString().equals(vanishCurse)){
                    itemStack.setCount(0);
                }
            }
        }
    }

    public static boolean checkForTicket(ServerPlayerEntity player) {
        for (ItemStack itemStack : player.getInventory().main) {
            if (itemStack.getItem().equals(TicketOfEternalKeep.ticketItem)) {
                NbtComponent nbt = itemStack.get(DataComponentTypes.CUSTOM_DATA);
                if (nbt != null && nbt.contains(TicketOfEternalKeep.nbtName)) {
                    return nbt.getNbt().getBoolean(TicketOfEternalKeep.nbtName);
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

        CustomModelDataComponent customModelDataComponent = new CustomModelDataComponent(modConfig.getCustomModelDataNumber());
        itemStack.set(DataComponentTypes.CUSTOM_MODEL_DATA, customModelDataComponent);

        NbtCompound nbt = new NbtCompound();
        nbt.putBoolean(TicketOfEternalKeep.nbtName, true);
        itemStack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
        return itemStack;
    }
}
