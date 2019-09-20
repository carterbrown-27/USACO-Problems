/*
ID: carter.2
LANG: JAVA
TASK: skidesign
 */
import java.util.*;
import java.io.*;

public class skidesign {
	
	// Solution for the problem "Ski Course Design"
	public static void main(String[] args) throws IOException {
		BufferedReader in;
		String file = "skidesign.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}	
		int N = Integer.parseInt(in.readLine());
		
		int[] hills = new int[N];
		for(int i = 0; i < N; i++) {
			hills[i] = Integer.parseInt(in.readLine());
		}
		in.close();
		
		Arrays.sort(hills);
		
		int min = hills[0];
		int max = hills[hills.length-1];
		
		int range = max-min;
		
		// if range is already OK, cost to fix = $0
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("skidesign.out")));
		if(range <= 17) {
			out.println(0);
			out.close();
			return;
		}
		
		int minCost = Integer.MAX_VALUE;
		for(int lo = min+1; lo <= max-17; lo++) {
			int hi = lo+17;
			int cost = getCost(hills,lo,hi);
			if(cost<minCost) minCost = cost;
		}
		
		out.println(minCost);
		out.close();
		System.out.println(minCost);
	}
	
	public static int getCost(int[] hills, int lo, int hi) {
		int cost = 0;
		for(int h: hills) {
			if(h < lo) {
				cost += (int) Math.pow(lo-h, 2);
			}else if(h > hi) {
				cost += (int) Math.pow(h-hi, 2);
			}
		}
		return cost;
	}
}
