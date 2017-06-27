/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.MusicFileDAO;
import java.io.File;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import model.MusicFile;
import model.User;
import model.config.MainConfig;
import servlet.MusicFileServlet;

/**
 *
 * @author tkint
 */
@Named(value = "musicFileBean")
@SessionScoped
public class MusicFileBean implements Serializable {

    private MusicFile currentMusicFile;
    private String currentMusicFileNewName;
    private String newMusicFileName;

    private MusicFileDAO musicFileDAO;

    /**
     * Creates a new instance of MusicFileBean
     */
    public MusicFileBean() {
        musicFileDAO = MusicFileDAO.getInstance();
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
            currentMusicFileNewName = musicFile.name;

            currentMusicFile.file = new File("musicfiles/" + getFilePath(null));

            return "editor?faces-redirect=true";
        }
        return "index?faces-redirect=true";
    }

    public void close() {
        currentMusicFile = null;
    }

    public String rename() {
        currentMusicFile.name = currentMusicFileNewName;

        return open(currentMusicFile);
    }

    public String getFilePath(MusicFile musicFile) {
        String path = "";

        try {
            if (musicFile == null) {
                musicFile = currentMusicFile;
            }

            MainConfig config = MainConfig.getInstance();

            //path += config.getFilesPath();
            FacesContext context = FacesContext.getCurrentInstance();
            UserBean userBean = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);

            User user = userBean.getCurrentUser();

            path = user.id + "/" + musicFile.idFolder + "/" + musicFile.id + "/" + musicFile.id + musicFile.extension;

        } catch (NamingException ex) {
            System.out.println(ex.getMessage());
        }

        return path;
    }

    public String delete() {
        String redirect = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FolderBean folderBean = context.getApplication().evaluateExpressionGet(context, "#{folderBean}", FolderBean.class);

        if (MusicFileServlet.deleteMusicFile()) {
            musicFileDAO.deleteEntity(currentMusicFile);

            currentMusicFile = null;
            currentMusicFileNewName = null;
            
            redirect = folderBean.open(folderBean.getCurrentFolder());
        }

        return redirect;
    }
}
