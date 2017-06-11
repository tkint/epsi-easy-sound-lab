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
public class FolderDAO extends MainDAO<Folder> {

    public FolderDAO() {
        super(Folder.class);
    }

    public List<Folder> getFoldersByIdUser(int id) {
        List<Folder> folders = new ArrayList<>();

        try {
            String query = "SELECT " + fullFields + " FROM " + table + " WHERE id_user = " + id + "";

            ResultSet rs = Connexion.getInstance().executeQuery(query);

            while (rs.next()) {
                folders.add(mapEntity(rs));
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return folders;
    }
}
