/*
ID: conchim1
LANG: JAVA
TASK: job
 */

package section_4_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class job {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("/Users/idgmi_dc/Desktop/job.in");
		
		int N = in.nextInt();
		int M1 = in.nextInt();
		int M2 = in.nextInt();
		
		int[] A = new int[M1];
		for (int i = 0; i < A.length; i++) {
			A[i] = in.nextInt();
		}
		
		int[] B = new int[M2];
		for (int i = 0; i < B.length; i++) {
			B[i] = in.nextInt();
		}
		
		int result = solve(N, A, B);
		
		System.out.println(result);
	}
	
	static int solve(int N, int[] A, int[] B) {
		// compute first layer max rate
		int[][] graph1 = new int[2 + N + A.length][2 + N + A.length];
		
		// match jobs to machines A and source to jobs.
		for (int job = 0; job < N; job++) {
			for (int machineA = 0; machineA < A.length; machineA++) {
				graph1[job + 1][N + 1 + machineA] = 1;
			}
			
			graph1[0][job + 1] = 1;
		}
		
		// match machines A to immediate sink
		for (int machineA = 0; machineA < A.length; machineA++) {
			graph1[1 + N + machineA][graph1.length - 1] = A[machineA];
		}
		
		int rate1 = new MaxFlow(graph1, 0, graph1.length - 1).maxFlow();
		System.out.println("rate1: " + rate1);
		
		return 0;
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
