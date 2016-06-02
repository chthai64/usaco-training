/*
ID: conchim1
LANG: JAVA
TASK: ttwo
 */

package section_2_4;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class ttwo {
	enum Direction {N, E, S, W};

	public static void main(String[] args) throws Exception {
		Input in = fromFile("ttwo.in");
		char[][] grid = new char[10][10];
		
		for (int i = 0; i < 10; i++) {
			grid[i] = in.nextLine().toCharArray();
		}
		
		int result = solve(grid);
		
		PrintWriter pw = new PrintWriter(new File("ttwo.out"));
		pw.println(result);
		pw.close();
		in.close();
		
//		System.out.println(result);
	}
	
	static int solve(char[][] grid) {
		Point farmer = null;
		Point cow = null;
		
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				if (grid[x][y] == 'F') {
					farmer = new Point(x, y);
					grid[x][y] = '.';
				}
				
				if (grid[x][y] == 'C') {
					cow = new Point(x, y);
					grid[x][y] = '.';
				}
			}
		}
		
		Direction farmerDir = Direction.N;
		Direction cowDir = Direction.N;
		
		for (int min = 0; min <= 1000000; min++) {
			if (farmer.equals(cow)) {
				return min;
			}

			Point nextFarmer = move(grid, farmer, farmerDir);
			if (nextFarmer == null) {
				farmerDir = nextDir(farmerDir);
			} else {
				farmer = nextFarmer;
			}

			Point nextCow = move(grid, cow, cowDir);
			if (nextCow == null) {
				cowDir = nextDir(cowDir);
			} else {
				cow = nextCow;
			}
		}
		
		return 0;
	}
	
	static Point move(char[][] grid, Point point, Direction dir) {
		Point next = new Point(point.x, point.y);
		
		switch(dir) {
		case N:
			next.x--;
			
			break;
		case E:
			next.y++;
			break;
		case S:
			next.x++;
			break;
		case W:
			next.y--;
			break;
		}
		
		if (next.x < 0 || next.x >= 10)
			return null;
		
		if (next.y < 0 || next.y >= 10)
			return null;
		
		if (grid[next.x][next.y] == '*')
			return null;
		
		return next;
	}
	
	static Direction nextDir(Direction dir) {
		switch (dir) {
		case N:
			return Direction.E;
		case E:
			return Direction.S;
		case S:
			return Direction.W;
		case W:
			return Direction.N;
		}
		return Direction.N;
	}
	
	static class Point {
		int x, y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Point) {
				Point p = (Point) obj;
				return p.x == x && p.y == y;
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return Arrays.hashCode(new int[] {x,y});
		}
		
		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}
	}

	private static Input fromFile(String path) throws IOException {
		return new Input(new FileInputStream(new File(path)));
	}

	private static class Input {
		private BufferedReader reader;
		private StringTokenizer stt;

		public Input(InputStream stream) {		
			reader = new BufferedReader(new InputStreamReader(stream));
		}

		public String nextLine() {
			try {
				return reader.readLine();
			} catch (IOException e) {
				return null;
			}
		}

		public String nextString() {
			while (stt == null || !stt.hasMoreTokens()) {
				String line = nextLine();
				if (line == null)
					return null;
				
				stt = new StringTokenizer(line);
			}
			return stt.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(nextString());
		}

		public long nextLong() {
			return Long.parseLong(nextString());
		}

		public String next() {
			return nextString();
		}

		public void close() {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {}
			}
		}
	}
}
