import java.io.*;
import java.util.*;

public class Main{

    public static void main(String[] args) throws Throwable{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int ncases = Integer.parseInt(in.readLine());
        for(int c=0; c<ncases; c++){
            
            // Process test case. Read as a character array.
            char[] ca = in.readLine().toCharArray();
            System.out.println(process(ca));
        }
    }

    // Decodes a rle character array.
    static String process(char[] in){
        
        // Position of character we're processing.
        int pos = 0;
        boolean escape = false;

        // Decoded string
        StringBuilder out = new StringBuilder();

        while(pos < in.length){

            // Character we're dealing with
            char c = in[pos];
            
            // Handling when it's escaped (between two '1's)
            if(escape){
                if(c == '1'){

                    // If followed by a '1', then add '1' to output otherwise
                    // toggle escape.

                    // Edge case when we're already at the end of the array,
                    // then we have to be un-escaping, not encoding another
                    // '1' character.
                    if(pos < in.length-1 && in[pos+1] == '1'){
                        out.append('1');
                        pos++;
                    }
                    else escape = false;
                }
                // If it's not a one, then it can't be terminating the escape
                // sequence nor can it be escaping a '1'.
                else out.append(c);
            }

            // Handling otherwise
            else{
                // A '1' here enables escaping.
                if(c == '1')
                    escape = true;

                // When we're using run length encoding, digit followed by
                // the encoding character.
                else if(c >= '2' && c <= '9') {

                    // Number of times we repeat the character
                    int repeats = c - 0x30;

                    // Character we repeat
                    char repchar = in[pos+1];

                    for(int i=0; i<repeats; i++){
                        out.append(repchar);
                    }
                    pos++;
                }
            }

            pos++;
        }

        return out.toString();
    }
}

