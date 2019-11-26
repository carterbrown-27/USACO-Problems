package section_2;
/*
ID: carter.2
TASK: frac1
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class frac1 {
	
	// Solution for the problem "Ordered Fractions"
	// note: not optimized but more than satisfies the reqs for the problem
	// (finishes the worst-case in <5% of the allocated time)
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		
		BufferedReader in;
		String file = "frac1.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		int N = Integer.parseInt(in.readLine());
		
		Set<Integer> primes = new HashSet<Integer>();
		for(int x = 2; x <= N/2; x++) {
			boolean isPrime = true;
			for(int p: primes) {
				if(x%p==0) {
					isPrime = false;
					break;
				}
			}
			if(isPrime) {
				primes.add(x);
			}
		}
		
		// <Integer[]> = <[n,d]>
		ArrayList<Integer[]> fractions = new ArrayList<Integer[]>();
		for(int d = 2; d <= N; d++) {
			for(int n = 1; n < d; n++) {
				// if reducable continue
				if(Collections.disjoint(getPrimeFactors(n,primes), getPrimeFactors(d,primes))) {
					fractions.add(new Integer[] {n,d});
				}
			}
		}
		
		fractions.add(new Integer[] {0,1});
		fractions.add(new Integer[] {1,1});
		
		// sort
		Collections.sort(fractions, (Integer[] a, Integer[] b) -> {
			Float aVal = (float) a[0] / a[1];
			Float bVal = (float) b[0] / b[1];
			
			return(aVal.compareTo(bVal));
		});
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("frac1.out")));
		for(Integer[] f: fractions) {
			// System.out.println(f[0]+"/"+f[1]);
			out.println(f[0]+"/"+f[1]);
		}
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
	}
	
	public static Set<Integer> getPrimeFactors(int n, Set<Integer> primes) {
		Set<Integer> set = new HashSet<Integer>();
		for(int p: primes) {
			if(n%p==0) set.add(p);
		}
		return set;
	}
}
