/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Thomas
 */
public class Playlist extends Folder {
    
    private boolean shared;
    
    public Playlist(int id, String name) {
        super(id, name);
        this.shared = false;
    }
    
    public Playlist(int id, String name, boolean shared) {
        super(id, name);
        this.shared = shared;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }
}
