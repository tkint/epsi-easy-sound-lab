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
public class DatabaseConfigFactory implements ObjectFactory {

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        
        DatabaseConfig config = new DatabaseConfig();
        
        Reference reference = (Reference)obj;
        
        String user = (String)reference.get("user").getContent();
        String folder = (String)reference.get("folder").getContent();
        String playlist = (String)reference.get("playlist").getContent();
        String musicfile = (String)reference.get("musicfile").getContent();
        String mail = (String)reference.get("mail").getContent();
        String follow = (String)reference.get("follow").getContent();
        
        config.setUser(user);
        config.setFolder(folder);
        config.setPlaylist(playlist);
        config.setMusicfile(musicfile);
        config.setMail(mail);
        config.setFollow(follow);
        
        return config;
    }

}
