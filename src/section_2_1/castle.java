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
		Input in = fromFile("castle.in");
		
		int totalCols = in.nextInt();
		int totalRows = in.nextInt();
		
		Module[][] castle = new Module[totalRows][totalCols];
		for (int row = 0; row < totalRows; row++) {
			for (int col = 0; col < totalCols; col++) {
				castle[row][col] = new Module(in.nextInt(), 0);
			}
		}
		
		String result = solve(castle, totalRows, totalCols);
		
		PrintWriter pw = new PrintWriter(new File("castle.out"));
		pw.println(result);
		pw.close();
		in.close();
	}

	static String solve(Module[][] castle, int totalRows, int totalCols) {
		int[] roomSizes = new int[totalRows * totalCols];
		int totalRooms = initRooms(castle, roomSizes, totalRows, totalCols);
		
		int maxRoomSize = 0;
		for (int size : roomSizes) {
			maxRoomSize = Math.max(maxRoomSize, size);
		}
		
		int rowRemoved = 0;
		int colRemoved = 0;
		char direction = 'N';
		int maxSizeRemoved = 0;
		
		for (int col = 0; col < totalCols; col++) {
			for (int row = totalRows - 1; row >= 0; row--) {
				// check north
				if (row - 1 >= 0 && castle[row][col].room != castle[row - 1][col].room) {
					int sum = roomSizes[castle[row][col].room] + roomSizes[castle[row - 1][col].room];
					
					if (sum > maxSizeRemoved) {
						maxSizeRemoved = sum;
						rowRemoved = row;
						colRemoved = col;
						direction = 'N';
					}
				}
				
				// check east
				if (col + 1 < totalCols && castle[row][col].room != castle[row][col + 1].room) {
					int sum = roomSizes[castle[row][col].room] + roomSizes[castle[row][col + 1].room];
					
					if (sum > maxSizeRemoved) {
						maxSizeRemoved = sum;
						rowRemoved = row;
						colRemoved = col;
						direction = 'E';
					}
				}
			}
		}
		
		rowRemoved++;
		colRemoved++;
		
		return totalRooms + "\n" + maxRoomSize + "\n" + maxSizeRemoved + "\n" +
				   + rowRemoved + " " + colRemoved + " " + direction;
	}
	
	static int initRooms(Module[][] castle, int[] roomSizes, int totalRows, int totalCols) {
		int roomNumber = 0;
		
		for (int row = 0; row < totalRows; row++) {
			for (int col = 0; col < totalCols; col++) {
				if (!castle[row][col].visited) {
					int size = dfs(castle, roomNumber, row, col, totalRows, totalCols);
					roomSizes[roomNumber] = size;
					roomNumber++;
				}
			}
		}
		
		return roomNumber;
	}
	
	static int dfs(Module[][] castle, int roomNumber, int row, int col, int totalRows, int totalCols) {
		int count = 1;
		int walls = castle[row][col].wall;
		castle[row][col].room = roomNumber;
		castle[row][col].visited = true;
		
		// go south
		if (walls - SOUTH >= 0) {
			walls -= SOUTH;
		} else if (row + 1 < totalRows && !castle[row + 1][col].visited) {
			count += dfs(castle, roomNumber, row + 1, col, totalRows, totalCols);
		}
		
		// go east
		if (walls - EAST >= 0) {
			walls -= EAST;
		} else if (col + 1 < totalCols && !castle[row][col + 1].visited) {
			count += dfs(castle, roomNumber, row, col + 1, totalRows, totalCols);
		}
		
		// go north
		if (walls - NORTH >= 0) {
			walls -= NORTH;
		} else if (row - 1 >= 0 && !castle[row - 1][col].visited) {
			count += dfs(castle, roomNumber, row - 1, col, totalRows, totalCols);
		}
		
		// go west
		if (walls - WEST >= 0) {
			walls -= WEST;
		} else if (col - 1 >= 0 && !castle[row][col - 1].visited) {
			count += dfs(castle, roomNumber, row, col - 1, totalRows, totalCols);
		}
		
		return count;
	}
	
	static class Module {
		int wall;
		int room;
		boolean visited = false;
		
		public Module(int wall, int room) {
			this.wall = wall;
			this.room = room;
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
