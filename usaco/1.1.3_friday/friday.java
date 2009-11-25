
/*
TASK: friday
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class friday{
	public static void main(String... args) throws Throwable{
		prob();
	}
	
	static void prob() throws IOException{
		Scanner f = new Scanner(new FileInputStream("friday.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("friday.out")));
		int year = 1900;
		int month = 1;
		int day = 1;
		int end_date = 1900 + f.nextInt();
		int weekday = 1;
		int[] frequens = {0,0,0,0,0,0,0};
		while(true){
			if(year == end_date)
				break;
			day++;
			weekday++;
			int daysthismonth = daysinmonth(month, year);
			if(day > daysthismonth){
				month++;
				day = 1;
			}
			if(month>12){
				year++;
				month = 1;
			}
			if(weekday > 7)
				weekday = 1;
			if(day == 13){
				frequens[weekday-1]++;
			}
		}
		out.print(frequens[5] + " ");
		out.print(frequens[6] + " ");
		out.print(frequens[0] + " ");
		out.print(frequens[1] + " ");
		out.print(frequens[2] + " ");
		out.print(frequens[3] + " ");
		out.print(frequens[4] + "\n");
		out.close();
		System.exit(0);
	}
	
	static int[] dmonths = {31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	static int daysinmonth(int month, int year){
		if(month == 2)
			return isleapyear(year)? 29:28;
		return dmonths[month-1];
	}
	
	static boolean isleapyear(int year){
		if(year % 400 == 0) return true;
		if(year % 100 == 0) return false;
		return (year%4 == 0);
	}
}
