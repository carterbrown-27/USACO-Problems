package section_3;
/*
ID: carter.2
LANG: JAVA
TASK: msquare
 */
import java.io.*;
import java.util.*;
public class msquare {
	// Solution to Section 3.2: "Magic Squares"
	public static void main(String[] args) throws IOException{
		long first_time = System.nanoTime();

		BufferedReader in;
		String file = "msquare.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}

		int[][] targetSq = new int[2][4];

		StringTokenizer st = new StringTokenizer(in.readLine());
		for(int i = 0; i < 4; i++) {
			targetSq[0][i] = Integer.parseInt(st.nextToken());
		}

		for(int j = 3; j >= 0; j--) {
			targetSq[1][j] = Integer.parseInt(st.nextToken());
		}

		final Config BASE_C = new Config();
		final Config TARGET_C = new Config(targetSq);

		Queue<Config> q = new LinkedList<Config>();
		Set<String> seen = new HashSet<String>();
		q.add(BASE_C);

		// int lastLength = 0;
		Config c;
		// 3^n time, n = level of soln.
		do {
			c = q.poll();
			
			String str = c.moveString;
			String sqStr = c.squareToString();
			if(seen.contains(sqStr)) continue;
			seen.add(sqStr);
			
			int len = str.length();
			
			if(sqStr.equals(TARGET_C.squareToString())) {
				break;
			}
			
			// these if statements very marginally affect the runtime (+/-), as duplicates are already handled above.
			if(str.lastIndexOf('A') <= str.lastIndexOf('C')) {
				q.add(new Config(c,'A'));
			}
			
			if(!(str.substring(Math.max(0, len-3),len).equals("BBB"))) {
				q.add(new Config(c,'B'));
			}
			
			if(!(str.substring(Math.max(0, len-3),len).equals("CCC"))) {
				q.add(new Config(c,'C'));
			}
			
//			if(len > lastLength) {
//				lastLength++;
//				System.out.println(lastLength);
//			}
		}while(!q.isEmpty());

		System.out.println(c.moveString.length());
		System.out.println(c.moveString);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("msquare.out")));
		out.println(c.moveString.length());
		out.println(c.moveString);
		out.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}

	public static int[][] copyInt2D(int[][] arr){
		int[][] result = new int[arr.length][arr[0].length];
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++) {
				result[i][j] = arr[i][j];
			}
		}
		return result;
	}

	public static class Config {
		int[][] square = new int[2][4];
		String moveString = "";

		Config() {
			this.square = new int[][] {{1,2,3,4},{8,7,6,5}};
		};

		Config(int[][] _square) {
			this.square = copyInt2D(_square);
		};

		Config(Config c, char move){
			this.square = copyInt2D(c.square);
			this.moveString = c.moveString;
			moveString += move;
			switch(move) {
			case 'A':
				this.A();
				break;
			case 'B':
				this.B();
				break;
			case 'C':
				this.C();
				break;
			}
		}
		
		private String squareToString() {
			StringBuilder s = new StringBuilder();
			for(int x = 0; x < 2; x++) {
				for(int i = 0; i < 4; i++) s.append(square[x][i]);				
			}
			return s.toString();
		}

		// switch top/bottom rows
		private void A() {
			int[] temp = square[0];
			square[0] = square[1];
			square[1] = temp;
		}

		// shift right, wrap around
		private void B() {
			int[][] newSquare = copyInt2D(square);
			for(int i = 0; i < 4; i++) {
				int j = i-1;
				if(j==-1) j = 3;
				newSquare[0][i] = square[0][j];
				newSquare[1][i] = square[1][j];
			}
			this.square = newSquare;
		}

		// clockwise rotation of middle 4 tiles
		private void C() {
			int[][] newSquare = copyInt2D(square);
			newSquare[0][2] = square[0][1];
			newSquare[1][2] = square[0][2];
			newSquare[1][1] = square[1][2];
			newSquare[0][1] = square[1][1];
			this.square = newSquare;
		}
	}
}
