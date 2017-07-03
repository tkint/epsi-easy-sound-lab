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
public class MainConfigFactory implements ObjectFactory {

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {

        MainConfig config = new MainConfig();

        Reference reference = (Reference) obj;

        String filesPath = (String) reference.get("filespath").getContent();
        String disk = (String) reference.get("disk").getContent();

        config.setFilesPath(filesPath);
        config.setDisk(disk);

        return config;
    }

}
