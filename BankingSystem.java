import java.io.*;
import java.util.Scanner;

class Account {
    private String accountHolderName;
    private String accountNumber;
    private double balance;

    // Constructor to initialize account
    public Account(String accountHolderName, String accountNumber, double initialBalance) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    // Deposit money
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Withdraw money
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }

    // Display balance
    public void displayBalance() {
        System.out.println("Current Balance: $" + balance);
    }

    // Save account information to file
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(accountNumber + ".txt"))) {
            writer.write(accountHolderName + "\n");
            writer.write(accountNumber + "\n");
            writer.write(String.valueOf(balance));
            System.out.println("Account information saved.");
        } catch (IOException e) {
            System.out.println("Error saving account information.");
        }
    }

    // Load account information from file
    public static Account loadFromFile(String accountNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(accountNumber + ".txt"))) {
            String name = reader.readLine();
            String accNumber = reader.readLine();
            double balance = Double.parseDouble(reader.readLine());
            return new Account(name, accNumber, balance);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading account information.");
            return null;
        }
    }
}

public class BankingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Simple Banking System");
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();
        System.out.print("Enter account number: ");
        String accNumber = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();

        Account account = new Account(name, accNumber, initialBalance);
        account.saveToFile();

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    account.saveToFile();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    account.saveToFile();
                    break;
                case 3:
                    account.displayBalance();
                    break;
                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
