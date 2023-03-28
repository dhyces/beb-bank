package dhyces.bebbank;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class BebBank implements ModInitializer {
    public static final String MODID = "bebbank";
    public static Identifier id(String id) {
        return new Identifier(MODID, id);
    }
    // Packets needed:
    // ToggleAccountUpdateC2S - Tells the server to send account updates to the client. This is a simple boolean, is sent when the player turns on/off their wallet

    // Commands:
    // pay <player> <amount> - pay another player
    // wallet <on/off> - toggles the wallet display on the screen
    // account <withdraw/deposit> <amount> - allows the player to withdraw or deposit the selected amount. Will alert the player if not possible
    // wwssadadab - grants infinite money. Permitted for player's with the appropriate permissions <https://github.com/lucko/fabric-permissions-api/blob/master/USAGE.md>

    @Override
    public void onInitialize() {

    }
}
