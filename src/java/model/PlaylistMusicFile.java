/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotations.*;

/**
 *
 * @author tkint
 */
@ESLEntity(name = "playlistmusicfile")
public class PlaylistMusicFile extends JSONAble {

    @ESLField(name = "id_playlist")
    @ESLOneToMany(entity = Playlist.class)
    public int idPlaylist;

    @ESLField(name = "id_musicfile")
    @ESLOneToMany(entity = MusicFile.class)
    public int idMusicFile;

    public PlaylistMusicFile() {
    }

    public PlaylistMusicFile(int idPlaylist, int idMusicFile) {
        this.idPlaylist = idPlaylist;
        this.idMusicFile = idMusicFile;
    }

    public int getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(int idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public int getIdMusicFile() {
        return idMusicFile;
    }

    public void setIdMusicFile(int idMusicFile) {
        this.idMusicFile = idMusicFile;
    }
}
