/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logfileclusteringversion3;
import java.io.IOException;
import java.sql.SQLException;
/**
 *
 * @author DELL
 */
public class LogfileClusteringVersion3 {
static private String fileDirectory = "D:/major_stuffs/majorProject/backupLogFiles";

    public static void main(String[] args) throws SQLException {
       try {
        CreateLogModule3 createLogModule = new CreateLogModule3(fileDirectory);
        PatternFileGenerator patternFileGeneratorObject = new PatternFileGenerator();
        
            //createLogModule.fillDatabaseFirstPass();
            createLogModule.createModuleSecondPass();
         
            patternFileGeneratorObject.patternFileGenerator();
            
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}