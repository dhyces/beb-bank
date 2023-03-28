package dhyces.bebbank.accounts;

import com.mojang.serialization.Codec;

public class RegularAccount implements Account {
    public static final Codec<RegularAccount> CODEC = Codec.DOUBLE.xmap(RegularAccount::new, RegularAccount::getMoney);

    private double money;

    public RegularAccount(double money) {
        this.money = money;
    }

    @Override
    public double getMoney() {
        return money;
    }

    @Override
    public void setMoney(double money) {
        this.money = money;
    }
}
