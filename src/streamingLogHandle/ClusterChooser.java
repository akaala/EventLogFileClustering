/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package streamingLogHandle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author lnkhanal
 * File reading and inserting in the data structure.
 */
public class ClusterChooser {

    private String filePath;
    private float slopeTollerance = 3;
    private float lengthTollerance = 3;
    private float standardDeviationTollerance = 3;
    private float midPointTollerance = 3;

    ClusterChooser(String dir) {
        filePath = dir;
    }

    public void chooseCluster(String str, String logMessage) throws IOException {
        int fileCount = 0;
        
        String filename = String.format(str+".txt");
        String[] messageInfo = str.split("_");
        
        File file = new File(filePath);
        
        if (file.isDirectory()) {
            String[] filesInTheDirectory = file.list();
            for (fileCount = 0; fileCount < filesInTheDirectory.length; fileCount++) {

                String[] clusterInfo = filesInTheDirectory[fileCount].split("_");
                clusterInfo[3] = clusterInfo[3].substring(0, clusterInfo[3].length() - 4);


                float lengthDifference = Math.abs(Float.parseFloat(messageInfo[0]) - Float.parseFloat(clusterInfo[0]));
                float standardDeviationDifference = Math.abs(Float.parseFloat(messageInfo[1]) - Float.parseFloat(clusterInfo[1]));
                float midPositionDifference = Math.abs(Float.parseFloat(messageInfo[2]) - Float.parseFloat(clusterInfo[2]));
                float slopDifference = Math.abs(Float.parseFloat(messageInfo[3]) - Float.parseFloat(clusterInfo[3]));

                if (lengthDifference <= lengthTollerance && standardDeviationDifference <= standardDeviationTollerance && slopDifference <= slopeTollerance && midPositionDifference <= midPointTollerance) {
                    filename = filesInTheDirectory[fileCount];
                    break;
                }

            }
        }
        FileWriter writeFile = new FileWriter("D:/Major/clusters/" + filename, true);
        BufferedWriter bf = new BufferedWriter(writeFile);
        bf.append(logMessage + "\n");
        bf.close();
        writeFile.close();
    }
}
