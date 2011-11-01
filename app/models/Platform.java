/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import helper.PlatformParameters;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import play.cache.Cache;
import play.db.jpa.Model;

/**
 *
 * @author philipp
 */
@Entity
public class Platform extends Model {
    @OneToMany(mappedBy="platform")
    public List<Host> hosts;

    @ManyToOne
    public Distribution distribution;
    
    public String kernelName;
    public String kernelVersion;
    public String kernelRelease;
    public String machine;
    public String processor;
    public String hardwarePlatform;
    public String operationSystem;
    
    public Platform(){
        this.hosts = new LinkedList<Host>();
    }
    
    public static Platform findOrCreateByParameter(PlatformParameters paras){
        String key = paras.toString();
        Platform p = Cache.get(key, Platform.class);
        if(p == null){
            p = Platform.find(" operationSystem = ? and machine = ? and kernelVersion = ? and kernelRelease = ?",
                    paras.operationSystem,
                    paras.machine,
                    paras.kernelVersion,
                    paras.kernelRelease).first();
        }
        if(p == null){
            p = new Platform();
            p.operationSystem = paras.operationSystem;
            p.machine = paras.machine;
            p.kernelVersion = paras.kernelVersion;
            p.kernelRelease = paras.kernelRelease;
        }
        return p;
    }
    
    public Platform update(){
        String key = operationSystem+"_"+machine+" "+kernelVersion+" "+kernelRelease;
        Cache.set(key, this, "1d");
        if(this.id == null){
            return this.save();
        }
        return this.merge();
    }
    
    public void setDistribution(Distribution distribution){
        this.distribution = distribution;
    }
    
    public void addHost(Host host){
        this.hosts.add(host);
    }
    
    @Override
    public String toString(){
        return operationSystem+"_"+machine+" "+kernelVersion;
    }
}
