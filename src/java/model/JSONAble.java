/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author tkint
 */
public abstract class JSONAble {

    public String toJSON() {
        Gson gson = new GsonBuilder().create();

        return gson.toJson(this);
    }
}
