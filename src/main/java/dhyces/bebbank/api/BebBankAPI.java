package dhyces.bebbank.api;

import dhyces.bebbank.BebBankPermissions;
import dhyces.bebbank.accounts.Account;
import dhyces.bebbank.accounts.InfiniteAccount;
import dhyces.bebbank.world.BankState;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Optional;

public class BebBankAPI {
    public static boolean hasInfiniteAccount(PlayerEntity player) {
        return Permissions.check(player, BebBankPermissions.INFINITE_ACCOUNT);
    }

    public static Optional<Account> getOptionalAccount(ServerCommandSource source) {
        if (source.isExecutedByPlayer()) {
            return Optional.of(BankState.getAccount(source.getPlayer()));
        }
        return Permissions.check(source, BebBankPermissions.INFINITE_ACCOUNT) ? Optional.of(InfiniteAccount.INSTANCE) : Optional.empty();
    }
}
