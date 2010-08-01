import java.io.*;
import java.util.*;

public class Main{

  // Fast way to convert between hex and binary
  static Map<Character,byte[]> Hexmap;
  static int cs;

  public static void main(String[] args) throws Throwable{
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    // Initialize hex map
    Hexmap = new HashMap<Character,byte[]>();
    Hexmap.put('0',new byte[]{0,0,0,0});
    Hexmap.put('1',new byte[]{0,0,0,1});
    Hexmap.put('2',new byte[]{0,0,1,0});
    Hexmap.put('3',new byte[]{0,0,1,1});
    Hexmap.put('4',new byte[]{0,1,0,0});
    Hexmap.put('5',new byte[]{0,1,0,1});
    Hexmap.put('6',new byte[]{0,1,1,0});
    Hexmap.put('7',new byte[]{0,1,1,1});
    Hexmap.put('8',new byte[]{1,0,0,0});
    Hexmap.put('9',new byte[]{1,0,0,1});
    Hexmap.put('A',new byte[]{1,0,1,0});
    Hexmap.put('B',new byte[]{1,0,1,1});
    Hexmap.put('C',new byte[]{1,1,0,0});
    Hexmap.put('D',new byte[]{1,1,0,1});
    Hexmap.put('E',new byte[]{1,1,1,0});
    Hexmap.put('F',new byte[]{1,1,1,1});

    int ncases = Integer.parseInt(in.readLine());

    for(cs=1; cs<=ncases; cs++)
      processCase(in);
  }

