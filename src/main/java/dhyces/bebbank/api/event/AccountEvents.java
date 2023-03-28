package dhyces.bebbank.api.event;

import dhyces.bebbank.accounts.RegularAccount;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

// TODO: write javadoc
public class AccountEvents {

    public static final Event<CreateAccount> CREATE = EventFactory.createArrayBacked(CreateAccount.class, (baseAccount, player) -> baseAccount, callbacks -> (baseAccount, player) -> {
        for (CreateAccount createAccount : callbacks) {
            createAccount.createAccount(baseAccount, player);
        }
        return baseAccount;
    });


    public interface CreateAccount {
        RegularAccount createAccount(RegularAccount baseAccount, PlayerEntity player);
    }
}
