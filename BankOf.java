// 1. Class & Object + Encapsulation
class BankAccount {
    private String accountHolder;
    private double balance;

    public BankAccount(String accountHolder, double balance) {
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println(amount + " deposited. Current Balance = " + balance);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println(amount + " withdrawn. Current Balance = " + balance);
        } else {
            System.out.println("Insufficient Balance!");
        }
    }
}

// 2. Inheritance
class SavingsAccount extends BankAccount {
    private double interestRate = 0.05; // 5% interest

    public SavingsAccount(String accountHolder, double balance) {
        super(accountHolder, balance);
    }

    // 3. Polymorphism (Overriding)
    public void addInterest() {
        double interest = getBalance() * interestRate;
        deposit(interest);
        System.out.println("Interest Added: " + interest);
    }
}

class CurrentAccount extends BankAccount {
    private double overdraftLimit = 1000;

    public CurrentAccount(String accountHolder, double balance) {
        super(accountHolder, balance);
    }

    // Overriding withdraw method (Polymorphism)
    @Override
    public void withdraw(double amount) {
        if (amount <= getBalance() + overdraftLimit) {
            super.withdraw(amount);
        } else {
            System.out.println("Overdraft limit exceeded!");
        }
    }
}

// 4. Abstraction using Interface
interface Payment {
    void makePayment(double amount);
}

class UpiPayment implements Payment {
    public void makePayment(double amount) {
        System.out.println("Payment of Rs." + amount + " done via UPI.");
    }
}

class CardPayment implements Payment {
    public void makePayment(double amount) {
        System.out.println("Payment of Rs." + amount + " done via Debit/Credit Card.");
    }
}

// Main Class
public class BankingSystem {
    public static void main(String[] args) {
        // Object Creation
        SavingsAccount s1 = new SavingsAccount("Raviraj", 5000);
        CurrentAccount c1 = new CurrentAccount("Raj", 2000);

        // Encapsulation in action
        System.out.println("Account Holder: " + s1.getAccountHolder());
        s1.deposit(1000);
        s1.withdraw(2000);
        s1.addInterest();

        System.out.println("\nAccount Holder: " + c1.getAccountHolder());
        c1.deposit(500);
        c1.withdraw(2800); // overdraft allowed

        // Abstraction in action
        Payment p1 = new UpiPayment();
        Payment p2 = new CardPayment();
        p1.makePayment(1500);
        p2.makePayment(2500);
    }
}
