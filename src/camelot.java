/*
ID: carter.2
LANG: JAVA
TASK: camelot
 */
import java.io.*;
import java.util.*;
import java.awt.Point;
public class camelot {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		long first_time = System.nanoTime();
		BufferedReader in;
		String file = "camelot.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		final int W = Integer.parseInt(st.nextToken());
		final int H = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(in.readLine());
		
		Point king = new Point(st.nextToken().toCharArray()[0] - 'A', Integer.parseInt(st.nextToken()) - 1);
		// System.out.println("king = " + king);
				
		// read in knights.
		ArrayList<Point> knights = new ArrayList<>();
		String line = in.readLine();
		while(line != null) {
			st = new StringTokenizer(line);
			while(st.hasMoreTokens()) {
				knights.add(new Point(st.nextToken().toCharArray()[0] - 'A', Integer.parseInt(st.nextToken()) - 1));
			}
			line = in.readLine();
		}
		
		// knights.forEach(i -> System.out.println(i));
		
		boolean[][] dummyMap = new boolean[W][H];
		
		LinkedHashMap<Point,ArrayList<Point>> edgeMap = new LinkedHashMap<>();
		
		for(int x = 0; x < W; x++) {
			for(int y = 0; y < H; y++) {
				Point a = new Point(x,y);
				Point[] nbs = new Point[8];
				nbs[0] = new Point(x-1,y-2);
				nbs[1] = new Point(x+1,y-2);
				nbs[2] = new Point(x-1,y+2);
				nbs[3] = new Point(x+1,y+2);
				nbs[4] = new Point(x-2,y-1);
				nbs[5] = new Point(x-2,y+1);
				nbs[6] = new Point(x+2,y-1);
				nbs[7] = new Point(x+2,y+1);
				
				ArrayList<Point> lst = new ArrayList<>();
				boolean dummy;
				for(Point b: nbs) {
					// screens OOB points.
					try {
						dummy = dummyMap[b.x][b.y];
					} catch(ArrayIndexOutOfBoundsException e) {
						continue;
					}
					
					lst.add(b);
				}
				edgeMap.put(a,lst);
			}
		}
		
		// roughly 5ms for Worst Case.
		// System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [INIT]");
		
		// DEBUG:
//		edgeMap.forEach((k,v) -> {
//			System.out.print(k+" || ");
//			v.forEach(i -> System.out.print(i+","));
//			System.out.println();
//		});
		
		ArrayList<Point> nodes = new ArrayList<>(edgeMap.keySet());
		int[][] edges = new int[nodes.size()][nodes.size()];
		final int LARGE = 10000000;
		
		for(int i = 0; i < edges.length; i++) {
			for(int j = 0; j <= i; j++) {
				// if i's edge map contains j
				if(i==j) {
					edges[i][j] = 0;
				}else if(edgeMap.get(nodes.get(i)).contains(nodes.get(j))) {
					edges[i][j] = 1;
					edges[j][i] = 1;
				} else {
					edges[i][j] = LARGE;
					edges[j][i] = LARGE;
				}
			}
		}
		
		// System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [FW_INIT]");
		
		// FW for the board.
		for(int k = 0; k < edges.length; k++) {
			for(int i = 0; i < edges.length; i++) {
				for(int j = 0; j < edges.length; j++) {
					edges[i][j] = Math.min(edges[i][j], edges[i][k] + edges[k][j]);
				}
			}
		}
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [FW_DONE]");
		
		int[][] kingMap = new int[W][H];
		for(int x = 0; x < W; x++) {
			for(int y = 0; y < H; y++) {
				kingMap[x][y] = Math.max(Math.abs(king.x-x), Math.abs(king.y-y));
			}
		}
		
		// TODO: mounting
		// mount at point where (edge[knight][point] + 0/1 (king distance) + edge[point][final]) is minimized
		// must be less than kingmap[point]
		// this becomes mountingDistance and it replaces kingDist and the mountingKnight dist.
		// *** don't need to check every point, simply those in those adjacent to the king (or the initial space).
		// this is because moving a knight is quicker/equal to the king's moves over any distance greater than 1
		// (knightdist: 1 away = 2 (URDL) or 3 (Diagonal) ... > 1 (king move))
		
		// final calculation
		int minSum = LARGE;
		Point minLoc = new Point(-1,-1);
		
		// convene at (x,y).
		for(int x = 0; x < W; x++) {
			for(int y = 0; y < H; y++) {
				int src = x*H + y;
				
				// mounting here.
				int minDist = LARGE;
				Point mountKnight = new Point(-1,-1);
				for(int kdx = -1; kdx <= 1 &&  king.x + kdx < W; kdx++) {
					if(king.x+kdx < 0) continue;
					for(int kdy = -1; kdy <= 1 && king.y + kdy < H; kdy++) {
						if(king.y+kdy < 0) continue;
						
						int pIndex = (king.x+kdx)*H + (king.y+kdy);
						
						for(Point knight: knights) {
							int knIndex = knight.x*H + knight.y;
							int dist = edges[knIndex][pIndex] + ((kdx == 0 && kdy == 0) ? 0 : 1) + edges[pIndex][src];
							if(dist < minDist && dist < edges[knIndex][src] + kingMap[x][y]) {
								minDist = dist;
								mountKnight = knight;
							}
							// knight already in position.
							if(edges[knIndex][pIndex] == 0) break;
						}
					}
				}
				
				int sum = 0;
				for(Point knight: knights) {
					if(knight == mountKnight) continue;
					int i = knight.x*H + knight.y;
					sum += edges[src][i];
					if(sum > minSum) break;
				}
				
				if(mountKnight.x != -1) {
					sum += minDist;
				}else {
					sum += kingMap[x][y];
				}
				
				if(sum < minSum) {
					minSum = sum;
					minLoc = new Point(x,y);
				}
				
				// System.out.println((char) ('A'+x)+","+(y+1)+": "+sum + "\t || "+mountKnight);
			}
		}
		
		
		System.out.println(minSum + " @ " + (char) ('A' + minLoc.x) + "" + (minLoc.y+1));
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("camelot.out")));
		out.println(minSum);
		out.close();
	}
}
