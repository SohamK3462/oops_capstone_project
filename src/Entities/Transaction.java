package Entities;

import enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {
    private String transactionId;
    private BigDecimal amount;
    private String accountNo;
    private LocalDateTime timestamp;
    private TransactionType type;
    private String toAccountNo;
    public Transaction(){

    }

    public Transaction(String accountNo, BigDecimal amount, LocalDateTime timestamp, String transactionId, TransactionType type) {
        this.accountNo = accountNo;
        this.amount = amount;
        this.timestamp = timestamp;
        this.transactionId = transactionId;
        this.type = type;
    }

    public Transaction(String accountNo, BigDecimal amount, LocalDateTime timestamp, String transactionId, TransactionType type,String toAccountNo) {
        this.accountNo = accountNo;
        this.amount = amount;
        this.timestamp = timestamp;
        this.transactionId = transactionId;
        this.type = type;
        this.toAccountNo = toAccountNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "accountNo='" + accountNo + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", type=" + type +
                '}';
    }

    @Override
    public int hashCode(){
        return Objects.hash(transactionId);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if (this==obj){
            return  true;
        }
        Transaction transaction = (Transaction) obj;
        return Objects.equals(transactionId,transaction.transactionId);
    }
}
