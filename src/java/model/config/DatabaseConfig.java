/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.config;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author tkint
 */
public class DatabaseConfig {

    static DatabaseConfig config = null;

    public static DatabaseConfig getInstance() throws NamingException {
        if (config == null) {
            config = (DatabaseConfig) ((new InitialContext()).lookup("DATABASE-Config"));
        }
        return config;
    }

    private String user;
    private String folder;
    private String playlist;
    private String musicfile;
    private String mail;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getPlaylist() {
        return playlist;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }

    public String getMusicfile() {
        return musicfile;
    }

    public void setMusicfile(String musicfile) {
        this.musicfile = musicfile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
