package section_3;
/*
ID: carter.2
LANG: JAVA
TASK: range
 */
import java.io.*;
import java.util.*;
public class range {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		long first_time = System.nanoTime();

		BufferedReader in;
		String file = "range.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}

		// 2d array
		// for each spot (= top left corner)
		// find squares.
		int N = Integer.parseInt(in.readLine());
		int[][] field = new int[N][N];

		for(int y = 0; y < N; y++) {
			String[] str = in.readLine().split("");
			for(int x = 0; x < N; x++) {
				field[x][y] = Integer.parseInt(str[x]);
			}
		}

		// 0 ---> N
		int[] tally = new int[N+1];
		
		search:
		for(int y = 0; y < N; y++) {
			for(int x = 0; x < N; x++) {
				squareCheck: 
				for(int s = 0; x+s < N && y+s < N; s++) {
					// check next lines of square
					for(int dx = 0; dx <= s; dx++) {
						if(field[x+dx][y+s] == 0) break squareCheck;
					}

					for(int dy = 0; dy <= s; dy++) {
						if(field[x+s][y+dy] == 0) break squareCheck;
					}

					// valid
					tally[s]++;
					
					// if this = size of field (worst-case otherwise).
					if(s==N-1) {
						for(int i = s-1; i >= 1; i--) {
							tally[i] = ((s-i)+1) * ((s-i)+1); // (s-i+1)^2.
						}
						break search;
					}
				}
			}
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("range.out")));
		for(int i = 1; i < tally.length; i++) {
			if(tally[i] > 0) {
				// System.out.printf("%d %d\n", i+1, tally[i]);
				out.printf("%d %d\n", i+1, tally[i]);
			}
		}
		out.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}
}
