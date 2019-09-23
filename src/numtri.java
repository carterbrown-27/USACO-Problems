/*
ID: carter.2
TASK: numtri
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class numtri {
	
	// Solution for "Problem 68: Number Triangles"
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		BufferedReader in;
		String file = "numtri.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}	

		int R = Integer.parseInt(in.readLine());

		int[][] triangle = new int[R][];

		StringTokenizer st;
		for(int i = 0; i < R; i++) {
			int[] line = new int[i+1];
			st = new StringTokenizer(in.readLine());
			int c = 0;
			while(st.hasMoreTokens()) {
				line[c] = Integer.parseInt(st.nextToken());
				c++;
			}
			triangle[i] = line;
			
		}
		in.close();

		// second last row --> first
		for(int y = R-1-1; y>=0; y--) {
			// row length = y
			for(int x = 0; x <= y; x++) {
				triangle[y][x] += Math.max(triangle[y+1][x], triangle[y+1][x+1]);
			}
		}
		
		int maxSum = triangle[0][0];
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("numtri.out")));
		out.println(maxSum);
		System.out.println(maxSum);
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
	}
}
