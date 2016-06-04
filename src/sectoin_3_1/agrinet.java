/*
ID: conchim1
LANG: JAVA
TASK: agrinet
 */

package sectoin_3_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class agrinet {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("C:/Users/Chau Thai/Desktop/agrinet.in");
		int N = in.nextInt();
		int[][] mat = new int[N][N];
		
		for (int row = 0; row < N; row++) {
			for (int col = 0; col < N; col++) {
					mat[row][col] = in.nextInt();
			}
		}
		
		int result = solve(mat, N);
		
		PrintWriter pw = new PrintWriter(new File("agrinet.out"));
		pw.println(result);
		pw.close();
		in.close();
		
		System.out.println(result);
	}
	
	// no priority queue
	static int solve(int[][] mat, int N) {
		boolean[] inTree = new boolean[N];
		int[] distance = new int[N];
		int[] closestToTree = new int[N];
		
		int treeSize = 1;
		int span = 0;
		
		Arrays.fill(distance, Integer.MAX_VALUE);
		inTree[0] = true;
		
		for (int i = 1; i < N; i++) {
			closestToTree[i] = 0;
			distance[i] = mat[0][i];
		}
		
		while (treeSize < N) {
			// find closest
			int vertex = 0;
			int dist = Integer.MAX_VALUE;
			
			for (int i = 0; i < N; i++) {
				if (!inTree[i] && distance[i] < dist) {
					dist = distance[i];
					vertex = i;
				}
			}
			
			inTree[vertex] = true;
			treeSize++;
			span += dist;
			
			// update distance
			for (int i = 0; i < N; i++) {
				if (!inTree[i] && mat[vertex][i] < distance[i]) {
					distance[i] = mat[vertex][i];
					closestToTree[i] = vertex;
				}
			}
		}
		
		return span;
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
