import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


class GoalManagementSystem {
    private Map<String, Goal> goals;

    public GoalManagementSystem() {
        this.goals = new HashMap<>();
    }

    public void setGoal(String name, double targetAmount, String category, String deadline, String priority) {
        goals.put(name, new Goal(name, targetAmount, category, deadline, priority));
    }

    public Goal getGoal(String name) {
        return goals.get(name);
    }

    public void updateGoal(String name, double targetAmount, String category, String deadline, String priority) {
        if (goals.containsKey(name)) {
            goals.get(name).updateGoal(targetAmount, category, deadline, priority);
        } else {
            System.out.println("Goal not found. Please enter a valid goal name.");
        }
    }

    public void addAmountToGoal(String name, double amount) {
        if (goals.containsKey(name)) {
            goals.get(name).addAmount(amount);
        } else {
            System.out.println("Goal not found. Please enter a valid goal name.");
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
      
        System.out.println("Welcome to the Goal Management System!");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a new goal");
            System.out.println("2. Edit an existing goal");
            System.out.println("3. View all goals");
            System.out.println("4. Exit");

            
            int choice;
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
                GoalSetting goal = new GoalSetting();
            switch (choice) {
                case 1:
                	
                    goal.run();
                    break;
                case 2:
                	
                    goal.run2();
                    break;
                case 3:
                    viewGoals();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer choice.");
                // Optionally, print more information about the input:
                System.out.println("Input received: " + scanner.nextLine());
                // Handle the error gracefully, possibly by asking the user to input again
            }
        }
    }

   

   

    private void viewGoals() {
        if (goals.isEmpty()) {
            System.out.println("No goals found.");
            return;
        }

        System.out.println("\nGoals Set:");
        for (Goal goal : goals.values()) {
            System.out.println(goal);
        }
    }
    
    public static void main(String[] args) {
        GoalManagementSystem goalSystem = new GoalManagementSystem();
        goalSystem.run();
    }
    
}