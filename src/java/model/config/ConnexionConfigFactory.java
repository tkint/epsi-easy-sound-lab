/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.config;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

/**
 *
 * @author tkint
 */
public class ConnexionConfigFactory implements ObjectFactory {

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        
        ConnexionConfig config = new ConnexionConfig();
        
        Reference reference = (Reference)obj;
        
        String url = (String)reference.get("url").getContent();
        String port = (String)reference.get("port").getContent();
        String login = (String)reference.get("login").getContent();
        String password = (String)reference.get("password").getContent();
        String database = (String)reference.get("database").getContent();
        
        config.setUrl(url);
        config.setPort(port);
        config.setLogin(login);
        config.setPassword(password);
        config.setDatabase(database);
        
        return config;
    }

}
