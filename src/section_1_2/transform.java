/*
ID: conchim1
LANG: JAVA
TASK: transform
 */

package section_1_2;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class transform {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("transform.in");
		int n = in.nextInt();
		
		char[][] before = new char[n][n];
		for (int row = 0; row < n; row++) {
			String line = in.nextString();
			before[row] = line.toCharArray();
		}
		
		char[][] after = new char[n][n];
		for (int row = 0; row < n; row++) {
			String line = in.nextString();
			after[row] = line.toCharArray();
		}
		
		int result = solve(before, after, n);
		
		PrintWriter pw = new PrintWriter(new File("transform.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	public static int solve(char[][] before, char[][] after, int n) {
		if (Arrays.deepEquals(after, rotate90(before, n)))
			return 1;
		
		if (Arrays.deepEquals(after, rotate180(before, n)))
			return 2;
		
		if (Arrays.deepEquals(after, rotate270(before, n)))
			return 3;
		
		if (Arrays.deepEquals(after, reflect(before, n)))
			return 4;
		
		for (int code = 1; code <= 3; code++) {
			if (Arrays.deepEquals(after, combine(before, n, code)))
				return 5;
		}
		
		if (Arrays.deepEquals(before, after))
			return 6;
		
		return 7;
	}
	
	static char[][] combine(char[][] before, int n, int code) {
		char[][] after = reflect(before, n);
		if (code ==  1) 
			return rotate90(after, n);
		
		if (code == 2)
			return rotate180(after, n);
		
		return rotate270(after, n);
	}
	
	static char[][] reflect(char[][] before, int n) {
		char[][] after = new char[n][n];
		
		for (int row = 0; row < n; row++) {
			after[row] = before[row].clone();
			reverse(after[row]);
		}
		
		return after;
	}
	
	static void reverse(char[] a) {
		int left = 0;
		int right = a.length - 1;
		
		while (left < right) {
			char temp = a[left];
			a[left] = a[right];
			a[right] = temp;
			
			left++;
			right--;
		}
	}
	
	static char[][] rotate90(char[][] before, int n) {
		char[][] after = new char[n][n];
		
		for (int offset = 0; offset <= n/2; offset++) {
			// top to right
			for (int col = offset; col < n - offset; col++) {
				after[col][n - 1 - offset] = before[offset][col];
			}
			
			// right to bottom
			for (int row = offset; row < n - offset; row++) {
				after[n - 1 - offset][n - 1 - row] = before[row][n - 1 - offset];
			}
			
			// bottom to left
			for (int col = offset; col < n - offset; col++) {
				after[col][offset] = before[n - 1 - offset][col];
			}
			
			// left to top
			for (int row = offset; row < n - offset; row++) {
				after[offset][n - 1 - row] = before[row][offset];
			}
		}
		
		return after;
	}
	
	static char[][] rotate180(char[][] before, int n) {
		char[][] after = rotate90(before, n);
		after = rotate90(after, n);
		return after;
	}
	
	static char[][] rotate270(char[][] before, int n) {
		char[][] after = rotate180(before, n);
		after = rotate90(after, n);
		return after;
	}
	
	static void printMat(char[][] mat) {
		for (char[] a : mat) {
			System.out.println(new String(a));
		}
	}

	private static Input fromFile(String path) throws IOException {
		return new Input(new FileInputStream(new File(path)));
	}

	static class Input {
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
