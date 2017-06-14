/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotations.*;
import java.io.File;

/**
 *
 * @author tkint
 */
@ESLEntity(name = "musicfile")
public class MusicFile {

    @ESLId
    @ESLField(name = "id_musicfile")
    public int id;

    @ESLField(name = "id_folder")
    @ESLReference(entity = Folder.class)
    public int idFolder;

    @ESLField(name = "name")
    public String name;

    @ESLField(name = "extension")
    public String extension;

    @ESLField(name = "duration")
    public int duration;

    @ESLField(name = "shared")
    public boolean shared;

    @ESLField(name = "absolutepath")
    public String absolutePath;

    @ESLField(name = "path")
    public String path;

    public File file;

    public MusicFile() {
    }

    public MusicFile(int idFolder, String name, String extension, int duration, boolean shared) {
        this.idFolder = idFolder;
        this.name = name;
        this.extension = extension;
        this.duration = duration;
        this.shared = shared;
        this.absolutePath = "";
        this.path = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFolder() {
        return idFolder;
    }

    public void setIdFolder(int idFolder) {
        this.idFolder = idFolder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "MusicFile{" + "id=" + id + ", name=" + name + ", duration=" + duration + '}';
    }
}
