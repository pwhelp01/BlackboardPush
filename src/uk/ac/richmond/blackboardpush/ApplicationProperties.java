/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.richmond.blackboardpush;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 *
 * @author pete
 */
public class ApplicationProperties {
    
    private String dbserver;
    private String dbport;
    private String dbname;
    
    /**
     * Creates a new Application Properties object from the blackboardpush.properties
     * file located on the filesystem
     * 
     * @throws IOException If there is a problem reading the properties file
     */
    public ApplicationProperties() throws IOException {
        
        // Create new properties object
        Properties props = new Properties();
        
        // Try to open the file and read the properties
        props.load(new FileInputStream("blackboardpush.properties"));
        this.dbserver = props.getProperty("pc.dbserver");
        this.dbport = props.getProperty("pc.dbport");
        this.dbname = props.getProperty("pc.dbname");

    }
    
    /**
     * Return the name of the PowerCAMPUS database server
     * 
     * @return DB Server name
     */
    public String getDbserver() {
        return this.dbserver;
    }
    
    /**
     * Return the listening port of the PowerCAMPUS database server
     *
     * @return DB Port number
     */
    public String getDbport() {
        return this.dbport;
    }
    
    /**
     * Return the name of the PowerCAMPUS database
     * 
     * @return DB Name
     */
    public String getDbname() {
        return this.dbname;
    }
    
}
