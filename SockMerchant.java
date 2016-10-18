
John's clothing store has a pile of  loose socks where each sock  is labeled with an integer, , denoting its color. He wants to sell as many socks as possible, but his customers will only buy them in matching pairs. Two socks,  and , are a single matching pair if .

Given  and the color of each sock, how many pairs of socks can John sell?

Input Format

The first line contains an integer, , denoting the number of socks. 
The second line contains  space-separated integers describing the respective values of .

Constraints

Output Format

Print the total number of matching pairs of socks that John can sell.

Sample Input

9
10 20 20 10 10 30 50 10 20
Sample Output

3

****************************************************************************************************************************************


import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int countPair=0;
        int c[] = new int[n];
        for(int c_i=0; c_i < n; c_i++){
            c[c_i] = in.nextInt();
        }
        //Sorting the array so the duplicates elements are next to each other
        Arrays.sort(c);
        
		for (int i = 0; i < c.length-1; i++) {
			
		    if (c[i] == c[i+1]) {
		    	countPair++;
          // To avoid same duplicates counted more than once.
		    	i=i+1;
		        //System.out.println("duplicate item "+c[i+1]+" at Location"+(i+1) );
		    }

		}
		System.out.println(countPair);
        
    }
}
