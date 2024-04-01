//Goal.java Jerin Mulangan

import java.text.DecimalFormat;

public class Goal {
    private String name;
    private double targetAmount;
    private double currentAmount;
    private String category;
    private String deadline;
    private String priority;

    public Goal(String name, double targetAmount, String category, String deadline, String priority) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = 0.0; // Initialize current amount to 0
        this.category = category;
        this.deadline = deadline;
        this.priority = priority;
    }

    public void updateGoal(double targetAmount, String category, String deadline, String priority) {
        this.targetAmount = targetAmount;
        this.category = category;
        this.deadline = deadline;
        this.priority = priority;
    }

    public void addAmount(double amount) {
        currentAmount += amount;
    }

    public double getProgress() {
        return currentAmount / targetAmount * 100;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return "Goal: " + name + ", Target Amount: $" + targetAmount + ", Current Amount: $" + currentAmount +
                ", Progress: " + df.format(getProgress()) + "%, Category: " + category +
                ", Deadline: " + deadline + ", Priority: " + priority;
    }
}
