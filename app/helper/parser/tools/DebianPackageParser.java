/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper.parser.tools;

import java.util.ArrayList;
import java.util.List;
import models.AppPackage;
import models.Distribution;

/**
 *
 * @author philipp
 */
public class DebianPackageParser implements PackageParser {

    private String parts[];
    private Distribution distribution;

    public DebianPackageParser(Distribution distribution) {
        this.distribution = distribution;
    }

    public AppPackage parsePartsToPackage(String part) {
        parts = cleanArray(part.split(" "));
        AppPackage p = AppPackage.findOrCreateByNameAndVersionAndDistribution(parts[1].trim(), parts[2].trim(), distribution);
        if (p.id == null && parts.length > 2) {
            p.description = concatParts(parts, 3, " ");
            p.distribution = distribution;
        }
        return p.update();
    }

    private String[] cleanArray(String parts[]) {
        List<String> out = new ArrayList<String>();
        for (String part : parts) {
            if (!part.trim().isEmpty()) {
                out.add(part);
            }
        }
        parts = new String[out.size()];
        for (int i = 0; i < out.size(); i++) {
            parts[i] = (String) out.get(i);
        }
        return parts;
    }

    private String concatParts(String parts[], int fromIndex, String token) {
        StringBuilder sb = new StringBuilder();
        for (int i = fromIndex; i < parts.length; i++) {
            sb.append(parts[i]);
            sb.append(token);
        }
        return sb.toString().trim();
    }

    public Distribution getDistribution() {
        return this.distribution;
    }
    
}
