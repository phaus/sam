/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import play.db.jpa.Model;

/**
 *
 * @author philipp
 */
@Entity
public class HostPackage extends Model {

    @ManyToOne
    Host host;
    @ManyToOne
    AppPackage appPackage;

    public HostPackage(Host host, AppPackage appPackage) {
        this.host = host;
        this.appPackage = appPackage;
    }

    public static List<AppPackage> getAppPackagesForHost(Host host) {
        return HostPackage.find("Select hp.appPackage from HostPackage hp where hp.host = ?", host).fetch();
    }

    public static HostPackage findOrCreate(Host h, AppPackage ap) {
        HostPackage hp = HostPackage.find(" host = ? and appPackage = ? ", h, ap).first();
        if (hp == null) {
            hp = new HostPackage(h, ap);
            hp.save();
        }
        return hp;
    }

    public static void cleanForHost(Host host) {
        HostPackage.delete(" host = ?", host);
        /*
        List<HostPackage> hps = HostPackage.find(" host = ?", host).fetch();
        for (HostPackage hp : hps) {
            hp.delete();
        }
        
         */
    }
}
