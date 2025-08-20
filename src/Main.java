import Entities.*;
import enums.TransactionType;
import exceptions.InsufficientBalanceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final Map<String, Customer> customers = new HashMap<>();
    private static final Map<String, Account> accounts = new HashMap<>();
    private static final List<Transaction> transactions = new ArrayList<>();

    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public static void main(String[] args) {
        System.out.println("Welcome to HDFC banking application");


        try {
            while (true)
            {
                showMainMenu();
            }
        }catch (Exception e)
        {
            System.out.println("Error :"+e.getMessage());
        }finally {
            sc.close();
        }
    }

    private static void showMainMenu(){

        System.out.println("\n==Main Menu==");
        System.out.println("1. Register New Customer");
        System.out.println("2. Create Account");
        System.out.println("3. Perform Transactions");
        System.out.println("4. View Account Details");
        System.out.println("5. View Transaction History");
        System.out.println("6. Exit");

        System.out.println("Enter your choice");

        int choice = getInput();

        switch (choice)
        {
            case 1:registerCustomer();
            break;

            case 2:
                createAccount();
                break;

            case 3:
                performTransaction();
                break;

            case 4:
                viewAccountDetails();
                break;

            case 5:
                viewTransactionHistory();
                break;
        }


    }

    private static int getInput(){
        while (true){
            try {
                return Integer.parseInt(sc.nextLine().trim());
            }catch (NumberFormatException e){
                System.out.println("Please enter valid number");
            }
        }
    }

    public static void registerCustomer(){
        System.out.println("\nRegister Customer");

        System.out.println("Enter Customer id:");
        String customerId = sc.nextLine().trim();

        if (customers.containsKey(customerId)){
            System.out.println("Customer Already exists");
        }

        System.out.println("Enter name: ");
        String name = sc.nextLine().trim();

        System.out.println("Enter email: ");
        String email = sc.nextLine().trim();
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {
            System.out.println("Valid email");
        } else {
            System.out.println("Invalid email");
            return;
        }


        System.out.println("Enter phone no: ");
        String phone_no = sc.nextLine().trim();
        String phoneRegex = "^(?:\\+91|91|0)?[6-9]\\d{9}$";
        Pattern patternP = Pattern.compile(phoneRegex);
        Matcher matcherP = pattern.matcher(phone_no);

        if (matcherP.matches()) {
            System.out.println("Valid Phone no");
        } else {
            System.out.println("Invalid Phone no");
            return;
        }

        System.out.println("Enter dob in yyyy-mm-dd: ");
        String dobStr = sc.nextLine().trim();

        LocalDate dateOfBirth;

        try {
            dateOfBirth = LocalDate.parse(dobStr,dateFormatter);
        } catch (Exception e) {
            System.out.println("Invalid date format");
            return;
        }

        System.out.println("Enter Password: ");
        String password = sc.nextLine().trim();
        Customer customer = new Customer(customerId,dateOfBirth,email,name,password,phone_no);
        customers.put(customerId,customer);
        System.out.println("Customer Registered Succesfully");
    }


    private static void createAccount(){
        System.out.println("===Creating new Account===");
        System.out.println("Please enter customer id");
        String customerId = sc.next().trim();
        sc.nextLine();

        Customer customer = customers.get(customerId);

        if (customer==null)
        {
            System.out.println("Customer not found");
            return;
        }

        System.out.println("choose Account type");
        System.out.println("1. Savings Account(6% interest rate and minimum balance 1000");
        System.out.println("2. Current Account(4% interest rate and no minimum balance");

        int typeChoice = getInput();
        Account account = null;

        System.out.println("Enter initial Balance");
        String balance = sc.nextLine().trim();

        try {
            BigDecimal initialBalance = new BigDecimal(balance);
            String accountNo = generateAccountNo();

            switch (typeChoice){
                case 1:
                    account = new SavingsAccount(accountNo,initialBalance,customerId);
                    break;
                case 2:
                    account = new CurrentAccount(accountNo, initialBalance,customerId);
                    break;
                default:
                    System.out.println("invalid account typpe");
                    return;
            }
            accounts.put(accountNo,account);
            System.out.println("Account Created Successfull AccountNo: "+accountNo);
        }catch (Exception e)
        {
            System.out.println("Initial balance error");
        }
    }

    private static String generateAccountNo(){
        return String.format("%010d", System.currentTimeMillis() % 10000000000L);
    }

    private static void performTransaction(){
        System.out.println("\n=====Perform Transaction===");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer");

        System.out.println("Select Transaction type");
        int transactionChoice = getInput();

        switch (transactionChoice){
            case 1:
                performDeposit();
                break;
            case 2:
                performWithdraw();
                break;
            case 3:
                performTransfer();
                break;
        }
    }


    private static void performDeposit(){
        System.out.println("Enter Account No: ");
        String accountNo = sc.nextLine().trim();

        Account account = accounts.get(accountNo);
        if (account==null)
        {
            System.out.println("Account not found");
            return;
        }

        System.out.println("Enter Deposit Amount");
        String amountAStr = sc.nextLine().trim();

        try {
            BigDecimal amount = new BigDecimal(amountAStr);
            account.deposit(amount);
            String transactionId = generateTransactionId();
            Transaction transaction = new Transaction(accountNo,amount, LocalDateTime.now(),transactionId, TransactionType.DEPOSIT);
            transactions.add(transaction);
            System.out.println("Deposit Done successfully! New Balance: "+account.getBalance());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Amount");
        }
    }

    private static String generateTransactionId(){
        return "HDFC_TXN"+System.currentTimeMillis();
    }


    private static void performWithdraw(){
        System.out.println("Enter Account Number: ");
        String accountNo = sc.nextLine().trim();
        Account account = accounts.get(accountNo);

        if (account==null){
            System.out.println("Account not registered");
            return;
        }

        System.out.println("Enter withdrawal amount");
        String amountStr = sc.nextLine().trim();

        try {
            BigDecimal amount = new BigDecimal(amountStr);
            account.withdraw(amount);
            String transactionId = generateTransactionId();

            Transaction transaction = new Transaction(accountNo,amount, LocalDateTime.now(),transactionId, TransactionType.WITHDRAW);
            transactions.add(transaction);
            System.out.println("Withdrawal Successfull"+ account.getBalance());

        }catch (NumberFormatException e)
        {
            System.out.println("Invalid Amount");
        }
        catch (InsufficientBalanceException e){
            System.out.println("Error "+e.getMessage());
        }
    }

    private static void performTransfer(){
        System.out.println("Enter Source account no: ");
        String fromAccountNo = sc.nextLine().trim();
        Account fromAccount = accounts.get(fromAccountNo);
        if (fromAccount==null){
            System.out.println("Source Account not found");
            return;
        }

        System.out.println("Enter Destination account no: ");
        String toAccountNoStr = sc.nextLine().trim();
        Account toAccount = accounts.get(toAccountNoStr);
        if (toAccount==null){
            System.out.println("Destination Account not found");
            return;
        }


        if (fromAccountNo.equals(toAccountNoStr))
        {
            System.out.println("cant transfer to same account");
        }

        System.out.println("Enter amount");

        String amountStr = sc.nextLine().trim();

        try {
            BigDecimal amount = new BigDecimal(amountStr);
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
            String transactionId = generateTransactionId();
            Transaction transaction = new Transaction(fromAccountNo,amount, LocalDateTime.now(),transactionId, TransactionType.WITHDRAW,toAccountNoStr);
        }catch (NumberFormatException e)
        {
            System.out.println("invalid Amount entered");
        }catch (InsufficientBalanceException e)
        {
            System.out.println("Error "+e.getMessage());
        }
    }

    private static void viewAccountDetails(){
        System.out.println("\n===Account Details===");
        System.out.println("enter account number");

        String accountNoStr = sc.nextLine().trim();
        Account account = accounts.get(accountNoStr);

        if (account==null){
            System.out.println("Account not found");
            return;
        }

        System.out.println(account.toString());

        if(account instanceof SavingsAccount)
        {
            System.out.println("Account Type: Savings Account");
            System.out.println("Interest Rate:"+account.calculateInterest());
        }
        else {
            System.out.println("Account Type: Currents Accont");
            System.out.println("Interest Rate"+account.calculateInterest());
        }

    }


    private static void viewTransactionHistory(){
        System.out.println("\n===Transaction History===");
        System.out.println("Konsa Account ka Transaction Dekhna hai");

        String AccountNo = sc.nextLine().trim();
        Account account = accounts.get(AccountNo);

        if (account==null){
            System.out.println("Account not found");
            return;
        }
        List<Transaction> accountTransaction = transactions.stream()
                .filter(t->t.getAccountNo().equals(AccountNo))
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .toList();

        if (accountTransaction.isEmpty())
        {
            System.out.println("No transactions found");
            return;
        }

        for (Transaction transaction:accountTransaction)
        {
            System.out.println(transaction.toString());
        }

     Map<TransactionType,Long> transactionSummary = accountTransaction.stream()
             .collect(Collectors.groupingBy(Transaction::getType, Collectors.counting()));

        System.out.println("===Transaction Summary===");
        transactionSummary.forEach((type,count)->
        {
            System.out.println(type.getDisplayName()+" "+count);
        });

    }

}