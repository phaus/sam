/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper.unix.parser;

import helper.PackageParser;
import models.AppPackage;
import models.Distribution;

/**
 *
 * @author philipp
 */
public class ArcLinuxPackageParser implements PackageParser {

    private String parts[];
    private Distribution distribution;

    public ArcLinuxPackageParser(Distribution distribution) {
        this.distribution = distribution;
    }

    public AppPackage parsePartsToPackage(String part) {
        parts = part.split(" ");
        AppPackage p = AppPackage.findOrCreateByNameAndVersionAndDistribution(parts[0].trim(), parts[1].trim(), distribution);
        if (p.id == null && parts.length > 1) {
            p.description = "";
            p.distribution = distribution;
        }
        return p;
    }

    public Distribution getDistribution() {
        return this.distribution;
    }

}
