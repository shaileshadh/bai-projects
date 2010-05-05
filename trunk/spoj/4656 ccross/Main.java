
/* Solution to Problem 3: Mountain Climb. */

import java.io.*;
import java.util.*;
import static java.lang.Math.abs;

public class Main{

    /* Use a very big number to represent a case or subcase that is impossible.
       Instead of using Long.MAX_VALUE, this is in no danger of overflow and
       does not lose much generality.
    */
    static final long IMPOSSIBLE = 1000000000000L;

    public static void main(String[] args) throws Exception {
        
        BufferedReader in = new BufferedReader(
            new InputStreamReader(System.in));

        /* Each case is independant of another; they are parsed seperately,
           and calculations are performed on them. For each case a line of
           output is generated. */
        int numCases = Integer.parseInt(in.readLine());
        for(int i=0; i<numCases; i++){

            /* Parse the line before the data containing the number of columns,
               and the maximum acceptable discrepancy. */
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

    /* Procedure that calculates the mountain climb problem, given an array h
       of heights and integer d of maximum discrepancy. This implementation
       uses an approximately O(n^3) dynamic programming algorithm. */
    static long calculate(long[] h, long d){

        // Number of columns (of stones) in the mountain.
        int N = h.length;

        /* The columns in the DP array contain all of the relevant heights,
           which we are to calculate now. A relevant height is one of the
           form H_n +- k*D where H_n is some element of h, k is an integer
           between 0 and N, and D is the discrepency.

           Any other height need not be calculated, and if such a height
           is included then the algorithm will still run correctly but the
           column will be identical to a neighboring column.

           To save a few more columns, we note that k is not actually
           between 0 and N, but instead an integer from 0 to either the
           left or right end of the mountain, whichever is bigger. If
           H_n is in the middle, this cuts down the possible heights by
           quite a bit.

           The heights are stored in a TreeSet which filters duplicate
           elements and is automatically sorted. */
        Set<Long> allH_ = new TreeSet<Long>();
        for(int i=0; i<N; i++){
            
            // Distance from this column to farther column on the end
            int maxdist = Math.max(i, N-1-i);

            // Add all possible heights
            for(int j=-maxdist; j<=maxdist; j++){
                long posHeight = h[i] + j*d;
                if(posHeight >= 0)
                    allH_.add(posHeight);
            }
        }
        
        /* To make for constant time access, we convert the TreeSet allH_
           into an array. At the same time, we can find the final index
           of the result (which is a micro-optimization but may save a
           few milliseconds). The height of the result is already known,
           being the last element of h; however the index of that height
           within the DP array must be searched for. */
        long[] allH = new long[allH_.size()];
        int tree_ind = 0;
        int ind = -1;
        for(long ln : allH_){
            if(ln == h[N-1]) ind = tree_ind;

            allH[tree_ind] = ln;
            tree_ind++;
        }

        /* While normally this algorithm would take place in a two-dimensional
           array, we really only need to keep two rows of the array at any
           given time. This saves a lot of memory.

           The array dp[] refers to the row that would typically be above the
           cells we are working with, and dp_[] refers to the row we are
           working with, or the new row.

           The 2D DP array would contain N-1 rows and (allH.length) columns. */
        long[] dp = new long[allH.length];
        long[] _dp = new long[allH.length];

        /* Fill up the first row of the array. The first row can only be 0
           (if it's possible, and we don't have to move any stones), or it's
           impossible. */
        for(int j=0; j<allH.length; j++){
            long diff = abs(allH[j]-h[0]);
            if(diff <= d) dp[j] = 0;
            else dp[j] = IMPOSSIBLE;
        }

        // Fill up the rest of the array.
        for(int i=1; i<N-1; i++){

            /* Here we can observe a bitonicity property in the DP array, and
               cut down the running time of the algorithm from n^5 to n^3.

               Notice how k is reset to 0 at the start of a row, and not the
               start of a cell. So the value of k is kept between cells and
               is only cleared when we're starting a new row. */
            int k = 0;
            for(int j=0; j<allH.length; j++){

                // The maximum and minimum heights we're allowed to use
                long minVal = allH[j] - d;
                long maxVal = allH[j] + d;

                // Do a linear search for k, the starting point
                while(k < allH.length && allH[k] < minVal)
                    k++;

                /* We're looking for the minimum value of
                    dp[k] + abs(h[i]-allH[k])
                   within a known range of values. Because of bitonicity, once
                   we find an instance where the value for the next cell is
                   greater than the value for our current cell, the current
                   is the minimum.

                   We do an optimization here by not calculating two cells per
                   iteration but keeping the old 'next cell' and using it as
                   the new 'this cell' for the next iteration. */
                long dpthis = dp[k] + abs(h[i]-allH[k]);
                while(k < allH.length-1 && allH[k+1] <= maxVal){

                    // Value if k is incremented
                    long dpnext = dp[k+1] + abs(h[i]-allH[k+1]);

                    /* Anything over a certain value is impossible, one
                       instance of impossible may not be bigger than another.
                       This fixes a weird edge case bug. */
                    if(dpnext > IMPOSSIBLE) dpnext = IMPOSSIBLE;

                    // Minimum point reached
                    if(dpnext > dpthis) break;

                    // Keep on incrementing k otherwise
                    dpthis = dpnext;
                    k++;
                }

                _dp[j] = dpthis;
            }

            /* Swap the two rows. The current row becomes the previous row of
               the next iteration. Instead of declaring a new array on every
               iteration and garbage collecting the old array, we use the
               memory occupied by the old array to store the new array in. */
            long[] tmp = dp;
            dp = _dp;
            _dp = tmp;
        }

        return dp[ind];
    }
}

