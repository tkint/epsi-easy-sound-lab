/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Follow;
import model.User;

/**
 *
 * @author t.kint
 */
public class UserDAO extends MainDAO<User> {

    private static UserDAO instance;
    private FollowDAO followDAO;

    private UserDAO() {
        super(User.class);
        followDAO = FollowDAO.getInstance();
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    /**
     * Remonte un utilisateur correspondant à l'email spécifié
     *
     * @param email
     * @return User
     */
    public User getUserByEmail(String email) {
        User user = null;

        try {
            String query = "SELECT " + fullFields + " FROM " + table + " WHERE email = '" + email + "'";

            ResultSet rs = Connexion.getInstance().executeQuery(query);

            if (rs.next()) {
                user = mapEntity(rs);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return user;
    }

    public List<User> getUsersByEmailPseudoName(String value) {
        List<User> users = new ArrayList<>();

        try {
            String query = "SELECT " + fullFields + " FROM " + table + " WHERE pseudo LIKE '%" + value + "%'"
                    + " OR (email LIKE '%" + value + "%' AND public_email = 1)"
                    + " OR ((lastname LIKE '%" + value + "%' OR firstname LIKE '%" + value + "%') AND public_name = 1)";

            ResultSet rs = Connexion.getInstance().executeQuery(query);

            while (rs.next()) {
                User user = mapEntity(rs);
                users.add(user);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return users;
    }

    /**
     * Remonte un utilisateur correspondant à l'email et au password spécifiés
     *
     * @param email
     * @param password
     * @return User
     */
    public User getUserByEmailAndPassword(String email, String password) {
        User user = null;

        try {
            String query = "SELECT " + fullFields + " FROM " + table + " WHERE email = '" + email + "' AND password = '" + password + "'";

            ResultSet rs = Connexion.getInstance().executeQuery(query);

            if (rs.next()) {
                user = mapEntity(rs);
            }

        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    public List<User> getFollowersByIdUser(int id) {
        List<User> followers = new ArrayList<>();

        for (Follow follow : followDAO.getFollowersByIdUser(id)) {
            followers.add(getEntityById(follow.idFollower));
        }

        return followers;
    }

    public List<User> getFollowingsByIdUser(int id) {
        List<User> followings = new ArrayList<>();

        for (Follow follow : followDAO.getFollowingsByIdUser(id)) {
            followings.add(getEntityById(follow.idFollowing));
        }

        return followings;
    }

    public boolean isUserFollowedByUser(User followed, User follower) {
        if (followed != null) {
            followed.followers = getFollowersByIdUser(followed.id);
            return followed.isFollowedByUser(follower);
        }
        return false;
    }
    
    @Override
    public int deleteEntity(User entity) {
        for (Follow follower : followDAO.getFollowersByIdUser(entity.id)) {
            followDAO.deleteEntity(follower);
        }
        for (Follow following : followDAO.getFollowingsByIdUser(entity.id)) {
            followDAO.deleteEntity(following);
        }
        
        return super.deleteEntity(entity);
    }
}
