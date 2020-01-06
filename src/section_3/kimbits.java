package section_3;
/*
ID: carter.2
TASK: kimbits
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class kimbits {	
	// Solution for Section 3.2, adapted from the official USACO Analysis of the Solution (got stuck :P)
	
	static long[][] setSize = new long[33][33];
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		
		// input
		BufferedReader in;
		String file = "kimbits.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(st.nextToken());
		int L = Integer.parseInt(st.nextToken());
		long I = Long.parseLong(st.nextToken());
		in.close();
		
		initSet();
		String ans = getBits(N,L,I-1);
		System.out.println(ans);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("kimbits.out")));
		out.println(ans);
		out.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}
	
	public static void initSet() {
		for(int j = 0; j <= 32; j++) {
			setSize[0][j] = 1;
		}
		
		for(int i = 1; i <= 32; i++) {
			for(int j = 0; j <= 32; j++) {
				if(j == 0) {
					setSize[i][j] = 1;
				}else {
					setSize[i][j] = setSize[i-1][j] + setSize[i-1][j-1];
				}
			}
		}
	}
	
	public static String getBits(int N, int L, long I) {
		// no such thing as a non-null 0-length bitstring
		if(N == 0) return "";
		
		long s = setSize[N-1][L];
		
		if(s <= I) {
			return "1" + getBits(N-1,L-1,I-s);
		} else {
			return "0" + getBits(N-1,L,I);
		}
	}
}
