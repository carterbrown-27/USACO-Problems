/*
ID: carter.2
TASK: castle
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class castle {
	
	// Solution for "Problem 10The Castle"
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		
		BufferedReader in;
		String file = "castle.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}	
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int X = Integer.parseInt(st.nextToken());
		int Y = Integer.parseInt(st.nextToken());
		
		String[][] map = new String[Y][X];
		boolean[][] roomsVisited = new boolean[Y][X];
		
		int[][] roots = new int[Y][X];
		// read in rows & set all roomValues to -1
		for(int y = 0; y < Y; y++) {
			st = new StringTokenizer(in.readLine());
			for(int x = 0; x < X; x++) {
				String s = Integer.toBinaryString(Integer.parseInt(st.nextToken()));
				
				// left justify
				int sLen = s.length();
				for(int i = 0; i < 4 - sLen; i++) {
					s = "0" + s;
				}
				
				map[y][x] = s;
				roots[y][x] = -1;
			}
		}
		in.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [A]");
		
		// int[] = [x,y,room#]
		Queue<Integer[]> q = new LinkedList<Integer[]>();
		int rootID = 0;
		
		// HashMap<k,v> = <rootID, rootSize>
		Map<Integer,Integer> rootValueMap = new HashMap<Integer,Integer>();
		
		// Set<Integer[]>  = <[x,y,dir,rootID]
		ArrayList<Integer[]> walls = new ArrayList<Integer[]>();
		Integer[] rootRoom = findNewRoot(roomsVisited);
		q.add(rootRoom);
		
		do {
			Integer[] current = q.poll();
			
			int x = current[0];
			int y = current[1];
			
			if(!roomsVisited[y][x]) {
				current[2] = rootID;
				roots[y][x] = rootID;
				
				String roomInfo = map[y][x];
				
				// mark visited
				roomsVisited[y][x] = true;
				
				// add adjacents to q based on criteria
				// SENW order
				for(int i = 0; i < 4; i++) {
					char c = roomInfo.charAt(i);
					DIRECTION d = DIRECTION.values()[i];
					// Wall in this direction
					if(c == '1') {
						walls.add(new Integer[] {x,y,d.n,rootID});
						continue;
					}
					
					// check room in delta direction
					int x2 = x + d.DELTA[0];
					int y2 = y + d.DELTA[1];
					
					// Out of Bounds.
					if(x2 < 0 || x2 >= X || y2 < 0 || y2 >= Y) {
						continue;
					}
					
					// if already visited
					if(roomsVisited[y2][x2]) {
						continue;
					}

					// add new room to queue
					q.add(new Integer[] {x2,y2,rootID});
				}

			}
			
			// root is finished
			if(q.isEmpty()) {
				
				rootID++;
				rootRoom = findNewRoot(roomsVisited);
				q.add(rootRoom);
			}
		}while(rootRoom != null);
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [B]");
		
		for(int y = 0; y < Y; y++) {
			for(int x = 0; x < X; x++) {
				System.out.print(roots[y][x]+"\t");
				int val = rootValueMap.getOrDefault(roots[y][x], 0) + 1;
				rootValueMap.put(roots[y][x], val);
			}
			System.out.println();
		}
		
		int c = 0;
		int max = 0;
		for(int n: rootValueMap.values()) {
			if(n>1) System.out.println(c+":"+n);
			max = Math.max(max, n);
			c++;
		}
		
		ArrayList<Integer> rootValuesSorted = new ArrayList<Integer>(rootValueMap.values());
		Collections.sort(rootValuesSorted, Comparator.reverseOrder());
	
		// ^^ DONE GENERATING ROOM INFO
		// Start Knocking Wall Down.
		
		/*
		 * 1. find all the walls.
		 * 2. sort the walls based on the sum of their "sides"
		 * 3. find all walls of highest sum
		 * 4. find furthest to the WEST, (tiebreaker) then SOUTH, (tiebreaker 2) choose the 'N' wall.
		 */
		
		Collections.sort(walls, (Integer[] a, Integer[] b) -> {
			// west most
			if(a[0] == b[0]) {
				// south most
				if(a[1] == b[1]) {
					// N > E
					if(a[2] == DIRECTION.NORTH.n) {
						return -1;
					}else if (b[2] == DIRECTION.NORTH.n){
						return 1;
					}else {
						return 0;
					}
				}else {
					return b[1].compareTo(a[1]);
				}
			}else {
				return a[0].compareTo(b[0]);
			}
		});
		
		int maxSum = 0;
		Integer[] maxWall = new Integer[0];
		for(Integer[] wall: walls) {
			// y + x + dir
			int wallDir = wall[2];
			DIRECTION d = DIRECTION.values()[wallDir];
			if(d.equals(DIRECTION.SOUTH) || d.equals(DIRECTION.WEST)) continue;
			
			int x = wall[0];
			int y = wall[1];
			int root = wall[3];
			
			
			// check room in delta direction
			int x2 = x + d.DELTA[0];
			int y2 = y + d.DELTA[1];
			
			// Out of Bounds.
			if(x2 < 0 || x2 >= X || y2 < 0 || y2 >= Y) {
				continue;
			}
			
			// if in same room, continue
			int secondRootID = roots[y2][x2];
			if(secondRootID == root) {
				continue;
			}
			
			int sum = rootValueMap.get(root) + rootValueMap.get(secondRootID); // calculate it
			
			if(sum > maxSum) { // ties aren't allowed bc list is already sorted by tiebreakers.
				maxSum = sum;
				maxWall = wall;
			}
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("castle.out")));
		// FINAL OUTPUT
		out.println(rootValueMap.size());
		out.println(max);
		out.println(maxSum);
		out.println((maxWall[1]+1)+" "+(maxWall[0]+1)+" "+DIRECTION_CHARS[maxWall[2]]);
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [B]");
	}
	
	// int[] = [x,y]
	public static Integer[] findNewRoot (boolean[][] roomsVisited) {
		int Y = roomsVisited.length;
		int X = roomsVisited[0].length;
		for(int y = 0; y < Y; y++) {
			for(int x = 0; x < X; x++) {
				if(roomsVisited[y][x] == false) return (new Integer[] {x,y,-1});
			}
		}
		return null;
	}
	
	public static enum DIRECTION {
		SOUTH (0),
		EAST (1),
		NORTH (2),
		WEST (3);
		
		int n;
		char c;
		int[] DELTA;
		DIRECTION(int n){
			this.DELTA = DIRECTION_DELTAS[n];
			this.c = DIRECTION_CHARS[n];
			this.n = n;
		}
	}
	
	// SENW order
	public static final int[][] DIRECTION_DELTAS = {
			{0,1}, // S
			{1,0}, // E
			{0,-1}, // N
			{-1,0}  // W
	};
	
	public static final char[] DIRECTION_CHARS = {'S','E','N','W'};
	
//	public static DIRECTION getOppositeDir(DIRECTION d) {
//		int v = d.n;
//		int delta = 1;
//		if(v >= 2) {
//			delta = -1;
//		}
//		return DIRECTION.values()[v+(2*delta)];
//	}
}
