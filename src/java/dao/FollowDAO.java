/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Follow;
import model.User;

/**
 *
 * @author t.kint
 */
public class FollowDAO extends MainDAO<Follow> {

    private static FollowDAO instance;

    private FollowDAO() {
        super(Follow.class);
    }

    public static FollowDAO getInstance() {
        if (instance == null) {
            instance = new FollowDAO();
        }
        return instance;
    }
    
    public List<Follow> getFollowingsByIdUser(int id) {
        return getEntitiesByEntityReferenceId(User.class, id, 0);
    }
    
    public List<Follow> getFollowersByIdUser(int id) {
        return getEntitiesByEntityReferenceId(User.class, id, 1);
    }
}
