import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
// Testing Forget Password and resetting it Bhuvan Yerramsetty
// Use cases for forget passwords
public class PasswordReset {

    //map for the users
    private Map<String, String> users;

    // declaring scanner to get input
    private Scanner scanner;

    // reset constructor
    public PasswordReset() {
        this.users = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    // method to reset password for user
    public void resetPassword(String userId) {
        if (users.containsKey(userId)) {
            String currentPassword = users.get(userId);
            System.out.print("Enter your new password: ");
            String newPassword = scanner.nextLine();
            while (newPassword.equals(currentPassword)) {
                System.out.println("New password can't be the same as the current one. Please try again.");
                System.out.print("Enter your new password: ");
                newPassword = scanner.nextLine();
            }
            users.put(userId, newPassword);
            System.out.println("Password reset successful.");
        } else {
            System.out.println("User not found.");
        }
    }

    // adding new user 
    public void addUser(String userId, String password) {
        users.put(userId, password);
    }

    // test method
    public static void main(String[] args) {
        PasswordReset passwordReset = new PasswordReset();

        // adding users
        passwordReset.addUser("Bhuvan", "random");
        passwordReset.addUser("Test", "random2");

        // resetting password for user1
        passwordReset.resetPassword("Bhuvan");

        // resetting password for non existing user
        passwordReset.resetPassword("yerramsetty");
    }
}
