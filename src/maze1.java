/*
ID: carter.2
TASK: maze1
LANG: JAVA
 */
import java.awt.Point;
import java.io.*;
import java.util.*;
public class maze1 {
	
	public static final Point[] deltas = {new Point(0,-1),new Point(1,0),new Point(0,1),new Point(-1,0)};
	// Up, Right, Down, Left
	
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		
		BufferedReader in;
		String file = "maze1.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int smallW = Integer.parseInt(st.nextToken());
		int smallH = Integer.parseInt(st.nextToken());
		
		int W = 2*smallW + 1;
		int H = 2*smallH + 1;
		
		char[][] cmap = new char[W][H];
		
		Set<Point> exits = new HashSet<Point>();
		
		for(int y = 0; y < H; y++) {
			String str = in.readLine();
			for(int x = 0; x < W; x++) {
				char ch = str.charAt(x);
				if(ch == '+' || ch == '-' || ch == '|') {
					cmap[x][y] = '#'; // wall
					
				}else if(x % 2 != y % 2) {
					if(x == 0 || y == 0 || x == W-1 || y == H-1) {
						exits.add(new Point(x,y));
						System.out.println("exit: "+x+ " "+y);
					}
					cmap[x][y] = 'd';						
				}else {
					cmap[x][y] = '.';
				}
			}
		}
		
		boolean[][][] edgeMap = new boolean[smallW][smallH][4];
		
		
		Set<Point> trueExits = new HashSet<Point>();
		int trueY = 0;
		for(int y = 1; y < H; y+=2, trueY++) {
			int trueX = 0;
			for(int x = 1; x < W; x+=2, trueX++) {
				int c = 0;
				for(Point d: deltas) {
					Point p = new Point(x+d.x,y+d.y);
					edgeMap[trueX][trueY][c] = cmap[p.x][p.y] == '#';
					if(exits.contains(p)) {
						Point te =  new Point(trueX,trueY);
						if(!trueExits.contains(te)) {
							trueExits.add(te);
							System.out.println("true exit: "+te);
						}
					}
					c++;
				}
			}
		}
		
// [DEBUG]: prints the edge info for each node
//		for(int y = 0; y < smallH; y++) {
//			for(int x = 0; x < smallW; x++) {
//				System.out.print(x+","+y+": ");
//				for(int c = 0; c < 4; c++) {
//					System.out.print(edgeMap[x][y][c]+" ");
//
//				}
//				System.out.println();
//			}
//		}
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [A]");
		
		int[][] distanceMap = new int[smallW][smallH];
		
		for(Point src: trueExits) {
			boolean[][] visitedMap = new boolean[smallW][smallH];
			
			distanceMap[src.x][src.y] = 1;
			
			// int[] = [x,y,distance]
			Queue<Integer[]> q = new LinkedList<Integer[]>();
			q.add(new Integer[] {src.x,src.y,1});
			
			// [DEBUG] int counter = 0;
			do {
				// [DEBUG] counter++;
				Integer[] c = q.poll();
				int cx = c[0];
				int cy = c[1];
				int cd = c[2];
		
				
				if(visitedMap[cx][cy]) {
					continue;
				}
				
				if(distanceMap[cx][cy] == 0 || cd < distanceMap[cx][cy]) distanceMap[cx][cy] = cd;
				visitedMap[cx][cy] = true;
				
				for(int pos = 0; pos < 4; pos++) {
					if(edgeMap[cx][cy][pos]) continue;
					
					Point d = deltas[pos];
					Point newP = new Point(cx + d.x, cy + d.y);
					int x = newP.x;
					int y = newP.y;
					if(x < 0 || x >= smallW || y < 0 || y >= smallH) continue;
					
					
					if(!visitedMap[x][y]) {
						q.add(new Integer[] {x,y,cd+1});
					}
				}
				
				//if(counter % 1000 == 0) System.out.println(counter);
			}while(!q.isEmpty());
			
			// [DEBUG] System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [I]");
		}
		
		int max = 0;
		for(int y = 0; y < smallH; y++) {
			for(int x = 0; x < smallW; x++) {
				System.out.print(distanceMap[x][y]+"\t");
				max = Math.max(max, distanceMap[x][y]);
			}
			System.out.println();
		}
		
		System.out.println(max);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("maze1.out")));
		out.println(max);
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
	}
}
