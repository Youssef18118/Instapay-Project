import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class DbList implements DB {
    private List<User> users;

    public DbList() {
        this.users = new ArrayList<>();
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public boolean findUserName(String userName) {
        for (User user : users) {
//            if (Objects.equals(userName, user.getUserName())) {
                return true;
//            }
        }
        return false;
    }

    @Override
    public User findUser(String userName) {
        for (User user : users) {
//            if (Objects.equals(userName, user.getUserName())) {
                return user;
//            }
        }
        return null;
    }

    @Override
    public boolean verifyUser(User user) {
        return users.contains(user);
    }

    @Override
    public double getBalance(User user) {
        // You need to implement this method based on your logic
        // For now, it returns 0.0
        return 0.0;
    }

    @Override
    public void updateBalance(User user, double newBalance) {
        // You need to implement this method based on your logic
        // For now, it does nothing
    }
}
