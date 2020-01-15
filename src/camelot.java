/*
ID: carter.2
LANG: JAVA
TASK: camelot
 */
import java.io.*;
import java.util.*;
import java.awt.Point;
public class camelot {
	// Solution for Section 3.3: "Camelot"... Cases 1-10 out of 20.
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		BufferedReader in;
		String file = "camelot.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		final int H = Integer.parseInt(st.nextToken());
		final int W = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(in.readLine());
		
		Point king = new Point(st.nextToken().charAt(0) - 'A', Integer.parseInt(st.nextToken()) - 1);
		// System.out.println("king = " + king);
				
		// read in knights.
		ArrayList<Point> knights = new ArrayList<>();
		String line = in.readLine();
		while(line != null) {
			st = new StringTokenizer(line);
			while(st.hasMoreTokens()) {
				knights.add(new Point(st.nextToken().charAt(0) - 'A', Integer.parseInt(st.nextToken()) - 1));
			}
			line = in.readLine();
		}
		
		in.close();
		
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
		
		// roughly 35ms for Worst Case.
		// System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [INIT]");
		
		// DEBUG:
//		edgeMap.forEach((k,v) -> {
//			System.out.print(k+" || ");
//			v.forEach(i -> System.out.print(i+","));
//			System.out.println();
//		});
		
		ArrayList<Point> nodes = new ArrayList<>(edgeMap.keySet());
		int[][] edges = new int[nodes.size()][nodes.size()];
		final int LARGE = 100000;
		
		for(int i = 0; i < edges.length; i++) {
			for(int j = 0; j < i; j++) {
				// if i's edge map contains j
				if(edgeMap.get(nodes.get(i)).contains(nodes.get(j))) {
					edges[i][j] = 1;
					edges[j][i] = 1;
				} else {
					edges[i][j] = LARGE;
					edges[j][i] = LARGE;
				}
			}
		}
		
		// System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [FW_INIT]");
		
		// Floyd-Warshall for the board.
		for(int k = 0; k < edges.length; k++) {
			for(int i = 0; i < edges.length; i++) {
				for(int j = 0; j < edges.length; j++) {
					edges[i][j] = Math.min(edges[i][j], edges[i][k] + edges[k][j]);
				}
			}
		}
		
		// System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [FW_DONE]");
		
		int[][] kingMap = new int[W][H];
		for(int x = 0; x < W; x++) {
			for(int y = 0; y < H; y++) {
				kingMap[x][y] = Math.max(Math.abs(king.x-x), Math.abs(king.y-y));
			}
		}
		
		// mounting
		// mount at point where (edge[knight][point] + 0/1 (king distance) + edge[point][final]) is minimized
		// this becomes mountingDistance and it replaces kingDist and the mountingKnight dist.
		// *** don't need to check every point, simply those in those adjacent to the king (or the initial space).
		// this is because moving a knight is quicker/equal to the king's moves over any distance greater than 1
		// (knightdist: 1 away = 2 (URDL) or 3 (Diagonal) ... > 1 (king move))
		
		// final calculation
		int minSum = LARGE;
		Point minLoc = new Point(-1,-1);
		
		// convene at (x,y).
		for(int x = 0; x < W; x++) {
			inner:
			for(int y = 0; y < H; y++) {
				int src = x*H + y;
				
				int sum = 0;
				for(Point knight: knights) {
					int i = knight.x*H + knight.y;
					sum += edges[src][i];
					if(sum > minSum) continue inner;
				}
				// System.out.println("pre sum = "+sum);
				
				// mounting here.
				int minDist = LARGE;
				Point mountKnight = new Point(-1,-1);
				Point mountLocation = new Point(-1,-1);
				int minDistFromSrc = LARGE;
				
				final int MAX_KSPACES = 2;
				for(int kdx = -MAX_KSPACES; kdx <= MAX_KSPACES &&  king.x + kdx < W; kdx++) {
					if(king.x+kdx < 0) continue;
					for(int kdy = -MAX_KSPACES; kdy <= MAX_KSPACES && king.y + kdy < H; kdy++) {
						if(king.y+kdy < 0) {
							continue;
						}
						
						int pIndex = (king.x+kdx)*H + (king.y+kdy);
						
						for(Point knight: knights) {
							int knIndex = knight.x*H + knight.y;
							int dist = edges[knIndex][pIndex] + Math.max(Math.abs(kdx), Math.abs(kdy));
							
							if((dist < minDist && dist < edges[knIndex][src] + kingMap[x][y])
							|| (dist == minDist && edges[pIndex][src] < minDistFromSrc)) {
								minDist = dist;
								mountKnight = knight;
								mountLocation = new Point(king.x+kdx,king.y+kdy);
								minDistFromSrc = edges[pIndex][src];
							}
							// knight already in position.
							if(edges[knIndex][pIndex] == 0) break;
						}
					}
				}
				
				if(mountKnight.x != -1) {
					sum -= edges[mountKnight.x*H + mountKnight.y][src];
					sum += minDist;
					sum += minDistFromSrc;
					// if(x==0&&y==14) System.out.println(minDist+","+minDistFromSrc);
				}else {
					sum += kingMap[x][y];
				}
				
				if(sum < minSum) {
					minSum = sum;
					minLoc = new Point(x,y);
				}
				
				System.out.println((char) ('A'+x)+","+(y+1)+": "+sum + "\t || "+mountKnight+ " || "+mountLocation);
			}
		}
		
		
		System.out.println(minSum + " @ " + (char) ('A' + minLoc.x) + "" + (minLoc.y+1));
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("camelot.out")));
		out.println(minSum);
		out.close();
	}
}
