import java.util.ArrayList;
import java.util.List;

class DbList implements DB {
    private List<User> users;

    public DbList() {
        this.users = new ArrayList<>();
    }

    @Override
    public void addUser(User user) {

        if (!findUserNameANDPassword(user.getUserName(), user.getPassword())) {
            // If the username and password don't exist, add the user to the list
            users.add(user);
        }

    }

    @Override
    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public boolean findUserName(String userName) {
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean findUserNameANDPassword(String userName, String password) {
        for (User user : users) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User findUser(User usr) {
        for (User user : users) {
            if (user.equals(usr)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean verifyUser(User user) {
        // Assuming verification is successful if the user is present in the list
        return users.contains(user);
    }

    @Override
    public double getBalance(User user) {
        for (User u : users) {
            if (u.equals(user)) {
                return u.getBalance();
            }
        }
        return 0.0; // Return 0 if user not found
    }

    @Override
    public void updateBalance(User user, double newBalance) {
        for (User u : users) {
            if (u.equals(user)) {
                u.setBalance(newBalance);
                break;
            }
        }
    }
}
