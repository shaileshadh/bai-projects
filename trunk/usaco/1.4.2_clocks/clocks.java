
/*
TASK: clocks
LANG: JAVA
*/

// Wow I was stuck on this one for a LONG time.

import java.io.*;
import java.util.*;

public class clocks{
	public static void main(String... args) throws Throwable{
		BufferedReader in = new BufferedReader(new FileReader("clocks.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("clocks.out")));
		prob(in, out);
		out.close();
		System.exit(0);
	}

	static int[] initial, current;
	static int maxdepth;
	static int[] solution;

	/*
	 * 0 1 2   A B C
	 * 3 4 5   D E F
	 * 6 7 8   G H I
	 * */
	static final int[][] patterns = {
		{0,1,3,4},
		{0,1,2},
		{1,2,4,5},
		{0,3,6},
		{1,3,4,5,7},
		{2,5,8},
		{3,4,6,7},
		{6,7,8},
		{4,5,7,8}
	};

	static int[] used;

	static void prob(BufferedReader in, PrintWriter out) throws IOException{
		initial = new int[9];
		current = new int[9];
		used = new int[9];

		for(int i=0; i<3; i++){
			String line = in.readLine();
			StringTokenizer stok = new StringTokenizer(line);
			Scanner scan = new Scanner(line);
			initial[i*3] = scan.nextInt();
			initial[i*3+1] = scan.nextInt();
			initial[i*3+2] = scan.nextInt();
		}

		for(maxdepth=0;; maxdepth++){
			solution = new int[maxdepth];
			for(int i=0; i<9; i++){
				current[i]=initial[i];
				used[i]=0;
			}

			boolean f=search(0,0);
			if(f)
				break;
		}

		for(int i=0; i<solution.length-1; i++)
			out.print(solution[i] + " ");
		out.println(solution[solution.length-1]);
	}

	// When called, current[][] should contain the position from which
	// to begin the search.
	static boolean search(int startfrom, int curdepth){
		boolean all12 = true;

		// Is it already all 12?
		for(int i=0; i<9; i++)
			if(current[i]!=12){
				all12 = false;
				break;
			}
		
		if(all12){
			return true;
		}
		
		if(curdepth>=maxdepth)
			return false;

		for(int pat=startfrom; pat<9; pat++){
			if(used[pat]>=3)
				continue;

			//Adjust to, and back
			for(int c:patterns[pat])
				current[c] = adjust(current[c]);

			int temp = solution[curdepth];
			used[pat]++;
			solution[curdepth]=pat+1;
			boolean f = search(pat,curdepth+1);
			if(f)
				return true;
			solution[curdepth]=temp;
			used[pat]--;

			for(int c:patterns[pat])
				current[c] = adjustback(current[c]);
		}

		return false;
	}

	static int adjust(int a){
		if(a==12)
			return 3;
		else return a+3;
	}

	static int adjustback(int a){
		if(a==3)
			return 12;
		else return a-3;
	}

}
