/**
 * AppPackages
 * 30.10.2011
 * @author Philipp Haußleiter
 *
 */
package controllers;

import java.util.List;
import java.util.Set;
import models.AppPackage;
import models.Host;
import play.mvc.Controller;

public class AppPackages extends Controller {
    public static void index(){
        // TODO add order by name query
        List<AppPackage> packages = AppPackage.find("order by name asc").fetch();
        Set<Host> hosts = Application.getHosts();
        render(hosts,packages);
    }
}
