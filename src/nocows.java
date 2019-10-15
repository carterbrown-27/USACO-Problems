/*
ID: carter.2
TASK: nocows
LANG: JAVA
 */
import java.io.*;
import java.util.*;
import java.util.Map.*;
public class nocows {

	public static void main(String[] args) throws IOException {
		BufferedReader in;
		String file = "nocows.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("nocows.out")));

		int ans = 0;
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
}
