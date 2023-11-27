import java.util.Scanner;

public class SignIn {
    private String Username;
    private String password;

    public SignIn() {

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String userName) {
        Username = Username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return Username;
    }

    public User SignInPut() {
        Scanner scanner = new Scanner(System.in);

        // try {
        System.out.println("Enter UserName:");
        Username = scanner.nextLine();

        System.out.println("Enter password:");
        password = scanner.nextLine();

        User user = new User();
        user.setUserName(Username);
        user.setPassword(password);
        return user;

        // } catch (Exception e) {
        // System.out.println("An error occurred: " + e.getMessage());
        // return null;

        // } finally {
        // scanner.close();
        // }
    }

}