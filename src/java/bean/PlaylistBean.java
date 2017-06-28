/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.MusicFileDAO;
import dao.PlaylistDAO;
import dao.PlaylistMusicFileDAO;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import model.MusicFile;
import model.Playlist;
import model.PlaylistMusicFile;
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

    private Map<Integer, Boolean> selectedMusicFiles;
    private int idTarget;

    private PlaylistDAO playlistDAO;
    private MusicFileDAO musicFileDAO;
    private PlaylistMusicFileDAO playlistMusicFileDAO;

    /**
     * Creates a new instance of PlaylistBean
     */
    public PlaylistBean() {
        selectedMusicFiles = new HashMap<>();
        playlistDAO = PlaylistDAO.getInstance();
        musicFileDAO = MusicFileDAO.getInstance();
        playlistMusicFileDAO = PlaylistMusicFileDAO.getInstance();
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

    public Map<Integer, Boolean> getSelectedMusicFiles() {
        return selectedMusicFiles;
    }

    public void setSelectedMusicFiles(Map<Integer, Boolean> selectedMusicFiles) {
        this.selectedMusicFiles = selectedMusicFiles;
    }

    public int getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(int idTarget) {
        this.idTarget = idTarget;
    }

    public String addMusicFiles() {
        FacesContext context = FacesContext.getCurrentInstance();
        FolderBean folderBean = context.getApplication().evaluateExpressionGet(context, "#{folderBean}", FolderBean.class);

        Playlist playlist = playlistDAO.getEntityById(idTarget);

        if (playlist != null) {
            for (MusicFile musicFile : folderBean.getCurrentFolder().musicFiles) {
                if (selectedMusicFiles.get(musicFile.id)) {
                    PlaylistMusicFile playlistMusicFile = new PlaylistMusicFile(playlist.id, musicFile.id);
                    playlistMusicFileDAO.createEntity(playlistMusicFile);

                    playlist.addMusicFile(musicFile);
                }
            }
        }

        return open(playlist);
    }

    public String open(Playlist playlist) {
        if (playlist != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            UserBean userBean = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);

            currentPlaylist = playlist;
            currentPlaylistNewName = playlist.name;

            currentPlaylist.musicFiles = musicFileDAO.getMusicFilesByIdPlaylist(userBean.getCurrentUser().id, currentPlaylist.id);

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
