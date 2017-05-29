/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.UserDAO;
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
    private User profileUser;

    private String pseudo;
    private String password;
    private String passwordConfirm;
    private String firstName;
    private String lastName;
    private String email;

    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
    }

    @PostConstruct
    public void init() {
        currentUser = new User();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getProfileUser() {
        if (profileUser != null) {
            return profileUser;
        }
        return currentUser;
    }

    public void setProfileUser(User profileUser) {
        this.profileUser = profileUser;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String signIn() {
        User user = UserDAO.getUserByEmailAndPassword(email, password);

        if (user != null) {

            currentUser = user;

            for (int i = 0; i < 10; i++) {
                Folder folder = new Folder(i, "Folder " + i);

                for (int j = 0; j < 50; j++) {
                    MusicFile musicFile = new MusicFile(j, "Sound " + j, 3.36f);
                    folder.addMusicFile(musicFile);
                }

                currentUser.addFolder(folder);
            }

            for (int i = 0; i < 5; i++) {
                Playlist playlist = new Playlist(i, "Playlist " + i);

                for (int j = 0; j < 40; j++) {
                    MusicFile musicFile = new MusicFile(j, "Sound " + j, 3.36f);
                    playlist.addMusicFile(musicFile);
                }

                currentUser.addPlaylist(playlist);
            }

            email = null;
            password = null;

            FacesContext context = FacesContext.getCurrentInstance();
            SharedUserBean sharedUserBean = context.getApplication().evaluateExpressionGet(context, "#{sharedUserBean}", SharedUserBean.class);
            sharedUserBean.addOnlineUser(currentUser);
        }
        return "index?faces-redirect=true";
    }

    public String signOut() {
        FacesContext context = FacesContext.getCurrentInstance();
        SharedUserBean sharedUserBean = context.getApplication().evaluateExpressionGet(context, "#{sharedUserBean}", SharedUserBean.class);
        sharedUserBean.removeOnlineUser(currentUser);

        currentUser = new User();

        FolderBean folderBean = context.getApplication().evaluateExpressionGet(context, "#{folderBean}", FolderBean.class);
        folderBean.close();

        return "index?faces-redirect=true";
    }

    public String createUser() {
        if (password.equals(passwordConfirm)) {
            User user = new User(email, password, firstName, lastName, pseudo);

            user = UserDAO.createUser(user);

            if (user.getId() != -1) {
                currentUser = user;
                
                email = null;
                password = null;
                firstName = null;
                lastName = null;
                pseudo = null;

                FacesContext context = FacesContext.getCurrentInstance();
                SharedUserBean sharedUserBean = context.getApplication().evaluateExpressionGet(context, "#{sharedUserBean}", SharedUserBean.class);
                sharedUserBean.addOnlineUser(currentUser);
            }

            return "index?faces-redirect=true";
        }

        return "signup?faces-redirect=true";
    }

    public String signup() {
        return "signup?faces-redirect=true";
    }

    public String profile(User user) {
        profileUser = user;

        return "profile?faces-redirect=true";
    }
}
