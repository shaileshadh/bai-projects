
/*
TASK: calfflac
LANG: JAVA
*/

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class calfflac{
	public static void main(String... args) throws Throwable{
		BufferedReader in = new BufferedReader(new FileReader("calfflac.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("calfflac.out")));
		prob(in, out);
		out.close();
		System.exit(0);
	}
	
	static volatile boolean k1=true, k2=true;
	static volatile String best = "";
	static String str = "";

	static void prob(BufferedReader in, PrintWriter out) throws Throwable{
		str=readall(in);
		ExecutorService ex = Executors.newCachedThreadPool();
		
		//find even-length palindromes.
		Runnable even = new Runnable(){
			public void run(){
				String cur;
				for(int start=1; start<str.length(); start++){
					int lpos=start, rpos=start+1;
					lpalin_(lpos, rpos);
				}
				k1=false;
			}
		};
		
		//odd length ones.
		Runnable odd = new Runnable(){
			public void run(){
				String cur;
				for(int start=1; start<str.length(); start++){
					int lpos=start-1, rpos=start+1;
					lpalin_(lpos, rpos);
				}
				k2=false;
			}
		};
		
		ex.execute(even);
		ex.execute(odd);
		
		while(k1||k2)
			Thread.sleep(10);
	
		out.println(slength(best));
		out.println(best);
	}
	
	//used twice.
	static void lpalin_(int lpos, int rpos){
		while(lpos>=0&&rpos<str.length()){
			char prevchar=str.charAt(lpos), nextchar=str.charAt(rpos);
			if(!isASCII(prevchar)){
				lpos--;
				continue;
			}
			if(!isASCII(nextchar)){
				rpos++;
				continue;
			}
			if(!equals(prevchar, nextchar))
				break;
			lpos--;
			rpos++;
		}
		String unstripped = str.substring(lpos+1,rpos);
		if(unstripped.length()>best.length())
			if(slength(unstripped)>best.length())
				best=strip(unstripped);
	}
	
	static int slength(String s){
		int ret=0;
		for(char c:s.toCharArray())
			if(isASCII(c))
				ret++;
		return ret;
	}
	
	static String strip(String s){
		int firstchar=0, lastchar=0;
		for(int i=0; i<s.length(); i++){
			if(isASCII(s.charAt(i))){
				firstchar=i;
				break;
			}
		}
		for(int i=s.length()-1; i>=0; i--){
			if(isASCII(s.charAt(i))){
				lastchar=i;
				break;
			}
		}
		return s.substring(firstchar, lastchar+1);
	}
	
	//A-Za-z?
	static boolean isASCII(char c){
		return (97<=c&&c<=122) || (65<=c&&c<=90);
	}
	
	static boolean equals(char a, char b){
		return Character.toLowerCase(a)==Character.toLowerCase(b);
	}
	
	static String readall(BufferedReader in) throws IOException{
		StringBuilder ret= new StringBuilder();
		String next;
		while((next=in.readLine())!=null){
			ret.append(next);
			ret.append("\n");
		}
		return ret.toString();
	}
}
