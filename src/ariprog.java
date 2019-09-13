/*
ID: carter.2
TASK: ariprog
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class ariprog {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new FileReader("ariprog.in"));
		int NX = Integer.parseInt(in.readLine());
		int M = Integer.parseInt(in.readLine());
		in.close();
		
		long time = System.nanoTime();

		ArrayList<Integer> set = generateBisquareSet(M);
		Collections.sort(set);
		// System.out.println(set);

		// solutions, k = a, v = b. -- might just go with an arrayl of int[a,b]
		ArrayList<Integer[]> solutions = new ArrayList<Integer[]>();

		// int[a,b,ap,n]
		Queue<Integer[]> q = new LinkedList<Integer[]>();

		// b's and all a vals.
		HashMap<Integer,ArrayList<Integer>> usedABs = new HashMap<Integer,ArrayList<Integer>>();
		
		System.out.println((System.nanoTime() - time) / 1000000 + "ms");
		time = System.nanoTime();
		
		
		// 1,2,3,4 N=2 a can be < max 4-2+1, < 3. = 2 max
		for(int ap = 0; ap < set.size() - NX + 1 ; ap++) {
			int a = set.get(ap);
			if(ap % 1000 == 0) System.out.println(ap);
			// biggest number / N
			for(int b = (int) (2*Math.pow(M, 2) - a) / (NX-1); b > 0; b--) {
				if(b > 84 || NX >= 14) {
					if(b%84 > 0) continue;
				} else if(b > 4 || NX > 3) {
					if(b % 4 > 0) continue; 
				}
				
				boolean unique = true;
				ArrayList<Integer> as = new ArrayList<Integer>();
				
				// this takes forever. optimize.
				if(b <= a && usedABs.containsKey(b)) {
					as = usedABs.get(b);
					// a(y) % b(m) gives us the smallest "c" value
					unique = !as.contains(Math.abs(a - b));
				}
				as.add(a);
				usedABs.put(b,as);
				if(unique) {
					// System.out.println(a+","+b+" unique");
					Integer[] r = {a,b,ap,1};
					q.add(r);					
				}
				// System.out.println("b");
			}
		}

		System.out.println("done first q");

		Integer[] c = q.poll(); // doesn't throw exception
		System.out.println((System.nanoTime() - time) / 1000000 + "ms");
		time = System.nanoTime();
		
		int nLast = 0;
		while(c != null) {
			// System.out.println("c");
			int a = c[0];
			int b = c[1];
			int ap = c[2];
			int n = c[3];

			if(n > nLast) {
				// System.out.println(a+","+b+","+n);
				nLast = n;
				System.out.println((System.nanoTime() - time) / 1000000 + "ms");
				time = System.nanoTime();
			}
			if(n == NX) {
				Integer[] arr = {a,b};
				solutions.add(arr);
				// System.out.println(a+" "+b);
				c = q.poll();
				continue;
			}

			int next = a + (b*(n));
			
			if (next <= set.get(set.size()-1)) {
				// FIX THIS
				for (int i = ap; i < set.size(); i++) {
					if (set.get(i) == next) {
						Integer[] arr = { a, b, i, n + 1 };
						q.add(arr);
						break;
					} else if (set.get(i) > next) {
						break;
					}
				} 
			}
			// System.out.println((System.nanoTime() - time) + "ns");
			// time = System.nanoTime();
			c = q.poll();
		}
		
		Collections.sort(solutions, (Integer[] a, Integer[] b) -> {
			if(a[1].compareTo(b[1]) == 0) {
				return a[0].compareTo(b[0]);
			}else{
				return a[1].compareTo(b[1]);
			}
		});

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ariprog.out")));

		if(solutions.size() == 0) out.println("NONE");
		for(Integer[] arr: solutions) {
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

	public static ArrayList<Integer> generateBisquareSet(int M){
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int p = 0; p <= M; p++) {
			for(int q = 0; q <= p; q++) {
				int x = (int) (Math.pow(p,2) + Math.pow(q,2));
				if(!list.contains(x)) list.add(x);
			}
		}
		return list;
	}

}
