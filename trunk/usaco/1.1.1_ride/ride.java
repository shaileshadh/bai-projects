
/*
TASK: ride
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class ride{
	public static void main(String... args) throws Throwable{
		prob();
	}
	
	static void prob() throws IOException{
		BufferedReader f = new BufferedReader(new FileReader("ride.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ride.out")));
		String str1 = f.readLine();
		String str2 = f.readLine();
		out.println(gostay(str1, str2));
		out.close();
		System.exit(0);
	}
	
	static String gostay(String comet, String group){
		long numc = number(comet);
		long numg = number(group);
		if(numc % 47 == numg % 47)
			return "GO";
		else return "STAY";
	}
	
	static long number(String s){
		long sum = 1;
		for(char c : s.toCharArray()){
			sum *= (c-64);
		}
		return sum;
	}
}
