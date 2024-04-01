import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GoalSetting {
    private Map<String, Goal> goals;

    public GoalSetting() {
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

    public void run() {
        GoalSetting goalSetting = new GoalSetting();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Goal Setting Page!");

        while (true) {
            System.out.print("Enter a name for your goal: ");
            String name = scanner.nextLine();

            System.out.print("Enter the target amount for your goal: $");
            double targetAmount = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter the category for your goal (e.g., vacation, car, house): ");
            String category = scanner.nextLine();

            System.out.print("Enter the deadline for your goal (e.g., MM/YYYY): ");
            String deadline = scanner.nextLine();

            System.out.print("Enter the priority for your goal (low, medium, high): ");
            String priority = scanner.nextLine();

            goalSetting.setGoal(name, targetAmount, category, deadline, priority);

            System.out.print("Do you want to set another goal? (yes/no): ");
            String choice = scanner.nextLine().toLowerCase();
            if (!choice.equals("yes")) {
                break;
            }
        }

        System.out.println("\nGoals Set:");
        for (Goal goal : goalSetting.goals.values()) {
            System.out.println(goal);
        }
        scanner.nextLine();
        
    }
    
    public void run2() {
    	GoalSetting goalSetting2 = new GoalSetting();
        Scanner scanner2 = new Scanner(System.in);
        
        System.out.println("Welcome to the Goal Editing Page! Enter the name of the goal you want to edit: ");
        String name = scanner2.nextLine();

        if (goalSetting2.getGoal(name) != null) {
            System.out.println("Do you want to update the goal: " + name + " (yes/no)");
            String updateChoice = scanner2.nextLine().toLowerCase();
            if (updateChoice.equals("yes")) {
                // If the user wants to update, prompt for new details
                System.out.print("Enter the new target amount for your goal: $");
                double targetAmount = Double.parseDouble(scanner2.nextLine());

                System.out.print("Enter the new category for your goal (e.g., vacation, car, house): ");
                String category = scanner2.nextLine();

                System.out.print("Enter the new deadline for your goal (e.g., MM/YYYY): ");
                String deadline = scanner2.nextLine();

                System.out.print("Enter the new priority for your goal (low, medium, high): ");
                String priority = scanner2.nextLine();

                // Update the goal with new details
                goalSetting2.updateGoal(name, targetAmount, category, deadline, priority);
                System.out.println("Goal updated successfully.");
            } else {
                System.out.println("Skipping goal update.");
            }
        
    }
        scanner2.nextLine();
       
}
}