/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotations.ESLEntity;
import annotations.ESLField;
import annotations.ESLId;
import java.util.ArrayList;

/**
 *
 * @author Thomas
 */
@ESLEntity(name = "playlist")
public class Playlist {

    @ESLId
    @ESLField(name = "id_playlist")
    public int id;

    @ESLField(name = "id_user")
    public int idUser;
    
    @ESLField(name = "name")
    public String name;

    @ESLField(name = "shared")
    public boolean shared;

    public ArrayList<MusicFile> musicFiles;

    public Playlist(int idUser, String name) {
        this.idUser = idUser;
        this.name = name;
        this.shared = false;
        this.musicFiles = new ArrayList<>();
    }

    public Playlist(int id, int idUser, String name, boolean shared) {
        this.id = id;
        this.idUser = idUser;
        this.name = name;
        this.shared = shared;
        this.musicFiles = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public ArrayList<MusicFile> getMusicFiles() {
        return musicFiles;
    }

    public void setMusicFiles(ArrayList<MusicFile> musicFiles) {
        this.musicFiles = musicFiles;
    }

    @Override
    public String toString() {
        return "Playlist{" + "id=" + id + ", name=" + name + ", shared=" + shared + ", musicFiles=" + musicFiles + '}';
    }

    public int getLastMusicFileId() {
        int id = 0;

        for (MusicFile musicFile : musicFiles) {
            if (musicFile.id > id) {
                id = musicFile.id;
            }
        }

        return id;
    }

    public void addMusicFile(MusicFile musicFile) {
        musicFiles.add(musicFile);
    }
}
