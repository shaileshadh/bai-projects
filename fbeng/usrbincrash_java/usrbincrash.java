import java.io.*;
import java.util.*;

public class usrbincrash{
    public static void main(String[] args) throws Exception{

        BufferedReader in = new BufferedReader(
            new FileReader(args[0]));

        // Minimum weight to prevent crash
        int crashw = Integer.parseInt(in.readLine());
        
        // List containing weights of items
        List<Integer> itemW = new ArrayList<Integer>();

        // List containing values of items
        List<Integer> itemV = new ArrayList<Integer>();

        String parse;
        while( (parse = in.readLine()) != null){
            Scanner scn = new Scanner(parse);
            scn.next();

            itemW.add(scn.nextInt());
            itemV.add(scn.nextInt());
        }

        // Take the GCD's before starting the DP
        int gcd = crashw;
        for(int i : itemW) gcd = gcd(gcd, i);
        
        // Divide all weights by gcd
        crashw /= gcd;
        for(int i=0; i<itemW.size(); i++)
            itemW.set(i, itemW.get(i)/gcd);

        // Calculate optimal fit using dynamic programming
        int[][] dp = new int[itemW.size()][crashw+1];

        // First row of DP array done separately
        dp[0][0] = 0;
        for(int j=1; j<=crashw; j++){
            
            int aW = itemW.get(0);
            int aV = itemV.get(0);

            if(aW > j) dp[0][j] = aV;
            else dp[0][j] = aV + dp[0][j-aW];
        }

        // Filling up the rest of the DP array
        for(int i=1; i<dp.length; i++){

            dp[i][0] = 0;
            for(int j=1; j<=crashw; j++){
                
                int iW = itemW.get(i);
                int iV = itemV.get(i);

                // Cell directly up from current
                int imUp = dp[i-1][j];

                // Cell left of it by iW
                int imLeft = 0;
                if(iW > j) imLeft = iV;
                else imLeft = iV + dp[i][j-iW];

                // Smallest of the two
                dp[i][j] = imUp<imLeft? imUp: imLeft;
            }
        }

        System.out.println(dp[itemW.size()-1][crashw]);
    }

    // GCD using the Euclid algorithm
    static int gcd(int a, int b){
        if(b == 0) return a;
        return gcd(b, a%b);
    }
}
