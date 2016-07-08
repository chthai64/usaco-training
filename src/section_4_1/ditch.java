/*
ID: conchim1
LANG: JAVA
TASK: ditch
 */

package section_4_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

// typical max flow problem.
public class ditch {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("ditch.in");

		int N = in.nextInt();
		int M = in.nextInt();
		int[][] capacity = new int[M + 1][M + 1];

		for (int i = 0; i < N; i++) {
			int from = in.nextInt();
			int to = in.nextInt();
			int rate = in.nextInt();

			capacity[from][to] += rate;
		}

		int result = solve(M, capacity);

		PrintWriter pw = new PrintWriter(new File("ditch.out"));
		pw.println(result);
		pw.close();
		in.close();
	}

	static int solve(int M, int[][] capacity) {
		MaxFlow maxFlow = new MaxFlow(capacity, 1, M);
		return maxFlow.maxFlow();
	}

	static class MaxFlow {
		int[][] capacity;
		int source, sink;

		public MaxFlow(int[][] capacity, int source, int sink) {
			this.capacity = capacity;
			this.source = source;
			this.sink = sink;
		}

		public int maxFlow() {
			int result = 0;

			while (true) {
				// find the capacity of the augmenting path
				int pathCapacity = findPathBfs();

				if (pathCapacity > 0) {
					result += pathCapacity;
				} else {
					return result;
				}
			}
		}

		private int findPathBfs() {
			Queue<Integer> queue = new LinkedList<Integer>();
			boolean[] visited = new boolean[capacity.length];

			int[] from = new int[capacity.length];
			Arrays.fill(from, -1);

			queue.add(source);
			visited[source] = true;

			while (!queue.isEmpty()) {
				int where = queue.poll();

				if (where == sink) {
					break;
				}

				for (int next = 0; next < capacity.length; next++) {
					if (next != where && !visited[next] && capacity[where][next] > 0) {
						queue.add(next);
						visited[next] = true;
						from[next] = where;
					}
				}
			}

			// no path found
			if (from[sink] == -1)
				return 0;

			// find capacity of the path
			int pathCapacity = Integer.MAX_VALUE;
			int where = sink;

			while (from[where] != -1) {
				int prev = from[where];
				pathCapacity = Math.min(pathCapacity, capacity[prev][where]);
				where = prev;
			}

			// update the residual network
			where = sink;
			while (from[where] != -1) {
				int prev = from[where];
				capacity[prev][where] -= pathCapacity;
				capacity[where][prev] += pathCapacity;
				where = prev;
			}

			return pathCapacity;
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
