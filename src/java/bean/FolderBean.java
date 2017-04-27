/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import model.Folder;
import model.User;

/**
 *
 * @author tkint
 */
@Named(value = "folderBean")
@SessionScoped
public class FolderBean implements Serializable {

    private Folder currentFolder;
    private String currentFolderNewName;
    private String newFolderName;

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

    public String getCurrentFolderNewName() {
        return currentFolderNewName;
    }

    public void setCurrentFolderNewName(String currentFolderNewName) {
        this.currentFolderNewName = currentFolderNewName;
    }

    public String getNewFolderName() {
        return newFolderName;
    }

    public void setNewFolderName(String newFolderName) {
        this.newFolderName = newFolderName;
    }

    public String open(Folder folder) {
        if (folder != null) {
            currentFolder = folder;
            currentFolderNewName = folder.getName();

            return "folder?faces-redirect=true";
        }
        return "index?faces-redirect=true";
    }

    public void close() {
        currentFolder = null;
    }

    public String addFolder() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserBean userBean = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);

        Folder folder = new Folder(userBean.getCurrentUser().getLastFolderId() + 1, newFolderName);
        userBean.getCurrentUser().addFolder(folder);
        newFolderName = null;

        return open(folder);
    }

    public String rename() {
        currentFolder.setName(currentFolderNewName);
        
        return open(currentFolder);
    }

    public String delete() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserBean userBean = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);

        User user = userBean.getCurrentUser();

        Folder folder = null;

        if (currentFolder.getId() < user.getLastFolderId()) {
            folder = user.getFolderById(currentFolder.getId() + 1);
        } else {
            folder = user.getFolderById(currentFolder.getId() - 1);
        }
        user.deleteFolderById(currentFolder.getId());

        return open(folder);
    }
}
