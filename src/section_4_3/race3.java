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
	private static final int MAX_POINTS = 50;
	
	public static void main(String[] args) throws Exception {
		 solve(getGraph());
	}
	
	static Map<Integer, Set<Integer>> getGraph() throws Exception {
		Input in = fromFile("race3.in");
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
	
	static void solve(Map<Integer, Set<Integer>> graph) throws FileNotFoundException {
		List<Integer> unavoids = getUnavoidablePoints(graph);
		List<Integer> splits = getSplits(graph, unavoids);
		
		PrintWriter pw = new PrintWriter("race3.out");
		printList(unavoids, pw);
		printList(splits, pw);
		pw.close();
	}
	
	static void printList(List<Integer> list, PrintWriter pw) {
		pw.print(list.size());
		for (int i = 0; i < list.size(); i++) {
			pw.print(" " + list.get(i));
		}
		pw.println();
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
	
	static List<Integer> getSplits(Map<Integer, Set<Integer>> graph, List<Integer> unavoids) {
		List<Integer> splits = new ArrayList<>();
		
		for (int p : unavoids) {
			if (isSplitPoint(graph, p)) {
				splits.add(p);
			}
		}
		
		Collections.sort(splits);
		return splits;
	}
	
	static boolean isUnavoidable(Map<Integer, Set<Integer>> graph, int p) {
		int N = graph.keySet().size() - 1;
		if (p == 0 || p == N)
			return false;
		
		Queue<Integer> queue = new ArrayDeque<>();
		boolean[] visited = new boolean[MAX_POINTS];
		
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
	
	static boolean isSplitPoint(Map<Integer, Set<Integer>> graph, int p) {
		int N = graph.keySet().size() - 1;
		if (p == 0 || p == N)
			return false;
		
		boolean[] visited1 = new boolean[MAX_POINTS];
		boolean[] visited2 = new boolean[MAX_POINTS];
		
		dfs(graph, visited1, 0, p);
		dfs(graph, visited2, p, -1);
		
		for (int i = 0; i < MAX_POINTS; i++) {
			if (visited1[i] && visited2[i])
				return false;
		}
		
		return true;
	}
	
	static void dfs(Map<Integer, Set<Integer>> graph, boolean[] visited, int node, int exclude) {
		if (visited[node])
			return;
		
		visited[node] = true;
		for (int next : graph.get(node)) {
			if (next != exclude) {
				dfs(graph, visited, next, exclude);
			}
		}
	}
	
	static void debugPrint(List<Integer> list) {
		System.out.print(list.size());
		for (int i = 0; i < list.size(); i++) {
			System.out.print(" " + list.get(i));
		}
		System.out.println();
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
