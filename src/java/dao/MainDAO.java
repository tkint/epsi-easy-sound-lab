/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import annotations.*;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 *
 * @author t.kint
 * @param <T>
 */
public abstract class MainDAO<T> {

    private Class<T> entity;
    protected String table;
    protected String idField;
    protected String fields;
    protected String fullFields;

    public MainDAO(Class<T> entity) {
        this.entity = entity;
        this.table = Connexion.getTableName(getESLEntityName());
        this.idField = getESLFieldNameByField(getESLIdField());
        this.fields = getFieldsAsString();
        this.fullFields = idField + ", " + fields;
    }

    /**
     * Retourne toutes les entités
     *
     * @return
     */
    public List<T> getEntities() {
        List<T> entities = new ArrayList<>();

        try {
            String query = "SELECT " + fullFields + " FROM " + table;

            ResultSet rs = Connexion.getInstance().executeQuery(query);

            while (rs.next()) {
                entities.add(mapEntity(rs));
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return entities;
    }

    /**
     * Retourne l'entité avec l'id spécifié
     *
     * @param id
     * @return
     */
    public T getEntityById(int id) {
        T entity = null;

        try {
            String query = "SELECT " + fullFields + " FROM " + table + " WHERE " + idField + " = " + id;

            ResultSet rs = Connexion.getInstance().executeQuery(query);

            if (rs.next()) {
                entity = mapEntity(rs);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return entity;
    }

    /**
     * Créé une entité
     *
     * @param entity
     * @return
     */
    public T createEntity(T entity) {
        try {
            Connexion connexion = Connexion.getInstance();

            String query = "INSERT INTO " + table + "(" + fields + ") VALUES(";

            List<Field> fields = getFields(false);

            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                if (needQuotes(field.get(entity))) {
                    query += "'";
                }
                query += field.get(entity);
                if (needQuotes(field.get(entity))) {
                    query += "'";
                }
                if (i < fields.size() - 1) {
                    query += ", ";
                }
            }

            query += ")";

            int nb = connexion.executeUpdate(query);

            if (nb > 0) {
                ResultSet rs = connexion.getStatement().getGeneratedKeys();
                rs.next();
                getESLIdField().set(entity, rs.getInt(1));
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return entity;
    }

    /**
     * Supprime l'entité spécifiée
     *
     * @param entity
     * @return
     */
    public int deleteEntity(T entity) {
        int nb = 0;

        try {
            Connexion connexion = Connexion.getInstance();

            String query = "DELETE FROM " + table + " WHERE " + idField + " = " + getESLIdField().get(entity);

            nb = connexion.executeUpdate(query);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return nb;
    }

    /**
     * Met à jour l'entité spécifiée
     *
     * @param entity
     * @return
     */
    public T updateEntity(T entity) {
        try {
            Connexion connexion = Connexion.getInstance();

            String query = "UPDATE " + table + " SET ";

            List<Field> fields = getFields(false);

            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                query += getESLFieldNameByField(field) + " = ";
                if (needQuotes(field.get(entity))) {
                    query += "'";
                }
                query += field.get(entity);
                if (needQuotes(field.get(entity))) {
                    query += "'";
                }
                if (i < fields.size() - 1) {
                    query += ", ";
                }
            }

            query += " WHERE " + idField + " = " + getESLIdField().get(entity);

            int nb = connexion.executeUpdate(query);

            if (nb > 0) {
                ResultSet rs = connexion.getStatement().getGeneratedKeys();
                rs.next();
                getESLIdField().set(entity, rs.getInt(1));
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return entity;
    }

    /**
     * Insére les données d'une ligne de resultset dans un objet
     *
     * @param rs
     * @return
     * @throws Exception
     */
    protected T mapEntity(ResultSet rs) throws Exception {
        T instance = entity.newInstance();

        try {
            Field id = getESLIdField();
            Object o = rs.getObject(getESLFieldNameByField(getESLIdField()));
            id.set(instance, o);

            for (Field field : getFields(false)) {
                if (checkIfESLField(field)) {;
                    o = rs.getObject(getESLFieldNameByField(field));
                    field.set(instance, o);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return instance;
    }

    /**
     * Retourne l'entité positionnée à l'index spécifié
     *
     * @param index
     * @return
     */
    public T getEntityByIndex(int index) {
        T entity = null;
        List<T> entities = getEntities();

        if (entities.size() > index) {
            entity = entities.get(index);
        }

        return entity;
    }

    /**
     * Retourne le dernier id des entités
     *
     * @return
     */
    public int getLastEntityId() {
        int id = -1;

        try {
            ResultSet rs = Connexion.getInstance().executeQuery("SELECT MAX(" + idField + ") AS id FROM " + table);

            if (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return id;
    }

    /**
     * Retourne la dernière entité créée
     *
     * @return
     */
    public T getLastEntity() {
        return getEntityById(getLastEntityId());
    }

    /**
     * Vérifie si un champ est annoté par @ESLField
     *
     * @param field
     * @return
     */
    private boolean checkIfESLField(Field field) {
        boolean ok = false;
        int i = 0;
        Annotation[] annotations = field.getDeclaredAnnotations();

        while (i < annotations.length && !ok) {
            if (annotations[i] instanceof ESLField) {
                ok = true;
            }
            i++;
        }

        return ok;
    }

    /**
     * Retourne le nom de référence de l'entité dans l'annotation @ESLEntity
     * Permet de mapper avec le nom de table dans les FNDI
     *
     * @return
     */
    private String getESLEntityName() {
        String reference = null;

        try {
            Annotation annotation = entity.getAnnotation(ESLEntity.class);

            if (annotation instanceof ESLEntity) {

                ESLEntity entity = (ESLEntity) annotation;

                reference = entity.name();
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return reference;
    }

    /**
     * Retourne le champ annoté par @ESLField et @ESLId
     *
     * @return
     */
    private Field getESLIdField() {
        Field idField = null;

        try {
            int i = 0;

            while (i < entity.getFields().length && idField == null) {
                if (entity.getFields()[i].getDeclaredAnnotation(ESLField.class) != null
                        && entity.getFields()[i].getDeclaredAnnotation(ESLId.class) != null) {
                    idField = entity.getFields()[i];
                }

                i++;
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return idField;
    }

    /**
     * Retourne tous les champs annotés par @ESLField Si idField est à true,
     * inclut le champ id
     *
     * @param idField
     * @return
     */
    private List<Field> getFields(boolean idField) {
        List<Field> fields = new ArrayList<>();

        try {

            for (Field field : entity.getFields()) {
                if (field.getDeclaredAnnotation(ESLField.class) != null
                        && ((idField && field.equals(getESLIdField())) || !field.equals(getESLIdField()))) {
                    fields.add(field);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return fields;
    }

    /**
     * Retourne les champs séparés d'une virgule dans une chaîne
     *
     * @return
     */
    private String getFieldsAsString() {
        String s = "";

        List<Field> fields = getFields(false);

        for (int i = 0; i < fields.size(); i++) {
            s += getESLFieldNameByField(fields.get(i));
            if (i < fields.size() - 1) {
                s += ", ";
            }
        }

        return s;
    }

    /**
     * Retourne le nom de référence du champ spécifié
     *
     * @param field
     * @return
     */
    private String getESLFieldNameByField(Field field) {
        String fieldName = null;

        if (field != null) {
            ESLField eslf = (ESLField) field.getDeclaredAnnotation(ESLField.class);
            fieldName = eslf.name();
        }

        return fieldName;
    }

    /**
     * Vérifie si une valeur a besoin de guillemets
     *
     * @param o
     * @return
     */
    private boolean needQuotes(Object o) {
        boolean need = true;

        Class[] c = {Boolean.class};
        int i = 0;

        while (i < c.length && need) {
            if (o.getClass().equals(c[i])) {
                need = false;
            }
            i++;
        }

        return need;
    }
}
