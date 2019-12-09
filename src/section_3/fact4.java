package section_3;
/*
ID: carter.2
LANG: JAVA
TASK: fact4
*/
import java.io.*;

public class fact4 {
	// Solution to Section 3.2: "Problem 20_Factorials"
	public static void main(String[] args) throws IOException{
		long first_time = System.nanoTime();
		
		// input
		BufferedReader in;
		String file = "fact4.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		int N = Integer.parseInt(in.readLine());
		in.close();
		
		// this is the key to the problem
		// since n! = (n-1)! * n, you need only keep the last $keep non-zero digits
		// no other digits will affect the final result.
		int lastDigits = 1;
		int keep = String.valueOf(N).length();
		
		for(int i = 1; i <= N; i++) {
			String s = String.valueOf(lastDigits*i);
			for(int x = s.length()-1; x > 0; x--) {
				if(s.charAt(x) != '0') {
					s = s.substring(Math.max(0,x+1-keep),x+1);
					break;
				}
			}
			lastDigits = Integer.parseInt(s);
			// DEBUG System.out.println("--"+i+"--");
			// DEBUG System.out.println(lastDigits);	
		}		
		
		// output
		String str = String.valueOf(lastDigits);
		int ans = str.charAt(str.length()-1) - '0';
		System.out.println(ans);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fact4.out")));
		out.println(ans);
		out.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}
}
