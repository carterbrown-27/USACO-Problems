/*
ID: carter.2
TASK: kimbits
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class kimbits {
	
	// Solution for Section 3.2: "Stringsobits" for cases 1-7
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
		int I = Integer.parseInt(st.nextToken());
		in.close();
		
		int min = (int) Math.pow(2,L-1);
		System.out.println("min= "+min);
		String ans = "";
		if(I <= min) {
			ans = Integer.toBinaryString(I-1);
		}else {
			// min = even to start.
			for(int d = min, i = min+1; i <= I; d++) {
				String str = Integer.toBinaryString(d);
				int count = 0;
				for(char c: str.toCharArray()) {
					if(c == '1') {
						count++;
						// if(count>L) break... slow.
					}
				}
				
				if(count<=L) {
					ans = str;
					i++;
					// if this number has <= L 1s, and is odd, then the next will also be <=.
					if(d%2==1 && i<I-1) {
						d++;
						i++;
					}
				}
			}
		}
		
		String format = "%" + N + "s";
		ans = String.format(format, ans).replaceAll(" ", "0");
		System.out.println(ans);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("kimbits.out")));
		out.println(ans);
		out.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}
}
