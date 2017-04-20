/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import model.Folder;

/**
 *
 * @author tkint
 */
@Named(value = "folderBean")
@SessionScoped
public class FolderBean implements Serializable {

    private boolean browsing;
    private Folder currentFolder;
    private String newFolderName;

    /**
     * Creates a new instance of FolderBean
     */
    public FolderBean() {
    }
    
    @PostConstruct
    public void init() {
        this.browsing = true;
    }

    public boolean isBrowsing() {
        return browsing;
    }

    public void setBrowsing(boolean browsing) {
        this.browsing = browsing;
    }

    public Folder getCurrentFolder() {
        return currentFolder;
    }

    public void setCurrentFolder(Folder currentFolder) {
        this.currentFolder = currentFolder;
    }

    public String getNewFolderName() {
        return newFolderName;
    }

    public void setNewFolderName(String newFolderName) {
        this.newFolderName = newFolderName;
    }

    public String open(Folder currentFolder) {
        this.currentFolder = currentFolder;

        return "folder";
    }

    public void close() {
        this.currentFolder = null;
    }

    public String addFolder() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserBean userBean = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);

        Folder folder = new Folder(userBean.getCurrentUser().getLastFolderId() + 1, newFolderName);
        userBean.getCurrentUser().addFolder(folder);
        newFolderName = null;

        return open(folder);
    }
}
