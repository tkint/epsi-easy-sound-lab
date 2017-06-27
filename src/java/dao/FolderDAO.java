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
public class FolderDAO extends MainDAO<Folder> {

    private static FolderDAO instance;
    
    private FolderDAO() {
        super(Folder.class);
    }

    public static FolderDAO getInstance() {
        if (instance == null) {
            instance = new FolderDAO();
        }
        return instance;
    }

    public List<Folder> getFoldersByIdUser(int id) {
        return getEntitiesByEntityReferenceId(User.class, id);
    }
}
