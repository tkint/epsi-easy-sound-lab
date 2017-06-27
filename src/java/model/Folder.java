/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotations.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tkint
 */
@ESLEntity(name = "folder")
public class Folder {

    @ESLId
    @ESLField(name = "id_folder")
    public int id;

    @ESLField(name = "id_user")
    @ESLOneToMany(entity = User.class)
    public int idUser;

    @ESLField(name = "name")
    public String name;

    public List<MusicFile> musicFiles;

    public Folder() {
        this.musicFiles = new ArrayList<>();
    }

    public Folder(int idUser, String name) {
        this.idUser = idUser;
        this.name = name;
        this.musicFiles = new ArrayList<>();
    }

    public Folder(int id, int idUser, String name) {
        this.id = id;
        this.idUser = idUser;
        this.name = name;
        this.musicFiles = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MusicFile> getMusicFiles() {
        return musicFiles;
    }

    public void setMusicFiles(List<MusicFile> musicFiles) {
        this.musicFiles = musicFiles;
    }

    @Override
    public String toString() {
        return "Folder{" + "id=" + id + "id_user=" + idUser + ", name=" + name + ", musicFiles=" + musicFiles + '}';
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
