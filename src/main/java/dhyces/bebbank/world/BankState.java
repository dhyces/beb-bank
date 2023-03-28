package dhyces.bebbank.world;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import dhyces.bebbank.accounts.Account;
import dhyces.bebbank.accounts.InfiniteAccount;
import dhyces.bebbank.accounts.RegularAccount;
import dhyces.bebbank.api.BebBankAPI;
import dhyces.bebbank.api.event.AccountEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.PersistentState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BankState extends PersistentState {
    public static final Codec<List<Pair<UUID, RegularAccount>>> CODEC = Codec.list(Codec.pair(Codecs.UUID, RegularAccount.CODEC)).fieldOf("bank_accounts").codec();

    private final Map<UUID, RegularAccount> playerToAccount = new HashMap<>();
    // list of account listeners. Are alerted when an account is changed. Maybe better to exist on the actual account?

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        DataResult<NbtElement> result = CODEC.encode(playerToAccount.entrySet().stream().map(entry -> Pair.of(entry.getKey(), entry.getValue())).toList(), NbtOps.INSTANCE, nbt);
        if (result.error().isPresent()) {
            throw new IllegalStateException(result.error().get().message());
        }
        return (NbtCompound) result.result().get();
    }

    public static BankState createFromNbt(NbtCompound compound) {
        BankState state = new BankState();
        if (!compound.contains("bank_accounts")) {
            return state;
        }
        DataResult<List<Pair<UUID, RegularAccount>>> result = CODEC.parse(NbtOps.INSTANCE, compound);
        if (result.error().isPresent()) {
            throw new IllegalStateException(result.error().get().message());
        }
        result.result().get().forEach(pair -> state.playerToAccount.put(pair.getFirst(), pair.getSecond()));
        return state;
    }

    public static BankState getBankState(MinecraftServer server) {
        BankState state = server.getOverworld().getPersistentStateManager().getOrCreate(BankState::createFromNbt, BankState::new, "bebbank:bank");
        state.markDirty();
        return state;
    }

    public static Account getAccount(PlayerEntity player) {
        if (BebBankAPI.hasInfiniteAccount(player)) {
            return InfiniteAccount.INSTANCE;
        }
        return getBankState(player.world.getServer()).playerToAccount.computeIfAbsent(player.getUuid(), uuid -> AccountEvents.CREATE.invoker().createAccount(new RegularAccount(0), player));
    }
}
