/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper.parser.tools;

import models.AppPackage;
import models.Distribution;

/**
 *
 * @author philipp
 */
public interface PackageParser {

    public AppPackage parsePartsToPackage(String part);

    public Distribution getDistribution();

}
