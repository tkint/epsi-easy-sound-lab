/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import model.User;

/**
 *
 * @author t.kint
 */
public class UserDAO {
    
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        
        users.add(getUserById(0));
        users.add(getUserById(1));
        
        return users;
    }
    
    public static User getUserById(int id) {
        User user = new User(id);
        
        user.setLogin("Login " + id);
        
        return user;
    }
    
    public static User getUserByLoginAndPassword(String login, String password) {
        User user = new User(0, login, password, "firstname", "lastname", "email");
        
        return user;
    }
}
