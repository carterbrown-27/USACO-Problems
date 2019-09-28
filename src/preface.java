/*
ID: carter.2
TASK: preface
LANG: JAVA
 */
import java.util.*;
import java.io.*;
public class preface {

	// Solution for "Problem 92_Preface Numbering"
	public static void main(String[] args) throws IOException{
		long first_time = System.nanoTime();
		
		BufferedReader in;
		String file = "preface.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}	
		
		Integer N = Integer.parseInt(in.readLine());
		in.close();
		
		final int[] NUMERALS = {1000, 500, 100, 50, 10, 5, 1};
		final char[] NUMERAL_CHARS = {'M','D','C','L','X','V','I'};
		
		int[] totals = new int[NUMERALS.length];
		for(int i = 1; i <= N; i++) {
			int cN = 0;
			int t = i;
			do {
				if(t < NUMERALS[cN]) {
					// handle CM/IX cases
					if(cN % 2 == 0 && cN < 6) {
						// 1000 checks 100, etc.
						int r = NUMERALS[cN]-NUMERALS[cN+2];
						if(t >= r) {
							t-=r;
							totals[cN]++;
							totals[cN+2]++;
						}
					}else {
						// 500 checks 100, etc.
						int r = NUMERALS[cN]-NUMERALS[cN+1];
						if(t >= r) {
							t-=r;
							totals[cN]++;
							totals[cN+1]++;
						}
					}
					cN++;
					continue;
				}else {
					t -= NUMERALS[cN];
					totals[cN]++;
				}
			}while(t > 0);
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("preface.out")));
		for(int i = totals.length; i-- > 0;) {
			if(totals[i] > 0) out.println(NUMERAL_CHARS[i]+" "+totals[i]);
		}
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
	}
}
