/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clusterCreator;

import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class RepresentationOfLogLine {

    int lengthOfBitSequenceForPosition = 7;
    String binaryRepresentationOfLogLine ;
    String binaryStringForPosition = "00000000";
    String binaryStringForCombinationOfLetter;

    public String binaryRepresentationOfLogLine(String lineOfLog) {

       
        StringTokenizer token = null;
        String tokenizer = " ,";
        String word;
        int positionOfWord = 0; 
        binaryRepresentationOfLogLine = "";


        token = new StringTokenizer(lineOfLog, tokenizer);
        while (token.hasMoreElements()) {
            positionOfWord++;
            word = token.nextToken();
//            System.out.println(word);
            binaryStringForPosition = binaryStringAccordingToPosition(word, positionOfWord);
//            System.out.println(binaryStringForPosition);
            binaryStringForCombinationOfLetter = binaryStringAccordingToCombinationOfLetter(word);
//            System.out.println(binaryStringForCombinationOfLetter);
            binaryRepresentationOfLogLine = binaryRepresentationOfLogLine+ binaryStringForPosition+binaryStringForCombinationOfLetter;
           
        }
        //System.out.println("  final result is:"+binaryRepresentationOfLogLine);
        return binaryRepresentationOfLogLine;
    }

    private String binaryStringAccordingToPosition(String word, int positionOfWord) {
        String binaryStringTemp = Integer.toBinaryString(positionOfWord);
        binaryStringForPosition = binaryStringForPosition.substring(binaryStringTemp.length(), lengthOfBitSequenceForPosition);
        binaryStringForPosition = binaryStringForPosition + binaryStringTemp;
        return binaryStringForPosition;
    }

    private String binaryStringAccordingToCombinationOfLetter(String word) {
        try {
            binaryStringForCombinationOfLetter = "";
            byte[] binaryTemp = word.getBytes("UTF-8");
            for (byte b : binaryTemp) {
                binaryStringForCombinationOfLetter = binaryStringForCombinationOfLetter + (Integer.toBinaryString(b));

            }

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RepresentationOfLogLine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return binaryStringForCombinationOfLetter;

    }
}
