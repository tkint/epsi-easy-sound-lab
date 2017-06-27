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
@ESLEntity(name = "follow")
public class Follow extends JSONAble {

    @ESLField(name = "id_follower")
    @ESLOneToMany(entity = User.class)
    public int idFollower;

    @ESLField(name = "id_following")
    @ESLOneToMany(entity = User.class)
    public int idFollowing;

    public Follow() {
    }

    public Follow(int idFollower, int idFollowing) {
        this.idFollower = idFollower;
        this.idFollowing = idFollowing;
    }

    public int getIdFollower() {
        return idFollower;
    }

    public void setIdFollower(int idFollower) {
        this.idFollower = idFollower;
    }

    public int getIdFollowing() {
        return idFollowing;
    }

    public void setIdFollowing(int idFollowing) {
        this.idFollowing = idFollowing;
    }
}
