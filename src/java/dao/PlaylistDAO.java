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

    private PlaylistDAO() {
        super(Playlist.class);
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
    
    public List<Playlist> getPlaylistsByName(int idUser, String value) {
        List<Playlist> playlists = getPlaylistsByIdUser(idUser);
        
        for (Playlist playlist : playlists) {
            if (!playlist.name.toLowerCase().contains(value.toLowerCase())) {
                playlists.remove(playlist);
            }
        }
        
        return playlists;
    }
}
