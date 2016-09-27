
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) throws IOException {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        //Get input
    String[] line = (new BufferedReader(new InputStreamReader(System.in))).readLine().split(" ");
    BigInteger A = BigInteger.valueOf(Byte.parseByte(line[0]));
    BigInteger B = BigInteger.valueOf(Byte.parseByte(line[1]));
    
    //Solve
    for(byte n = (byte)(Byte.parseByte(line[2]) - 2); n > 0; --n){
      BigInteger C = B.multiply(B).add(A);
      A = B;
      B = C;
    }
    
    //Print output
    System.out.print(B);
    }
}
