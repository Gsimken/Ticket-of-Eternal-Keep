package net.gsimken.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.permission.v1.PermissionNode;
import net.fabricmc.fabric.api.permission.v1.PermissionPredicates;
import net.gsimken.TicketOfEternalKeep;
import net.gsimken.utils.TicketUtils;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.Arrays;
import java.util.List;

public class GetTicketCommand {
    private static final PermissionNode<Boolean> GIVE_PERMISSION = PermissionNode.of("ticket-of-eternal-keep", "command.give");
    private static final PermissionNode<Boolean> RELOAD_PERMISSION = PermissionNode.of("ticket-of-eternal-keep", "command.reload");
    private static final PermissionNode<Boolean> CONFIG_PERMISSION = PermissionNode.of("ticket-of-eternal-keep", "command.config");

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("toek")
                .then(ticketGiveCommand("give"))
                .then(Commands.literal("reload")
                        .requires(GetTicketCommand::canReload)
                        .executes(context -> reloadConfig(context.getSource())))
                .then(configCommand()));

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
        return hasPermission(source, GIVE_PERMISSION);
    }

    private static boolean canReload(CommandSourceStack source) {
        return hasPermission(source, RELOAD_PERMISSION);
    }

    private static boolean canConfigure(CommandSourceStack source) {
        return hasPermission(source, CONFIG_PERMISSION);
    }

    private static boolean hasPermission(CommandSourceStack source, PermissionNode<Boolean> permission) {
        return PermissionPredicates.require(permission, PermissionLevel.GAMEMASTERS).test(source);
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

    private static LiteralArgumentBuilder<CommandSourceStack> configCommand() {
        return Commands.literal("config")
                .requires(GetTicketCommand::canConfigure)
                .then(Commands.literal("name")
                        .then(Commands.argument("name", StringArgumentType.greedyString())
                                .executes(context -> setTicketName(
                                        context.getSource(),
                                        StringArgumentType.getString(context, "name")
                                ))))
                .then(Commands.literal("lore")
                        .then(Commands.literal("set")
                                .then(Commands.argument("lines", StringArgumentType.greedyString())
                                        .executes(context -> setTicketLore(
                                                context.getSource(),
                                                StringArgumentType.getString(context, "lines")
                                        ))))
                        .then(Commands.literal("add")
                                .then(Commands.argument("line", StringArgumentType.greedyString())
                                        .executes(context -> addTicketLore(
                                                context.getSource(),
                                                StringArgumentType.getString(context, "line")
                                        ))))
                        .then(Commands.literal("clear")
                                .executes(context -> clearTicketLore(context.getSource()))));
    }

    private static int setTicketName(CommandSourceStack source, String name) {
        TicketOfEternalKeep.configManager.setTicketName(name);
        source.sendSuccess(() -> Component.literal("ToEK ticket name saved."), true);
        return 1;
    }

    private static int setTicketLore(CommandSourceStack source, String rawLore) {
        List<String> lore = Arrays.asList(rawLore.split("\\|", -1));
        TicketOfEternalKeep.configManager.setTicketLore(lore);
        source.sendSuccess(() -> Component.literal("ToEK ticket lore saved."), true);
        return lore.size();
    }

    private static int addTicketLore(CommandSourceStack source, String line) {
        TicketOfEternalKeep.configManager.addTicketLore(line);
        source.sendSuccess(() -> Component.literal("ToEK ticket lore line added."), true);
        return 1;
    }

    private static int clearTicketLore(CommandSourceStack source) {
        TicketOfEternalKeep.configManager.clearTicketLore();
        source.sendSuccess(() -> Component.literal("ToEK ticket lore cleared."), true);
        return 1;
    }
}
