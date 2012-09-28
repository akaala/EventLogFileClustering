/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logfileclusteringversion3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author DELL
 */
public class ClassScanner {
     static ClassScanner scan;

     
     public  Scanner scannerFunction (String path) throws FileNotFoundException {
        Scanner s = new Scanner(new File(path));

        return s;
        
    }

    
}