  // One test case at a time
  static void processCase(BufferedReader in) throws Throwable{

    // First line
    String fLine = in.readLine();
    // String array
    String[] sarr = fLine.split(" ");

    // Length and width
    int L = Integer.parseInt(sarr[0]);
    int W = Integer.parseInt(sarr[1]);

    // Board representation
    boolean[][] b = new boolean[L][W];

    // Fill up board
    for(int line = 0; line < L; line++){
      // This line, with W/4 characters
      String s = in.readLine();

      int i=0;
      // Fill up array
      for(char c : s.toCharArray()){
        // Byte array representation
        byte[] rep = Hexmap.get(c);
        for(int j=0; j<4; j++)
          b[line][i+j] = rep[j]==1;
        i+=4;
      }
    }

    // Dynamic programming cache array
    // d[i,j] contains largest checkerboard with bottom right corner on it
    PriTree d = new PriTree(L,W);

    // Initialize d to 1
    for(int i=0; i<L; i++)
      for(int j=0; j<W; j++)
        d.put(i,j,1);

    // Initialize d
    for(int i=1; i<L; i++){
      for(int j=1; j<W; j++){
        // Color of square
        boolean sq = b[i][j];
        // Is the corner satisfactory?
        boolean sat = sq==b[i-1][j-1] && sq!=b[i][j-1] && sq!=b[i-1][j];
        if(!sat) continue;

        // Smallest board of the three
        int sm = Integer.MAX_VALUE;
        if(d.get(i-1,j-1)<sm) sm = d.get(i-1,j-1);
        if(d.get(i,j-1)<sm) sm = d.get(i,j-1);
        if(d.get(i-1,j)<sm) sm = d.get(i-1,j);

        // Fill in d
        d.put(i,j,sm+1);
      }
    }

    // Sizes of removed chessboards
    List<Integer> resultL = new ArrayList<Integer>();

    while(true){
      // Find max
      WeightedPair wp = d.extractMax();
      if(wp==null) break;
      int m = wp.y;
      int n = wp.x;
      int max = d.get(m,n);

      // No more left
      if(max <= 1) break;

      // Remove chunk from board
      for(int i=m-max+1; i<=m; i++){
        for(int j=n-max+1; j<=n; j++)
          d.put(i,j,0);
      }

      // Recalculate: first reseed the immediate rows then recalculate the
      // values for the rest of the space
      int by = m+max-1, bx=n+max-1, cy=m-max+1, cx=n-max+1;
      // bx,by = upper bounds; cx,cy = lower bounds
      if(bx>=W) bx = W-1;
      if(by>=L) by = L-1;
      if(cy<0) cy=0;
      if(cx<0) cx=0;

      // Reseed
      if(n+1<W)
        for(int i=cy; i<=m+1&&i<L; i++)
          d.put(i,n+1,1);
      if(m+1<L)
        for(int i=cx; i<=n+1&&i<W; i++)
          d.put(m+1,i,1);

      for(int i=cy; i<=by; i++){
        for(int j=cx; j<=bx; j++){
          if(i<=m&&j<=n) continue;
          if(i==0||j==0) continue;
          if(d.get(i,j)==0) continue;
          // Copy and pasted from above
          boolean sq = b[i][j];
          boolean sat = sq==b[i-1][j-1] && sq!=b[i][j-1] && sq!=b[i-1][j];
          if(!sat) continue;
          int sm = Integer.MAX_VALUE;
          if(d.get(i-1,j-1)<sm) sm = d.get(i-1,j-1);
          if(d.get(i,j-1)<sm) sm = d.get(i,j-1);
          if(d.get(i-1,j)<sm) sm = d.get(i-1,j);
          d.put(i,j,sm+1);
        }
      }

      resultL.add(max);
    }

    // Print results
    // Two arraylists containing k and ct respectively
    List<Integer> f1 = new ArrayList<Integer>();
    List<Integer> f2 = new ArrayList<Integer>();
    // We don't count the 1's so subtract them from the total
    int tot = L*W;
    if(resultL.size()>0){
      // Previous integer
      int k=resultL.get(0);
      // Count of previous integer
      int ct=0;
      // How many different types
      int typediff=1;
      // Go through results list
      for(int i=0; i<resultL.size(); i++){
        int j = resultL.get(i);
        if(j==k) ct++;
        else{
          // Change k and update
          f1.add(k);
          f2.add(ct);
          tot -= (k*k*ct);
          k=j;
          ct=1;
          typediff++;
        }
      }
      // Edge case
      f1.add(k);
      f2.add(ct);
      tot -= (k*k*ct);
    }
    // Ones
    if(tot>0){
      f1.add(1);
      f2.add(tot);
    }

    System.out.println("Case #" + cs + ": " + f1.size());
    for(int i=0; i<f1.size(); i++)
      System.out.printf("%d %d\n", f1.get(i), f2.get(i));
    
  }

  // 2D array that can extract max easily, is backed by both a priority queue
  // and a regular 2d array.
  static class PriTree{
    TreeSet<WeightedPair> tree;
    int[][] a;
    WPComparator cmp;
    int L, W;

    PriTree(int L, int W){
      cmp = new WPComparator();
      tree = new TreeSet<WeightedPair>(cmp);
      a = new int[L][W];
    }

    int get(int i, int j){
      return a[i][j];
    }

    void put(int i, int j, int v){
      tree.remove(new WeightedPair(i,j,a[i][j]));
      if(v>1)
        tree.add(new WeightedPair(i,j,v));
      a[i][j] = v;
    }

    // Retrieve max element; weightedpair acts as a point.
    WeightedPair extractMax(){
      if(tree.isEmpty()) return null;
      return tree.last();
    }
  }

  // Pair of integers with a weight for sorting
  static class WeightedPair {
    int x, y, val;
    WeightedPair(int yy, int xx, int vall){
      x=xx; y=yy; val=vall;
    };

    public String toString(){
      return "(" + y + "," + x + "," + val + ")";
    }
  }

  static class WPComparator implements Comparator<WeightedPair>{
    public int compare(WeightedPair a, WeightedPair b){
      // Usual case
      if(a.val != b.val)
        return a.val - b.val;
      // Special cases
      if(a.y != b.y)
        return b.y - a.y;
      return b.x - a.x;
    }
  }
}
