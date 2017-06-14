/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import model.*;

/**
 *
 * @author tkint
 */
public class MailDAO extends MainDAO<Mail> {

    private FolderDAO folderDAO;
    
    public MailDAO() {
        super(Mail.class);
    }

    public List<Mail> getMailByIdUser(int id) {
        return getEntitiesByEntityReferenceId(User.class, id);
    }
}
