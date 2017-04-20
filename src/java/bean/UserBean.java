/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import model.Folder;
import model.MusicFile;
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
    private String newFolderName;

    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
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

    public String getNewFolderName() {
        return newFolderName;
    }

    public void setNewFolderName(String newFolderName) {
        this.newFolderName = newFolderName;
    }

    public String connect() {
        currentUser = new User(0, login, password, "firstname", "lastname", "email");

        Folder folder1 = new Folder(0, "Premier dossier");
        folder1.addMusicFile(new MusicFile(0, "Sound 1", 3.50f));
        folder1.addMusicFile(new MusicFile(1, "Sound 2", 3.84f));
        folder1.addMusicFile(new MusicFile(2, "Sound 3", 3.84f));
        folder1.addMusicFile(new MusicFile(3, "Sound 4", 3.84f));
        folder1.addMusicFile(new MusicFile(4, "Sound 5", 3.84f));
        folder1.addMusicFile(new MusicFile(5, "Sound 6", 3.50f));
        folder1.addMusicFile(new MusicFile(6, "Sound 7", 3.84f));
        folder1.addMusicFile(new MusicFile(7, "Sound 8", 3.84f));
        folder1.addMusicFile(new MusicFile(8, "Sound 9", 3.84f));
        folder1.addMusicFile(new MusicFile(9, "Sound 10", 3.84f));
        folder1.addMusicFile(new MusicFile(9, "Sound 11", 3.84f));
        folder1.addMusicFile(new MusicFile(9, "Sound 12", 3.84f));
        folder1.addMusicFile(new MusicFile(9, "Sound 13", 3.84f));
        folder1.addMusicFile(new MusicFile(9, "Sound 14", 3.84f));
        folder1.addMusicFile(new MusicFile(9, "Sound 15", 3.84f));
        folder1.addMusicFile(new MusicFile(9, "Sound 16", 3.84f));

        Folder folder2 = new Folder(1, "Deuxième dossier");
        folder2.addMusicFile(new MusicFile(5, "Sound 6", 3.36f));

        currentUser.addFolder(folder1);
        currentUser.addFolder(folder2);
        
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
    
    public String addFolder() {
        Folder folder = new Folder(currentUser.getLastFolderId() + 1, newFolderName);
        currentUser.addFolder(folder);
        newFolderName = null;
        
        FacesContext context = FacesContext.getCurrentInstance();
        FolderBean folderBean = context.getApplication().evaluateExpressionGet(context, "#{folderBean}", FolderBean.class);
        
        return folderBean.open(folder);
    }
}
