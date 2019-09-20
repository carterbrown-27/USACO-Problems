/*
ID: carter.2
TASK: ariprog
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class ariprog {
	
	// Solution for the Problem "Arithmetic Progressions"
	public static void main(String[] args) throws IOException {
		
		BufferedReader in;
		String file = "ariprog.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}	
		
		int NX = Integer.parseInt(in.readLine());
		int M = Integer.parseInt(in.readLine());
		in.close();
		
		long time = System.nanoTime();
		long first_time = time;

		Set<Integer> set = generateBisquareSet(M);
		
		// arraylist of Integer[a,b,n]
		ArrayList<Integer[]> tsolutions = new ArrayList<Integer[]>();
		
		final int BIGBCAP = (int) (2*Math.pow(M, 2) / (NX-1));
		for(int b = BIGBCAP; b > 0; b--) {
			if(b > 84 || NX >= 14) {
				if(b%84 > 0) {
					continue;
				}
			} else if(b > 4 || NX > 3) {
				if(b % 4 > 0) continue; 
			}
			
			Set<Integer> prevs = new HashSet<Integer>();
			for(int a: set) {
				// no duplicates
				if(prevs.contains(a)) continue;
				int n = 0;
				int next = a;
				do {
					prevs.add(next);
					next+=b;
					n++;
				} while(set.contains(next));
				
				if(n >= NX) {
					Integer[] arr = {a,b,n};
					tsolutions.add(arr);
				}
			}
		}

		ArrayList<Integer[]> solutions = new ArrayList<Integer[]>();
		for(Integer[] arr: tsolutions) {
			for(int i = 0; i <= arr[2] - NX; i++) {
				Integer[] tarr = {arr[0]+(arr[1]*i),arr[1]};
				solutions.add(tarr);
			}
		}
		
		Collections.sort(solutions, (Integer[] a, Integer[] b) -> {
			if(a[1].compareTo(b[1]) == 0) {
				return a[0].compareTo(b[0]);
			}else{
				return a[1].compareTo(b[1]);
			}
		});
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms");
		time = System.nanoTime();

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ariprog.out")));

		if(solutions.size() == 0) out.println("NONE");
		for(Integer[] arr: solutions) {
			String s = arr[0] + " " + arr[1];
			// System.out.println(s);
			out.println(s);
		}
		out.close();
	}

	public static Set<Integer> generateBisquareSet(int M){
		Set<Integer> set = new HashSet<Integer>();
		for(int p = 0; p <= M; p++) {
			for(int q = 0; q <= p; q++) {
				int x = (int) (Math.pow(p,2) + Math.pow(q,2));
				if(!set.contains(x)) set.add(x);
			}
		}
		return set;
	}

}
