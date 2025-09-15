import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;


public class BankingApp {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.mainMenu();
    }
}

/**
 * Account class: encapsulates a bank account's data and operations.
 */
class Account {
    private static int nextAccountNumber = 1001; // auto-incremented account numbers

    private final int accountNumber;
    private final String accountHolderName;
    private double balance;
    private String email;
    private String phoneNumber;

    public Account(String accountHolderName, double initialDeposit, String email, String phoneNumber) {
        this.accountNumber = nextAccountNumber++;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
// Deposit: amount must be positive
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit failed: amount must be positive.");
            return false;
        }
        balance += amount;
        System.out.println("Deposit successful. New balance: " + formatMoney(balance));
        return true;
    }

    // Withdraw: amount must be positive and <= balance
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal failed: amount must be positive.");
            return false;
        }
        if (amount > balance) {
            System.out.println("Withdrawal failed: insufficient balance.");
            return false;
        }
        balance -= amount;
        System.out.println("Withdrawal successful. New balance: " + formatMoney(balance));
        return true;
    }
    public void displayAccountDetails() {
        System.out.println("----------------------------------------");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Holder Name    : " + accountHolderName);
        System.out.println("Balance        : " + formatMoney(balance));
        System.out.println("Email          : " + email);
        System.out.println("Phone Number   : " + phoneNumber);
        System.out.println("----------------------------------------");
    }

    public void updateContactDetails(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        System.out.println("Contact details updated successfully.");
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    private String formatMoney(double amount) {
        DecimalFormat df = new DecimalFormat("0.00");
        return "₹" + df.format(amount); // Using rupee symbol as example; change if needed
    }
}

/**
 * UserInterface class: handles user interaction and manages multiple accounts
 * using a Java array (resizes when full).
 */
class UserInterface {
    private Account[] accounts;
    private int accountCount;
    private static final Scanner scanner = new Scanner(System.in);

    private static final int INITIAL_CAPACITY = 5;

    public UserInterface() {
        this.accounts = new Account[INITIAL_CAPACITY];
        this.accountCount = 0;
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public void mainMenu() {
        System.out.println("Welcome to the Banking Application!");
        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Enter your choice: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1 -> createAccount();
                    case 2 -> performDeposit();
                    case 3 -> performWithdrawal();
                    case 4 -> showAccountDetails();
                    case 5 -> updateContact();
                    case 6 -> running = false;
                    default -> System.out.println("Invalid choice!");
}
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a valid integer choice.");
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. Create a new account");
        System.out.println("2. Deposit money");
        System.out.println("3. Withdraw money");
        System.out.println("4. View account details");
        System.out.println("5. Update contact details");
        System.out.println("6. Exit");
    }

    public void createAccount() {
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty. Account creation aborted.");
            return;
        }

    double initialDeposit;
        System.out.print("Enter initial deposit amount: ");
        try {
            initialDeposit = Double.parseDouble(scanner.nextLine().trim());
            if (initialDeposit < 0) {
                System.out.println("Initial deposit cannot be negative. Account creation aborted.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Account creation aborted.");
            return;
        }

        System.out.print("Enter email address: ");
        String email = scanner.nextLine().trim();
        if (!isValidEmail(email)) {
            System.out.println("Invalid email format. Account creation aborted.");
            return;
        }

        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine().trim();
        if (!isValidPhone(phone)) {
            System.out.println("Invalid phone number. Account creation aborted.");
            return;
        }

        ensureCapacity();
        Account acct = new Account(name, initialDeposit, email, phone);
        accounts[accountCount++] = acct;
        System.out.println("Account created successfully with Account Number: " + acct.getAccountNumber());
    }

    public void performDeposit() {
        System.out.print("Enter account number: ");
        int accNum = readIntFromLine();
        if (accNum < 0) return;
        Account acct = findAccount(accNum);
        if (acct == null) {
            System.out.println("Account not found.");
            return;
        }
        System.out.print("Enter amount to deposit: ");
        double amt = readDoubleFromLine();
        if (Double.isNaN(amt)) return;
        acct.deposit(amt);
    }

    public void performWithdrawal() {
        System.out.print("Enter account number: ");
        int accNum = readIntFromLine();
        if (accNum < 0) return;
        Account acct = findAccount(accNum);
        if (acct == null) {
            System.out.println("Account not found.");
            return;
        }
        System.out.print("Enter amount to withdraw: ");
        double amt = readDoubleFromLine();
        if (Double.isNaN(amt)) return;
        acct.withdraw(amt);
    }
    public void showAccountDetails() {
        System.out.print("Enter account number: ");
        int accNum = readIntFromLine();
        if (accNum < 0) return;
        Account acct = findAccount(accNum);
        if (acct == null) {
            System.out.println("Account not found.");
            return;
        }
        acct.displayAccountDetails();
    }

    public void updateContact() {
        System.out.print("Enter account number: ");
        int accNum = readIntFromLine();
        if (accNum < 0) return;
        Account acct = findAccount(accNum);
        if (acct == null) {
            System.out.println("Account not found.");
            return;
        }
        System.out.print("Enter new email address: ");
        String email = scanner.nextLine().trim();
        if (!isValidEmail(email)) {
            System.out.println("Invalid email. Update aborted.");
            return;
        }
        System.out.print("Enter new phone number: ");
        String phone = scanner.nextLine().trim();
        if (!isValidPhone(phone)) {
            System.out.println("Invalid phone number. Update aborted.");
            return;
        }
        acct.updateContactDetails(email, phone);
    }

    // Helper: find account by account number
    private Account findAccount(int accountNumber) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber() == accountNumber) {
                return accounts[i];
            }
        }
        return null;
    }

    // Helper: ensure array capacity; if full, double its size
    private void ensureCapacity() {
    if (accountCount >= accounts.length) {
        int newSize = accounts.length * 2;
        accounts = Arrays.copyOf(accounts, newSize);
        System.out.println("Internal: account storage resized to " + newSize + " slots.");
    }
}

// Basic email validation using a simple pattern (sufficient for demo)
    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        // very simple checks: contains @ and a dot after @
        int at = email.indexOf('@');
        int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1;
    }

    // Basic phone validation: digits only, length between 7 and 15
    private boolean isValidPhone(String phone) {
        if (phone == null) return false;
        String digits = phone.replaceAll("\\D", ""); // remove non-digits
        return digits.length() >= 7 && digits.length() <= 15;
    }

// Read integer safely from a line, returns -1 on error
    private int readIntFromLine() {
        try {
            String line = scanner.nextLine().trim();
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Invalid integer input.");
            return -1;
        }
    }

    // Read double safely from a line, returns NaN on error
    private double readDoubleFromLine() {
        try {
            String line = scanner.nextLine().trim();
            double val = Double.parseDouble(line);
            if (val <= 0) {
                System.out.println("Amount must be positive.");
                return Double.NaN;
            }
            return val;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number input.");
            return Double.NaN;
        }
    }
}