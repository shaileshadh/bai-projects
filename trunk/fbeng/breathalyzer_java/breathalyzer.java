import java.io.*;
import java.util.*;

public class breathalyzer{
    public static void main(String[] args) throws Exception{

        BufferedReader in = new BufferedReader(
            new FileReader(args[0]));

        // List (actually map) of words we can match.
        // We use a map so it's easy to tell whether a word is in the list
        // but we don't actually map it to anything.
        Map<String,Object> dictionary = new LinkedHashMap<String,Object>();

        readDictionary(dictionary);

        // Sentence we're supposed to analyze
        String sentence = in.readLine();

        // Tally up the total minimum edit distances
        int total = 0;

        Scanner parseSentence = new Scanner(sentence);
        while(parseSentence.hasNext()){
            String word = parseSentence.next();
            total += dictDistance(dictionary, word);
        }

        System.out.println(total);
    }

    // Minimum edit distance from a word to a entry in the dictionary
    static int dictDistance(Map<String,Object> d, String word){

        // Already is a word?
        if(d.containsKey(word)) return 0;

        // Convert it to a character array before using it
        char[] wordA = word.toCharArray();
        int wLen = word.length();

        int minDistance = Integer.MAX_VALUE;
        for(String entry : d.keySet()){

            // Length of this word; If the difference in lengths is greater or
            // equal to the minimum distance then we can skip it.
            int eLen = entry.length();
            int diff = eLen - wLen;
            if(diff < 0) diff = -diff;
            if(diff >= minDistance) continue;

            // Loop through the dictionary to find the best match
            int dist = distance(entry.toCharArray(), wordA);

            // Replace if smaller
            if(dist < minDistance) minDistance = dist;

            // We can't have an exact match at this point.
            // The minimum possible is one.
            if(minDistance == 1) break;
        }
        
        return minDistance;
    }

    static void readDictionary(Map<String,Object> d) throws Exception{

        // Actual location of file is changed depending on whether we're
        // debugging or submitting the program.
        BufferedReader in = new BufferedReader(
            //new FileReader("./dict.txt"));
            new FileReader("/var/tmp/twl06.txt"));

        // Convert all dictionary words into lowercase when reading it so we
        // don't have to deal with casing later.
        String word;
        while( (word = in.readLine()) != null)
            d.put(word.toLowerCase(),null);
    }

    // Edit distance between two character arrays using insertion,
    // deletion, and substitution. Ignores case.
    static int distance(char[] a, char[] b){
        
        // Use a dynamic programming algorithm.
        int[][] dp = new int[a.length+1][b.length+1];

        // Fill up first row and column
        for(int i=0; i<=a.length; i++) dp[i][0] = i;
        for(int i=0; i<=b.length; i++) dp[0][i] = i;

        // Fill up dp array
        for(int i=1; i<=a.length; i++)
            for(int j=1; j<=b.length; j++){
                
                boolean same = a[i-1] == b[j-1];

                // Substitution
                int su = dp[i-1][j-1];
                if(!same) su++;

                // Insertion and deletion
                int in = dp[i-1][j]+1;
                int de = dp[i][j-1]+1;

                // Least of the three
                int ce = su;
                if(in < ce) ce = in;
                if(de < ce) ce = de;

                // Write to array
                dp[i][j] = ce;
            }

        return dp[a.length][b.length];
    }
}
