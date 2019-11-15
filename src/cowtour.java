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
	// This is now the correct solution for Section 2.4 - Cow Tours.
	// With help from this blog post: http://zeffsalgo.blogspot.com/2013/12/usaco-training-problem-cow-tours.html
	private static final double DMAX = Double.MAX_VALUE;
	
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
				}else {
					edges[x][y] = DMAX;
					edges[y][x] = DMAX;
				}
				// System.out.print(placeRound(edges[x][y],4) + "\t");
			}
			// System.out.println();
		}
		
		in.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [I]");
		
		double[][] master = floyd_warshall(edges);
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [A]");
		
		double[] longestPath = new double[N];
		
		for(int j = 0; j < N; j++) {
			double max = 0;
			for(int k = 0; k < N; k++) {
				// System.out.print(master[j][k]+"\t");
				if(master[j][k] < DMAX) max = Math.max(master[j][k], max);
			}
			// System.out.println();
			longestPath[j] = max;
		}
		
		double[] fieldDiameter = new double[N];
		for(int j = 0; j < longestPath.length; j++) {
			for(int k = 0; k < N; k++) {
				// if nodes are connected, update pasture diameter at that node
				if(master[j][k] < DMAX) fieldDiameter[k] = Math.max(longestPath[j], fieldDiameter[k]);
			}
		}

		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [B]");
		
		double answer = DMAX;
		
		for(int b = 0; b < N; b++) {
			for(int a = 0; a < b; a++) {
				// can't be an existing connected set of points
				if(master[a][b] < DMAX) continue;
				
				if(a == 0 && b==5) {
					System.out.println("pythag: "+ pythag(nodes[a],nodes[b]));
					System.out.println("longa: "+longestPath[a]);
					System.out.println("longb: "+longestPath[b]);
				}
				
				// longest path from a in its field, plus that of b, plus the cost of connecting them
				double connectedDiameter = pythag(nodes[a],nodes[b]) + longestPath[a] + longestPath[b];
				
				// the greatest diameter between the two fields
				double greatestFieldDiameter = Math.max(fieldDiameter[a], fieldDiameter[b]);
				
				// take the bigger of the two
				double diameter = Math.max(greatestFieldDiameter, connectedDiameter);
				
				// System.out.println(a+","+b+": "+diameter);
				// update answer
				answer = Math.min(diameter, answer);
			}
		}
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [C]");
		// FORMAT/OUTPUT
		
		String s = String.format("%.6f",answer);
		// System.out.println(s);
		int B = 6 + (s.split("\\.")[0].length() + 1);
		
		String output = String.format("%-"+B+"s",s).replace(' ', '0');
		System.out.println(output);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cowtour.out")));
		out.println(output);
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}
	
	public static double pythag(Point a, Point b) {
		int dx = a.x - b.x;
		int dy = a.y - b.y;
		return Math.sqrt((dx*dx) + (dy*dy));
	}
	
	public static double[][] floyd_warshall(double[][] edges) {
		int N = edges.length;
		double[][] floyd = new double[N][N];
		for(int j = 0; j < N; j++) {
			for(int k = 0; k <= j; k++) {
				floyd[j][k] = edges[j][k];
				floyd[k][j] = edges[j][k];
			}
		}
		
		for(int inter = 0; inter < N; inter++) {
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					double val = Math.min(floyd[i][inter] + floyd[inter][j], floyd[i][j]);
					floyd[i][j] = val;
					floyd[j][i] = val;
				}
			}
		}
		return floyd;
	}
	
//	public static double[] dijkstra(int src, double[][] edges) {
//		int N = edges.length;
//		
//		double[] dist = new double[N];
//		boolean[] inPath = new boolean[N];
//		
//		for(int i = 0; i < N; i++) {
//			dist[i] = Double.MAX_VALUE;
//		}
//		dist[src] = 0;
//		
//		for(int n = 0; n < N; n++) {
//			double min = Double.MAX_VALUE;
//			int a = -1;
//			for(int i = 0; i < N; i++) {
//				if(!inPath[i] && dist[i] < min) {
//					a = i;
//					min = dist[i];
//				}
//			}
//			
//			if(a >= 0) {
//				// if adjacent found.
//				inPath[a] = true;
//				
//				for(int v = 0; v < N; v++) {
//					if(!inPath[v] && edges[a][v] > 0) {
//						// shortest of current node + edge and value stored in v
//						dist[v] = Math.min(dist[v],  dist[a] + edges[a][v]);
//					}
//				}
//			}
//		}
//		
//		return dist;
//	}
}
