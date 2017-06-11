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

    public PlaylistDAO() {
        super(Playlist.class);
    }

    public List<Playlist> getPlaylistsByIdUser(int id) {
        List<Playlist> playlists = new ArrayList<>();

        try {
            String query = "SELECT " + fullFields + " FROM " + table + " WHERE id_user = " + id + "";

            ResultSet rs = Connexion.getInstance().executeQuery(query);

            while (rs.next()) {
                playlists.add(mapEntity(rs));
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return playlists;
    }
}
