/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper.unix.parser;

import helper.PackageParser;
import java.util.ArrayList;
import java.util.List;
import models.AppPackage;
import models.Distribution;

/**
 *
 * @author philipp
 */
public class SusePackageParser implements PackageParser{
    
    private String parts[];
    private Distribution distribution;
    
    public SusePackageParser(Distribution distribution){
        this.distribution = distribution;
    }
    
    public Distribution getDistribution() {
        return this.distribution;
    }
    
    
    public AppPackage parsePartsToPackage(String part) {
        parts = cleanArray(part.split(" "));
        AppPackage p = AppPackage.findOrCreateByNameAndVersionAndDistribution(parts[0].trim(), parts[1].trim(), distribution);
        if (p.id == null && parts.length > 2) {
            p.description = concatParts(parts, 2, " ");
            p.distribution = distribution;
        }
        return p.save();
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
    
}
