/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Thomas
 */
public class Playlist {

    private int id;
    private String name;
    private boolean shared;
    private ArrayList<MusicFile> musicFiles;

    public Playlist(int id, String name) {
        this.id = id;
        this.name = name;
        this.shared = false;
        this.musicFiles = new ArrayList<>();
    }

    public Playlist(int id, String name, boolean shared) {
        this.id = id;
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
            if (musicFile.getId() > id) {
                id = musicFile.getId();
            }
        }

        return id;
    }

    public void addMusicFile(MusicFile musicFile) {
        musicFiles.add(musicFile);
    }
}
