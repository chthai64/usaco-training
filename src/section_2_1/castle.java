/*
ID: conchim1
LANG: JAVA
TASK: castle
 */

package section_2_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class castle {
	private static final int SOUTH = 8;
	private static final int EAST = 4;
	private static final int NORTH = 2;
	private static final int WEST = 1;

	public static void main(String[] args) throws Exception {
		Input in = fromFile("C:\\Users\\Chau Thai\\Desktop\\castle.in");
		
		int totalCols = in.nextInt();
		int totalRows = in.nextInt();
		int[][] mat = new int[totalRows][totalCols];
		
		for (int row = 0; row < totalRows; row++) {
			for (int col = 0; col < totalCols; col++) {
				mat[row][col] = in.nextInt();
			}
		}
		
		boolean[][] graph = buildGraph(mat, totalRows, totalCols);
		String result = solve(graph, totalRows,totalCols);
		System.out.println(result);
		
//		PrintWriter pw = new PrintWriter(new File("castle.out"));
//		pw.println(result);
//		pw.close();
//		in.close();
	}
	
	static String solve(boolean[][] graph, int totalRows, int totalCols) {
		int totalRooms = 0;
		int largestRoom = 0;
		
		Set<Integer> visited = new HashSet<Integer>();
		int totalVertices = totalRows * totalCols;
		
		for (int vertex = 0; vertex < totalVertices; vertex++) {
			if (visited.contains(vertex))
				continue;
			totalRooms++;
			
			Queue<Integer> queue = new LinkedList<Integer>();
			queue.add(vertex);
			visited.add(vertex);
			
			int roomSize = 0;
			while (!queue.isEmpty()) {
				roomSize++;
				int currVertex = queue.poll();
				
				for (int neighbor : getNeighbor(totalRows, totalCols, currVertex)) {
					if (graph[currVertex][neighbor] && !visited.contains(neighbor)) {
						visited.add(neighbor);
						queue.add(neighbor);
					}
				}
			}
			
			largestRoom = Math.max(largestRoom, roomSize);
		}
		
		return totalRooms + "\n" + largestRoom;
	}
	
	static List<Integer> getNeighbor(int totalRows, int totalCols, int vertex) {
		List<Integer> list = new ArrayList<Integer>();
		Point point = toPoint(totalCols, vertex);
		
		// top
		if (point.row - 1 >= 0) {
			Point neighbor = new Point(point.row - 1, point.col);
			list.add(toVertex(totalCols, neighbor));
		}

		// bottom
		if (point.row + 1 < totalRows) {
			Point neighbor = new Point(point.row + 1, point.col);
			list.add(toVertex(totalCols, neighbor));
		}
		
		// left
		if (point.col - 1 >= 0) {
			Point neighbor = new Point(point.row, point.col - 1);
			list.add(toVertex(totalCols, neighbor));
		}
		
		// right
		if (point.col + 1 < totalCols) {
			Point neighbor = new Point(point.row, point.col + 1);
			list.add(toVertex(totalCols, neighbor));
		}
		
		return list;
	}
	
	static boolean[][] buildGraph(int[][] mat, int totalRows, int totalCols) {
		int totalVertices = totalRows * totalCols;
		
		boolean[][] graph = new boolean[totalVertices][totalVertices];
		for (boolean[] a : graph) {
			Arrays.fill(a, true);
		}
		
		for (int row = 0; row < totalRows; row++) {
			for (int col = 0; col < totalCols; col++) {
				int vertex = toVertex(totalCols, row, col);
				int walls = mat[row][col];
				
				// check south
				if (walls - SOUTH >= 0) {
					if (row + 1 < totalRows) {
						int neighborVertex = toVertex(totalCols, row + 1, col);
						graph[vertex][neighborVertex] = graph[neighborVertex][vertex] = false;
					}
					walls -= SOUTH;
				}
				
				// check east
				if (walls - EAST >= 0) {
					if (col + 1 < totalCols) {
						int neighborVertex = toVertex(totalCols, row, col + 1);
						graph[vertex][neighborVertex] = graph[neighborVertex][vertex] = false;
					}
					walls -= EAST;
				}

				// check north
				if (walls - NORTH >= 0) {
					if (row - 1 >= 0) {
						int neighborVertex = toVertex(totalCols, row - 1, col);
						graph[vertex][neighborVertex] = graph[neighborVertex][vertex] = false;
					}
					walls -= NORTH;
				}

				// check west
				if (walls - WEST >= 0) {
					if (col - 1 >= 0) {
						int neighborVertex = toVertex(totalCols, row, col - 1);
						graph[vertex][neighborVertex] = graph[neighborVertex][vertex] = false;
					}
					walls -= WEST;
				}
			}
		}
		
		return graph;
	}
	
	static int toVertex(int totalCols, Point point) {
		return toVertex(totalCols, point.row, point.col);
	}
	
	static int toVertex(int totalCols, int row, int col) {
		return row * totalCols + col;
	}
	
	static Point toPoint(int totalCols, int vertex) {
		int row = vertex / totalCols;
		int col = vertex % totalCols;
		return new Point(row, col);
	}
	
	static class Point {
		int row, col;
		
		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}

		@Override
		public String toString() {
			return "[row=" + row + ", col=" + col + "]";
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
				stt = new StringTokenizer(nextLine());
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
