package dhyces.bebbank.accounts;

public class InfiniteAccount implements Account {
    public static final InfiniteAccount INSTANCE = new InfiniteAccount();
    private InfiniteAccount() {}

    @Override
    public double getMoney() {
        return Double.MAX_VALUE;
    }

    @Override
    public void setMoney(double money) {
    }
}
