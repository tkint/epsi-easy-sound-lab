/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import model.MusicFile;

/**
 *
 * @author tkint
 */
@Named(value = "editorBean")
@SessionScoped
public class EditorBean implements Serializable {

    private MusicFile currentMusicFile;
    private String currentMusicFileNewName;
    private String newMusicFileName;

    /**
     * Creates a new instance of MusicFileBean
     */
    public EditorBean() {
    }

    public MusicFile getCurrentMusicFile() {
        return currentMusicFile;
    }

    public void setCurrentMusicFile(MusicFile currentMusicFile) {
        this.currentMusicFile = currentMusicFile;
    }

    public String getCurrentMusicFileNewName() {
        return currentMusicFileNewName;
    }

    public void setCurrentMusicFileNewName(String currentMusicFileNewName) {
        this.currentMusicFileNewName = currentMusicFileNewName;
    }

    public String getNewMusicFileName() {
        return newMusicFileName;
    }

    public void setNewMusicFileName(String newMusicFileName) {
        this.newMusicFileName = newMusicFileName;
    }

    public String open(MusicFile musicFile) {
        if (musicFile != null) {
            currentMusicFile = musicFile;
            currentMusicFileNewName = musicFile.getName();

            return "editor?faces-redirect=true";
        }
        return "index?faces-redirect=true";
    }

    public void close() {
        currentMusicFile = null;
    }

    public String rename() {
        currentMusicFile.setName(currentMusicFileNewName);
        
        return open(currentMusicFile);
    }
}
