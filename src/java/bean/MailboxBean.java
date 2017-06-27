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
import model.User;

/**
 *
 * @author tkint
 */
@Named(value = "mailboxBean")
@SessionScoped
public class MailboxBean implements Serializable {

    private int idTarget;
    private String title;
    private String content;

    private User target;

    private String display;

    private MailDAO mailDAO;

    /**
     * Creates a new instance of FolderBean
     */
    public MailboxBean() {
        mailDAO = MailDAO.getInstance();
    }

    public int getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(int idTarget) {
        this.idTarget = idTarget;
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

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public MailDAO getMailDAO() {
        return mailDAO;
    }

    public void setMailDAO(MailDAO mailDAO) {
        this.mailDAO = mailDAO;
    }

    public String send() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserBean userBean = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);

        Mail mail = new Mail(userBean.getCurrentUser().id, idTarget, title, content);

        mail = mailDAO.createEntity(mail);

        if (mail.id != -1) {
            idTarget = -1;
            title = null;
            content = null;
            
            display = "inbox";
        }

        return sent();
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
