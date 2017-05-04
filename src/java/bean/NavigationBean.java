/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.net.MalformedURLException;
import javax.faces.context.FacesContext;

/**
 *
 * @author tkint
 */
@Named(value = "navigationBean")
@SessionScoped
public class NavigationBean implements Serializable {

    private int index;
    private boolean panelRight;

    /**
     * Creates a new instance of NavigationBean
     */
    public NavigationBean() {
        this.index = -1;
        this.panelRight = false;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isPanelRight() {
        return panelRight;
    }

    public void setPanelRight(boolean panelRight) {
        this.panelRight = panelRight;
    }

    public void updateIndex() {
        FacesContext context = FacesContext.getCurrentInstance();
        String page = context.getViewRoot().getViewId();
        page = page.substring(1, page.length() - 6);
        switch (page) {
            case "profile":
                index = 0;
                break;
            case "mailbox":
                index = 1;
                break;
            case "folder": case "editor":
                index = 2;
                break;
            case "playlist":
                index = 3;
                break;
            case "activity":
                index = 4;
                break;
            default:
                index = -1;
                break;
        }
    }
    
    public String getCssFile() throws MalformedURLException {
        String cssPath;
        FacesContext context = FacesContext.getCurrentInstance();
        String page = context.getViewRoot().getViewId();
        page = page.substring(1, page.length() - 6);
        
        switch (page) {
            case "folder": case "playlist":
                cssPath = "folder";
                break;
            default:
                cssPath = page;
                break;
        }
        
        return cssPath;
    }

    public void switchPanelRight() {
        panelRight = !panelRight;
    }
}
