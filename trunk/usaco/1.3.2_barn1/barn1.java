
/*
TASK: barn1
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class barn1{
	public static void main(String... args) throws Throwable{
		BufferedReader in = new BufferedReader(new FileReader("barn1.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("barn1.out")));
		prob(in, out);
		out.close();
		System.exit(0);
	}

	static void prob(BufferedReader in, PrintWriter out) throws IOException{
		String firstline = in.readLine();
		StringTokenizer firstparser = new StringTokenizer(firstline);
		int maxb = Integer.parseInt(firstparser.nextToken());
		int nstalls = Integer.parseInt(firstparser.nextToken());
		int ncows = Integer.parseInt(firstparser.nextToken());
		
		boolean[] stalls = new boolean[nstalls];
		Arrays.fill(stalls, false);
		String nextl = "";
		while((nextl=in.readLine())!=null){
			int occ = Integer.parseInt(nextl);
			stalls[occ-1] = true;
		}
		
		//whether stalls[n] is boarded.
		boolean[] boarded = new boolean[nstalls];
		int firstcow=0, lastcow=0;
		for(int i=0; i<boarded.length; i++){
			if(stalls[i]){
				firstcow=i;
				break;
			}
		}
		for(int i=boarded.length-1; i>=0; i--){
			if(stalls[i]){
				lastcow=i;
				break;
			}
		}
		
		//Initial configuration.
		for(int i=firstcow; i<=lastcow; i++){
			boarded[i] = true;
		}
		
		for(int board=1; board<maxb; board++){
			boarded = calcfill(stalls, boarded);
		}
		
		int count=0;
		for(boolean b:boarded)
			if(b) count++;
			
		out.println(count);
	}
	
	static boolean[] calcfill(final boolean[] stalls, final boolean[] boarded){
		class Stall implements Comparable{
			boolean hascow;
			boolean boarded;
			public Stall(boolean a, boolean b){
				hascow=a;
				boarded=b;
			}
			public int compareTo(Object ob){
				Stall o = (Stall) ob;
				return boarded==o.boarded? 0: 1;
			}
			public String toString(){
				return "[cow:" + hascow + "; board:" + boarded + "]";
			}
		}
	
		List<Stall> gst = new ArrayList<Stall>();//To group the stalls.
		for(int i=0; i<boarded.length; i++){
			gst.add(new Stall(stalls[i], boarded[i]));
		}
		
		int mx=0, memst=0, memen=0, off=0;
		List<List<Stall>> gped = group(gst); //grouped. 
		for(List<Stall> sub : gped){
			if(sub.get(0).boarded){
				int st=0, en=0;
				boolean las=false;
				for(int i=0; i<sub.size(); i++){
					Stall th = sub.get(i);
					if(th.hascow != las){
						if(!(th.hascow)){
							st=i;
							en=i;
						}
						else{
							en=i;
							int dis=en-st;
							if(dis>mx){
								mx=dis;
								memst=st+off;
								memen=en+off;
							}
						}
					}
					las=th.hascow;
				}
			}
			off+=sub.size();
		}
		
		boolean[] next = new boolean[boarded.length];
		for(int i=0; i<next.length; i++){
			if(memst<=i && i<memen)
				next[i] = false;
			else next[i] = boarded[i];
		}
		return next;
	}
	
	//group equal consecutive objects.
	static <T extends Comparable<T>> List<List<T>> group(List<T> list){
		T start=list.get(0);
		int p=0;
		List<List<T>> ret = new ArrayList<List<T>>();
		for(int i=0; i<list.size(); i++){
			T ob = list.get(i);
			int cp = ob.compareTo(start);
			if(cp!=0){
				List<T> group = list.subList(p,i);
				ret.add(group);
				start=ob;
				p=i;
			}
		}
		ret.add(list.subList(p, list.size()));
		return ret;
	}
}
