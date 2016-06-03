/*
ID: conchim1
LANG: JAVA
TASK: comehome
 */

package section_2_4;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class comehome {
	static final int TOTAL_NODES = charToIndex('Z') + 1;
	
	
	public static void main(String[] args) throws Exception {
		Input in = fromFile("comehome.in");

		int[][]	graph = new int[TOTAL_NODES][TOTAL_NODES];
		
		for (int[] a : graph) {
			Arrays.fill(a, Integer.MAX_VALUE);
		}
		
		int P = in.nextInt();
		for (int i = 0; i < P; i++) {
			String[] split = in.nextLine().split(" ");
			int a = charToIndex(split[0].charAt(0));
			int b = charToIndex(split[1].charAt(0));
			int weight = Integer.parseInt(split[2]);
			
			graph[b][a] = graph[a][b] = Math.min(graph[a][b], weight);
		}
		
		String result = solve(graph);
		
		PrintWriter pw = new PrintWriter(new File("comehome.out"));
		pw.println(result);
		pw.close();
		in.close();
		
		System.out.println(result);
	}
	
	static String solve(int[][] graph) {
		for (int k = 0; k < TOTAL_NODES; k++) {
			for (int i = 0; i < TOTAL_NODES; i++) {
				for (int j = 0; j < TOTAL_NODES; j++) {
					if (graph[i][k] != Integer.MAX_VALUE && graph[k][j] != Integer.MAX_VALUE) {
						graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
					}
				}
			}
		}

		int min = Integer.MAX_VALUE;
		char cow = 'a';
		
		for (int index = charToIndex('A'); index < charToIndex('Z'); index++) {
			int dist = graph[index][charToIndex('Z')];
			if (dist < min) {
				min = dist;
				cow = indexToChar(index);
			}
		}
		
		return cow + " " + min;
	}
	
	static char indexToChar(int index) {
		if (index <= 'z' - 'a') {
			return (char) (index + 'a');
		}
		
		return (char) (index + 'A' - 'z' + 'a' - 1);
	}
	
	static int charToIndex(char c) {
		if ('a' <= c && c <= 'z') {
			return c - 'a';
		}
		
		return c - 'A' + 'z' - 'a' + 1;
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
