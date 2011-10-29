/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import play.cache.Cache;
import play.db.jpa.Model;

/**
 *
 * @author philipp
 */
@Entity
public class Distribution extends Model {

    public String version;
    public String name;
    @OneToMany(mappedBy = "distribution")
    public List<AppPackage> packages;
    @OneToMany(mappedBy = "distribution")
    public List<Platform> platforms;

    public Distribution(String name, String version) {
        this.packages = new LinkedList<AppPackage>();
        this.platforms = new LinkedList<Platform>();
        this.name = name;
        this.version = version;
    }

    public static Distribution findOrCreateByNameAndVersion(String name, String version) {
        String key = "DIST_" + name + "_" + version;
        Distribution dist = Cache.get(key, Distribution.class);
        if (dist == null) {
            dist = Distribution.find(" name = ? and version = ?", name, version).first();
        }
        if (dist == null) {
            dist = new Distribution(name, version);
            dist.save();
            Cache.set(key, dist, "1d");
        }
        return dist;
    }
    
    public void addPlatform(Platform platform){
        this.platforms.add(platform);
    }
    
    public void addPackage(AppPackage appPackage){
        this.packages.add(appPackage);
    }
    
    public void addPackages(List<AppPackage> packages){
        this.packages.addAll(packages);
    }
    
    @Override
    public String toString(){
        return this.name+" "+this.version;
    }
}
