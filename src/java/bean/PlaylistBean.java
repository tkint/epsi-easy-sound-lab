/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import model.Playlist;
import model.User;

/**
 *
 * @author tkint
 */
@Named(value = "playlistBean")
@SessionScoped
public class PlaylistBean implements Serializable {

    private Playlist currentPlaylist;
    private String currentPlaylistNewName;
    private String newPlaylistName;

    /**
     * Creates a new instance of PlaylistBean
     */
    public PlaylistBean() {
    }

    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    public void setCurrentPlaylist(Playlist currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }

    public String getCurrentPlaylistNewName() {
        return currentPlaylistNewName;
    }

    public void setCurrentPlaylistNewName(String currentPlaylistNewName) {
        this.currentPlaylistNewName = currentPlaylistNewName;
    }

    public String getNewPlaylistName() {
        return newPlaylistName;
    }

    public void setNewPlaylistName(String newPlaylistName) {
        this.newPlaylistName = newPlaylistName;
    }

    public String open(Playlist playlist) {
        if (playlist != null) {
            currentPlaylist = playlist;
            currentPlaylistNewName = playlist.getName();

            return "playlist?faces-redirect=true";
        }
        return "index?faces-redirect=true";
    }

    public void close() {
        currentPlaylist = null;
    }

    public String addPlaylist() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserBean userBean = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);

        Playlist playlist = new Playlist(userBean.getCurrentUser().getLastPlaylistId() + 1, newPlaylistName);
        userBean.getCurrentUser().addPlaylist(playlist);
        newPlaylistName = null;

        return open(playlist);
    }

    public String rename() {
        currentPlaylist.setName(currentPlaylistNewName);

        return open(currentPlaylist);
    }

    public String delete() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserBean userBean = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);

        User user = userBean.getCurrentUser();

        Playlist playlist = null;

        if (currentPlaylist.getId() < user.getLastPlaylistId()) {
            playlist = user.getPlaylistById(currentPlaylist.getId() + 1);
        } else {
            playlist = user.getPlaylistById(currentPlaylist.getId() - 1);
        }
        user.deletePlaylistById(currentPlaylist.getId());

        return open(playlist);
    }
}
