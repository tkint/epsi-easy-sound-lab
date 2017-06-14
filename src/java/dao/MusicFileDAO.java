/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import model.*;

/**
 *
 * @author tkint
 */
public class MusicFileDAO extends MainDAO<MusicFile> {

    private FolderDAO folderDAO;
    
    public MusicFileDAO() {
        super(MusicFile.class);
        folderDAO = new FolderDAO();
    }

    public List<MusicFile> getMusicFilesByIdFolder(int id) {
        return getEntitiesByEntityReferenceId(Folder.class, id);
    }
    
    public List<MusicFile> getMusicFilesByIdUser(int id) {
        List<MusicFile> musicFiles = new ArrayList<>();
        
        for (Folder folder : folderDAO.getFoldersByIdUser(id)) {
            musicFiles.addAll(getMusicFilesByIdFolder(folder.id));
        }
        
        return musicFiles;
    }
    
    public List<MusicFile> getSharedMusicFilesByIdUser(int id) {
        List<MusicFile> musicFiles = getMusicFilesByIdUser(id);
        musicFiles.removeIf(mf -> !mf.shared);
        return musicFiles;
    }
}
