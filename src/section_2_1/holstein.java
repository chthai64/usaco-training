/*
ID: conchim1
LANG: JAVA
TASK: holstein
 */

package section_2_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class holstein {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("C:\\Users\\Chau Thai\\Desktop\\holstein.in");
		
		int V = in.nextInt();
		int[] req = new int[V];
		for (int i = 0; i < V; i++) {
			req[i] = in.nextInt();
		}
		
		int G = in.nextInt();
		int[][] feedTypes = new int[G][V];
		
		for (int feedIndex = 0; feedIndex < G; feedIndex++) {
			for (int type = 0; type < V; type++) {
				feedTypes[feedIndex][type] = in.nextInt();
			}
		}
		
		String result = solve(req, feedTypes, V, G);
		System.out.println(result);
		
//		PrintWriter pw = new PrintWriter(new File("holstein.out"));
//		pw.println(result);
//		pw.close();
//		in.close();
	}
	
	static String solve(int[] req, int[][] feedTypes, int V, int G) {
		List<Integer> result = new ArrayList<Integer>();
		int[] needs = req.clone();
		boolean[] taken = new boolean[G];
		
		dfs(result, feedTypes, taken, needs);
		
		Collections.sort(result);
		StringBuilder sb = new StringBuilder();
		sb.append(result.size() + " ");
		
		for (int i = 0; i < result.size(); i++) {
			sb.append(result.get(i) + 1);
			if (i != result.size() - 1)
				sb.append(' ');
		}
		
		return sb.toString();
	}
	
	static int dfs(List<Integer> result, int[][] feedTypes, boolean[] taken, int[] needs) {
		if (done(needs)) {
			return 0;
		}
		
		int minCount = Integer.MAX_VALUE;
		int minFeed = 0;
		
		for (int feed = 0; feed < feedTypes.length; feed++) {
			if (taken[feed])
				continue;
			
			taken[feed] = true;
			
			int count = dfs(result, feedTypes, taken, needs);
			if (count < minCount) {
				minCount = count;
				minFeed = feed;
			}
			
			taken[feed] = false;
		}
		
		
	}
	
	static boolean done(int[] needs) {
		for (int i : needs) {
			if (i != 0)
				return false;
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
				stt = new StringTokenizer(nextLine());
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
