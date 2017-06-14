/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotations.*;

/**
 *
 * @author Thomas
 */
@ESLEntity(name = "mail")
public class Mail {

    @ESLId
    @ESLField(name = "id_mail")
    public int id;

    @ESLField(name = "id_author")
    @ESLReference(entity = User.class)
    public int idAuthor;

    @ESLField(name = "id_target")
    @ESLReference(entity = User.class)
    public int idTarget;

    @ESLField(name = "title")
    public String title;

    @ESLField(name = "content")
    public String content;

    @ESLField(name = "datetime")
    public String datetime;

    public Mail() {
    }

    public Mail(int idAuthor, int idTarget, String title, String content) {
        this.idAuthor = idAuthor;
        this.idTarget = idTarget;
        this.title = title;
        this.content = content;
    }

    public Mail(int id, int idAuthor, int idTarget, String title, String content, String datetime) {
        this.id = id;
        this.idAuthor = idAuthor;
        this.idTarget = idTarget;
        this.title = title;
        this.content = content;
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
