/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper.unix.parser;

import helper.ProcessParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import models.AppPackage;
import models.Distribution;
import play.Logger;

/**
 *
 * @author philipp
 */
public class ListPackagePP implements ProcessParser {
    private List<AppPackage> packages = new LinkedList<AppPackage>();
    private Distribution distribution;
    public void parse(BufferedReader bufferedreader) {
        try {
            boolean on = false;
            String line;
            while ((line = bufferedreader.readLine()) != null && !line.startsWith("Warning:")) {
                if (on) {
                    packages.add(parsePartsToPackage(cleanArray(line.split(" "))));
                }
                if (line.startsWith("+++-")) {
                    on = true;
                }
            }
        } catch (IOException ex) {
            Logger.error(ex.getLocalizedMessage());
        }
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

    private AppPackage parsePartsToPackage(String parts[]) {
        AppPackage p = AppPackage.findOrCreateByNameAndVersionAndDistribution(parts[1].trim(), parts[2].trim(), this.distribution);
        
        //Logger.info(showArray(parts));
        if (p.created && parts.length > 2) {
            p.status = parts[0].trim();
            p.description = concatParts(parts, 3, " ");
            p.distribution = this.distribution;
            p.save();
        }
        return p;
    }

    private String concatParts(String parts[], int fromIndex, String token) {
        StringBuilder sb = new StringBuilder();
        for (int i = fromIndex; i < parts.length; i++) {
            sb.append(parts[i]);
            sb.append(token);
        }
        return sb.toString().trim();
    }

    public void setDistribution(Distribution distribution){
        this.distribution = distribution;
    }
    
    public List<AppPackage> getPackages() {
        return this.packages;
    }
}
