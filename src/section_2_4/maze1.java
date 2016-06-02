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
		Input in = fromFile("C:/Users/Chau Thai/Desktop/maze1.in");
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
		
		int[][][][] graph = buildGraph(grid, W, H);
		int result = solve(graph, W, H, exit1, exit2);
		
//		PrintWriter pw = new PrintWriter(new File("maze1.out"));
//		pw.println(result);
//		pw.close();
//		in.close();
		
		System.out.println(result);
	}
	
	static int solve(int[][][][] graph, int W, int H, Point exit1, Point exit2) {
		
		for (int iRow = 0; iRow < H; iRow++) {
			for (int iCol = 0; iCol < W; iCol++) {
				for (int row1 = 0; row1 < H; row1++) {
					for (int col1 = 0; col1 < W; col1++) {
						for (int row2 = 0; row2 < H; row2++) {
							for (int col2 = 0; col2 < W; col2++) {

								if (graph[row1][col1][iRow][iCol] != Integer.MAX_VALUE && 
										graph[iRow][iCol][row2][col2] != Integer.MAX_VALUE) {
									
									int otherLength = graph[row1][col1][iRow][iCol] + 
											graph[iRow][iCol][row2][col2];

									graph[row1][col1][row2][col2] = Math.min(
											graph[row1][col1][row2][col2],
											otherLength
									);
								}
							}
						}						
					}
				}
			}
		}

		int max = 0;
		
		for (int row = 0; row < H; row++) {
			for (int col = 0; col < W; col++) {
				int dist1 = Math.max(max, graph[row][col][exit1.row][exit1.col]);
				int dist2 = Math.max(max, graph[row][col][exit2.row][exit2.col]);
				max = Math.min(dist1, dist2);
			}
		}
		
		return max + 1;
	}

	static int[][][][] buildGraph(char[][] grid, int W, int H) {
		int[][][][] graph = new int[H][W][H][W];
		
		for (int row = 0; row < H; row++) {
			for (int col = 0; col < W; col++) {
				for (int row2 = 0; row2 < H; row2++) {
					for (int col2 = 0; col2 < W; col2++) {
						graph[row][col][row2][col2] = Integer.MAX_VALUE;
						
						if (row == row2 && col == col2) {
							graph[row][col][row2][col2] = 0;
						}
					}
				}
			}
		}
		
		for (int row = 0; row < H; row++) {
			for (int col = 0; col < W; col++) {
				// top
				if (row - 1 >= 0) {
					if (grid[getRawIndex(row) - 1][getRawIndex(col)] == ' ')
						graph[row][col][row - 1][col] = 1;
				}
				
				// bottom
				if (row + 1 < H) {
					if (grid[getRawIndex(row) + 1][getRawIndex(col)] == ' ')
						graph[row][col][row + 1][col] = 1;
				}
				
				// left
				if (col - 1 >= 0) {
					if (grid[getRawIndex(row)][getRawIndex(col) - 1] == ' ')
						graph[row][col][row][col - 1] = 1;
				}
				
				// right
				if (col + 1 < W) {
					if (grid[getRawIndex(row)][getRawIndex(col) + 1] == ' ')
						graph[row][col][row][col + 1] = 1;
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
