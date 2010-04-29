import java.io.*;
import java.util.*;

public class hoppity{
    public static void main(String[] args) throws Exception{

        BufferedReader in = new BufferedReader(
            new FileReader(args[0]));

        int upto = Integer.parseInt(in.readLine());
        for(int i=1; i<=upto; i++){
            if(i%5==0 && i%3==0)    System.out.println("Hop");
            else if(i%5==0)         System.out.println("Hophop");
            else if(i%3==0)         System.out.println("Hoppity");
        }
    }
}
