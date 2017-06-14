/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.*;

/**
 *
 * @author tkint
 */
public class PlaylistDAO extends MainDAO<Playlist> {

    public PlaylistDAO() {
        super(Playlist.class);
    }

    public List<Playlist> getPlaylistsByIdUser(int id) {
        return getEntitiesByEntityReferenceId(User.class, id);
    }
}
