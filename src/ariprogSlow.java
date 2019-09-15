/*
ID: carter.2
TASK: ariprog
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class ariprogSlow {

	// WORKS BUT SLOW, see ariprog.java for quickeset solution
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new FileReader("ariprog.in"));
		int NX = Integer.parseInt(in.readLine());
		int M = Integer.parseInt(in.readLine());
		in.close();
		
		long time = System.nanoTime();
		long first_time = time;

		Set<Integer> set = generateBisquareSet(M);
		
		// int[a,b,n]
		Queue<Integer[]> q = new LinkedList<Integer[]>();

		// b's and all a vals.
//		HashMap<Integer,Set<Integer>> usedABs = new HashMap<Integer,Set<Integer>>();
		
		System.out.println((System.nanoTime() - time) / 1000000 + "ms");
		time = System.nanoTime();
		
		// final int BIGBCAP = (int) (2*Math.pow(M, 2) / (NX-1));
		
		// 1,2,3,4 N=2 a can be < max 4-2+1, < 3. = 2 max
		for(int a: set) {
			// biggest number / N
			for(int b = (int) ((2*Math.pow(M, 2) - a) / (NX-1)); b > 0; b--) {
				if(b > 84 || NX >= 14) {
					if(b%84 > 0) {
						continue;
					}
				} else if(b > 4 || NX > 3) {
					if(b % 4 > 0) continue; 
				}
				
				boolean unique = true;
				
//				if (a > b) {
//					// if (a == 2602) System.out.println(b + ", " + (a - b));
//					// this takes forever. optimize.
//					if (usedABs.containsKey(a - b)) {
//						// if (a == 2602) System.out.println("a-b");
//						Set<Integer> bs = usedABs.get(a - b);
//						// a(y) % b(m) gives us the smallest "c" value
//						//if(a == 2602) System.out.println(bs);
//						unique = !bs.contains(b);
//						// if(a == 2602) System.out.println(unique);
//					}
//				}
				
//				if(usedABs.containsKey(a)) {
//					usedABs.get(a).add(b);
//				}else {
//					usedABs.put(a, new HashSet<Integer>(b));
//				}
				if(unique) {
					Integer[] r = {a,b,1};
					q.add(r);					
					// System.out.println(a+","+b+" unique");
				}
				// System.out.println("b");
			}
		}

		System.out.println("done first q");
		System.out.println((System.nanoTime() - time) / 1000000 + "ms");
		time = System.nanoTime();

		// arraylist of int[a,b]
		ArrayList<Integer[]> tsolutions = new ArrayList<Integer[]>();

		Integer[] c = q.poll(); // doesn't throw exception
		
		int nLast = 0;
		while(c != null) {
			// System.out.println("c");
			int a = c[0];
			int b = c[1];
//			int ap = c[2];
			int n = c[2];

			if(n > nLast) {
				// System.out.println(a+","+b+","+n);
				nLast = n;
				System.out.println((System.nanoTime() - time) / 1000000 + "ms");
				time = System.nanoTime();
			}

			int next = a + (b*(n));
			
			if(n == NX) {
				Integer[] arr = {a,b,n};
				tsolutions.add(arr);
			}else{
				boolean foundNext = (set.contains(next));
				
				if(foundNext) {
					Integer[] arr = { a, b, n + 1 };
					q.add(arr);
					foundNext = true;
				}
			}
			
//			else if(n >= NX) { // was N >= NX
//				Integer[] arr = {a,b,n};
//				tsolutions.add(arr);
//				// System.out.println(a+" "+b);
//			}
			
			// System.out.println((System.nanoTime() - time) + "ns");
			// time = System.nanoTime();
			c = q.poll();
		}
		
//		ArrayList<Integer[]> solutions = new ArrayList<Integer[]>();
//		for(Integer[] arr: tsolutions) {
//			for(int i = 0; i <= arr[2] - NX; i++) {
//				Integer[] tarr = {arr[0]+(arr[1]*i),arr[1],NX};
//				solutions.add(tarr);
//			}
//		}
		
		Collections.sort(tsolutions, (Integer[] a, Integer[] b) -> {
			if(a[1].compareTo(b[1]) == 0) {
				return a[0].compareTo(b[0]);
			}else{
				return a[1].compareTo(b[1]);
			}
		});
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms");
		time = System.nanoTime();

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ariprog.out")));

		if(tsolutions.size() == 0) out.println("NONE");
		for(Integer[] arr: tsolutions) {
			String s = arr[0] + " " + arr[1];
			System.out.println(s);
			out.println(s);
		}
		out.close();
	}

	//	public static int checkForProg(ArrayList<Integer> set, int a, int b, int n) {
	//		int c = a;
	//		int x = 0;
	//		do {
	//			c = a + b*x;
	//			
	//			if(false || b*x > 1000) {
	//				int ci = set.indexOf(c);
	//				if(ci == -1) return -1;
	//				// System.out.println(c);
	//				set = shrinkSet(set, c, ci, b, n);
	//			}else{
	//				if(!set.contains(c)) return -1;
	//			}
	//			x++;
	//		} while(x < n);
	//		return c;
	//	}

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
