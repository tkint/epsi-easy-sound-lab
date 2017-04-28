/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import model.User;

/**
 *
 * @author t.kint
 */
public class UserDAO {

    // TODO Remonter la liste des utilisateurs depuis la base de donnée
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        users.add(new User());
        users.add(new User());

        return users;
    }

    // TODO Récupère la liste des utilisateurs grâce à getUsers et remonte 
    // l'utilisateur possédant l'id demandé
    // OU Récupère directement l'utilisateur demandé depuis la BDD (plus rapide)
    public static User getUserById(int id) {
        User user = getUsers().get(id);

        user.setLogin("Login " + id);

        return user;
    }

    // TODO Supprimer le -1 quand le constructeur de visiteur est créé
    public static User getUserByIndex(int index) {
        User user = new User(-1);
        ArrayList<User> users = getUsers();

        if (users.size() > index) {
            user = users.get(index);
        }

        return user;
    }

    // TODO Récupère la liste des utilisateurs grâce à getUsers et remonte 
    // l'utilisateur possédant les identifiants demandés
    // OU Récupère directement l'utilisateur demandé depuis la BDD (plus rapide)
    public static User getUserByLoginAndPassword(String login, String password) {
        User user = new User(login, password, "Firstname", "Lastname", "Email");

        return user;
    }
}
