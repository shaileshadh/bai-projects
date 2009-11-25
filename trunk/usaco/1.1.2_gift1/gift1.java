
/*
TASK: gift1
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class gift1{
	public static void main(String... args) throws Throwable{
		prob();
	}
	
	static void prob() throws IOException{
		Scanner f = new Scanner(new FileInputStream("gift1.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("gift1.out")));
		String s_numpeopl = f.nextLine();
		int i_numpeopl = Integer.parseInt(s_numpeopl);
		String[] names = new String[i_numpeopl];
		Map<String, Integer> accounts = new LinkedHashMap<String, Integer>();
		for(int i = 0;i < i_numpeopl;i++){
			names[i] = f.nextLine();
			accounts.put(names[i], 0);
		}
		while(f.hasNextLine()){
			String giver = f.nextLine();
			String t_argx = f.nextLine();
			int moneygivn = Integer.parseInt(t_argx.split("\\s")[0]);
			int peopgivn = Integer.parseInt(t_argx.split("\\s")[1]);
			if(peopgivn == 0) continue;
			int moneyper = moneygivn / peopgivn;
			for(int i = 0;i < peopgivn;i++){
				String nextrecipent = f.nextLine();
				addmoney(accounts, nextrecipent, moneyper);
				addmoney(accounts, giver, -moneyper);
			}
		}
		for(String name : names){
			out.println(name + " " + accounts.get(name));
		}
		out.close();
		System.exit(0);
	}
	
	static void addmoney(Map<String, Integer> map, String name, int amount){
		int existing = map.get(name);
		int newamount = existing + amount;
		map.remove(name);
		map.put(name, newamount);
	}
}
