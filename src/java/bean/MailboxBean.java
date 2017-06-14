/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.MailDAO;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import model.Mail;

/**
 *
 * @author tkint
 */
@Named(value = "mailboxBean")
@SessionScoped
public class MailboxBean implements Serializable {

    private int target;
    private String title;
    private String content;

    private String display;
    
    private MailDAO mailDAO;

    /**
     * Creates a new instance of FolderBean
     */
    public MailboxBean() {
        mailDAO = new MailDAO();
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
    
    public String send() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserBean userBean = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);
        
        display = "inbox";
        
        Mail mail = new Mail(userBean.getCurrentUser().id, target, title, content);
        
        mailDAO.createEntity(mail);

        return "mailbox?faces-redirect=true";
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
