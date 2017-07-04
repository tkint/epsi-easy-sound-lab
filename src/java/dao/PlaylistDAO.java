/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.*;

/**
 *
 * @author tkint
 */
public class PlaylistDAO extends MainDAO<Playlist> {

    private static PlaylistDAO instance;
    private PlaylistMusicFileDAO playlistMusicFileDAO;

    private PlaylistDAO() {
        super(Playlist.class);
        playlistMusicFileDAO = PlaylistMusicFileDAO.getInstance();
    }

    public static PlaylistDAO getInstance() {
        if (instance == null) {
            instance = new PlaylistDAO();
        }
        return instance;
    }

    public List<Playlist> getPlaylistsByIdUser(int id) {
        return getEntitiesByEntityReferenceId(User.class, id);
    }

    public List<Playlist> getSharedPlaylistsByIdUser(int id) {
        List<Playlist> playList = getPlaylistsByIdUser(id);
        playList.removeIf(p -> !p.shared);
        return playList;
    }
    
    public List<Playlist> getSharedPlaylistsByIdUser(int id, int idUser) {
        List<Playlist> playList = getPlaylistsByIdUser(id);
        playList.removeIf(p -> !p.shared && p.idUser != idUser);
        return playList;
    }
    
    public List<Playlist> getPlaylistsByName(int idUser, String value) {
        List<Playlist> playlists = getPlaylistsByIdUser(idUser);
        
        for (Playlist playlist : playlists) {
            if (!playlist.name.toLowerCase().contains(value.toLowerCase())) {
                playlists.remove(playlist);
            }
        }
        
        return playlists;
    }

    @Override
    public int deleteEntity(Playlist entity) {
        for (PlaylistMusicFile playlistMusicFile : playlistMusicFileDAO.getMusicFilesByIdPlaylist(entity.id)) {
            playlistMusicFileDAO.deleteEntity(playlistMusicFile);
        }
        
        return super.deleteEntity(entity);
    }
}
