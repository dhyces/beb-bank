package dhyces.bebbank.transactions;

import com.mojang.serialization.DataResult;
import dhyces.bebbank.accounts.Account;

public class TransactionManager {
    public static DataResult<TransactionResult> performPayment(Account payee, double amount, Account receiver) {
        if (payee.getMoney() < amount) {
            return DataResult.error("Payee's account is too low");
        }
        payee.setMoney(payee.getMoney()-amount);
        receiver.setMoney(receiver.getMoney()+amount);
        return DataResult.success(TransactionResult.SUCCESS);
    }

    public enum TransactionResult {
        SUCCESS, FAIL
    }
}
