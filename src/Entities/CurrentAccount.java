package Entities;

import enums.AccountType;

import java.math.BigDecimal;

public class CurrentAccount extends Account{

    public static final BigDecimal interest_rate = new BigDecimal(4);
    public static final BigDecimal minimum_balance = new BigDecimal(0);

    public CurrentAccount(){
    super();
    setType(AccountType.CURRENT);
    }

    public CurrentAccount(String accountNo, BigDecimal balance, String customerId) {
        super(accountNo, balance, customerId,AccountType.CURRENT);
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
        return "{Account :" +AccountType.CURRENT+
                "AccountNo: "+getAccountNo()+
                "customerId: "+getCustomerId()+
                "balance: "+getBalance();
    }
}
