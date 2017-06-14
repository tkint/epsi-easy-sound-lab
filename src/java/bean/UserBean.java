/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.*;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import model.*;
import servlet.MusicFileServlet;

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

    private UserDAO userDAO;
    private FolderDAO folderDAO;
    private PlaylistDAO playlistDAO;
    private MusicFileDAO musicFileDAO;

    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
        userDAO = new UserDAO();
        folderDAO = new FolderDAO();
        playlistDAO = new PlaylistDAO();
        musicFileDAO = new MusicFileDAO();
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

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public FolderDAO getFolderDAO() {
        return folderDAO;
    }

    public void setFolderDAO(FolderDAO folderDAO) {
        this.folderDAO = folderDAO;
    }

    public PlaylistDAO getPlaylistDAO() {
        return playlistDAO;
    }

    public void setPlaylistDAO(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    public MusicFileDAO getMusicFileDAO() {
        return musicFileDAO;
    }

    public void setMusicFileDAO(MusicFileDAO musicFileDAO) {
        this.musicFileDAO = musicFileDAO;
    }

    public String signIn() {
        User user = userDAO.getUserByEmailAndPassword(email, password);

        if (user != null) {

            currentUser = user;

            currentUser.folders = folderDAO.getFoldersByIdUser(user.id);

            currentUser.playlists = playlistDAO.getPlaylistsByIdUser(user.id);

            email = currentUser.email;
            password = null;
            passwordConfirm = null;
            firstName = currentUser.firstName;
            lastName = currentUser.lastName;
            pseudo = currentUser.pseudo;

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
        if (password != null && !password.equals("") && password.equals(passwordConfirm)) {
            User user = new User(email, password, firstName, lastName, pseudo);

            user = userDAO.createEntity(user);

            if (user.id != -1) {
                currentUser = user;

                email = currentUser.email;
                password = null;
                passwordConfirm = null;
                firstName = currentUser.firstName;
                lastName = currentUser.lastName;
                pseudo = currentUser.pseudo;

                FacesContext context = FacesContext.getCurrentInstance();
                SharedUserBean sharedUserBean = context.getApplication().evaluateExpressionGet(context, "#{sharedUserBean}", SharedUserBean.class);
                sharedUserBean.addOnlineUser(currentUser);
                
                MusicFileServlet.createDir(String.valueOf(currentUser.id));
            }

            return "index?faces-redirect=true";
        }

        return "signup?faces-redirect=true";
    }

    public String updateUser() {

        if (email != null && !email.equals("")) {
            currentUser.email = email;
        }

        if (password != null && !password.equals("") && password.equals(passwordConfirm)) {
            currentUser.password = password;
        }

        if (firstName != null && !firstName.equals("")) {
            currentUser.firstName = firstName;
        }

        if (lastName != null && !lastName.equals("")) {
            currentUser.lastName = lastName;
        }

        if (pseudo != null && !pseudo.equals("")) {
            currentUser.pseudo = pseudo;
        }

        userDAO.updateEntity(currentUser);

        return "profile?faces-redirect=true";
    }

    public String signup() {
        return "signup?faces-redirect=true";
    }

    public String profile(User user) {
        profileUser = user;

        return "profile?faces-redirect=true";
    }

    public String users() {
        return "users?faces-redirect=true";
    }
}
