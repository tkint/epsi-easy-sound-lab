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
    private ArrayList<Playlist> playlists;

    public User(int id) {
        this.id = id;
        this.folders = new ArrayList<>();
        this.playlists = new ArrayList<>();
    }

    public User(int id, String login, String password, String firstName, String lastName, String eMail) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.folders = new ArrayList<>();
        this.playlists = new ArrayList<>();
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

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
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
            i++;
        }

        return folder;
    }

    public Folder getFolderByName(String name) {
        int i = 0;
        Folder folder = null;

        while (i < folders.size() && folder == null) {
            if (folders.get(i).getName().equals(name)) {
                folder = folders.get(i);
            }
            i++;
        }

        return folder;
    }

    public int getLastFolderId() {
        int id = 0;

        for (Folder folder : folders) {
            if (folder.getId() > id) {
                id = folder.getId();
            }
        }

        return id;
    }
    
    public Folder getLastFolder() {
        Folder folder = null;
        
        folder = folders.get(folders.size() - 1);
        
        return folder;
    }

    public void addFolder(Folder folder) {
        if (this.getFolderByName(folder.getName()) == null) {
            folders.add(folder);
        }
    }

    public void addMusicFileToFolder(MusicFile musicFile, int folderId) {
        Folder folder = getFolderById(folderId);

        if (folder != null) {
            folder.addMusicFile(musicFile);
        }
    }
    
    public Playlist getPlaylistById(int id) {
        int i = 0;
        Playlist playlist = null;

        while (i < playlists.size() && playlist == null) {
            if (playlists.get(i).getId() == id) {
                playlist = playlists.get(i);
            }
            i++;
        }

        return playlist;
    }

    public Playlist getPlaylistByName(String name) {
        int i = 0;
        Playlist playlist = null;

        while (i < playlists.size() && playlist == null) {
            if (playlists.get(i).getName().equals(name)) {
                playlist = playlists.get(i);
            }
            i++;
        }

        return playlist;
    }

    public int getLastPlaylistId() {
        int id = 0;

        for (Playlist playlist : playlists) {
            if (playlist.getId() > id) {
                id = playlist.getId();
            }
        }

        return id;
    }
    
    public Playlist getLastPlaylist() {
        Playlist playlist = null;
        
        playlist = playlists.get(playlists.size() - 1);
        
        return playlist;
    }

    public void addPlaylist(Playlist playlist) {
        if (this.getPlaylistByName(playlist.getName()) == null) {
            playlists.add(playlist);
        }
    }

    public void addMusicFileToPlaylist(MusicFile musicFile, int playlistId) {
        Playlist playlist = getPlaylistById(playlistId);

        if (playlist != null) {
            playlist.addMusicFile(musicFile);
        }
    }
}
