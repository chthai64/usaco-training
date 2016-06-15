/*
ID: conchim1
LANG: JAVA
TASK: fence
 */

package section_3_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class fence {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("fence.in");
		
		int F = in.nextInt();
		int[][] graph = new int[501][501];
		
		for (int i = 0; i < F; i++) {
			int a = in.nextInt();
			int b = in.nextInt();
			
			graph[a][b]++;
			graph[b][a]++;
		}
		
		String result = solve(graph);
		
		PrintWriter pw = new PrintWriter(new File("fence.out"));
		pw.println(result);
		pw.close();
		in.close();
		
//		System.out.println(result);
		
	}
	
	static String solve(int[][] graph) {
		List<Integer> path = new ArrayList<Integer>();
		getEulerianPath(graph, path, findStart(graph));
		
		Collections.reverse(path);
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < path.size(); i++) {
			sb.append(path.get(i));
			if (i != path.size() - 1)
				sb.append("\n");
		}
		
		return sb.toString();
	}
	
	static int findStart(int[][] graph) {
		int circleCheck = hasCircle(graph);
		if (circleCheck != -1) {
			return circleCheck;
		}
		
		int start = 500;
		
		for (int row = 1; row < graph.length; row++) {
			for (int col = 1; col < graph.length; col++) {
				if (graph[row][col] > 0) {
					start = Math.min(start, row);
					start = Math.min(start, col);
				}
			}
		}
		
		return start;
	}
	
	/**
	 * @return index of odd degree node if the graph doesn't have circle, -1 otherwise.
	 */
	static int hasCircle(int[][] graph) {
		for (int node = 1; node <= 500; node++) {
			int count = 0;
			
			for (int i = 1; i <= 500; i++) {
				count += graph[node][i];
			}
			
			if (count % 2 != 0)
				return node;
		}
		
		return -1;
	}
	
	static void getEulerianPath(int[][] graph, List<Integer> path, int start) {
		for (int neighbor = 1; neighbor <= 500; neighbor++) {
			while (graph[start][neighbor] > 0) {
				graph[start][neighbor]--;
				graph[neighbor][start]--;
				
				getEulerianPath(graph, path, neighbor);
			}
		}
		
		path.add(start);
	}
	
	static List<Integer> getNeighbors(int[][] graph, int node) {
		List<Integer> list = new ArrayList<Integer>();
		
		for (int neighbor = 1; neighbor <= 500; neighbor++) {
			if (graph[node][neighbor] > 0) {
				list.add(neighbor);
			}
		}
		
		return list;
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
