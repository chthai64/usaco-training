/*
ID: conchim1
LANG: JAVA
TASK: camelot
 */

package section_3_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class camelot {
	static final int MAX = 1000000000;
	
	static int ROWS;
	static int COLS;
	static Position king;
	static List<Position> knights;
	
	public static void main(String[] args) throws Exception {
		Input in = fromFile("Ccamelot.in");
		ROWS = in.nextInt();
		COLS = in.nextInt();
		
		king = new Position(
				toIndex(in.nextString().charAt(0)),
				in.nextInt() - 1
		);
		
		knights = new ArrayList<Position>();
		
		String s = in.nextString();
		while (s != null) {
			int col = toIndex(s.charAt(0));
			int row = in.nextInt() - 1;
			knights.add(new Position(col, row));
			
			s = in.nextString();
		}
		
		int result = solve();
		
		PrintWriter pw = new PrintWriter(new File("camelot.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static int solve() {
		// [row1][col1][row2][col2] = minimum distance for knight from 1 to 2
		long start = System.currentTimeMillis();
		int[][][][] precompute = getPrecompute();
		long total = System.currentTimeMillis() - start;
		System.out.println("pretime: " + total + " ms");
		
		int result = Integer.MAX_VALUE;
		
		for (int destRow = 0; destRow < ROWS; destRow++) {
			for (int destCol = 0; destCol < COLS; destCol++) {
				// total moves without picking king up
				int movesNoPickUp = moveNoPickUp(precompute, destRow, destCol);
				result = Math.min(movesNoPickUp, result);
				
				// with pickup
				int movesWithPickUp = moveWithPickUp(precompute, movesNoPickUp, destRow, destCol);
				result = Math.min(movesWithPickUp, result);
			}
		}
		
		return result;
	}

	static int moveNoPickUp(int[][][][] precompute, int destRow, int destCol) {
		int total = getDistKing(king.row, king.col, destRow, destCol);
		
		for (Position knight : knights) {
			int moves = precompute[destRow][destCol][knight.row][knight.col];
			if (moves == MAX)
				return MAX;
			
			total += moves;
		}
		return total;
	}

	static int moveWithPickUp(int[][][][] precompute, int distNoPickup, int destRow, int destCol) {
		int result = distNoPickup;
		int distNoKing = distNoPickup - getDistKing(destRow, destCol, king.row, king.col);
		
		for (int deltaRow = -2; deltaRow <= 2; deltaRow++) {
			for (int deltaCol = -2; deltaCol <= 2; deltaCol++) {
				int pickRow = king.row + deltaRow;
				int pickCol = king.col + deltaCol;
				
				boolean validRow = 0 <= pickRow && pickRow < ROWS;
				boolean validCol = 0 <= pickCol && pickCol < COLS;

				if (validRow && validCol) {
					for (Position knight : knights) {
						int knightDist = precompute[pickRow][pickCol][knight.row][knight.col];

						if (knightDist != MAX) {
							int localResult = distNoKing;
							localResult -= precompute[destRow][destCol][knight.row][knight.col];
							localResult += getDistKing(king.row, king.col, pickRow, pickCol) + knightDist;
							localResult += precompute[destRow][destCol][pickRow][pickCol];

							if (localResult < result)
								result = localResult;
						}
					}
				}
			}
		}
		
		return result;
	}
	
	static int getDistKing(int row1, int col1, int row2, int col2) {
		int deltaRow = Math.abs(row1 - row2);
		int deltaCol = Math.abs(col1 - col2);
		return deltaRow + deltaCol - Math.min(deltaRow, deltaCol);
	}
	
	static int[][][][] getPrecompute() {
		int[][][][] precompute = new int[ROWS][COLS][ROWS][COLS];

		for (int row1 = 0; row1 < ROWS; row1++) {
			for (int col1 = 0; col1 < COLS; col1++) {
				for (int row2 = 0; row2 < ROWS; row2++) {
					for (int col2 = 0; col2 < COLS; col2++) {
						precompute[row1][col1][row2][col2] = MAX;
					}
				}
			}
		}

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				bfs(precompute, row, col);
			}
		}
		
		return precompute;
	}
	
	static final int[] deltaRow = {1, 2, 2, 1, -1, -2, -2, -1};
	static final int[] deltaCol = {-2, -1, 1, 2, 2, 1, -1, -2};
	
	static void bfs(int[][][][] precompute, int row, int col) {
		int[][] distMat = new int[ROWS][COLS];
		for (int[] a : distMat)
			Arrays.fill(a, -1);
		
		Position start = new Position(col, row);
		Queue<Position> queue = new ArrayDeque<Position>();
		
		queue.add(start);
		distMat[start.row][start.col] = 0;
		
		while (!queue.isEmpty()) {
			Position pos = queue.poll();
			int dist = distMat[pos.row][pos.col];
			precompute[row][col][pos.row][pos.col] = dist;
			
			for (int i = 0; i < deltaRow.length; i++) {
				Position nextPos = new Position(deltaCol[i] + pos.col, deltaRow[i] + pos.row);
				boolean validRow = 0 <= nextPos.row && nextPos.row < ROWS;
				boolean validCol = 0 <= nextPos.col && nextPos.col < COLS;
				
				if (validRow && validCol && distMat[nextPos.row][nextPos.col] == -1) {
					queue.add(nextPos);
					distMat[nextPos.row][nextPos.col] = dist + 1;
				}
			}
		}
	}
	
	static int toIndex(char c) {
		return c - 'A';
	}
	
	static char toChar(int index) {
		return (char) (index + 'A');
	}
	
	static class Position {
		final int row;
		final int col;
		
		public Position(int col, int row) {
			this.row = row;
			this.col = col;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Position) {
				Position p = (Position) obj;
				return p.row == row && p.col == col;
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return row * 31 + col;
		}

		@Override
		public String toString() {
			return toChar(col) + "" + (row + 1);
		}
	}
	
	// Helper methods, classes
	static List<String> splitString(String text, int splitAfter) {
		List<String> strings = new ArrayList<String>();
		int index = 0;

		while (index < text.length()) {
			strings.add(text.substring(index, Math.min(index + splitAfter, text.length())));
			index += splitAfter;
		}

		return strings;
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
