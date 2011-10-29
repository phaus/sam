/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.persistence.Entity;
import play.db.jpa.Model;

/**
 *
 * @author philipp
 */
@Entity
public class Repository extends Model {
    /**
     *  OK   http://security.ubuntu.com oneiric-security/universe Translation-en   
        OK   http://de.archive.ubuntu.com oneiric/restricted amd64 Packages        
        Ign   http://de.archive.ubuntu.com oneiric/universe amd64 Packages
        OK   http://de.archive.ubuntu.com oneiric/multiverse amd64 Packages
        OK   http://de.archive.ubuntu.com oneiric/main i386 Packages

     */
    public String status;
    public String url;
    public String distribution;
    public String architecture;
    public String type;
    
}
