/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.FolderDAO;
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
    
    private FolderDAO folderDAO;

    /**
     * Creates a new instance of FolderBean
     */
    public FolderBean() {
        folderDAO = new FolderDAO();
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
            currentFolderNewName = folder.name;

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
        
        Folder folder = new Folder(userBean.getCurrentUser().id, newFolderName);
        
        folder = folderDAO.createEntity(folder);
        
        userBean.getCurrentUser().addFolder(folder);
        
        newFolderName = null;

        return open(folder);
    }

    public String rename() {
        currentFolder.name = currentFolderNewName;
        
        folderDAO.updateEntity(currentFolder);
        
        return open(currentFolder);
    }

    public String delete() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserBean userBean = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);

        User user = userBean.getCurrentUser();

        Folder folder = null;

        // TODO Modifier le fonctionnement avec l'index des folders plutôt que les IDs
        if (currentFolder.id < user.getLastFolderId()) {
            folder = user.getFolderById(currentFolder.id + 1);
        } else {
            folder = user.getFolderById(currentFolder.id - 1);
        }
        
        user.deleteFolderById(currentFolder.id);
        
        folderDAO.deleteEntity(currentFolder);

        return open(folder);
    }
}
