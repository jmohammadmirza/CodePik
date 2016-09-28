Consider a staircase of size :4

   #
  ##
 ###
####

*****************************************************************************************************************************


import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
      
     for(int i = 0; i < n; i++)
     {
       String s = "";
       for(int j = 0; j < n; j++)
       {
            if(n - i - 2 < j)
            {
                s += "#";
            }
            else
            {
                s += " ";
            }
      }
       System.out.println(s);
     }
  }
}
