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
public class UserDAO {

    private static String table = "user";
    private static String[] fields = {"id", "email", "password", "pseudo"};

    /**
     * Remonte la liste des utilisateurs depuis la base de données
     *
     * @return ArrayList<User>
     */
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet rs = Connexion.getInstance().executeQuery("SELECT " + getFields(true) + " FROM " + table);

            while (rs.next()) {
                users.add(mapUser(rs));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            return users;
        }
    }

    /**
     * Remonte l'utilisateur correspondant à l'id spécifié
     *
     * @param id
     * @return User
     */
    public static User getUserById(int id) {
        User user = null;

        try {
            ResultSet rs = Connexion.getInstance().executeQuery(
                    "SELECT " + getFields(true) + " FROM " + table
                    + " WHERE id = " + id);

            if (rs.first()) {
                user = mapUser(rs);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return user;
    }

    /**
     * Remonte l'utilisateur à l'index spécifié
     *
     * @param index
     * @return User
     */
    public static User getUserByIndex(int index) {
        User user = new User();
        ArrayList<User> users = getUsers();

        if (users.size() > index) {
            user = users.get(index);
        }

        return user;
    }

    /**
     * Remonte le dernier utilisateur créé
     *
     * @return int
     */
    public static int getLastUserId() {
        int id = -1;

        try {
            ResultSet rs = Connexion.getInstance().executeQuery("SELECT MAX(id) FROM " + table);

            if (rs.first()) {
                id = rs.getInt("id");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return id;
    }

    /**
     * Remonte un utilisateur correspondant à l'email spécifié
     *
     * @param email
     * @return User
     */
    public static User getUserByEmail(String email) {
        User user = null;

        try {
            ResultSet rs = Connexion.getInstance().executeQuery(
                    "SELECT " + getFields(true) + " FROM " + table
                    + " WHERE email = '" + email + "'");

            if (rs.first()) {
                user = mapUser(rs);
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
    public static User getUserByEmailAndPassword(String email, String password) {
        User user = null;

        try {
            ResultSet rs = Connexion.getInstance().executeQuery(
                    "SELECT " + getFields(true) + " FROM " + table
                    + " WHERE email = '" + email + "' AND password = '" + password + "'");

            if (rs.first()) {
                user = mapUser(rs);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return user;
    }

    public static User createUser(User user) {
        try {
            int nb = Connexion.getInstance().executeUpdate(
                    "INSERT INTO " + table + "(" + getFields(false) + ") VALUES("
                    + "'" + user.getEmail() + "', "
                    + "'" + user.getPassword() + "', "
                    + "'" + user.getPseudo() + "'"
                    + ")"
            );

            if (nb > 0) {
                user = getUserByEmail(user.getEmail());
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return user;
    }

    /**
     * Mape un resultset avec un objet User
     *
     * @param rs
     * @return User
     */
    private static User mapUser(ResultSet rs) {
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

    private static String getFields(boolean id) {
        String f = "";

        if (id) {
            f += fields[0] + ", ";
        }

        for (int i = 1; i < fields.length; i++) {
            f += fields[i];
            if (i < fields.length - 1) {
                f += ", ";
            }
        }

        return f;
    }
}
