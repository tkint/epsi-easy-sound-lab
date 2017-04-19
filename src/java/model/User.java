/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author tkint
 */
public class User {

    private int id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String eMail;
    private ArrayList<Folder> folders;

    public User(int id) {
        this.id = id;
        this.folders = new ArrayList<>();
    }

    public User(int id, String login, String password, String firstName, String lastName, String eMail) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.folders = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public ArrayList<Folder> getFolders() {
        return folders;
    }

    public void setFolders(ArrayList<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login=" + login + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName + ", eMail=" + eMail + '}';
    }
    
    public String getFullName() {
        return lastName + " " + firstName;
    }
    
    public Folder getFolderById(int id) {
        int i = 0;
        Folder folder = null;
        
        while (i < folders.size() && folder == null) {
            if (folders.get(i).getId() == id) {
                folder = folders.get(i);
            }
        }
        
        return folder;
    }
    
    public void addFolder(Folder folder) {
        folders.add(folder);
    }
    
    public void addMusicFileToFolder(MusicFile musicFile, int folderId) {
        Folder folder = getFolderById(folderId);
        
        if (folder != null) {
            folder.addMusicFile(musicFile);
        }
    }
}
