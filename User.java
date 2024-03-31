// User.java - Shayan Saberi-Nikou

// This class represents a user of the system. It stores user details and manages account status.

// Test Case 1: Create a new User account with valid credentials.
// Expected Result: The account is created with an active status, and appropriate details are stored.

// Test Case 2: Delete an existing User account.
// Expected Result: The account's status is set to inactive, and an account deletion message is displayed.

public class User {
    // Attributes to store the username, password, email, and account status
    private String username;
    private String password;
    private String email;
    private boolean isAccountActive;

    // Constructor to initialize a new User object with username, password, and email. Sets the account as active.
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAccountActive = true; // Account is active upon creation
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password. In a real application, consider enforcing strong password policies here.
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email. Could include validation for email format.
    public void setEmail(String email) {
        this.email = email;
    }

    // Check if the account is active
    public boolean isAccountActive() {
        return isAccountActive;
    }

    // Method to simulate account deletion. Sets the account status to inactive.
    public void deleteAccount() {
        this.isAccountActive = false;
        System.out.println("User account has been deleted.");
    }

    // Static method to simulate account creation. Returns a new User object.
    // In a real application, this would involve more checks (e.g., for existing username) and database interaction.
    public static User createAccount(String username, String password, String email) {
        // Here, additional checks for username uniqueness, password complexity, and email format can be implemented.
        return new User(username, password, email);
    }
}