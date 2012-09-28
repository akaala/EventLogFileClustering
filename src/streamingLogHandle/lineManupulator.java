/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package streamingLogHandle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import logfileclusteringversion3.sqlConnection;

/**
 *
 * @author DELL
 */
public class lineManupulator {

    private String logLine;
    private String newLogLine;

    String getlogLine() {

        return logLine;
    }

    public void setlogLine(String Line) {
        this.logLine = Line;
    }

    public void patternGenerator() {
        StringTokenizer token = null;
        String tokenizer = " ,@=";
        String word;
        newLogLine = "";

        token = new StringTokenizer(getlogLine(), tokenizer);
        while (token.hasMoreElements()) {
            word = token.nextToken();
            word = askForWord(word);
            newLogLine = newLogLine+word;
        }
    }
    
    public String getnewLogLine(){
        this.patternGenerator();
        return newLogLine;
    }

    private String askForWord(String word) {
        try {
            sqlConnection sql = new sqlConnection();
            ResultSet result_countWord = sql.ExecuteQuery("SELECT count,word from wordmodule where word = '" + word + "'");
            if (result_countWord.next()) {
            } else {
                word = "*";
            }
        } catch (SQLException ex) {
            Logger.getLogger(lineManupulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return word;
    }
}
