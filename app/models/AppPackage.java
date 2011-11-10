/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import play.cache.Cache;
import play.db.jpa.Model;

/**
 *
 * @author philipp
 */
@Entity
public class AppPackage extends Model {

    public String version;
    public String name;
    public String description;
    @ManyToOne
    public Distribution distribution;
    @OneToMany(mappedBy = "appPackage")
    public List<HostPackage> hosts;

    public AppPackage() {
        hosts = new LinkedList<HostPackage>();
    }

    public static AppPackage findOrCreateByNameAndVersionAndDistribution(String name, String version, Distribution distribution) {
        String key = "PACKAGE_" + name + "_" + version + "_" + distribution.id;
        AppPackage ap = Cache.get(key, AppPackage.class);
        if (ap == null) {
            ap = AppPackage.find(" name = ? and version = ? and distribution = ?", name, version, distribution).first();
        }
        if (ap == null) {
            ap = new AppPackage();
            ap.name = name;
            ap.version = version;
        }
        return ap;
    }

    @Override
    public String toString() {
        return name + " " + version + " for " + distribution;
    }
}
