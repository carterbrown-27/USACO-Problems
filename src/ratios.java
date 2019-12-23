/*
ID: carter.2
LANG: JAVA
TASK: ratios
 */
import java.io.*;
import java.util.*;
public class ratios {

	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();

		BufferedReader in;
		String file = "ratios.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}

		int[] goal = new int[3];
		int[][] feeds = new int[3][3];

		StringTokenizer st = new StringTokenizer(in.readLine());
		for(int i = 0; i < 3; i++) goal[i] = Integer.parseInt(st.nextToken());

		for(int i = 0; i < 3; i++) {
			st = new StringTokenizer(in.readLine());
			for(int j = 0; j < 3; j++) {
				feeds[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int[] best = new int[3];
		int bestQ = Integer.MAX_VALUE;
		int mixQ = 0;
		
		boolean[] goalZ = new boolean[] {
				goal[0] == 0,
				goal[1] == 0,
				goal[2] == 0
		};

		// BRUTE FORCE
		// Very Ugly: Java doesn't really do array division or div by zero handling easily.
		// Probably a better way to do this
		// i = amt of 1, j - 2, k - 3
		for(int i = 0; i < 100; i++) {
			for(int j = 0; j < 100; j++) {
				for(int k = 0; k < 100; k++) {
					int[] sum = new int[] {
							feeds[0][0]*i + feeds[1][0]*j + feeds[2][0]*k,
							feeds[0][1]*i + feeds[1][1]*j + feeds[2][1]*k,
							feeds[0][2]*i + feeds[1][2]*j + feeds[2][2]*k
					};

					if(sum[0] < goal[0] || sum[1] < goal[1] || sum[2] < goal[2]) continue; 
					
					if((goal[0] == 0 && sum[0] != 0) || (goal[1] == 0 && sum[1] != 0) || (goal[2] == 0 && sum[2] != 0)) continue;
					
					if((!goalZ[0] && sum[0] % goal[0] != 0)
					|| (!goalZ[1] && sum[1] % goal[1] != 0)
					|| (!goalZ[2] && sum[2] % goal[2] != 0)) continue;

					if((goalZ[0] || goalZ[1] || sum[0]/goal[0] == sum[1]/goal[1])
					&& (goalZ[0] || goalZ[2] || sum[0]/goal[0] == sum[2]/goal[2])
					&& (goalZ[2] || goalZ[1] || sum[2]/goal[2] == sum[1]/goal[1])
						){
						if(i+j+k < bestQ) {
							bestQ = i+j+k;
							best = new int[] {i,j,k};
							mixQ = sum[0]/goal[0];
						}
					}
				}
			}
		}

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ratios.out")));
		if(mixQ == 0) {
			System.out.println("NONE");
			out.println("NONE");
		}else {
			for(int i: best) {
				System.out.print(i+" ");
				out.print(i+" ");
			}
			System.out.println(mixQ);
			out.println(mixQ);
		}
		
		out.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}
}
