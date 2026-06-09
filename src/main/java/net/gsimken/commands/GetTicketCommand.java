package net.gsimken.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.gsimken.TicketOfEternalKeep;
import net.gsimken.utils.TicketUtils;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.List;

public class GetTicketCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("toek")
                .then(ticketGiveCommand("give"))
                .then(Commands.literal("reload")
                        .requires(GetTicketCommand::canReload)
                        .executes(context -> reloadConfig(context.getSource()))));

        dispatcher.register(ticketGiveCommand("getticket"));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> ticketGiveCommand(String commandName) {
        return Commands.literal(commandName)
                .requires(GetTicketCommand::canGiveTicket)
                .executes(context -> giveTickets(context.getSource(), List.of(context.getSource().getPlayerOrException())))
                .then(Commands.argument("players", EntityArgument.players())
                        .executes(context -> giveTickets(context.getSource(), EntityArgument.getPlayers(context, "players"))));
    }

    private static boolean canGiveTicket(CommandSourceStack source) {
        return hasOpLevelTwo(source);
    }

    private static boolean canReload(CommandSourceStack source) {
        return hasOpLevelTwo(source);
    }

    private static boolean hasOpLevelTwo(CommandSourceStack source) {
        return Commands.LEVEL_GAMEMASTERS.check(source.permissions());
    }

    private static int giveTickets(CommandSourceStack source, Collection<ServerPlayer> players) {
        for (ServerPlayer player : players) {
            ItemStack ticket = TicketUtils.createTicket();
            boolean itemGiven = player.addItem(ticket);
            if (!itemGiven) {
                player.drop(ticket, false);
            }
        }

        source.sendSuccess(() -> Component.translatable("command.ticket_of_eternal_keep.give_successfully"), true);
        return players.size();
    }

    private static int reloadConfig(CommandSourceStack source) {
        TicketOfEternalKeep.configManager.loadConfig();
        source.sendSuccess(() -> Component.translatable("command.ticket_of_eternal_keep.reload_successfully"), true);
        return 1;
    }
}
