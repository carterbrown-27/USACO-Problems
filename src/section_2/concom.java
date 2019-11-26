package section_2;
/*
ID: carter.2
TASK: concom
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class concom {

	public static int[][] stakes = new int[100][100];
	public static Set<String> answers = new HashSet<String>();
	
	// Solution for Section 2.3 "Controlling Companies"
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		
		BufferedReader in;
		String file = "concom.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		
		
		int N = Integer.parseInt(in.readLine());
		
		StringTokenizer st;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(in.readLine());
			
			int I = Integer.parseInt(st.nextToken());
			int J = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			
			stakes[I-1][J-1] += K;
		}
		
		in.close();
		
		int ansLen = 0;
		do {
			ansLen = answers.size();
			runThrough();
		}while(answers.size() > ansLen);
		
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("concom.out")));
		
		ArrayList<String> ansList = new ArrayList<String>();
		
		
		// run through one last time, slight alterations.
		// record answers in increasing order.
		for(int a = 0; a < 100; a++) {
			for(int b = 0; b < 100; b++) {
				if(a == b) continue;
				if(stakes[a][b] > 50) {
					ansList.add((a+1)+" "+(b+1));
				}else {
					for(int c = 0; c < 100; c++) {
						if(a == c) continue;
						if(stakes[a][c] > 50 && stakes[c][b] > 50) {
							ansList.add((a+1)+" "+(b+1));
							break;
						}
					}
				}
			}
		}
		
		for(String s: ansList) {
			// System.out.println(s);
			out.println(s);
		}
		
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
	}
	

	public static void runThrough (){
		// for each company
		for(int A = 0; A < 100; A++) {
			// for each other company
			for(int B = 0; B < 100; B++) {
				if(A == B) continue;
				
				// owns directly
				String ans = (A+1)+" "+(B+1);
				if(stakes[A][B] > 50 && !answers.contains(ans)) {
					answers.add(ans);
					
					for(int C = 0; C < 100; C++) {
						stakes[A][C] += stakes[B][C];
					}
				}
			}
		}
	}
}
