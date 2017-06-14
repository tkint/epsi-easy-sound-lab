/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import annotations.*;
import java.util.List;

/**
 *
 * @author tkint
 */
@ESLEntity(name = "user")
public class User {

    @ESLId
    @ESLField(name = "id_user")
    public int id;

    @ESLField(name = "email")
    public String email;

    @ESLField(name = "password")
    public String password;

    @ESLField(name = "firstname")
    public String firstName;

    @ESLField(name = "lastname")
    public String lastName;

    @ESLField(name = "pseudo")
    public String pseudo;

    public List<Folder> folders;
    public List<Playlist> playlists;
    public List<User> followers;
    public List<User> following;

    public User() {
        this.id = -1;
        this.folders = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
    }

    public User(String email, String password, String firstName, String lastName, String pseudo) {
        this.id = -1;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pseudo = pseudo;
        this.folders = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
    }

    public User(int id, String email, String password, String firstName, String lastName, String pseudo) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pseudo = pseudo;
        this.folders = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public List<Folder> getFolders() {
        return folders;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName + ", pseudo=" + pseudo + '}';
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }

    public Folder getFolderById(int id) {
        int i = 0;
        Folder folder = null;

        while (i < folders.size() && folder == null) {
            if (folders.get(i).id == id) {
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
            if (folders.get(i).name.equals(name)) {
                folder = folders.get(i);
            }
            i++;
        }

        return folder;
    }

    public int getLastFolderId() {
        int id = 0;

        for (Folder folder : folders) {
            if (folder.id > id) {
                id = folder.id;
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
        if (this.getFolderByName(folder.name) == null) {
            folders.add(folder);
        }
    }

    public void addMusicFileToFolder(MusicFile musicFile, int folderId) {
        Folder folder = getFolderById(folderId);

        if (folder != null) {
            folder.addMusicFile(musicFile);
        }
    }

    public void deleteFolderById(int id) {
        int i = 0;
        boolean trouve = false;

        while (i < folders.size() && !trouve) {
            if (folders.get(i).id == id) {
                folders.remove(i);
                trouve = true;
            }
            i++;
        }
    }

    public Playlist getPlaylistById(int id) {
        int i = 0;
        Playlist playlist = null;

        while (i < playlists.size() && playlist == null) {
            if (playlists.get(i).id == id) {
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
            if (playlists.get(i).name.equals(name)) {
                playlist = playlists.get(i);
            }
            i++;
        }

        return playlist;
    }

    public int getLastPlaylistId() {
        int id = 0;

        for (Playlist playlist : playlists) {
            if (playlist.id > id) {
                id = playlist.id;
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
        if (this.getPlaylistByName(playlist.name) == null) {
            playlists.add(playlist);
        }
    }

    public void addMusicFileToPlaylist(MusicFile musicFile, int playlistId) {
        Playlist playlist = getPlaylistById(playlistId);

        if (playlist != null) {
            playlist.addMusicFile(musicFile);
        }
    }

    public void deletePlaylistById(int id) {
        int i = 0;
        boolean trouve = false;

        while (i < playlists.size() && !trouve) {
            if (playlists.get(i).id == id) {
                playlists.remove(i);
                trouve = true;
            }
            i++;
        }
    }
}
