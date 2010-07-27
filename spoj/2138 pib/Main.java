// Incomplete program.

// Benchmark:
// P(3000) ends with 222981.

import java.io.*;
import java.util.*;
import java.math.*;

public class Main{
  static long Pi =  314159265358979L;
  static long One = 100000000000000L;

  public static void main(String[] args) throws Throwable {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    cache = new HashMap<Long,BigInteger>();

    String s;
    while(!((s = in.readLine()).equals("-1"))){
      print50(getP(Long.parseLong(s)*One).toString());
    }
  }

  static void print50(String s){
    int j = 0;
    while(j < s.length()){

      int k;
      if(s.length() - j > 50) k = j+50;
      else k = s.length();

      System.out.println(s.substring(j,k));
      j+=50;
    }
  }

  static Map<Long,BigInteger> cache;

  // Returns P(n/10000) (so P(pi) would be getP(314159) )
  // Store results in a map to be more efficient.
  static BigInteger getP(long n){
    if(n<4*One) return BigInteger.ONE;

    // Already have this value.
    if(cache.containsKey(n))
      return cache.get(n);

    // Calculate value, put in the cache
    BigInteger val = getP(n-One).add(getP(n-Pi));
    //cache.put(n,val);

    return val;
  }
}
