/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logfileclusteringversion3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author DELL
 */
public class PatternFileGenerator {

    static private String logLineAfterParse;
    static private String logLineAfterPattern;
    static private String pathOfFileAfterParsing = "D:/major_stuffs/majorProject/patternFile/logFileAfterParse.log";
    String tempLineOfLog;
    Writer outputAfterReplacement = null;
    File logfileAfterReplacement = new File("D:/major_stuffs/majorProject/patternFile/logFileAfterReplacement.log");
    static Scanner scan;
    ClassScanner scanner = new ClassScanner();

    public void patternFileGenerator() throws SQLException {
        int noOfWord;


        try {

            scan = scanner.scannerFunction(pathOfFileAfterParsing);
            outputAfterReplacement = new BufferedWriter(new FileWriter(logfileAfterReplacement));
            sqlConnection sql = new sqlConnection();
            while (scan.hasNextLine()) {
                String lineOfLog = scan.nextLine();
                tempLineOfLog = "";
                String[] word = lineOfLog.split("\\s{1,}");
                noOfWord = word.length;
                
                for (int j = 0; j < noOfWord; j++) {

                    
                    ResultSet result_countWord = sql.ExecuteQuery("SELECT count,word from wordmodule where word = '" + word[j] + "'");
                    if (result_countWord.next()) {
                        
                    }
                    else 
                        word[j] = "*";
                    tempLineOfLog = tempLineOfLog + word[j] + " ";
                    result_countWord.close();

                }
                 logLineAfterPattern = tempLineOfLog + "\n";
                
                outputAfterReplacement.write(logLineAfterPattern);

            }
            outputAfterReplacement.close();
           sql.sqlClose();
            
            

        } catch (IOException ex) {
            Logger.getLogger(PatternFileGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//    public void getLogLineAfterParse(String loglineafterparse) {
//        logLineAfterParse = loglineafterparse;
//    }
//
//    public void getPatternOfLogLine(String loglineafterpattern) {
//        logLineAfterPattern = loglineafterpattern;
//    }
}
