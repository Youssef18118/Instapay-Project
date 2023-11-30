import java.util.ArrayList;
import java.util.List;

class DbList implements DB {
    private List<User> users;
    private List<BankUser> Bankusers;

    public DbList() {
        this.users = new ArrayList<>();
        this.Bankusers = new ArrayList<>();
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void addBankUser(BankUser user) {
        Bankusers.add(user);
    }

    @Override
    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public void removeBankUser(BankUser user) {
        Bankusers.remove(user);
    }

    @Override
    public boolean findUserName(String userName) {
        for (User user : users) {
            // System.out.println("Inside FindUserName " + user.getUserName());
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean findUserNameANDPassword(String userName, String password) {
        for (User user : users) {
            String storedUserName = user.getUserName();
            String storedPassword = user.getPassword();

            // System.out.println(user);

            if (storedUserName != null && storedPassword != null &&
                    storedUserName.equals(userName) && storedPassword.equals(password)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public User getUser(String userName, String password) {
        for (User user : users) {
            String storedUserName = user.getUserName();
            String storedPassword = user.getPassword();

            // System.out.println(user);

            if (storedUserName != null && storedPassword != null &&
                    storedUserName.equals(userName) && storedPassword.equals(password)) {
                return user;
            }
        }
        return null;

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
            // System.out.println(user.getUserType());
            if (user.getUserType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public UserType findUserType(String userName) {
        for (User user : users) {
            // System.out.println("Inside FindUserType " + user.getUserType());
            if (user.getUserName().equals(userName)) {
                return user.getUserType();
            }
        }
        return null;
    }

    @Override
    public User findUserByphone(String phoneNum) {
        for (User user : users) {
            // System.out.println("Inside findUserByphone " + user.getUserType());
            if (user.getMobileNo().equals(phoneNum)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public BankUser findCardNo(String Cardno) {
        for (BankUser user : Bankusers) {
            // System.out.println(user.getCardNo());
            if (user.getCardNo().equals(Cardno)) {
                return user;
            }
        }
        return null;
    }

}
