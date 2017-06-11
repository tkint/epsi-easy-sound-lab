/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotations.*;

/**
 *
 * @author tkint
 */
@ESLEntity(name = "musicfile")
public class MusicFile {

    @ESLId
    @ESLField(name = "id_musicfile")
    public int id;

    @ESLField(name = "name")
    public String name;

    @ESLField(name = "duration")
    public float duration;

    public MusicFile(int id, String name, float duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "MusicFile{" + "id=" + id + ", name=" + name + ", duration=" + duration + '}';
    }
}
