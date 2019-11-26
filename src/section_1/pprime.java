package section_1;
/*
ID: carter.2
TASK: pprime
LANG: JAVA
 */
import java.util.*;
import java.io.*;
public class pprime {
	
	// Solution for the problem "Prime Palindromes"
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		
		BufferedReader in;
		String file = "pprime.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}	
		StringTokenizer st = new StringTokenizer(in.readLine());

		int a = Integer.parseInt(st.nextToken());
		int b = Integer.parseInt(st.nextToken());
		in.close();

		// generates all primes up to sqrt(B);
		ArrayList<Integer> primes = new ArrayList<Integer>();
		ArrayList<Integer> halfPrimes = new ArrayList<Integer>();
		for(int x = 3; x <= Math.sqrt(b); x++) {
			if(x%2==0) continue;
			boolean flag = false;
			for(int p: halfPrimes) {
				if(x%p == 0) {
					flag = true;
					break;
				}
				if(p*p > x) {
					break;
				}
			}
			if(!flag) {
				primes.add(x);
				if(x < Math.sqrt(b) / 2) halfPrimes.add(x);
			}
		}	
		primes.add(2);
		Collections.sort(primes);

		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [A]");

		ArrayList<Integer> palindromes = new ArrayList<Integer>();

		LinkedList<String> q = new LinkedList<String>();
		for(char i = '0'; i <= '9'; i++) {
			String si = String.valueOf(i);
			q.add(si);
			q.add(si.concat(si));
		}
		
		String s = q.poll();
		do {
			if(Integer.parseInt(s) <= b) {
				if((s.charAt(0) - '0') % 2 != 0) { // prime palindromes can't have even starts/finishes
					palindromes.add(Integer.parseInt(s));
				}
				for(char i = '0'; i <= '9'; i++) {
					String str = String.valueOf(i);
					/*	this statement here instead of at top is 2x faster
						avoids q.add and q.poll for invalid #s 			*/
					if(s.length()<=7) q.add(str.concat(s).concat(str));
				}
			}
			s = q.poll();
		}while(s != null);
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [B]");

		ArrayList<Integer> pprimes = new ArrayList<Integer>();
		for(int p: palindromes) {
			if(p >= a) {
				if(isPrime(p,primes)) pprimes.add(p);
			}
		}

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("pprime.out")));
		Collections.sort(pprimes);
		for(int x: pprimes) {
			out.println(x);
		}
		out.close();

		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
		return;
	}

	public static boolean isPrime(int x, ArrayList<Integer> set) {
		for(int p: set) {
			if(p==x || p*p > x) return true;
			if(x%p==0) return false;
		}
		return true;
	}

//	public static boolean isPalindrome(int x) {
//		String s = String.valueOf(x);
//		// 6 check 3, 7 check 3 (middle always = middle)
//		int sLen = s.length();
//
//		if(s.charAt(0) % 2 == 0 || s.charAt(sLen-1) % 2 == 0) return false;
//
//		for(int i = 0; i < sLen/2; i++) {
//			if(s.charAt(i) != s.charAt(sLen-1-i)) return false;
//		}
//		return true;
//	}
}
