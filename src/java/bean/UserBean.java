/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import model.Folder;
import model.MusicFile;
import model.Playlist;
import model.User;

/**
 *
 * @author tkint
 */
@Named(value = "userBean")
@SessionScoped
public class UserBean implements Serializable {

    private User currentUser;

    private String login;
    private String password;

    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
    }
    
    @PostConstruct
    public void init() {
        currentUser = new User(-1);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String connect() {
        currentUser = new User(0, login, password, "firstname", "lastname", "email");

        for (int i = 0; i < 25; i++) {
            Folder folder = new Folder(i, "Folder " + i);
            
            for (int j = 0; j < 10; j++) {
                MusicFile musicFile = new MusicFile(j, "Sound " + j, 3.36f);
                folder.addMusicFile(musicFile);
            }
            
            currentUser.addFolder(folder);
        }
        
        for (int i = 0; i < 25; i++) {
            Playlist playlist = new Playlist(i, "Playlist " + i);
            
            for (int j = 0; j < 10; j++) {
                MusicFile musicFile = new MusicFile(j, "Sound " + j, 3.36f);
                playlist.addMusicFile(musicFile);
            }
            
            currentUser.addPlaylist(playlist);
        }

        login = null;
        password = null;

        return "index";
    }

    public String disconnect() {
        currentUser = new User(-1);

        FacesContext context = FacesContext.getCurrentInstance();
        FolderBean folderBean = context.getApplication().evaluateExpressionGet(context, "#{folderBean}", FolderBean.class);
        folderBean.close();

        return "index";
    }

    public String register() {
        return "register";
    }

    public String user() {
        return "user";
    }
}
