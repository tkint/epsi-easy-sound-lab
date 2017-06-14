/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.config;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author tkint
 */
public class MainConfig {

    static MainConfig config = null;

    public static MainConfig getInstance() throws NamingException {
        if (config == null) {
            config = (MainConfig) ((new InitialContext()).lookup("MAIN-Config"));
        }
        return config;
    }

    private String filesPath;

    public String getFilesPath() {
        return filesPath;
    }

    public void setFilesPath(String path) {
        this.filesPath = path;
    }
}
