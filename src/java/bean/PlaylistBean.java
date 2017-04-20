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

/**
 *
 * @author tkint
 */
@Named(value = "playlistBean")
@SessionScoped
public class PlaylistBean implements Serializable {

    private boolean browsing;
    private Playlist currentPlaylist;
    private String newPlaylistName;

    /**
     * Creates a new instance of PlaylistBean
     */
    public PlaylistBean() {
    }

    public boolean isBrowsing() {
        return browsing;
    }

    public void setBrowsing(boolean browsing) {
        this.browsing = browsing;
    }

    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    public void setCurrentPlaylist(Playlist currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }

    public String getNewPlaylistName() {
        return newPlaylistName;
    }

    public void setNewPlaylistName(String newPlaylistName) {
        this.newPlaylistName = newPlaylistName;
    }

    public String open(Playlist currentPlaylist) {
        this.currentPlaylist = currentPlaylist;

        return "playlist";
    }

    public void close() {
        this.currentPlaylist = null;
    }

    public String addPlaylist() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserBean userBean = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);

        Playlist playlist = new Playlist(userBean.getCurrentUser().getLastPlaylistId() + 1, newPlaylistName);
        userBean.getCurrentUser().addPlaylist(playlist);
        newPlaylistName = null;

        return open(playlist);
    }
}
