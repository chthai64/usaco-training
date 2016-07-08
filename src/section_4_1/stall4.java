/*
ID: conchim1
LANG: JAVA
TASK: stall4
 */

package section_4_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class stall4 {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("stall4.in");
		
		int cows = in.nextInt();
		int stalls = in.nextInt();
		
		boolean[][] graph = new boolean[cows][stalls];
		
		for (int cow = 0; cow < cows; cow++) {
			int S = in.nextInt();
			
			for (int i = 0; i < S; i++) {
				int stall = in.nextInt() - 1;
				graph[cow][stall] = true;
			}
		}
		
		BipartiteMatch bpm = new BipartiteMatch(graph);
		int result = bpm.maxMatch();
		
		PrintWriter pw = new PrintWriter(new File("stall4.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static class BipartiteMatch {
		boolean[][] graph;
		
		/**
		 * @param graph graph[row][col]
		 */
		public BipartiteMatch(boolean[][] graph) {
			this.graph = graph;
		}
		
		public int maxMatch() {
			if (graph.length == 0 || graph[0].length == 0)
				return 0;
			
			int result = 0;
			
			int[] matchRow = new int[graph[0].length];
			Arrays.fill(matchRow, -1);
			
			for (int row = 0; row < graph.length; row++) {
				boolean[] seen = new boolean[graph[0].length];
				
				if (dfs(matchRow, row, seen)) {
					result++;
				}
			}
			
			return result;
		}
		
		private boolean dfs(int[] matchRow, int row, boolean[] seen) {
			for (int col = 0; col < graph[0].length; col++) {
				if (graph[row][col]  && !seen[col]) {
					seen[col] = true;
					
					if (matchRow[col] == -1 || dfs(matchRow, matchRow[col], seen)) {
						matchRow[col] = row;
						return true;
					}
				}
			}
			
			return false;
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
