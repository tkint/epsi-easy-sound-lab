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
public class MailDAO extends MainDAO<Mail> {

    private static MailDAO instance;

    private MailDAO() {
        super(Mail.class);
    }

    public static MailDAO getInstance() {
        if (instance == null) {
            instance = new MailDAO();
        }
        return instance;
    }

    public List<Mail> getMailsByIdAuthor(int id) {
        return getEntitiesByEntityReferenceId(User.class, id, 0);
    }
    
    public List<Mail> getMailsByIdTarget(int id) {
        return getEntitiesByEntityReferenceId(User.class, id, 1);
    }
    
    public List<Integer> getMailsIdByIdAuthor(int id) {
        return getEntitiesIdByEntityReferenceId(User.class, id, 0);
    }
    
    public List<Integer> getMailsIdByIdTarget(int id) {
        return getEntitiesIdByEntityReferenceId(User.class, id, 1);
    }
}
