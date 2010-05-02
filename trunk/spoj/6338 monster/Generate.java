
/*  File to generate the main program, applying a basic form of run length
    encoding to compress the file. Also using Vim to do some additional
    formatting in the c file.

    This is not intended to be a solid implementation of RLE, and is just
    a temporary program to generate the main program.
*/

import java.io.*;
import java.util.*;

public class Generate{

    public static void main(String[] args) throws Throwable{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String s;
        while((s = in.readLine()) != null)
            System.out.print(rle(s) + "\\n");
    }

    // Encodes using run length compression
    static StringBuilder rle(String s){

        char[] ca = s.toCharArray();

        StringBuilder r = new StringBuilder();
        char prev = 0;  // Previous character
        int run = 1;    // How many times this character has been encountered

        for(int i=0; i<ca.length; i++){
            char ch = ca[i];
            if(ch == prev) run++;

            // Not first character
            if(prev != 0){
                // Switching character or end of line
                if(ch != prev || i==ca.length-1){
                    if(run != 1)
                        r.append(run);
                    r.append(prev);
                    run = 1;
                }

                // Too many of same character
                else if(run == 10){
                    r.append(9);
                    r.append(prev);
                    run = 1;
                }
            }

            prev = ch;
        }

        // Edge case where last character wasn't showing
        if(run == 1)
            r.append(prev);

        return r;
    }
}
