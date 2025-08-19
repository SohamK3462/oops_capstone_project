package Entities;

import enums.AccountType;
import exceptions.InsufficientBalanceException;
import exceptions.InvalidDepositValue;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Account {

    private String accountNo;
    private String customerId;
    private AccountType type;
    private BigDecimal balance;

    public Account(){

    }

    public Account(String accountNo, BigDecimal balance, String customerId, AccountType type) {
        this.accountNo = accountNo;
        this.balance = balance;
        this.customerId = customerId;
        this.type = type;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNo='" + accountNo + '\'' +
                ", customerId='" + customerId + '\'' +
                ", type=" + type +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null)
        {
            return false;
        }
        if(getClass() != obj.getClass())
        {
            return false;
        }

        Account account = (Account) obj;
        return Objects.equals(accountNo,account.accountNo);
    }


    @Override
    public int hashCode() {
        return Objects.hash(accountNo);
    }

    public synchronized void deposit(BigDecimal amount)
    {
        if (amount.compareTo(BigDecimal.ZERO)<=0){
             throw new InvalidDepositValue("Value should be greater than zero");
        }
        this.balance = this.balance.add(amount);
    }

    public synchronized void withdraw(BigDecimal amount){
        if(amount.compareTo(balance)>0)
        {
            throw new InsufficientBalanceException("Insufficient Balance");
        }
        this.balance = this.balance.subtract(amount);
    }

    public abstract BigDecimal getInterestRate();
    public abstract BigDecimal getMinimumBalance();


    public BigDecimal calculateInterest(){
        return balance.multiply(getInterestRate().divide(new BigDecimal(100)));

    }

}
