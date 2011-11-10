/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import play.db.jpa.Model;

/**
 *
 * @author philipp
 */
@Entity
public class Distribution extends Model {

    @OneToMany(mappedBy = "distribution")
    public List<AppPackage> packages;
    @OneToMany(mappedBy = "distribution")
    public List<Platform> platforms;
    
    public String version;
    public String name;
    
    public Distribution(String name, String version) {
        this.packages = new LinkedList<AppPackage>();
        this.platforms = new LinkedList<Platform>();
        this.name = name;
        this.version = version;
    }

    public static Distribution findOrCreateByNameAndVersion(String name, String version) {
        Distribution dist = Distribution.find(" name = ? and version = ?", name, version).first();
        if (dist == null) {
            dist = new Distribution(name, version);
        }
        return dist;
    }

    public List<AppPackage>getPackages(){
        return AppPackage.find("distribution = ?", this).fetch();
    }
    
    @Override
    public String toString(){
        return name+" "+version;
    }
}
