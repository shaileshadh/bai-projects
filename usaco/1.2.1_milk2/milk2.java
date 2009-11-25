
/*
TASK: milk2
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class milk2{
	public static void main(String... args) throws Throwable{
		BufferedReader in = new BufferedReader(new FileReader("milk2.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk2.out")));
		prob(in, out);
		out.close();
		System.exit(0);
	}

	static void prob(BufferedReader in, PrintWriter out) throws IOException{
		long sttime=System.currentTimeMillis();
		int numfarmers=Integer.parseInt(in.readLine());
		List<MilkAction> actions = new ArrayList<MilkAction>();
		for(int i=0; i<numfarmers; i++){
			String line = in.readLine();
			String[] linearray=new String[2];
			StringTokenizer tok=new StringTokenizer(line);
			linearray[0]=tok.nextToken();
			linearray[1]=tok.nextToken();
			int start = Integer.parseInt(linearray[0]);
			int end = Integer.parseInt(linearray[1]);
			actions.add(new MilkAction(start, MilkAction.START));
			actions.add(new MilkAction(end, MilkAction.END));
		}
		Collections.sort(actions);
		
		int cowsbeingmilked=0, startcount=0, cowslast=0;
		boolean first=true;
		List<Integer> milkedtrue=new ArrayList<Integer>(), milkedfalse=new ArrayList<Integer>();
		for(MilkAction action:actions){
			if(action.action==100)//Start
				cowsbeingmilked++;
			else if(action.action==101)//End
				cowsbeingmilked--;
				
			if(cowsbeingmilked>0&&cowslast==0){//Finished period of non-milking
				if(!first)
					milkedfalse.add(action.time-startcount);
				first=false;
				startcount=action.time;
			}
			else if(cowsbeingmilked<=0&&cowslast!=0){//Finished period of milking
				milkedtrue.add(action.time-startcount);
				startcount=action.time;
			}
			
			cowslast=cowsbeingmilked;
		}
		
		out.println(max(milkedtrue) + " " + max(milkedfalse));
	}
	
	static int max(List<Integer> list){
		int m=0;
		for(int e:list){
			if(e>m)
				m=e;
		}
		return m;
	}
}

class MilkAction implements Comparable{
	int time;
	int action;
	static final int START=100, END=101;
	MilkAction(int time, int action){
		this.time=time; this.action=action;
	}
	public int compareTo(Object o){
		MilkAction ob = (MilkAction)o;
		int ret=time-ob.time;
		if(ret!=0)
			return ret;
		else return action-ob.action;
	}
	@Override public String toString(){
		String act_s = "";
		switch(action){
			case START:
				act_s="START";
				break;
			case END:
				act_s="END";
				break;
		}
		return act_s + ":" + time;
	}
}
