/*
ID: carter.2
TASK: cowtour
LANG: JAVA
 */
import java.util.*;
import java.awt.Point;
import java.io.*;
public class cowtour {
	
	// initial solution was correct through cases 1...7 out of 9, ran quickly
	// this solution is completely correct, but runs slowly.
	// TODO: switch out dijkstra for Floyd-Warshall maybe?
	// Alternatively, could flood fill all nodes, then connect them, but this is slow in the worst case (isolated nodes)
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		
		BufferedReader in;
		String file = "cowtour.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		int N = Integer.parseInt(in.readLine());
		
		Point[] nodes = new Point[N];
		
		StringTokenizer st;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(in.readLine());
			nodes[i] = new Point(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
		}
		
		double[][] edges = new double[N][N];
		
		for(int y = 0; y < N; y++) {
			String str = in.readLine();
			for(int x = 0; x < y; x++) {
				if(str.charAt(x) == '1') {
					double edge = pythag(nodes[x],nodes[y]);
					edges[x][y] = edge;
					edges[y][x] = edge;
				}
				// System.out.print(placeRound(edges[x][y],4) + "\t");
			}
			// System.out.println();
		}
		
		in.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [I]");
		
		double[] dist = new double[N];
		
		// minDistance doesn't give wanted answer for test 8, remove
//		double minDistance = Double.MAX_VALUE;
		double ans = Double.MAX_VALUE;
//		ArrayList<Integer[]> bridges = new ArrayList<Integer[]>();
		
		// TODO: have a list of possible bridges, iterate over all of them
		int eval_count = 0;
		// comments = distance of bridge checking
		for(int b = 0; b < N; b++) {
			double[] temp = dijkstra(b,edges);
			
			for(int a = 0; a < b; a++) {
				// can't be an existing edge.
				if(edges[a][b] > 0) continue;
				double d = pythag(nodes[a],nodes[b]);
//				
				if(d > ans) {
					continue;
				}			
				if(temp[a] < Double.MAX_VALUE) continue;
				
				// bridges.add(new Integer[] {a,b});
//				if(true /* d == minDistance */) {
//					bridges.add(new Integer[] {a,b});
//				}
				/*
				}else if(d < minDistance) {

					minDistance = d;
					bridges.clear();
					bridges.add(new Integer[] {a,b});
				}*/
				
				int[] bridge = {a,b};
				
				// System.out.println(bridge[0]+ "|" + bridge[1]);
				double bridge_d = pythag(nodes[bridge[0]], nodes[bridge[1]]);
				edges[bridge[1]][bridge[0]] = bridge_d;
				edges[bridge[0]][bridge[1]] = bridge_d;
				
				dist = dijkstra(0,edges);
				
				double max = 0;
				int mp = -1;
				for(int i = 0; i < dist.length; i++) {
					if(dist[i] < Double.MAX_VALUE && dist[i] > max) {
						max = dist[i];
						mp = i;
					}
				}
				
//				if(max>ans*3) {
//					continue;
//				}
				
				if(mp == -1) {
					ans = Math.min(ans,bridge_d);
					continue;
				}
				
				eval_count++;
				
				// System.out.println("mp: "+mp);
				dist = dijkstra(mp,edges);
				
				double tempAns = 0.0;
				for(double dv: dist) {
					// System.out.println(d+"-");
					if(dv < Double.MAX_VALUE) tempAns = Math.max(tempAns, dv);
				}
				
				// System.out.println(tempAns);
				if(tempAns > 0) {
					ans = Math.min(tempAns, ans);		
				}
				
				edges[bridge[1]][bridge[0]] = 0;
				edges[bridge[0]][bridge[1]] = 0;
			}
		}

		// System.out.println("md: "+minDistance);
//		System.out.println("bridges: "+bridges.size());
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [B]");
		
		// BRIDGES
		System.out.println(eval_count);
		
		
//		for(Integer[] bridge: bridges) {
//
//		}
		
		// FORMAT/OUTPUT
		
		ans = placeRound(ans,6);
		
		String s = String.valueOf(ans);
		int B = 6 + (s.split("\\.")[0].length() + 1);
		
		String output = String.format("%-"+B+"s",ans).replace(' ', '0');
		System.out.println(output);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cowtour.out")));
		out.println(output);
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
	}
	
	public static double pythag(Point a, Point b) {
		int dx = a.x - b.x;
		int dy = a.y - b.y;
		return Math.sqrt((dx*dx) + (dy*dy));
	}
	
	public static double placeRound(double n, int d) {
		return Math.round(n * Math.pow(10, d)) / Math.pow(10, d);
	}
	
	
	// TODO: fix this fn, this is the problem.
//	public static double getDiameter(double[] dist) {
//		double maxNode = Double.MIN_VALUE;
//		double minNode = Double.MAX_VALUE;
//		
//		for(int i = 0; i < dist.length; i++) {
//			double v = dist[i];
//			if(v < Double.MAX_VALUE) {
//				maxNode = Math.max(maxNode, v);
//				minNode = Math.min(minNode, v);
//			}
//		}
//		return Math.abs(maxNode - minNode);
//	}
	
	public static double[] dijkstra(int src, double[][] edges) {
		int N = edges.length;
		
		double[] dist = new double[N];
		boolean[] inPath = new boolean[N];
		
		for(int i = 0; i < N; i++) {
			dist[i] = Double.MAX_VALUE;
		}
		dist[src] = 0;
		
		for(int n = 0; n < N; n++) {
			double min = Double.MAX_VALUE;
			int a = -1;
			for(int i = 0; i < N; i++) {
				if(!inPath[i] && dist[i] < min) {
					a = i;
					min = dist[i];
				}
			}
			
			if(a >= 0) {
				// if adjacent found.
				inPath[a] = true;
				
				for(int v = 0; v < N; v++) {
					if(!inPath[v] && edges[a][v] > 0) {
						// shortest of current node + edge and value stored in v
						dist[v] = Math.min(dist[v],  dist[a] + edges[a][v]);
					}
				}
			}
		}
		
		return dist;
	}
}
