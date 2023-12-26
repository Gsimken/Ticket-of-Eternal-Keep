package net.gsimken.utils;

import net.gsimken.TicketOfEternalKeep;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

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

}
