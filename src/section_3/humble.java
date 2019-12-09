package section_3;
/*
ID: carter.2
LANG: JAVA
TASK: humble
*/
import java.io.*;
import java.util.*;
public class humble {

	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		BufferedReader in;
		String file = "humble.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		int K = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());
		
		int[] primes = new int[K];
		st = new StringTokenizer(in.readLine());
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < K; i++) {
			int prime = Integer.parseInt(st.nextToken());
			min = Math.min(prime, min);
			primes[i] = prime;
		}
		
		int[] humbles = new int[N+1];
		humbles[0] = 1;
		
		int c = 1;
		int lastHumble = 1;
		
		// since the humble #s are increasing, the next humble number can't be before the last index for the same prime
		int[] lastH = new int[K];
		
		// generate the first N humble numbers
		while(c<=N) {
			// find the smallest hum#, p*humblest[h], that is bigger than the last.
			int nextHumble = Integer.MAX_VALUE;
			for(int a = 0; a < primes.length; a++) {
				int p = primes[a];
				for(int h = lastH[a]; h < c; h++) {
					if(p*humbles[h] > lastHumble) {
						nextHumble = Math.min(p*humbles[h],nextHumble);
						lastH[a] = h;
						break;
					}
				}
			}
			humbles[c] = nextHumble;
			lastHumble = nextHumble;
			c++;
		}
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [A]");
		
		// for(int i: humbles) System.out.println(i);
		
		int ans = humbles[N];
		
		System.out.println(ans);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("humble.out")));
		out.println(ans);
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}

}
