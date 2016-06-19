/*
ID: conchim1
LANG: JAVA
TASK: range
 */

package section_3_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class range {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("range.in");
		
		int n = in.nextInt();
		boolean[][] mat = new boolean[n][n];
		
		for (int row = 0; row < n; row++) {
			String line = in.nextLine();
			for (int col = 0; col < n; col++) {
				mat[row][col] = line.charAt(col) == '1';
			}
		}
		
		String result = solve(mat);
		
		PrintWriter pw = new PrintWriter(new File("range.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	
	// Dynamic programming
	static String solve(boolean[][] mat) {
		final int MAX_SIZE = mat.length + 1;
		int[] sizeCount = new int[MAX_SIZE];
		
		// we could use two 2D-array instead of one huge 3D-array.
		boolean[][][] DP = new boolean[MAX_SIZE][mat.length][mat.length]; // [size][rowStart][colStart]

		// init DP with size = 1
		for (int row = 0; row < mat.length; row++) {
			for (int col = 0; col < mat.length; col++) {
				DP[1][row][col] = mat[row][col];
			}
		}
		
		for (int size = 2; size < sizeCount.length; size++) {
			for (int row = 0; row + size - 1 < mat.length; row++) {
				for (int col = 0; col + size - 1 < mat.length; col++) {
					if (possible(mat, DP, size, row, col)) {
						sizeCount[size]++;
					}
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for (int size = 2; size < sizeCount.length; size++) {
			if (sizeCount[size] > 0) {
				if (sb.length() > 0)
					sb.append("\n");
				sb.append(size + " " + sizeCount[size]);
			}
		}
		
		return sb.toString();
	}
	
	static boolean possible(boolean[][] mat, boolean[][][] DP, int size, int rowStart, int colStart) {
		if (!DP[size - 1][rowStart][colStart]) {
			return false;
		}

		for (int deltaRow = 0; deltaRow <= 1; deltaRow++) {
			for (int deltaCol = 0; deltaCol <= 1; deltaCol++) {
				if (!DP[size - 1][rowStart + deltaRow][colStart + deltaCol])
					return false;
			}
		}
		
		DP[size][rowStart][colStart] = true;
		return true;
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
