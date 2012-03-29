/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.util.List;
import models.AppPackage;
import models.Distribution;
import models.Host;
import models.Platform;

/**
 *
 * @author philipp
 */
public interface PlatformHelper {
    
   public Platform detectPlatform();
   public Distribution dectectDistribution();
   public Host detectHost();
   public List<AppPackage> listPackages(); 

   public boolean installPackage(String packageName);
   public boolean removePackage(String packageName);
   public void updateRepository();
   public void setHost(Host host);
   public Host getHost();
   public Distribution getDistribution();
   
   // TODO provisorische Lösung :-)
   public List<String> updatedPackages(); 
   public List<String> upgradeDistribution(); 
   public List<String> searchPackage(String query);
   public int getStatus();
}
