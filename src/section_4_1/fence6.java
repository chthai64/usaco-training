/*
ID: conchim1
LANG: JAVA
TASK: fence6
 */

package section_4_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class fence6 {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("fence6.in");
		
		int N = in.nextInt();
		Line[] lines = new Line[N + 1];
		
		for (int n = 0; n < N; n++) {
			int id = in.nextInt();
			int length = in.nextInt();
			int n1 = in.nextInt();
			int n2 = in.nextInt();
			
			Set<Integer> set1 = new HashSet<Integer>();
			for (int i = 0; i < n1; i++) {
				set1.add(in.nextInt());
			}
			
			Set<Integer> set2 = new HashSet<Integer>();
			for (int i = 0; i < n2; i++) {
				set2.add(in.nextInt());
			}
			
			Line line = new Line(id, length, set1, set2);
			lines[id] = line;
		}
		
		int result = solve(lines);
		
		PrintWriter pw = new PrintWriter(new File("fence6.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static int solve(Line[] lines) {
		int minPerimeter = Integer.MAX_VALUE;
		
		for (int start = 1; start < lines.length; start++) {
			boolean[][] visited = new boolean[lines.length][2];
			
			for (int direction = 0; direction <= 1; direction++) {
				visited[start][direction] = true;
				minPerimeter = Math.min(minPerimeter, 
						dfs(lines, visited, start, minPerimeter, lines[start].length, start));
				visited[start][direction] = false;
			}
		}
		
		return minPerimeter;
	}
	
	static int dfs(Line[] lines, boolean[][] visited, int start, int max, int value, int line) {
		if (value >= max)
			return max;
		
		if (line == start && visited[line][0] && visited[line][1]) {
			return value;
		}
		
		// dir: one of the ends of the line, zero or one.
		for (int dir = 0; dir <= 1; dir++) {
			boolean go = (line != start)? !visited[line][dir] : visited[line][dir];
			
			if (go) {
				visited[line][dir] = true;
				Set<Integer> neighbors = lines[line].sets.get(dir);

				for (int neighbor : neighbors) {
					int neighborDir = lines[neighbor].sets.get(0).contains(line)? 0 : 1;
					if (!visited[neighbor][neighborDir]) {
						visited[neighbor][neighborDir] = true;
						
						int length = (neighbor != start)? value + lines[neighbor].length : value;
						int localResult = dfs(lines, visited, start, max, length, neighbor);
						max = Math.min(max, localResult);
						
						visited[neighbor][neighborDir] = false;
					}
				}
				
				visited[line][dir] = false;
			}
		}
		
		return max;
	}
	
	static class Line {
		int id;
		int length;
		Set<Integer> set1;
		Set<Integer> set2;
		List<Set<Integer>> sets = new ArrayList<Set<Integer>>();
		
		public Line(int id, int length, Set<Integer> set1, Set<Integer> set2) {
			this.id = id;
			this.length = length;
			this.set1 = set1;
			this.set2 = set2;
			sets.add(set1);
			sets.add(set2);
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
