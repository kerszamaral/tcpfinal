package SpendWise.Managers;

import java.util.Hashtable;
import SpendWise.User;

public class UserManager {
    private Hashtable<String, User> users;

    public UserManager() {
        this.users = new Hashtable<String, User>();
    }

    public boolean createUser(User user){

        if (this.users.containsKey(user.getUsername())) {
            return false;
        }
        this.users.put(user.getUsername(), user);
        return true;
    }

    public boolean validateLogin(String username, String password) {
        if (!this.users.containsKey(username)) {
            return false;
        }
        User user = this.users.get(username);
        return user.checkPassword(password);
    }

    public boolean checkUsername(String username) {
        return this.users.containsKey(username);
    }
    
}
