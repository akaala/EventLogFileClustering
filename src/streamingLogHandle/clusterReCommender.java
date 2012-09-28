/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package streamingLogHandle;

import clusterCreator.RepresentationOfLogLine;
import javax.naming.BinaryRefAddr;

/**
 *
 * @author DELL
 */
public class clusterReCommender extends lineManupulator {
    private String binReprentation;
    public void reCommendation(String logLine , String orginalLine){
        
        RepresentationOfLogLine representer = new RepresentationOfLogLine();
        binReprentation = representer.binaryRepresentationOfLogLine(logLine);
        System.out.println("orginal: "+orginalLine+"\nParsed:"+logLine+"\nRepresentation:"+binReprentation+"\n\n");
    }
    public String getBinary(){
        return binReprentation;
    }
}
