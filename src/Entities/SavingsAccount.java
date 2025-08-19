package Entities;

import enums.AccountType;

import java.math.BigDecimal;

public class SavingsAccount extends Account{

    public static final BigDecimal interest_rate = new BigDecimal(6);
    public static final BigDecimal minimum_balance = new BigDecimal(1000);

    public SavingsAccount() {
        super();
        setType(AccountType.SAVINGS);
    }

    public SavingsAccount(String accountNo, BigDecimal balance, String customerId) {
        super(accountNo, balance, customerId, AccountType.SAVINGS);
    }

    @Override
    public BigDecimal getInterestRate() {
        return interest_rate;
    }

    @Override
    public BigDecimal getMinimumBalance() {
        return minimum_balance;
    }

    @Override
    public String toString() {
        return "{Account :" +AccountType.SAVINGS+
                "AccountNo: "+getAccountNo()+
                "customerId: "+getCustomerId()+
        "balance: "+getBalance();
    }
}
