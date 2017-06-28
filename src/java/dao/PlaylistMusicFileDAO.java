/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.MusicFile;
import model.Playlist;
import model.PlaylistMusicFile;

/**
 *
 * @author t.kint
 */
public class PlaylistMusicFileDAO extends MainDAO<PlaylistMusicFile> {

    private static PlaylistMusicFileDAO instance;

    private PlaylistMusicFileDAO() {
        super(PlaylistMusicFile.class);
    }

    public static PlaylistMusicFileDAO getInstance() {
        if (instance == null) {
            instance = new PlaylistMusicFileDAO();
        }
        return instance;
    }
    
    public List<PlaylistMusicFile> getMusicFilesByIdPlaylist(int id) {
        return getEntitiesByEntityReferenceId(MusicFile.class, id);
    }
    
    public List<PlaylistMusicFile> getPlaylistsByIdMusicFile(int id) {
        return getEntitiesByEntityReferenceId(Playlist.class, id);
    }
}
