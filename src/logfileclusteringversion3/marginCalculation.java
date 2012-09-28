/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logfileclusteringversion3;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author DELL
 */
public class marginCalculation {

    /*
     * The marginOfLog function returns the marginal value for take decision about accept word or reject it to take into model.
     * margin is different for different loglines.
     * for a logline, the margin is calculated as their individual frequency 
     * and determination of the mean, median. and other several mathematical calculations.
     */
    
    static private int margin = 0;
    
    static int marginOfLog(String lineOfLog) {
        try {
            //split line into individual words, it is done by space
            String[] word = lineOfLog.split("\\s{1,}");
            int noOfWord = word.length;
            int[] count = new int[noOfWord];
            boolean repeatFlag = false;
            List <Integer> countList = new ArrayList<Integer>();
            List <Integer> myList = new ArrayList<Integer>();



            //loop for all until word exists

            /*
             * for use probability(normal distribution) we should determine count of all words in logevent line, 
             * then replace all the words by ther count numbers 
             * then determine mean and standard deviation 
             * finally eveuate the marginal value using formula: marginal_value = percentage% * standard_deviation+mean
             */
            
            sqlConnection sql = new sqlConnection();
            for (int i = 0; i < noOfWord; i++) {
                
                
                ResultSet result_countWord = sql.ExecuteQuery("SELECT count,word from wordcollection where word = '" + word[i] + "'");
                while (result_countWord.next()) {
                    word[i] = result_countWord.getString("word");
                    count[i] = result_countWord.getInt("count");
                }
                System.out.print(" "+count[i]);
            }
            sql.sqlClose();
            /*
             * calculation of margin
             * we can get median and also standard deviation of the sequence from stat class 
             * use these to calculate the margin of the data(log count)
             */
            double mean = 0;
            double standardDev = 0;
            mean = Stat.caclMedian(count, noOfWord);
            standardDev = Stat.caclStanDev(count, noOfWord);
            
            // If there is any of word, whose count is greater then mean and there is only one 
            //word having count greater then mean should be neglected. 
            // and also should have distance between mean and higher-count greater then distribution.
            
            Arrays.sort(count);
            for(int i = 0 ;i<count.length;i++)
                countList.add(count[i]);
            
            System.out.println("mean upper:"+mean +"sd is:"+standardDev);
            
            if(count[count.length-1]>mean && ((count[count.length -1]-mean)>standardDev)){
                countList.remove(count.length-1);
                repeatFlag = true;
            }
            if(repeatFlag)
            for(int i =0;i<countList.size();i++){
                count[i] = countList.get(i);
                System.out.print( "  "+count[i]);
            }
            
            // new mean and standard deviation calculation
            if(repeatFlag){
            mean = Stat.caclMedian(count, noOfWord-1);
            standardDev = Stat.caclStanDev(count, noOfWord-1);
            }
//            margin = (int) (-0.1 * standardDev + mean);
            margin = (int)( mean-0.2*standardDev);
            System.out.println("meand and standard deviation is:"+mean + "and "+standardDev+"and margin is:"+margin);

        } catch (Exception e) {
            System.err.println("error message:" + e.getMessage());
        }
        return margin;
    }
    
    
}
