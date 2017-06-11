/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author t.kint
 */
public class UserDAO extends MainDAO<User> {

    public UserDAO() {
        super(User.class);
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
}
