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
    
    private ArrayList<User> connectedUsers;

    /**
     * Creates a new instance of SharedUserBean
     */
    public SharedUserBean() {
        connectedUsers = new ArrayList<>();
    }

    public ArrayList<User> getConnectedUsers() {
        return connectedUsers;
    }

    public void setConnectedUsers(ArrayList<User> connectedUsers) {
        this.connectedUsers = connectedUsers;
    }
    
    public int getConnectedUserIndex(User user) {
        int i = 0;
        int index = -1;

        while (i < connectedUsers.size() && index == -1) {
            if (connectedUsers.get(i).getId() == user.getId()) {
                index = i;
            }
            i++;
        }

        return index;
    }
    
    public void addConnectedUser(User user) {
        if (!isUserAlreadyConnected(user)) {
            connectedUsers.add(user);
        }
    }
    
    public void removeConnectedUser(User user) {
        if (isUserAlreadyConnected(user)) {
            if (getConnectedUserIndex(user) != -1) {
                connectedUsers.remove(getConnectedUserIndex(user));
            }
        }
    }
    
    private boolean isUserAlreadyConnected(User user) {
        int i = 0;
        boolean connected = false;
        
        while (i < connectedUsers.size() && !connected) {
            if (connectedUsers.get(i).getId() == user.getId()) {
                connected = true;
            }
            i++;
        }
        
        return connected;
    }
}
