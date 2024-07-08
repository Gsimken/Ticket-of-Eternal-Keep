package net.gsimken.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.gsimken.utils.TicketUtils;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class GetTicketCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("getticket")
                .requires((source) -> (Permissions.check(source ,"toek.command.getticket")) || source.hasPermissionLevel(2))
                .executes(context -> getTicket(context.getSource(), null)) // Sin argumento, da el ítem al ejecutor
                .then(CommandManager.argument("playerName", StringArgumentType.string())
                        .executes(context -> getTicket(context.getSource(), StringArgumentType.getString(context, "playerName")))
                )
        );
    }

    private static int getTicket(ServerCommandSource source, String playerName) throws CommandSyntaxException {
        ServerPlayerEntity player;

        if (playerName != null) {
            player = source.getServer().getPlayerManager().getPlayer(playerName);
            if (player == null) {
                return 0;
            }
        } else {
            player = source.getPlayerOrThrow();
        }

        ItemStack ticket = TicketUtils.createTicket(player);
        boolean itemGiven = player.giveItemStack(ticket);
        if (!itemGiven) {
            player.dropItem(ticket, false);
        }

        source.sendFeedback(() -> Text.translatable("command.ticket_of_eternal_keep.give_successfully"), true);
        return 1;
    }
}
