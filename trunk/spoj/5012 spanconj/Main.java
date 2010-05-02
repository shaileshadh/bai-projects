import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Main{

    // First index: ending
    // Second index: person (first, second, third)
    // Third index: singular/plural
    static final String[][][] conjugations = {
        {{"o","amos"}, {"as","Ais"}, {"a","an"}}, // -ar
        {{"o","emos"}, {"es","Eis"}, {"e","en"}}, // -er
        {{"o","imos"}, {"es","Is"}, {"e","en"}}, // -ir
    };

    public static void main(String[] args) throws Throwable{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String s;
        while((s = in.readLine()) != null){

            // Parse using regular expressions
            final Pattern pattern = Pattern.compile("(.+), (.+): (.+)");
            Matcher matcher = pattern.matcher(s);
            matcher.find();

            String regular = matcher.group(1);
            String tense = matcher.group(2);
            String conjugate = matcher.group(3);

            // Seperate regular into base and ending
            int reglen = regular.length();
            String base = regular.substring(0,reglen-2);
            String ending = regular.substring(reglen-2);

            // Detect person
            int person = -1;
            if(tense.startsWith("first person"))        person = 0;
            else if(tense.startsWith("second person"))  person = 1;
            else if(tense.startsWith("third person"))   person = 2;

            // Detect plurality
            int plural = -1;
            if(tense.endsWith("singular"))      plural = 0;
            else if(tense.endsWith("plural"))   plural = 1;

            // Detect ending
            int endint = -1;
            if(ending.equals("ar"))         endint = 0;
            else if(ending.equals("er"))    endint = 1;
            else if(ending.equals("ir"))    endint = 2;

            // Conjugate according to rules
            String trueConjugate = base + conjugations[endint][person][plural];
            
            // Detect if it's correct
            if(trueConjugate.equals(conjugate))
                System.out.println("correct");
            else System.out.println("incorrect, should be " + trueConjugate);
        }
    }
}
