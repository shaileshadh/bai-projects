
/*
TASK: ariprog
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class ariprog{
	public static void main(String... args) throws Throwable{
		BufferedReader in = new BufferedReader(new FileReader("ariprog.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ariprog.out")));
		prob(in, out);
		out.close();
		System.exit(0);
	}

	static void prob(BufferedReader in, PrintWriter out) throws IOException{
		int plength = Integer.parseInt(in.readLine());
		int upperbound = Integer.parseInt(in.readLine());
	}
}
