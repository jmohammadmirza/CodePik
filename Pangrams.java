//All Domains  Algorithms  Strings  Pangrams

//Sample Input

//Input #1

//We promptly judged antique ivory buckles for the next prize    
//Input #2

//We promptly judged antique ivory buckles for the prize    
//Sample Output

//Output #1

//pangram
//Output #2

//not pangram
//Explanation

//In the first test case, the answer is pangram because the sentence contains all the letters of the English alphabet.

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        
        Scanner input=new Scanner(System.in);
        String test="";
        String s=input.nextLine();
               
        String temp[]=s.split(" ");
        
         for(int j=0;j<temp.length;j++)
         {
             //System.out.println(temp[j]);
              test=test+temp[j];
         }
         // System.out.println(test);
        
        String lowerCase = test.toLowerCase();
        char characters[] = lowerCase.toCharArray();
        int countOfUniqueChars = test.length();
        for (int i = 0; i < characters.length; i++) {
            
            if (i != lowerCase.indexOf(characters[i])) {
                countOfUniqueChars--;
            }
         }
    //  System.out.println(countOfUniqueChars);  
        
        if (countOfUniqueChars==26)
          System.out.println("pangram"); 
        else
           System.out.println("not pangram");   
   } 
}
