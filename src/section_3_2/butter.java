/*
ID: conchim1
LANG: JAVA
TASK: butter
 */

package section_3_2;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class butter {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("butter.in");
		
		int N = in.nextInt();
		int P = in.nextInt();
		int C = in.nextInt();
		
		int[] cowCount = new int[P];
		for (int i = 0; i < N; i++) {
			int position = in.nextInt();
			cowCount[position - 1]++;
		}
		
		Map<Integer, List<Node>> mapAdj = new HashMap<Integer, List<Node>>();
		for (int i = 0; i < C; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			int dist = in.nextInt();
			
			List<Node> listA = mapAdj.containsKey(a)? mapAdj.get(a) : new ArrayList<Node>();
			listA.add(new Node(b, dist));
			mapAdj.put(a, listA);
			
			List<Node> listB = mapAdj.containsKey(b)? mapAdj.get(b) : new ArrayList<Node>();
			listB.add(new Node(a, dist));
			mapAdj.put(b, listB);
		}
		
		int result = solve(P, cowCount, mapAdj);
		
		PrintWriter pw = new PrintWriter(new File("butter.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static int solve(int totalNodes, int[] cowCount, Map<Integer, List<Node>> mapAdj) {
		int min = Integer.MAX_VALUE;
		
		for (int start = 0; start < totalNodes; start++) {
			min = Math.min(min, totalDist(totalNodes, cowCount, mapAdj, start));
		}
		
		return min;
	}
	
	static int totalDist(int totalNodes, int[] cowCount, Map<Integer, List<Node>> mapAdj, int start) {
		PriorityQueue<Node> queue = new PriorityQueue<Node>(totalNodes, null);
		boolean[] visited = new boolean[totalNodes];
	
		int[] distances = new int[totalNodes];
		Arrays.fill(distances, Integer.MAX_VALUE);
		distances[start] = 0;
		
		for (int node = 0; node < totalNodes; node++) {
			if (node == start) {
				queue.add(new Node(node, 0));
			} else {
				queue.add(new Node(node, Integer.MAX_VALUE));
			}
		}
		
		int total = 0;
		
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			if (visited[node.index])
				continue;
			
			visited[node.index] = true;
			total += cowCount[node.index] * node.distance;
			
			List<Node> neighbors = mapAdj.get(node.index);
			if (neighbors != null) {
				for (Node neighbor : neighbors) {
					if (!visited[neighbor.index]) {
						if (node.distance + neighbor.distance < distances[neighbor.index]) {
							distances[neighbor.index] = node.distance + neighbor.distance;
							queue.add(new Node(neighbor.index, distances[neighbor.index]));
						}
					}
				}
			}
		}
		
		return total;
	}
	
	static class Node implements Comparable<Node> {
		int index;
		int distance;
		
		public Node(int node, int distance) {
			this.index = node;
			this.distance = distance;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Node) {
				Node node = (Node) obj;
				return index == node.index;
			}
			return false;
		}
		
		@Override
		public int compareTo(Node node) {
			if (distance > node.distance)
				return 1;
			if (distance < node.distance)
				return -1;
			return 0;
		}

		@Override
		public String toString() {
			return "Node [index=" + index + ", distance=" + distance + "]";
		}
	}
	
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
