package dhyces.bebbank;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class BebBank implements ModInitializer {
    public static final String MODID = "bebbank";
    public static Identifier id(String id) {
        return new Identifier(MODID, id);
    }

    @Override
    public void onInitialize() {

    }
}
