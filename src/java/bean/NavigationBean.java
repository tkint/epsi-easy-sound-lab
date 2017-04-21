/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author tkint
 */
@Named(value = "navigationBean")
@SessionScoped
public class NavigationBean implements Serializable {

    private int index;
    
    /**
     * Creates a new instance of NavigationBean
     */
    public NavigationBean() {
        this.index = -1;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
