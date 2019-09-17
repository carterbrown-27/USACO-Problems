/*
ID: carter.2
TASK: sprime
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class sprime {

	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		
		BufferedReader in = new BufferedReader(new FileReader("sprime.in"));
		int N = Integer.parseInt(in.readLine());
		in.close();
		
		ArrayList<Integer> primes = new ArrayList<Integer>();
		ArrayList<Integer> halfPrimes = new ArrayList<Integer>();
		int b = (int) Math.pow(10, N);
		int sqrtB = (int) Math.sqrt(b);
		for(int x = 3; x <= sqrtB; x++) {
			if(x%2==0) continue;
			boolean flag = false;
			for(int p: halfPrimes) {
				//System.out.println(p);
				if(x%p == 0) {
					flag = true;
					break;
				}
				if(p*p > x) {
					break;
				}
			}
			if(!flag) {
				//System.out.println(x);
				primes.add(x);
				if(x < sqrtB / 2) halfPrimes.add(x);
			}
		}	
		primes.add(2);
		Collections.sort(primes);
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [A]");
		
		ArrayList<Integer> solutions = new ArrayList<Integer>();
		Set<Integer> superPrimes = new HashSet<Integer>();
		// Map<Integer,Boolean> checked = new HashMap<Integer,Boolean>();
		
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(2);
		q.add(3);
		q.add(5);
		q.add(7);
		
		int[] primeDigits = {1,3,7,9};
		
		// add dynamic
		int c;
		do {
			c = q.poll();
			superPrimes.add(c);
			for(int i: primeDigits) {
				int x = c*10 + i;
				if(isPrime(x,primes)) {
					q.add(x);
				}
			}
		}while(q.peek() != null && q.peek() < b);
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [B]");
		
		for(int s: superPrimes) {
			if(String.valueOf(s).length() == N) solutions.add(s);
		}
		
		Collections.sort(solutions);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sprime.out")));
		for(int sol: solutions) {
			// System.out.println(sol);
			out.println(sol);
		}
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
	}

	public static boolean isPrime(int x, ArrayList<Integer> set) {
		if(x == 1) return false;
		for(int p: set) {
			if(p==x) return true;
			if(x%p==0) return false;
		}
		return true;
	}
}
