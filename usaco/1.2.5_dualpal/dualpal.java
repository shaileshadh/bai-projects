
/*
TASK: dualpal
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class dualpal{
	public static void main(String... args) throws Throwable{
		BufferedReader in = new BufferedReader(new FileReader("dualpal.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dualpal.out")));
		prob(in, out);
		out.close();
		System.exit(0);
	}

	static void prob(BufferedReader in, PrintWriter out) throws IOException{
		String ioline = in.readLine();
		StringTokenizer reader = new StringTokenizer(ioline);
		int nleft=Integer.parseInt(reader.nextToken());
		int cur=Integer.parseInt(reader.nextToken());
		
		while(nleft>0){
			cur++;
			if(palin_in_atleast_2(cur)){
				out.println(cur);
				nleft--;
			}
		}
	}
	
	static boolean palin_in_atleast_2(int i){
		int palins=0;
		for(int radix=2; radix<=10; radix++){
			String str=Integer.toString(i,radix);
			if(str.equals(new StringBuilder(str).reverse().toString()))
				palins++;
			if(palins>=2)
				return true;
		}
		return false;
	}
}
