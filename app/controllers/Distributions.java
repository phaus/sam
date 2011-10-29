/**
 * Distributions
 * 29.10.2011
 * @author Philipp Haußleiter
 *
 */
package controllers;

import java.util.List;
import java.util.Set;
import models.Distribution;
import models.Host;
import play.mvc.Controller;

public class Distributions extends Controller {
    public static void index(){
        List<Distribution> distributions = Distribution.findAll();
        Set<Host> hosts = Application.getHosts();
        render(hosts, distributions);
    }
}
