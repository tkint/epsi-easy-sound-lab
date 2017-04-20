/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import model.Folder;

/**
 *
 * @author tkint
 */
@Named(value = "folderBean")
@SessionScoped
public class FolderBean implements Serializable {
    
    private Folder currentFolder;

    private int id;

    /**
     * Creates a new instance of FolderBean
     */
    public FolderBean() {
    }

    public Folder getCurrentFolder() {
        return currentFolder;
    }

    public void setCurrentFolder(Folder currentFolder) {
        this.currentFolder = currentFolder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String open(Folder currentFolder) {
        this.currentFolder = currentFolder;
        
        return "folder";
    }

    public void close() {
        this.currentFolder = null;
    }
}
