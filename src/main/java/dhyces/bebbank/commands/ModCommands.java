package dhyces.bebbank.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            PayCommand.register(dispatcher);
        });
    }
}
