
/*
TASK: beads
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class beads{
	public static void main(String... args) throws Throwable{
		Scanner in = new Scanner(new FileInputStream("beads.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("beads.out")));
		prob(in, out);
		out.close();
		System.exit(0);
	}

	static void prob(Scanner in, PrintWriter out) throws IOException{
		in.nextLine();//ignore.
		String beads = in.nextLine();
		int max=0;
		for(int i=0; i<beads.length(); i++){
			String perm=beads.substring(i)+beads.substring(0,i);
			int len=bestchain(perm.toCharArray());
			if(len>max)
				max=len;
		}
		out.println(max);
	}
	
	static int bestchain(char[] beads){
		char[] choices={'b', 'r'};
		int max=0;
		for(int start=0; start<beads.length; start++)
			for(char left:choices)
				for(char right:choices){
					int chain=chain(beads, start, left, right);
					if(chain>max)
						max=chain;
				}
		return max;
	}
	
	//chain starting from a determined position:
	//  [0]   [1]   [2]   ...
	// ^    ^     ^     ^
	// 0    1     2     3
	static int chain(char[] beads, int start, char left, char right){
		int chain=0;
		for(int pos=start-1; pos>=0; pos--){//count left
			char cpos=beads[pos];
			if(cpos==left||cpos=='w')
				chain++;
			else break;
		}
		for(int pos=start; pos<beads.length; pos++){//count right
			char cpos=beads[pos];
			if(cpos==right||cpos=='w')
				chain++;
			else break;
		}
		return chain;
	}
}
