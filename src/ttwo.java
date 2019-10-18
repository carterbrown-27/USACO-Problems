/*
ID: carter.2
TASK: ttwo
LANG: JAVA
 */
import java.awt.Point;
import java.io.*;
public class ttwo {

	public static final int MAPSIZE = 10;
	// Up, Right, Down, Left
	public static final Point[] deltas = {new Point(0,-1),new Point(1,0),new Point(0,1),new Point(-1,0)};
	
	public static final int MAX_MINUTES = (MAPSIZE*MAPSIZE/2 +1) * (MAPSIZE*MAPSIZE/2);
	/*
	 *  $MAX_MINUTES = 
	 *  This is used to check for no-meet cases. Knowing that the longest each agent could possibly loop for is
	 *  MAPSIZE squared & given that these loops cannot overlap, it is safe to say that the longest that 2 non-
	 *  overlapping loops could extend would be half of the MAPSIZE^2, plus 1, as if both are
	 *  51 long when MS^2 = 100, they must overlap at least once.
	 *  
	 *  This is then multiplied by itself minus one, the max amount the loops could be offset (50 off for len = 51)
	 *  
	 *  NOTE: USACO uses 160000 as its max (MAPSIZE^4 * 4^2)
	 */
	
	// f[x,y,dir] -> c[x,y,dir]
	// public static HashMap<String,String> visited = new HashMap<String,String>();
	
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		
		BufferedReader in;
		String file = "ttwo.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		int[][] map = new int[MAPSIZE][MAPSIZE];
		
		Agent f = new Agent();
		Agent c = new Agent();
		
		for(int y = 0; y < MAPSIZE; y++) {
			String str = in.readLine();
			for(int x = 0; x < MAPSIZE; x++) {
				char ch = str.charAt(x);
				if(ch == '*') {
					map[x][y] = 1;
				}else if(ch != '.'){
					if(ch == 'F') {
						f = new Agent(new Point(x,y));
					}else if(ch == 'C') {
						c = new Agent(new Point(x,y));
					}
				}
			}
		}
		
		in.close();
		
		int minutes = 0;
		while(!f.p.equals(c.p)) {
	
			if(/* checkLoop(f,c) || */ minutes > MAX_MINUTES) {
				System.out.println("looped "+minutes);
				minutes = 0;
				break;
			}
			
			// visited.put(f.toString(),c.toString());
			
			if(aheadOpen(f, map)) {
				f.p.translate(deltas[f.dir].x, deltas[f.dir].y);
			}else {
				f.dir++;
				if(f.dir>=4) f.dir = 0;
			}
			
			if(aheadOpen(c, map)) {
				c.p.translate(deltas[c.dir].x, deltas[c.dir].y);
			}else {
				c.dir++;
				if(c.dir>=4) c.dir = 0;
			}
				
			minutes++;
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ttwo.out")));
		out.println(minutes);
		out.close();
		
		System.out.println(minutes);
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
	}
	
//	public static boolean checkLoop (Agent f, Agent c) {
//		String fs = f.toString();
//		String cs = c.toString();
//		
//		if(!visited.containsKey(fs)) return false;
//		String cvs = visited.get(fs);
//		
//		return cvs.equals(cs);
//	}
	
	public static boolean aheadOpen (Agent a, int[][] map) {
		Point np = new Point(a.p);
		
		np.translate(deltas[a.dir].x, deltas[a.dir].y);
		
		if(np.x < 0 || np.x >= MAPSIZE || np.y < 0 || np.y >= MAPSIZE) return false;
		return map[np.x][np.y] == 0;
	}
	
	public static class Agent {
		int dir = 0;
		Point p = new Point(0,0);
		
		Agent() {};
		
		
		Agent(Point p){
			this.p = p;
		}
		
		Agent(Point p, int dir) {
			this.p = p;
			this.dir = dir;
		}
		
//		Agent(Agent a){
//			this.dir = a.dir;
//			this.p = a.p;
//		}
		
//		public String toString() {
//			return p.x+" "+p.y+" "+dir;
//		}
	}
}
