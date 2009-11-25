
/*
TASK: milk
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class milk{
	public static void main(String... args) throws Throwable{
		BufferedReader in = new BufferedReader(new FileReader("milk.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk.out")));
		prob(in, out);
		out.close();
		System.exit(0);
	}

	static void prob(BufferedReader in, PrintWriter out) throws IOException{
		String line1 = in.readLine();
		StringTokenizer tk = new StringTokenizer(line1);
		int needed = Integer.parseInt(tk.nextToken());
		int nfarmers = Integer.parseInt(tk.nextToken());
		Farmer[] farmers = new Farmer[nfarmers];
		for(int i=0; i<nfarmers; i++){
			String line=in.readLine();
			StringTokenizer linet=new StringTokenizer(line);
			int price=Integer.parseInt(linet.nextToken());
			int available=Integer.parseInt(linet.nextToken());
			farmers[i] = new Farmer(price, available);
		}
		
		Arrays.sort(farmers);
		
		
		int cost=0;
		for(Farmer farmer:farmers){
			if(needed<0)
				break;
			int buy;
			if(farmer.available>needed)
				buy=needed;
			else buy=farmer.available;
			int price=buy*farmer.price;
			cost+=price;
			needed-=buy;
		}
		
		out.println(cost);
	}
}

class Farmer implements Comparable{
	final int price;
	final int available;
	Farmer(int price, int available){
		this.price=price; this.available=available;
	}
	public int compareTo(Object o){
		Farmer ob = (Farmer) o;
		return price-ob.price;
	}
	public boolean equals(Object o){
		Farmer ob = (Farmer) o;
		return price==ob.price && available==ob.available;
	}
	public String toString(){
		return "Price:" + price + " Available:" + available;
	}
}

