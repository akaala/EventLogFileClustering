/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logfileclusteringversion3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
final class CreateLogModule3 {

    static private String fileDirectory;
    static private String logDirectory;
    static Scanner scan;
    Writer outputAfterParsing = null;
    File logfileAfterParsing = new File("D:/major_stuffs/majorProject/patternFile/logFileAfterParse.log");
    
    VerifyWords3 Verify = new VerifyWords3();
    ClassScanner scanner = new ClassScanner();
    //PatternFileGenerator patternFileGenerator = new PatternFileGenerator();

    //constructor
    CreateLogModule3(String logPath) {
        fileDirectory = logPath;
        
    }

    public void fillDatabaseFirstPass() throws IOException {
        
        File f1 = new File(fileDirectory);
        int noOfWord;
        if (f1.isDirectory()) {
            boolean wordIsSpace = false;
            String s[] = f1.list();
            for (int i = 0; i < s.length; i++) {
                try {
                    logDirectory = fileDirectory + '/' + s[i];//sets the path of the multiple log files in the given directory.
                    scan = scanner.scannerFunction(logDirectory);
                    while (scan.hasNextLine()) {
                        String lineOfLog = scan.nextLine();
//                        System.out.println("log line is: " + lineOfLog);

                        lineOfLog = Verify.ReplaceIP(lineOfLog);
                        lineOfLog = Verify.ReplaceMAC(lineOfLog);
                        lineOfLog = Verify.ReplaceDate(lineOfLog);
                        lineOfLog = Verify.ReplaceTime(lineOfLog);
                        lineOfLog = Verify.ReplaceDateAndTime(lineOfLog);
                        lineOfLog = Verify.replaceDoublecort(lineOfLog);
                        lineOfLog = Verify.ReplaceDigit(lineOfLog);
                        lineOfLog = Verify.ReplacePath(lineOfLog);
                        lineOfLog = Verify.ReplaceIrregularExpression(lineOfLog);

                        //lineOfLog = Verify.replaceSpaces(lineOfLog);
//                        System.out.println("log line after parsing is:" + lineOfLog);
//                        makeFileAfterParsing(lineOfLog);

                        StringTokenizer token = null;
                        String tokenizer = " ,@=";
                        String word;

                        token = new StringTokenizer(lineOfLog, tokenizer);
                        while (token.hasMoreElements()) {
                            word = token.nextToken();


                            if (" ".equals(word)) {
                                wordIsSpace = true;
                            }
                            int count = 1;
                            boolean countFlag = false;
                            sqlConnection sql = new sqlConnection();
                            ResultSet result_countWord = sql.ExecuteQuery("SELECT count,word from wordcollection where word = '" + word + "'");
                            if (result_countWord.next()) {
                                count = result_countWord.getInt("count");
                                //word = result_countWord.getString(word);
                                count++;
                                countFlag = true;//flag set if there is found word
                                //if the new comming word is there then just increase count
                                sql.UpdateQuery("UPDATE wordcollection w SET w.count = '" + count + "' WHERE w.word = '" + word + "'");

                            }
                            if (!countFlag && !wordIsSpace) {
                                //insert the word into module because no word is found during the searching
//                                sql.UpdateQuery("insert into words_collection_midterm values ('" + word + "','" + count + "')");
                                sql.UpdateQuery("INSERT INTO `wordcollection` (`word`, `count`) VALUES ('" + word + "', '" + count + "')");
                            }
                            result_countWord.close();
                        }
                    }//end of tokenizer

//                    }//end ofwhile loop
                    scan.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CreateLogModule3.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CreateLogModule3.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    public void createModuleSecondPass() throws SQLException, IOException {
        
        //for file after parsing 
         outputAfterParsing = new BufferedWriter(new FileWriter(logfileAfterParsing));
         
        boolean insertFlag = false;
        boolean repeationFlag = false;
        int[] count;
        int noOfWord;
        int margin = 0;

        sqlConnection sql = new sqlConnection();

        try {
            File f1 = new File(fileDirectory);
            if (f1.isDirectory()) {
                String s[] = f1.list();
                for (int i = 0; i < s.length; i++) {
                    logDirectory = fileDirectory + '/' + s[i];//sets the path of the multiple log files in the given directory.
                    scan = scanner.scannerFunction(logDirectory);
                    while (scan.hasNextLine()) {
                        //get margin of line using margin funcion
                        String lineOfLog = scan.nextLine();

                        lineOfLog = Verify.ReplaceIP(lineOfLog);
                        lineOfLog = Verify.ReplaceMAC(lineOfLog);
                        lineOfLog = Verify.ReplaceDate(lineOfLog);
                        lineOfLog = Verify.ReplaceTime(lineOfLog);
                        lineOfLog = Verify.ReplaceDateAndTime(lineOfLog);
                        lineOfLog = Verify.replaceDoublecort(lineOfLog);
                        lineOfLog = Verify.ReplaceDigit(lineOfLog);
                        lineOfLog = Verify.ReplacePath(lineOfLog);
                        lineOfLog = Verify.ReplaceIrregularExpression(lineOfLog);

                        
//                        patternFileGenerator.getLogLineAfterParse(lineOfLog);
                        makeFileAfterParsing(lineOfLog);
                        
                        margin = marginCalculation.marginOfLog(lineOfLog);

//                        System.out.println("margin of line is:" + margin);

                        String[] word = lineOfLog.split("[ ,@=]");
                        noOfWord = word.length;
                        count = new int[noOfWord];
                        for (int j = 0; j < noOfWord; j++) {
                            
                            ResultSet result_countWord = sql.ExecuteQuery("SELECT count,word from wordcollection where word = '" + word[j] + "' AND count > " + margin);
                            while (result_countWord.next()) {
                                word[j] = result_countWord.getString("word");

                                count[j] = result_countWord.getInt("count");

                                insertFlag = true;
//                                System.out.println(" word and count is:" + word[j] + "  " + count[j]);

                            }
                            if (insertFlag) {
                                ResultSet result_checkRepeation = sql.ExecuteQuery("SELECT word from wordmodule where word = '" + word[j] + "'");
                                while (result_checkRepeation.next()) {
//                                    repeationFlag = true;
                                    insertFlag = false;
                                }
                            }
//                            if (insertFlag && !repeationFlag) {
                            if (insertFlag) {
                                //                 sql.UpdateQuery("insert into pattern values ('" + word[i] + "','" + count[i] + "')");
                                sql.UpdateQuery("INSERT INTO `wordmodule` (`word`, `count`) VALUES ('" + word[j] + "', '" + count[j] + "')");
                                insertFlag = false;
//                                repeationFlag = false;
                            }

                        }

                    }//close of while loop
                    //close of the fileafterparsing.txt
                    scan.close();
                   outputAfterParsing.close();
                }
            }
        } catch (Exception e) {
            System.err.println("error message:" + e.getMessage());
        }


    }
    
    //for formation of file after parsing of file
    // which is use for determine the final pattern of the log file
    private void makeFileAfterParsing(String lineOfLog) {

        try {
            outputAfterParsing.write(lineOfLog + "\n");
        } catch (IOException ex) {
            Logger.getLogger(CreateLogModule3.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
