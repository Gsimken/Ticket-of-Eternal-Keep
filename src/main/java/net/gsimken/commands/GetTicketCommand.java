package net.gsimken.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.gsimken.TicketOfEternalKeep;
import net.gsimken.utils.TicketUtils;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.permission.Permission;
import net.minecraft.command.permission.PermissionLevel;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;
import java.util.List;

public class GetTicketCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("toek")
                .then(ticketGiveCommand("give"))
                .then(CommandManager.literal("reload")
                        .requires(GetTicketCommand::canReload)
                        .executes(context -> reloadConfig(context.getSource()))));

        dispatcher.register(ticketGiveCommand("getticket"));
    }

    private static LiteralArgumentBuilder<ServerCommandSource> ticketGiveCommand(String commandName) {
        return CommandManager.literal(commandName)
                .requires(GetTicketCommand::canGiveTicket)
                .executes(context -> giveTickets(context.getSource(), List.of(context.getSource().getPlayerOrThrow())))
                .then(CommandManager.argument("players", EntityArgumentType.players())
                        .executes(context -> giveTickets(context.getSource(), EntityArgumentType.getPlayers(context, "players"))));
    }

    private static boolean canGiveTicket(ServerCommandSource source) {
        return Permissions.check(source, "toek.command.give")
                || Permissions.check(source, "toek.command.getticket")
                || hasOpLevelTwo(source);
    }

    private static boolean canReload(ServerCommandSource source) {
        return Permissions.check(source, "toek.command.reload") || hasOpLevelTwo(source);
    }

    private static boolean hasOpLevelTwo(ServerCommandSource source) {
        return source.getPermissions().hasPermission(new Permission.Level(PermissionLevel.GAMEMASTERS));
    }

    private static int giveTickets(ServerCommandSource source, Collection<ServerPlayerEntity> players) {
        for (ServerPlayerEntity player : players) {
            ItemStack ticket = TicketUtils.createTicket();
            boolean itemGiven = player.giveItemStack(ticket);
            if (!itemGiven) {
                player.dropItem(ticket, false);
            }
        }

        source.sendFeedback(() -> Text.translatable("command.ticket_of_eternal_keep.give_successfully"), true);
        return players.size();
    }

    private static int reloadConfig(ServerCommandSource source) {
        TicketOfEternalKeep.configManager.loadConfig();
        source.sendFeedback(() -> Text.translatable("command.ticket_of_eternal_keep.reload_successfully"), true);
        return 1;
    }
}
