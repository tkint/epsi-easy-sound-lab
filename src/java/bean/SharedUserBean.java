/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import model.User;

/**
 *
 * @author t.kint
 */
@Named(value = "sharedUserBean")
@ApplicationScoped
public class SharedUserBean {

    private ArrayList<User> onlineUsers;

    /**
     * Creates a new instance of SharedUserBean
     */
    public SharedUserBean() {
        onlineUsers = new ArrayList<>();
    }

    public ArrayList<User> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(ArrayList<User> onLineUsers) {
        this.onlineUsers = onLineUsers;
    }

    public int getOnlineUserIndex(User user) {
        int i = 0;
        int index = -1;

        while (i < onlineUsers.size() && index == -1) {
            if (onlineUsers.get(i).id == user.id) {
                index = i;
            }
            i++;
        }

        return index;
    }

    public void addOnlineUser(User user) {
        if (!isUserOnline(user)) {
            onlineUsers.add(user);
        }
    }

    public void removeOnlineUser(User user) {
        if (isUserOnline(user) && getOnlineUserIndex(user) != -1) {
            onlineUsers.remove(getOnlineUserIndex(user));
        }
    }

    private boolean isUserOnline(User user) {
        int i = 0;
        boolean online = false;

        while (i < onlineUsers.size() && !online) {
            if (onlineUsers.get(i).id == user.id) {
                online = true;
            }
            i++;
        }

        return online;
    }
}
