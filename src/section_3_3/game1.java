/*
ID: conchim1
LANG: JAVA
TASK: game1
 */

package section_3_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class game1 {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("game1.in");

		int N = in.nextInt();
		int[] a = new int[N];

		for (int i = 0; i < a.length; i++) {
			a[i] = in.nextInt();
		}

		String result = solve(a);
		
		PrintWriter pw = new PrintWriter(new File("game1.out"));
		pw.println(result);
		pw.close();
		in.close();
		
		System.out.println(result);
	}

	static String solve(int[] a) {
		// DP[leftIndex][rightIndex][state]
		// state = 1 if it's the current player's turn, 0 if it's not.
		int[][][] DP = new int[a.length][a.length][2];  
		
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				DP[i][j][0] = DP[i][j][1] = Integer.MIN_VALUE;
			}
		}
		
		int sum = 0;
		for (int i : a)
			sum += i;
		
		int player1 = getMaxPoint(a, DP, 0, a.length - 1, 1);
		return player1 + " " + (sum - player1);
	}
	
	static int getMaxPoint(int[] a, int[][][] DP, int leftIndex, int rightIndex, int state) {
		if (leftIndex > rightIndex)
			return 0;
		
		if (leftIndex == rightIndex) {
			if (state == 1) {
				DP[leftIndex][rightIndex][state] = a[leftIndex];
				return a[leftIndex];
			} else {
				DP[leftIndex][rightIndex][state] = 0;
				return 0;
			}
		}
		
		if (DP[leftIndex][rightIndex][state] != Integer.MIN_VALUE) {
			return DP[leftIndex][rightIndex][state];
		}
		
		int pickLeft = getMaxPoint(a, DP, leftIndex + 1, rightIndex, 0) + a[leftIndex];
		int pickRight = getMaxPoint(a, DP, leftIndex, rightIndex - 1, 0) + a[rightIndex];
		
		// my turn to pick
		if (state == 1) {
			DP[leftIndex][rightIndex][state] = Math.max(pickLeft, pickRight);
			return Math.max(pickLeft, pickRight);
		} 

		// other player picks right
		if (pickLeft < pickRight) {
			DP[leftIndex][rightIndex][state] = getMaxPoint(a, DP, leftIndex, rightIndex - 1, 1);
		} 
		
		// other player picks right
		else {
			DP[leftIndex][rightIndex][state] = getMaxPoint(a, DP, leftIndex + 1, rightIndex, 1);
		}
		
		return DP[leftIndex][rightIndex][state];
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
