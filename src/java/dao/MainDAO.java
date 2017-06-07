/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author t.kint
 * @param <T>
 */
public abstract class MainDAO<T> {
    
    protected String table;

    public MainDAO(Class subclass) {
        this.table = Connexion.getTableName(subclass);
    }

    public abstract ArrayList<T> getEntities();

    public abstract T getEntityById(int id);

    public abstract T getEntityByIndex(int index);

    public abstract T getLastEntity();
    
    public abstract int getLastEntityId();
    
    public abstract T createEntity(T entity);
    
    public abstract int deleteEntity(T entity);

    protected abstract T mapEntity(ResultSet rs);

}
