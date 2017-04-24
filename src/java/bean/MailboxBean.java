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
@Named(value = "mailboxBean")
@SessionScoped
public class MailboxBean implements Serializable {

    private String display;

    /**
     * Creates a new instance of FolderBean
     */
    public MailboxBean() {
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String newMail() {
        display = "new";

        return "mailbox?faces-redirect=true";
    }

    public String inbox() {
        display = "inbox";

        return "mailbox?faces-redirect=true";
    }

    public String sent() {
        display = "sent";

        return "mailbox?faces-redirect=true";
    }
}
