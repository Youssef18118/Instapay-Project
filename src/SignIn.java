
public class SignIn {
    private String Username;
    private String password;
    private InstapaySystem system;

    public void setSystem(InstapaySystem system) {
        this.system = system;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String userName) {
        Username = Username;
    }

    public InstapaySystem getSystem() {
        return system;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return Username;
    }
    public boolean login(String Username, String Password) {


        if (system.db.findUserName(Username)) {
            // Display success message
            java.lang.System.out.println("Login successful!");
            return true;
        } else {
            // Display failure message
            System.out.println("Login failed. Invalid username or password.");
            return false;
        }
    }
}