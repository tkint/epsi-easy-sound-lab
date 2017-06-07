/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author t.kint
 */
public class UserDAO extends MainDAO<User> {

    public UserDAO() {
        super(UserDAO.class);
    }

    @Override
    public ArrayList<User> getEntities() {
        ArrayList<User> users = new ArrayList<>();

        try {
            ResultSet rs = Connexion.getInstance().executeQuery("SELECT id, email, password, pseudo FROM " + table);

            while (rs.next()) {
                users.add(mapEntity(rs));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return users;
    }

    @Override
    public User getEntityById(int id) {
        User user = null;

        try {
            ResultSet rs = Connexion.getInstance().executeQuery(
                    "SELECT id, email, password, pseudo FROM " + table
                    + " WHERE id = " + id);

            if (rs.next()) {
                user = mapEntity(rs);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return user;
    }

    @Override
    public User getEntityByIndex(int index) {
        User user = new User();
        ArrayList<User> users = getEntities();

        if (users.size() > index) {
            user = users.get(index);
        }

        return user;
    }

    @Override
    public User getLastEntity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getLastEntityId() {
        int id = -1;

        try {
            ResultSet rs = Connexion.getInstance().executeQuery("SELECT MAX(id) AS id FROM " + table);

            if (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return id;
    }

    @Override
    public User createEntity(User entity) {
        try {
            Connexion connexion = Connexion.getInstance();
            int nb = connexion.executeUpdate(
                    "INSERT INTO " + table + "(email, password, pseudo) VALUES("
                    + "'" + entity.getEmail() + "', "
                    + "'" + entity.getPassword() + "', "
                    + "'" + entity.getPseudo() + "'"
                    + ")"
            );

            if (nb > 0) {
                ResultSet rs = connexion.getStatement().getGeneratedKeys();
                rs.next();
                entity.setId(rs.getInt(1));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return entity;
    }

    @Override
    public int deleteEntity(User entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Mape un resultset avec un objet User
     *
     * @param rs
     * @return User
     */
    @Override
    protected User mapEntity(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setPseudo(rs.getString("pseudo"));
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
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
            ResultSet rs = Connexion.getInstance().executeQuery(
                    "SELECT id, email, password, pseudo FROM " + table
                    + " WHERE email = '" + email + "'");

            if (rs.next()) {
                user = mapEntity(rs);
            }

        } catch (SQLException ex) {
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
            ResultSet rs = Connexion.getInstance().executeQuery(
                    "SELECT id, email, password, pseudo FROM " + table
                    + " WHERE email = '" + email + "' AND password = '" + password + "'");

            if (rs.next()) {
                user = mapEntity(rs);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return user;
    }
}
