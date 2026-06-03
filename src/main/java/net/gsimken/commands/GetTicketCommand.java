package net.gsimken.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.gsimken.utils.TicketUtils;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.permission.Permission;
import net.minecraft.command.permission.PermissionLevel;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class GetTicketCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("toek")
                .then(ticketGiveCommand("give")));

        dispatcher.register(ticketGiveCommand("getticket"));
    }

    private static LiteralArgumentBuilder<ServerCommandSource> ticketGiveCommand(String commandName) {
        return CommandManager.literal(commandName)
                .requires(GetTicketCommand::canGiveTicket)
                .executes(context -> getTicket(context.getSource(), null))
                .then(CommandManager.argument("playerName", StringArgumentType.string())
                        .executes(context -> getTicket(context.getSource(), StringArgumentType.getString(context, "playerName"))));
    }

    private static boolean canGiveTicket(ServerCommandSource source) {
        return Permissions.check(source, "toek.command.give")
                || Permissions.check(source, "toek.command.getticket")
                || hasOpLevelTwo(source);
    }

    private static boolean hasOpLevelTwo(ServerCommandSource source) {
        return source.getPermissions().hasPermission(new Permission.Level(PermissionLevel.GAMEMASTERS));
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

        ItemStack ticket = TicketUtils.createTicket();
        boolean itemGiven = player.giveItemStack(ticket);
        if (!itemGiven) {
            player.dropItem(ticket, false);
        }

        source.sendFeedback(() -> Text.translatable("command.ticket_of_eternal_keep.give_successfully"), true);
        return 1;
    }
}
