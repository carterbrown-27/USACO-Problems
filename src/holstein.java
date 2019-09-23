/*
ID: carter.2
TASK: holstein
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class holstein {
	
	// Solution for the problem "Healthy Holsteins"
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		
		BufferedReader in;
		String file = "holstein.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}

		int V = Integer.parseInt(in.readLine());
		Integer[] vReqs = new Integer[V];
		StringTokenizer st = new StringTokenizer(in.readLine());
	
		for(int i = 0; i < V; i++) {
			vReqs[i] = Integer.parseInt(st.nextToken());
		}

		int G = Integer.parseInt(in.readLine());
		Integer[][] feeds = new Integer[G][V];

		for(int i = 0; i < G; i++) {
			st = new StringTokenizer(in.readLine());
			for(int t = 0; t < V; t++) {
				feeds[i][t] = Integer.parseInt(st.nextToken());
			}
		}

		in.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [A]");
		Set<Integer> mtSet = new HashSet<Integer>();
		recur(vReqs,feeds,mtSet,-1);
		
		// System.out.println(minScoops + " " + minIDSum);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("holstein.out")));
		out.print(minScoops);
		for(int i: minUsed) {
			out.print(" " + (i+1));
		}
		out.println();
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
	}

	public static int minScoops = Integer.MAX_VALUE;
	public static int minIDSum = Integer.MAX_VALUE;
	public static Set<Integer> minUsed;
	
	public static void recur(Integer[] vReqs, Integer[][] feeds, Set<Integer> used, int last) {
		int scoops = used.size();
		if(scoops > minScoops) {
			return;
		}
		
		// if all used
		if(scoops == feeds.length) {
			minScoops = scoops;
			minUsed = used;
			return;
		}

		// successful base case
		if(isGreaterThanReqs(vReqs, feeds, used)) {
			int IDSum = sumOfSet(used);
			if(scoops < minScoops) {
				minIDSum = IDSum;
				minScoops = scoops;
				minUsed = used;
			// scoops == minScoops must be true at this point
			}else if(IDSum < minIDSum) {
				minIDSum = IDSum;
				minUsed = used;
			}
			return;
		}
		
		// recursive calls:
		for(int i = last+1; i < feeds.length; i++) {
			if(used.contains(i)) continue;
			Set<Integer> newUsed = new HashSet<Integer>(used);
			newUsed.add(i);
			recur(vReqs,feeds,newUsed, i);
		}
	}

	public static boolean isGreaterThanReqs(Integer[] vReqs, Integer[][] arr, Set<Integer> indices) {
		int[] sumsOfColumns = new int[vReqs.length];
		for(int i: indices) {
			for(int x = 0; x < arr[i].length; x++) {
				sumsOfColumns[x] += arr[i][x];
			}
		}

		for(int i = 0; i < sumsOfColumns.length; i++) {
			if(sumsOfColumns[i] < vReqs[i]) return false;
		}

		return true;
	}

	public static int sumOfArray(Integer[] arr) {
		int sum = 0;
		for(int x: arr) {
			sum += x;
		}
		return sum;
	}

	public static int sumOfSet(Set<Integer> set) {
		int sum = 0;
		for(int x: set) {
			sum += x;
		}
		return sum;
	}
}
