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

    private static MusicFileDAO instance;
    private FolderDAO folderDAO;
    private PlaylistMusicFileDAO playlistMusicFileDAO;

    private MusicFileDAO() {
        super(MusicFile.class);
        folderDAO = FolderDAO.getInstance();
        playlistMusicFileDAO = PlaylistMusicFileDAO.getInstance();
    }

    public static MusicFileDAO getInstance() {
        if (instance == null) {
            instance = new MusicFileDAO();
        }
        return instance;
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

    public List<MusicFile> getMusicFilesByIdPlaylist(int idUser, int id) {
        List<MusicFile> musicFiles = getMusicFilesByIdUser(idUser);
        List<PlaylistMusicFile> playlistMusicFiles = playlistMusicFileDAO.getMusicFilesByIdPlaylist(id);
        List<MusicFile> mf = new ArrayList<>();

        for (PlaylistMusicFile playlistMusicFile : playlistMusicFiles) {
            for (MusicFile musicFile : musicFiles) {
                if (musicFile.id == playlistMusicFile.idMusicFile) {
                    mf.add(musicFile);
                }
            }
        }

        return mf;
    }
}
