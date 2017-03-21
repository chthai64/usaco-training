/*
ID: conchim1
LANG: JAVA
TASK: race3
 */

package section_4_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class race3 {
	private static final String PATH = "/Users/chau/Documents/workspace/usaco-training/src/section_4_3/race3.in";
	
	
	public static void main(String[] args) throws Exception {
		 solve(getGraph());
	}
	
	static Map<Integer, Set<Integer>> getGraph() throws Exception {
		Input in = fromFile(PATH);
		Map<Integer, Set<Integer>> graph = new HashMap<>();
		
		int d = in.nextInt();
		int n = 0;
		while (d != -1) {
			if (d == -2) {
				if (!graph.containsKey(n)) {
					graph.put(n, new HashSet<>());
				}
				n++;
			} else {
				Set<Integer> set = graph.containsKey(n)? graph.get(n) : new HashSet<>();
				set.add(d);
				graph.put(n, set);
			}
			
			d = in.nextInt();
		}
		
		in.close();
		return graph;
	}
	
	static void solve(Map<Integer, Set<Integer>> graph) {
		List<Integer> unavoids = getUnavoidablePoints(graph);
		
		System.out.print(unavoids.size());
		for (int i = 0; i < unavoids.size(); i++) {
			System.out.print(" " + unavoids.get(i));
		}
		System.out.println();
	}
	
	static List<Integer> getUnavoidablePoints(Map<Integer, Set<Integer>> graph) {
		List<Integer> unavoids = new ArrayList<>();
		
		for (int p : graph.keySet()) {
			if (isUnavoidable(graph, p)) {
				unavoids.add(p);
			}
		}
		
		Collections.sort(unavoids);
		return unavoids;
	}
	
	static boolean isUnavoidable(Map<Integer, Set<Integer>> graph, int p) {
		int N = graph.keySet().size() - 1;
		if (p == 0 || p == N)
			return false;
		
		Queue<Integer> queue = new ArrayDeque<>();
		boolean[] visited = new boolean[53];
		
		queue.add(0);
		visited[0] = true;
		
		while (!queue.isEmpty()) {
			int curr = queue.poll();
			if (curr == N)
				return false;
			
			for (int next : graph.get(curr)) {
				if (next != p && !visited[next]) {
					visited[next] = true;
					queue.add(next);
				}
			}
		}
		
		return true;
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
