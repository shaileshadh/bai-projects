
/*
TASK: transform
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class transform{
	public static void main(String... args) throws Throwable{
		BufferedReader in = new BufferedReader(new FileReader("transform.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("transform.out")));
		prob(in, out);
		out.close();
		System.exit(0);
	}

	static void prob(BufferedReader in, PrintWriter out) throws IOException{
		int matrixsize = Integer.parseInt(in.readLine());
		char[][] matrixstart=new char[matrixsize][matrixsize], matrixend=new char[matrixsize][matrixsize];
		for(int i=0; i<matrixsize; i++){
			String line=in.readLine();
			char[] chararray=line.toCharArray();
			for(int k=0; k<matrixsize; k++)
				matrixstart[i][k]=chararray[k];
		}
		for(int i=0; i<matrixsize; i++){
			String line=in.readLine();
			char[] chararray=line.toCharArray();
			for(int k=0; k<matrixsize; k++)
				matrixend[i][k]=chararray[k];
		}
		
		char[][] rot90=rot90(matrixstart);
		char[][] rot180=rot180(matrixstart);
		char[][] rot270=rot270(matrixstart);
		char[][] reflect=reflect(matrixstart);
		char[][] reflectrot90=rot90(reflect);
		char[][] reflectrot180=rot180(reflect);
		char[][] reflectrot270=rot270(reflect);
		
		int result=7;
		if(Arrays.deepEquals(matrixend, rot90))
			result=1;
		else if(Arrays.deepEquals(matrixend, rot180))
			result=2;
		else if(Arrays.deepEquals(matrixend, rot270))
			result=3;
		else if(Arrays.deepEquals(matrixend, reflect))
			result=4;
		else if(Arrays.deepEquals(matrixend, reflectrot90)||Arrays.deepEquals(matrixend, reflectrot180)
				||Arrays.deepEquals(matrixend, reflectrot270))
			result=5;
		else if(Arrays.deepEquals(matrixend, matrixstart))
			result=6;
			
		out.println(result);
	}
	
	//Rotates char array 90 degrees clockwise.
	static char[][] rot90(char[][] in){
		char[][] copy = new char[in.length][in.length];
		for(int i1=0, i2=in.length-1; i1<in.length; i1++, i2--)
			for(int k=0; k<in.length; k++)
				copy[k][i2]=in[i1][k];
		return copy;
	}
	
	static char[][] rot180(char[][] in){
		return rot90(rot90(in));
	}
	
	static char[][] rot270(char[][] in){
		return rot90(rot180(in));
	}
	
	static char[][] reflect(char[][] in){
		char[][] copy = new char[in.length][in.length];
		for(int i=0; i<in.length; i++)
			for(int k1=0, k2=in.length-1; k1<in.length; k1++, k2--)
				copy[i][k1]=in[i][k2];
		return copy;	
	}
}
