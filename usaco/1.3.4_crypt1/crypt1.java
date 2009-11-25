
/*
TASK: crypt1
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class crypt1{
	public static void main(String... args) throws Throwable{
		BufferedReader in = new BufferedReader(new FileReader("crypt1.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("crypt1.out")));
		prob(in, out);
		out.close();
		System.exit(0);
	}

	static void prob(BufferedReader in, PrintWriter out) throws IOException{
		int numdigs = Integer.parseInt(in.readLine());
		int[] comb = new int[numdigs];
		String secline = in.readLine();
		StringTokenizer seclinetok = new StringTokenizer(secline);
		for(int i=0; i<numdigs; i++)
			comb[i] = Integer.parseInt(seclinetok.nextToken());
		Arrays.sort(comb);
		
		List<List<Integer>> combs3 = new ArrayList<List<Integer>>();
		List<List<Integer>> combs2 = new ArrayList<List<Integer>>();
		recurse(combs3, comb, new ArrayList<Integer>(), 3);
		recurse(combs2, comb, new ArrayList<Integer>(), 2);
		int sum=0;
		
		for(List<Integer> list2:combs2){
			int b10 = list2.get(0);
			int b1 = list2.get(1);
			int b = 10*b10 + b1;
			for(List<Integer> list3:combs3){
				int a100 = list3.get(0);
				int a10 = list3.get(1);
				int a1 = list3.get(2);
				int a = 100*a100 + 10*a10 + a1;
				
				int part1 = b1*a;
				int part2 = b10*a;
				int prod = part1 + 10*part2;
				
				if(part1>999 || part2>999 || prod>9999)
					break;
				
				if(allpartof(part1, comb) && allpartof(part2, comb) && allpartof(prod, comb))
					sum++;
			}
		}
		
		out.println(sum);
	}
	
	static void recurse(List<List<Integer>> addto, int[] combs, List<Integer> prev, int recurse){
		if(recurse==0)
			addto.add(prev);
		else{
			for(int next:combs){
				List<Integer> copy = new ArrayList<Integer>(prev);
				copy.add(next);
				recurse(addto, combs, copy, recurse-1);
			}	
		}
	}
	
	static boolean allpartof(int i, int[] combs){
		String str = Integer.toString(i);
		for(char c : str.toCharArray()){
			int t = Integer.parseInt(Character.toString(c));
			boolean g=false;
			for(int k:combs){
				if(t==k){
					g=true;
					break;
				}
			}
			if(!g)
				return false;
		}
		return true;
	}
}
