/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logfileclusteringversion3;

/**
 *
 * @author DELL
 */
///////////////////////////////////////////////////////////////////////////////////////////////
/*
 * This class carries out all the static operations and mathmatical operations
 */
public class Stat {
    
    static double caclStanDev(int[] data, int n){
        return Math.pow(caclVariance(data,n),0.5);
    }
   
    static double caclVariance(int[] data, int n ){
        double total = 0;
        double sTotal = 0;
        double scalar = 1/(double)(n-1);
        for(int i =0; i<n; i++){
            total += data[i];
            sTotal += Math.pow(data[i], 2);
        }
        return(scalar*(sTotal - (Math.pow(total, 2)/n)));
        
    }
   
     static double caclMedian(int[] data, int n){
         double total = 0 ;
         for (int i = 0; i <n ; i++)
             total += data[i];
         return(total/n);
     }

    
}
////////////////////////////////////////////////////////////////////////////////////////////////

