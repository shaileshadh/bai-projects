import java.io.*;
import java.util.*;

public class Main{

    public static void main(String[] args) throws Throwable{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String s;
        while( !(s = in.readLine()).equals("42"))
            System.out.println(s);
    }
}
