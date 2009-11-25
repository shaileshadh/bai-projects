
/*
TASK: palsquare
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class palsquare{
	public static void main(String... args) throws Throwable{
		BufferedReader in = new BufferedReader(new FileReader("palsquare.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("palsquare.out")));
		prob(in, out);
		out.close();
		System.exit(0);
	}

	static void prob(BufferedReader in, PrintWriter out) throws IOException{
		int radix=Integer.parseInt(in.readLine());
		for(int i=1; i<=300; i++){
			int isq=i*i;
			if(ispalindrome(Integer.toString(isq,radix)))
				out.println(Integer.toString(i,radix).toUpperCase()
					+ " " + Integer.toString(isq,radix).toUpperCase());
		}
	}
	
	static boolean ispalindrome(String s){
		return s.equals(new StringBuilder(s).reverse().toString());
	}
}
