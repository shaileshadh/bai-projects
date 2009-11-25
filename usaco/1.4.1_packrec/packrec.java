
/*
TASK: packrec
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class packrec{
	public static void main(String... args) throws Throwable{
		BufferedReader in = new BufferedReader(new FileReader("packrec.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("packrec.out")));
		prob(in, out);
		out.close();
		System.exit(0);
	}

	static int[][] rects;
	static int[][][] perms;
	static int[][] cur;
	static boolean[] rectsused;
	static int index;

	static void prob(BufferedReader in, PrintWriter out) throws IOException{
		rects = new int[4][];
		for(int i=0; i<4; i++){
			String line = in.readLine();
			Scanner sc = new Scanner(line);
			int[] thisrect = new int[2];
			thisrect[0] = sc.nextInt();
			thisrect[1] = sc.nextInt();
			rects[i]=thisrect;
		}

		perms = new int[384][][]; //4! * 2^4 = 384
		cur = new int[4][];
		rectsused = new boolean[4];
		Arrays.fill(rectsused, false);
		index = 0;
		makeperms(0);
		
		index--; //off by 1.
		List<Integer> areas = new ArrayList<Integer>();
		List<int[]> dimensions = new ArrayList<int[]>();
		for(int i=0; i<index; i++){
			int[][] t = perms[i]; //simplification
			int[][] bigrect = new int[5][];

			bigrect[0] = new int[2];
			bigrect[0][0] = t[0][0] + t[1][0] + t[2][0] + t[3][0];
			bigrect[0][1] = mx(t[0][1], t[1][1], t[2][1], t[3][1]);

			bigrect[1] = new int[2];
			int b1bottomx = t[0][0];
			int b1bottomy = t[0][1];
			int b1topx = t[1][0] + t[2][0] + t[3][0];
			int b1topy = mx(t[1][1], t[2][1], t[3][1]);
			bigrect[1][0] = mx(b1bottomx, b1topx);
			bigrect[1][1] = b1bottomy + b1topy;

			bigrect[2] = new int[2];
			int b2tallx = t[0][0];
			int b2tally = t[0][1];
			int b2bottomx = t[1][0];
			int b2bottomy = t[1][1];
			int b2topleftx = t[2][0] + t[3][0];
			int b2toplefty = mx(t[2][1], t[3][1]);
			int b2leftx = mx(b2bottomx, b2topleftx);
			int b2lefty = b2bottomy + b2toplefty;
			bigrect[2][0] = b2tallx + b2leftx;
			bigrect[2][1] = mx(b2tally, b2lefty);

			bigrect[3] = new int[2];
			int b3middlex = mx(t[0][0], t[1][0]);
			int b3middley = t[0][1] + t[1][1];
			bigrect[3][0] = t[2][0] + t[3][0] + b3middlex;
			bigrect[3][1] = mx(t[2][1], t[3][1], b3middley);

			// Skipping one which is geometrically identical to the
			// previous one.

			// Last one is very tricky.
			// 3 2
			// 0 1
			bigrect[4] = new int[2];
			int b4accum = t[0][0] + t[1][0];
			if(t[0][1] < t[1][1])
				b4accum = mx(b4accum, t[1][0] + t[3][0]);
			if(t[1][1] < t[0][1])
				b4accum = mx(b4accum, t[0][0] + t[2][0]);
			if(t[0][1] + t[3][1] > t[1][1])
				b4accum = mx(b4accum, t[2][0] + t[3][0]);
			bigrect[4][0] = mx(b4accum, t[2][0], t[3][0]);
			bigrect[4][1] = mx(t[0][1] + t[3][1], t[1][1] + t[2][1]);

			for(int m=0; m<5; m++){
				int area = bigrect[m][0] * bigrect[m][1];
				areas.add(area);
				dimensions.add(new int[]{bigrect[m][0], bigrect[m][1]});
			}
		}

		Collections.sort(areas);
		int smallest = areas.get(0);
		out.println(smallest);

		Set<int[]> outlist = new TreeSet<int[]>(new ArrayCmp());
		for(int[] dim : dimensions){
			int ar = dim[0] * dim[1];
			if(ar == smallest){
				Arrays.sort(dim);
				outlist.add(dim);
			}
		}

		for(int[] a : outlist)
			out.println(a[0] + " " + a[1]);
	}

	static class ArrayCmp implements Comparator{
		public int compare(Object o1, Object o2){
			int[] a1 = (int[]) o1, a2 = (int[]) o2;
			if(a1[0] == a2[0] && a1[1] == a2[1])
				return 0;
			return a1[0] - a2[0];
		}
	}

	static void makeperms(int pos){
		if(pos==4){
			perms[index] = new int[4][];
			for(int i=0; i<4; i++){
				perms[index][i] = new int[2];
				perms[index][i][0] = cur[i][0];
				perms[index][i][1] = cur[i][1];
			}
			index++;
			return;
		}
		for(int i=0; i<4; i++){
			if(rectsused[i])
				continue;
			rectsused[i] = true;
			cur[pos] = rects[i];
			makeperms(pos+1);
			// Switch terms around.
			cur[pos] = new int[]{rects[i][1], rects[i][0]};
			makeperms(pos+1);
			rectsused[i] = false;
		}
	}

	static int mx(int... array){
		int ret=0;
		for(int i:array)
			if(i>ret)
				ret=i;
		return ret;
	}
}
