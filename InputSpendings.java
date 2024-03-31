import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
//Testing Valid Input Spendings by Jones Mao
//Use Case for Input Spendings
//Testing for negative values, null case, valid categories, 

public class InputSpendings {
    private Map<String, Double> spendingRecords;

    public InputSpendings() {
        this.spendingRecords = new HashMap<>();
    }

    public void addSpending(double amount, String category) {
        category = category.toLowerCase();
        if (!spendingRecords.containsKey(category)) {
            spendingRecords.put(category, 0.0);
        }
        double currentAmount = spendingRecords.get(category);
        spendingRecords.put(category, currentAmount + amount);
    }

    public double getTotalSpending() {
        double totalSpending = 0;
        for (double amount : spendingRecords.values()) {
            totalSpending += amount;
        }
        return totalSpending;
    }

    public double getCategorySpending(String category) {
        category = category.toLowerCase();
        return spendingRecords.getOrDefault(category, 0.0);
    }

    public static void main(String[] args) {
        InputSpendings InputSpendings = new InputSpendings();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            double amount = 0;
            while (true) {
                System.out.print("Enter the amount spent (or 0 to stop): $");
                String input = scanner.nextLine();
                if (input.equals("")) {
                    System.out.println("Please enter a valid amount.");
                    continue;
                }
                try {
                    amount = Double.parseDouble(input);
                    if (amount < 0) {
                        System.out.println("Please enter a positive amount.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
            }
            if (amount == 0) {
                break;
            }
            
            String category;
            while (true) {
                System.out.print("Enter the category (groceries, bills, gas, entertainment): ");
                category = scanner.nextLine().toLowerCase();
                if (category.equals("")) {
                    System.out.println("Please enter a valid category.");
                    continue;
                } else if (!category.equals("groceries") && !category.equals("bills") &&
                           !category.equals("gas") && !category.equals("entertainment")) {
                    System.out.println("Please enter a valid category.");
                    continue;
                }
                break;
            }
            InputSpendings.addSpending(amount, category);
        }

        System.out.println("Total spending: $" + InputSpendings.getTotalSpending());
        System.out.println("Spending on groceries: $" + InputSpendings.getCategorySpending("groceries"));
        System.out.println("Spending on bills: $" + InputSpendings.getCategorySpending("bills"));
        System.out.println("Spending on gas: $" + InputSpendings.getCategorySpending("gas"));
        System.out.println("Spending on entertainment: $" + InputSpendings.getCategorySpending("entertainment"));
        
        scanner.close();
    }
}