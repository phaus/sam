/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper.parser;

import helper.ProcessParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import play.Logger;

/**
 *
 * @author philipp
 */
public class SimpeOutputPP implements ProcessParser {
    
    public String startToken =  null;
    public List<String> output;
    
    public SimpeOutputPP(){
        this.output = new ArrayList<String>();
    }
    
    public void parse(BufferedReader bufferedreader) {
        try {
            boolean on = false;
            String line;
            while ((line = bufferedreader.readLine()) != null && !line.startsWith("Warning:")) {
                if(startToken != null && line.startsWith(startToken)){
                    on = true;
                }
                if(startToken == null || on){
                    this.output.add(line.trim());
                }
                
            }
        } catch (IOException ex) {
            Logger.error(ex.getLocalizedMessage());
        }
    }
    
    public List<String> getOutput(){
        return this.output;
    }
}
