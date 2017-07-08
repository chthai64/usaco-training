/*
ID: conchim1
LANG: JAVA
TASK: milk6
 */

package section_4_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class milk6 {

	public static void main(String[] args) throws Exception {
		solve();
	}
	
	static void solve() throws Exception {
		Input in = fromFile("milk6.in");
		int N = in.nextInt();
		int M = in.nextInt();
		
		int[] S = new int[M];
		int[] E = new int[M];
		int[] C = new int[M];
		
		// s and e starts at 0, different from the question.
		for (int i = 0; i < M; i++) {
			S[i] = in.nextInt() - 1;
			E[i] = in.nextInt() - 1;
			C[i] = in.nextInt();
		}
		
		String ans = actualSolve(N, S, E, C);
		PrintWriter pw = new PrintWriter("milk6.out");
		pw.print(ans);
		pw.close();
	}
	
	static String actualSolve(int N, int[] S, int[] E, int[] C) {
		List<Edge> edges = getEdges(S, E, C);
		int[][] cap = new int[N][N];
		
		for (Edge edge : edges) {
			cap[edge.s][edge.e] += edge.cost;
		}
		
		int initMaxFlow = maxFlow(N, cap, edges);
		if (initMaxFlow == 0)
			return "0 0\n";
		
		int maxFlow = initMaxFlow;
		List<Integer> cut = new ArrayList<>();
		Collections.sort(edges);
		
		for (int i = 0; i < edges.size(); i++) {
			Edge edge = edges.get(i);
			if (edge.cost > maxFlow)
				continue;

			cap[edge.s][edge.e] -= edge.cost;
			int currMaxFlow = maxFlow(N, cap, edges);
			
			if (maxFlow - currMaxFlow == edge.cost) {
				cut.add(edge.id);
				maxFlow = currMaxFlow;
			} else {
				cap[edge.s][edge.e] += edge.cost;
			}
			
			if (maxFlow == 0)
				break;
		}
		
		Collections.sort(cut);
		StringBuilder sb = new StringBuilder();
		sb.append(initMaxFlow + " " + cut.size() + "\n");
		
		for (int edge : cut) {
			sb.append(edge + "\n");
		}
		
		return sb.toString();
	}
	
	static int maxFlow(int N, int[][] cap, List<Edge> edges) {
		int[][] residual = deepCopy(cap);
		int pathCap = findPath(N, cap, edges, residual);
		int total = 0;
		
		while (pathCap != 0) {
			total += pathCap;
			pathCap = findPath(N, cap, edges, residual);
		}
		
		return total;
	}
	
	static int findPath(int N, int[][] cap, List<Edge> edges, int[][] residual) {
		Queue<Integer> queue = new ArrayDeque<>();
		int[] prevs = new int[N];
		
		Arrays.fill(prevs, -1);
		queue.add(0);
		
		while (!queue.isEmpty()) {
			int curr = queue.poll();
			if (curr == N-1) {
				break;
			}
			
			for (int next = 0; next < N; next++) {
				if (residual[curr][next] > 0 && prevs[next] == -1) {
					prevs[next] = curr;
					queue.add(next);
				}
			}
		}
		
		if (prevs[N-1] == -1)
			return 0;
		
		// compute the path capacity
		int curr = N-1;
		int pathCap = Integer.MAX_VALUE;
		
		while (curr > 0) {
			int prev = prevs[curr];
			pathCap = Math.min(pathCap, residual[prev][curr]);
			curr = prev;
		}
		
		// update the residual graph
		curr = N-1;
		while (curr > 0) {
			int prev = prevs[curr];
			residual[prev][curr] -= pathCap;
			residual[curr][prev] += pathCap;
			curr = prev;
		}
		
		return pathCap;
	}
	
	static int[][] deepCopy(int[][] a) {
		int[][] b = new int[a.length][a.length];
		
		for (int i = 0; i < a.length; i++) {
			b[i] = a[i].clone();
		}
		
		return b;
	}
	
	static List<Edge> getEdges(int[] S, int[] E, int[] C) {
		List<Edge> list = new ArrayList<>();
		
		for (int i = 0; i < E.length; i++) {
			list.add(new Edge(S[i], E[i], C[i], i+1));
		}
		
		return list;
	}
	
	static class Edge implements Comparable<Edge> {
		int s, e, cost, id;
		
		public Edge(int s, int e, int cost, int id) {
			this.s = s;
			this.e = e;
			this.cost = cost;
			this.id = id;
		}
		
		@Override
		public int compareTo(Edge edge) {
			int cmp = edge.cost - cost;
			if (cmp != 0)
				return cmp;
			
			return id - edge.id;
		}
			
		@Override
		public String toString() {
			return "[s=" + s + ", e=" + e + ", cost=" + cost + "]";
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
