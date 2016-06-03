/*
ID: conchim1
LANG: JAVA
TASK: maze1
 */

package section_2_4;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class maze1 {

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		Input in = fromFile("maze1.in");
		int W = in.nextInt();
		int H = in.nextInt();
		
		char[][] grid = new char[2*H + 1][2*W + 1];
		for (int h = 0; h < grid.length; h++) {
			grid[h] = in.nextLine().toCharArray();
		}
		
		Point exit1 = null;
		Point exit2 = null;
		
		for (int rawRow = 1; rawRow < grid.length; rawRow += 2) {
			if (grid[rawRow][0] == ' ') {
				Point p = new Point(getIndex(rawRow), 0);
				if (exit1 == null) {
					exit1 = p;
				} else {
					exit2 = p;
				}
			}
			
			if (grid[rawRow][grid[0].length - 1] == ' ') {
				Point p = new Point(getIndex(rawRow), getIndex(grid[0].length - 1));
				if (exit1 == null) {
					exit1 = p;
				} else {
					exit2 = p;
				}
			}
		}
		
		for (int rawCol = 1; rawCol < grid[0].length; rawCol++) {
			if (grid[0][rawCol] == ' ') {
				Point p = new Point(0, getIndex(rawCol));
				if (exit1 == null) {
					exit1 = p;
				} else {
					exit2 = p;
				}
			}
			
			if (grid[grid.length - 1][rawCol] == ' ') {
				Point p = new Point(getIndex(grid.length - 1), getIndex(rawCol));
				if (exit1 == null) {
					exit1 = p;
				} else {
					exit2 = p;
				}
			}
		}
		
		
		boolean[][][] graph = buildGraph(grid, W, H);
		int result = solve(graph, W, H, exit1, exit2);
		
		PrintWriter pw = new PrintWriter(new File("maze1.out"));
		pw.println(result);
		pw.close();
		in.close();
		
		System.out.println(result);
		long total = System.currentTimeMillis() - start;
		System.out.println("time: " + total + " ms");
		
	}
	
	static int solve(boolean[][][] graph, int W, int H, Point exit1, Point exit2) {
		int[][] dist1 = bfs(graph, exit1, W, H);
		int[][] dist2 = bfs(graph, exit2, W, H);
		
		int max = 0;
		
		for (int row = 0; row < H; row++) {
			for (int col = 0; col < W; col++) {
				int dist = Math.min(dist1[row][col], dist2[row][col]);
				max = Math.max(dist, max);
			}
		}
		
		return max;
	}
	
	static int[] deltaX = {-1,0,1,0};
	static int[] deltaY = {0,1,0,-1};
	
	static int[][] bfs(boolean[][][] graph, Point source, int W, int H) {
		ArrayDeque<Point> queue = new ArrayDeque<Point>(100);
		int[][] distances = new int[H][W];
		
		queue.add(source);
		distances[source.row][source.col] = 1;
		
		while (!queue.isEmpty()) {
			Point p = queue.pollFirst();
			
			for (int i = 0; i < 4; i++) {
				int nextRow = p.row + deltaX[i];
				int nextCol = p.col + deltaY[i];
				
				boolean validRow = 0 <= nextRow && nextRow < H;
				boolean validCol = 0 <= nextCol && nextCol < W;
				
				Point nextPoint = new Point(nextRow, nextCol);
				
				if (validRow && validCol && graph[p.row][p.col][i] && distances[nextRow][nextCol] == 0) {
					distances[nextRow][nextCol] = distances[p.row][p.col] + 1;
					queue.addLast(nextPoint);
				}
			}
		}
		
		return distances;
	}
	
	static boolean[][][] buildGraph(char[][] grid, int W, int H) {
		boolean[][][] graph = new boolean[H][W][4];
		
		for (int row = 0; row < H; row++) {
			for (int col = 0; col < W; col++) {
				// top
				if (row - 1 >= 0) {
					if (grid[getRawIndex(row) - 1][getRawIndex(col)] == ' ')
						graph[row][col][0] = true;
				}
				
				// bottom
				if (row + 1 < H) {
					if (grid[getRawIndex(row) + 1][getRawIndex(col)] == ' ')
						graph[row][col][2]= true;
				}
				
				// left
				if (col - 1 >= 0) {
					if (grid[getRawIndex(row)][getRawIndex(col) - 1] == ' ')
						graph[row][col][3]= true;
				}
				
				// right
				if (col + 1 < W) {
					if (grid[getRawIndex(row)][getRawIndex(col) + 1] == ' ')
						graph[row][col][1] = true;
				}
			}
		}
		
		return graph;
	}
	
	static int getRawIndex(int i) {
		return i * 2 + 1;
	}
	
	static int getIndex(int raw) {
		return (raw - 1) / 2;
	}
	
	static class Point {
		int row, col;
		
		public Point(int x, int y) {
			this.row = x;
			this.col = y;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Point) {
				Point p = (Point) obj;
				return p.row == row && p.col == col;
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return Arrays.hashCode(new int[] {row,col});
		}
		
		@Override
		public String toString() {
			return "Point [x=" + row + ", y=" + col + "]";
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
