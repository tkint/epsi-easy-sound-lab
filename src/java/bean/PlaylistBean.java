/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.PlaylistDAO;
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

    private PlaylistDAO playlistDAO;

    /**
     * Creates a new instance of PlaylistBean
     */
    public PlaylistBean() {
        playlistDAO = new PlaylistDAO();
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
            currentPlaylistNewName = playlist.name;

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

        Playlist playlist = new Playlist(userBean.getCurrentUser().id, newPlaylistName);

        playlist = playlistDAO.createEntity(playlist);

        userBean.getCurrentUser().addPlaylist(playlist);

        newPlaylistName = null;

        return open(playlist);
    }

    public String rename() {
        currentPlaylist.name = currentPlaylistNewName;

        playlistDAO.updateEntity(currentPlaylist);

        return open(currentPlaylist);
    }

    public String delete() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserBean userBean = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);

        User user = userBean.getCurrentUser();

        Playlist playlist = null;

        // TODO Modifier le fonctionnement avec l'index des folders plutôt que les IDs
        if (currentPlaylist.id < user.getLastPlaylistId()) {
            playlist = user.getPlaylistById(currentPlaylist.id + 1);
        } else {
            playlist = user.getPlaylistById(currentPlaylist.id - 1);
        }

        user.deletePlaylistById(currentPlaylist.id);

        playlistDAO.deleteEntity(currentPlaylist);

        return open(playlist);
    }
}
