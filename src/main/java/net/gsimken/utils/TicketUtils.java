package net.gsimken.utils;

import net.gsimken.TicketOfEternalKeep;
import net.gsimken.config.ModConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class TicketUtils {
    public static void consumeTicket(ServerPlayerEntity player) {
        for (int i = 0; i < player.getInventory().size(); ++i) {
            ItemStack itemStack = player.getInventory().getStack(i);
            if (itemStack.getItem().equals(TicketOfEternalKeep.ticketItem)) {
                NbtCompound nbt = itemStack.getNbt();
                if (nbt != null && nbt.contains(TicketOfEternalKeep.nbtName) && nbt.getBoolean(TicketOfEternalKeep.nbtName)) {
                    itemStack.decrement(1); // Reduce la cantidad del ítem en uno
                    break;
                }
            }
        }
    }
    public static boolean checkForTicket(ServerPlayerEntity player) {
        for (ItemStack itemStack : player.getInventory().main) {
            if (itemStack.getItem().equals(TicketOfEternalKeep.ticketItem)) {
                NbtCompound nbt = itemStack.getNbt();
                if (nbt != null && nbt.contains(TicketOfEternalKeep.nbtName) && nbt.getBoolean(TicketOfEternalKeep.nbtName)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static ItemStack createTicket() {
        ItemStack itemStack = new ItemStack(TicketOfEternalKeep.ticketItem);
        itemStack.setNbt(ticketNBTs());
        return itemStack;
    }
    public static NbtCompound ticketNBTs() {
        NbtCompound nbt = new NbtCompound();
        NbtCompound displayNbt = new NbtCompound();

        // Obtener el nombre y el lore de la configuración
        ModConfig modConfig = TicketOfEternalKeep.configManager.getConfig();
        String name = modConfig.getName();
        List<String> loreLines = modConfig.getLore();

        // Configurar el nombre (usando tu formato existente)
        displayNbt.putString("Name", String.format("{'text':'%s'}", name));

        // Añadir las líneas de lore
        NbtList loreList = new NbtList();
        for (String loreLine : loreLines) {
            loreList.add(NbtString.of(String.format("{'text':'%s'}", loreLine)));
        }
        displayNbt.put("Lore", loreList);

        nbt.put("display", displayNbt);
        nbt.putBoolean(TicketOfEternalKeep.nbtName, true);
        nbt.putInt("HideFlags", 1);
        nbt.putInt("CustomModelData", modConfig.getCustomModelDataNumber());

        // Añadir el encantamiento Curse of Binding (si es necesario)
        NbtList enchList = new NbtList();
        NbtCompound ench = new NbtCompound();
        ench.putString("id", "minecraft:binding_curse");
        ench.putInt("lvl", 1);
        enchList.add(ench);
        nbt.put("Enchantments", enchList);
        return nbt;
    }
}
