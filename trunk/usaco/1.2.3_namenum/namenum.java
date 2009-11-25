
/*
TASK: namenum
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class namenum{
	public static void main(String... args) throws Throwable{
		BufferedReader in = new BufferedReader(new FileReader("namenum.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("namenum.out")));
		prob(in, out);
		out.close();
		System.exit(0);
	}

	static void prob(BufferedReader in, PrintWriter out) throws IOException{
		initmap();
		String num=in.readLine();
		Set<String> names = IO_dict(num.length());
		boolean found=false;
		for(String name:names)
			if(matches(num, name)){
				out.println(name);
				found=true;
			}
		if(!found)
			out.println("NONE");
	}
	
	static Set<String> IO_dict(int length) throws IOException{
		Set<String> names = new TreeSet<String>();
		BufferedReader in = new BufferedReader(new FileReader("dict.txt"));
		String next;
		while((next=in.readLine())!=null)
			if(next.length()==length)
				names.add(next);
		return names;
	}
	
	static Map<Character, char[]> keymap=null;
	
	static void initmap(){
		keymap = new HashMap<Character, char[]>();
		keymap.put('2', new char[]{'A', 'B', 'C'});
		keymap.put('3', new char[]{'D', 'E', 'F'});
		keymap.put('4', new char[]{'G', 'H', 'I'});
		keymap.put('5', new char[]{'J', 'K', 'L'});
		keymap.put('6', new char[]{'M', 'N', 'O'});
		keymap.put('7', new char[]{'P', 'R', 'S'});
		keymap.put('8', new char[]{'T', 'U', 'V'});
		keymap.put('9', new char[]{'W', 'X', 'Y'});
	}
	
	static boolean matches(String num, String name){
		for(int i=0; i<num.length(); i++){
			char c=num.charAt(i);
			char m=name.charAt(i);
			if(!(keymap.containsKey(c)))
				return false;
			char[] possibles=keymap.get(c);
			if(!(contains(possibles, m)))
				return false;
		}
		return true;
	}
	
	static boolean contains(char[] array, char elem){
		for(char e:array){
			if(e==elem)
				return true;
		}
		return false;
	}
}
