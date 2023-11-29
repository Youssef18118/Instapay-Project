import java.util.ArrayList;
import java.util.List;

class DbList implements DB {
    private List<User> users;

    public DbList() {
        this.users = new ArrayList<>();
    }

    @Override
    public void addUser(User user) {
        // return error while sign up as Bank User
        // if (!findUserNameANDPassword(user.getUserName(), user.getPassword())) {
        // If the username and password don't exist, add the user to the list
        // users.add(user);
        // }

        // Ensure both username and password are not null before adding the user to the
        // list
        // if (user.getUserName() != null && user.getPassword() != null
        // && !findUserNameANDPassword(user.getUserName(), user.getPassword())) {
        // // If the username and password don't exist, add the user to the list
        // users.add(user);
        // }

        // if (user == null) {
        // System.out.println("Nulllllll");
        // }

        users.add(user);

    }

    @Override
    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public boolean findUserName(String userName) {
        for (User user : users) {
            System.out.println("Inside FindUserName " + user.getUserName());
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    // this returns false all time
    @Override
    public boolean findUserNameANDPassword(String userName, String password) {
        for (User user : users) {
            String storedUserName = user.getUserName();
            String storedPassword = user.getPassword();

            System.out.println(user);

            if (storedUserName != null && storedPassword != null &&
                    storedUserName.equals(userName) && storedPassword.equals(password)) {
                return true;
            }
        }
        return false;

        // return true;
    }

    @Override
    public User getUser(String userName, String password) {
        for (User user : users) {
            String storedUserName = user.getUserName();
            String storedPassword = user.getPassword();

            System.out.println(user);

            if (storedUserName != null && storedPassword != null &&
                    storedUserName.equals(userName) && storedPassword.equals(password)) {
                return user;
            }
        }
        return null;

        // return true;
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
            if (u.getUserName().equals(user.getUserName())) {
                return u.getBalance();
            }
        }
        return 0.0; // Return 0 if user not found
    }

    @Override
    public void updateBalance(User user, double newBalance) {
        for (User u : users) {
            if (u.getUserName().equals(user.getUserName())) {
                u.setBalance(newBalance);
                break;
            }
        }
    }

    @Override
    public boolean findUserType(UserType type) {
        for (User user : users) {
            System.out.println(user.getUserType());
            if (user.getUserType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public UserType findUserType(String userName) {
        for (User user : users) {
            System.out.println("Inside FindUserType " + user.getUserType());
            if (user.getUserName().equals(userName)) {
                return user.getUserType();
            }
        }
        return null;
    }

    @Override
    public User findUserByphone(String phoneNum) {
        for (User user : users) {
            System.out.println("Inside findUserByphone " + user.getUserType());
            if (user.getMobileNo().equals(phoneNum)) {
                return user;
            }
        }
        return null;
    }

}
