/*
ID: conchim1
LANG: JAVA
TASK: cowtour
 */

package section_2_4;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class cowtour {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("cowtour.in");
		int N = in.nextInt();
		
		Vertex[] V = new Vertex[N];
		for (int v = 0; v < V.length; v++) {
			int x = in.nextInt();
			int y = in.nextInt();
			V[v] = new Vertex(v,x,y);
		}
		
		boolean[][] mat = new boolean[N][N];
		for (int x = 0; x < N; x++) {
			String line = in.nextLine();
			for (int y = 0; y < line.length(); y++) {
				mat[x][y] = line.charAt(y) == '1';
			}
		}
		
		String result = solve(mat, V, N);
		
		PrintWriter pw = new PrintWriter(new File("cowtour.out"));
		pw.println(result);
		pw.close();
		in.close();
	}

	static String solve(boolean[][] mat, Vertex[] V, int N) {
		int totalGroups = separateGroup(mat, V, N);
		
		double[][] D = buildDistances(mat, V, N);
		double[] maxTo = new double[N];
		double[] diameters = new double[totalGroups];
		
		for (int a = 0; a < N; a++) {
			for (int b = 0; b < N; b++) {
				if (V[a].group == V[b].group) {
					double dist = D[a][b];
					maxTo[a] = Math.max(maxTo[a], dist);
					diameters[V[a].group] = Math.max(diameters[V[a].group], dist);
				}
			}
		}
		
		double min = Double.POSITIVE_INFINITY;
		
		for (int a = 0; a < N; a++) {
			for (int b = 0; b < N; b++) {
				if (V[a].group != V[b].group) {
					
					double dist = Math.sqrt(Math.pow(V[a].x - V[b].x, 2) + Math.pow(V[a].y - V[b].y, 2));
					dist += maxTo[a] + maxTo[b];
					dist = Math.max(dist, Math.max(diameters[V[a].group], diameters[V[b].group]));
					min = Math.min(dist, min);
				}
			}
		}
		
		return String.format("%.6f", min);
	}
	
	static double[][] buildDistances(boolean[][] mat, Vertex[] V, int N) {
		double[][] D = new double[N][N];
		
		for (double[] a : D) {
			Arrays.fill(a, Double.POSITIVE_INFINITY);
		}
		
		for (int v1 = 0; v1 < N; v1++) {
			for (int v2 = 0; v2 < N; v2++) {
				if (v1 == v2) {
					D[v1][v2] = 0;
					continue;
				}
				
				if (mat[v1][v2]) {
					D[v1][v2] = Math.sqrt(Math.pow(V[v1].x - V[v2].x, 2) + Math.pow(V[v1].y - V[v2].y, 2));
				}
			}
		}
		
		for (int k = 0; k < N; k++) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (i != j) {
						D[i][j] = Math.min(D[i][j], D[i][k] + D[k][j]);
					}
				}
			}
		}
		
		return D;
	}
	
	static int separateGroup(boolean[][] mat, Vertex[] V, int N) {
		boolean[] visited = new boolean[V.length];
		int groupIndex = 0;

		for (int start = 0; start < N; start++) {
			if (visited[start])
				continue;
			
			Queue<Integer> queue = new LinkedList<Integer>();
			queue.add(start);
			visited[start] = true;

			while (!queue.isEmpty()) {
				int vertex = queue.poll();
				V[vertex].group = groupIndex;

				for (int neighbor = 0; neighbor < N; neighbor++) {
					if (mat[vertex][neighbor] && !visited[neighbor]) {
						visited[neighbor] = true;
						queue.add(neighbor);
					}
				}
			}
			
			groupIndex++;
		}
		
		return groupIndex;
	}
	
	static class Vertex extends Point {
		int index;
		int group = 0;
		
		public Vertex(int index, int x, int y) {
			super(x, y);
			this.index = index;
		}

		@Override
		public String toString() {
			return "[i=" + index + ", g=" + group + ", (" + x + ", " + y + ")]";
		}
	}
	
	static class Point {
		int x, y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Point) {
				Point p = (Point) obj;
				return p.x == x && p.y == y;
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return Arrays.hashCode(new int[] {x,y});
		}
		
		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
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
