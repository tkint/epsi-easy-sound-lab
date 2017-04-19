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
public class Folder {

    private int id;
    private String name;
    private ArrayList<MusicFile> musicFiles;

    public Folder(int id, String name) {
        this.id = id;
        this.name = name;
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

    public ArrayList<MusicFile> getMusicFiles() {
        return musicFiles;
    }

    public void setMusicFiles(ArrayList<MusicFile> musicFiles) {
        this.musicFiles = musicFiles;
    }

    @Override
    public String toString() {
        return "Folder{" + "id=" + id + ", name=" + name + ", musicFiles=" + musicFiles + '}';
    }

    public void addMusicFile(MusicFile musicFile) {
        musicFiles.add(musicFile);
    }
}
