
/* Solution to Problem 3: Mountain Climb. */

import java.io.*;
import java.util.*;

public class Main{

    // A very very big number
    static final long IMPOSSIBLE = 1000000000000L;

    public static void main(String[] args) throws Exception {
        
        BufferedReader in = new BufferedReader(
            new InputStreamReader(System.in));

        int numCases = Integer.parseInt(in.readLine());
        for(int i=0; i<numCases; i++){

            // Parse meta line (line before data)
            Scanner metaParse = new Scanner(in.readLine());
            int nCols = metaParse.nextInt();
            long mDiff = metaParse.nextInt();

            // Parse the data
            StringTokenizer dataParse = new StringTokenizer(in.readLine());
            long[] mountain = new long[nCols];
            for(int j=0; j<nCols; j++)
                mountain[j] = Long.parseLong(dataParse.nextToken());

            long result = calculate(mountain, mDiff);
            if(result >= IMPOSSIBLE) System.out.println("impossible");
            else System.out.println(result);
        }
    }

    // Input: Given mountain, and maximum allowed height difference
    static long calculate(long[] h, long d){

        int N = h.length;

        // Make a list of all possible relevant heights.
        Set<Long> allH_ = new TreeSet<Long>();
        for(int i=0; i<N; i++){
            
            // Distance from this column to farther column on the end
            int maxdist = Math.max(i, N-1-i);

            // A possible height is always what we already have +-
            // the height difference * an integer.
            for(int j=-maxdist; j<=maxdist; j++){
                long posHeight = h[i] + j*d;
                if(posHeight >= 0)
                    allH_.add(posHeight);
            }
        }
        
        // Convert allHT into array for faster access. Also find final
        // dp index here.
        long[] allH = new long[allH_.size()];
        int tree_ind = 0;
        int ind = -1;
        for(long ln : allH_){
            if(ln == h[N-1]) ind = tree_ind;

            allH[tree_ind] = ln;
            tree_ind++;
        }

        // Allocate the two rows here, instead of many times in the loop
        long[] dp = new long[allH.length];
        long[] _dp = new long[allH.length];

        // First row: either 0 or impossible.
        for(int j=0; j<allH.length; j++){
            long diff = abs(allH[j]-h[0]);
            if(diff <= d) dp[j] = 0;
            else dp[j] = IMPOSSIBLE;
        }

        // Rest of array, bulk of DP algorithm
        for(int i=1; i<N-1; i++){

            // Filling each cell of a row in the array
            int k = 0;
            for(int j=0; j<allH.length; j++){

                // Search through minimum up to maximum values
                long minVal = allH[j] - d;
                long maxVal = allH[j] + d;

                // Do a linear search for k, the starting point
                while(k < allH.length && allH[k] < minVal)
                    k++;

                // Search for minimum value of dp[i-1], taking advantage of the
                // bitonicity of the dp array; so keep going until our value is
                // bigger than what it was before
                long dpthis = dp[k] + abs(h[i]-allH[k]);
                while(k < allH.length-1 && allH[k+1] <= maxVal){

                    // Possible cells using this, and using the next
                    long dpnext = dp[k+1] + abs(h[i]-allH[k+1]);

                    // Anything over a certain value is impossible, one instance
                    // of impossible may not be bigger than another.
                    if(dpnext > IMPOSSIBLE) dpnext = IMPOSSIBLE;

                    if(dpnext > dpthis) break;
                    dpthis = dpnext;
                    k++;
                }

                _dp[j] = dpthis;
            }

            // Swap the two rows
            long[] tmp = dp;
            dp = _dp;
            _dp = tmp;
        }

        return dp[ind];
    }

    static long abs(long i){
        if(i>=0) return i;
        return -i;
    }
}

