/*
ID: conchim1
LANG: JAVA
TASK: skidesign
 */

package section_1_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class skidesign {
	private static final int MAX_DIFF = 17;

	public static void main(String[] args) throws Exception {
		Input in = fromFile("skidesign.in");

		int n = in.nextInt();
		int[] heights = new int[n];
		for (int i = 0; i < n; i++) {
			heights[i] = in.nextInt();
		}
		
		int result = solve(heights);
		
		PrintWriter pw = new PrintWriter(new File("skidesign.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static int solve(int[] heights) {
		if (heights.length < 2)
			return 0;
		
		Arrays.sort(heights);
		int lowest = heights[0];
		int highest = heights[heights.length - 1];
		int diff = highest - lowest;
		int maxReduce = Math.max(0, diff - MAX_DIFF);
		
		int minCost = Integer.MAX_VALUE;
		
		for (int reduce = 0; reduce <= maxReduce; reduce++) {
			int increase = maxReduce - reduce;
			int cost = getCostReduce(heights, reduce) + getCostIncrease(heights, increase);
			minCost = Math.min(cost, minCost);
		}
		
		return minCost;
	}
	
	static int getCostReduce(int[] heights, int reduce) {
		int cost = 0;
		int targetHeight = heights[heights.length - 1] - reduce;
		
		for (int i = heights.length - 1; i >= 0; i--) {
			if (heights[i] > targetHeight) {
				cost += (heights[i] - targetHeight) * (heights[i] - targetHeight);
			} else {
				break;
			}
		}
		
		return cost;
	}
	
	static int getCostIncrease(int[] heights, int increase) {
		int cost = 0;
		int targetHeight = heights[0] + increase;
		
		for (int h : heights) {
			if (h < targetHeight) {
				cost += (targetHeight - h) * (targetHeight - h);
			} else {
				break;
			}
		}
		
		return cost;
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
