package dhyces.bebbank.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dhyces.bebbank.accounts.Account;
import dhyces.bebbank.api.BebBankAPI;
import dhyces.bebbank.transactions.TransactionManager;
import dhyces.bebbank.world.BankState;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Optional;

public class PayCommand {
    static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("pay")
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .then(CommandManager.argument("amount", IntegerArgumentType.integer(0)).executes(PayCommand::run))));
    }
    // maybe include a debt system, so different players can literally go into debt to other players. Could be fun to
    // have an item like a 'debt-checker' that you can attach to other players and be alerted when they gain money

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        MinecraftServer server = context.getSource().getServer();
        ServerCommandSource commandSource = context.getSource();
        Optional<Account> payeeOptional = BebBankAPI.getOptionalAccount(commandSource);
        if (payeeOptional.isEmpty()) {
            return 0;
        }
        PlayerEntity playerToPay = EntityArgumentType.getPlayer(context, "player");
        Account receiver = BankState.getAccount(playerToPay);
        TransactionManager.performPayment(payeeOptional.get(), IntegerArgumentType.getInteger(context, "amount"), receiver);
        return Command.SINGLE_SUCCESS;
    }
}
