/*
ID: conchim1
LANG: JAVA
TASK: frameup
 */

package section_4_3;

import java.io.*;
import java.util.*;


public class frameup {

	public static void main(String[] args) throws Exception {
		solve();
	}
	
	static void solve() throws Exception {
		Input in = fromFile("frameup.in");
		int H = in.nextInt();
		int W = in.nextInt();
		char[][] mat = new char[H][W];
		
		for (int r = 0; r < H; r++) {
			mat[r] = in.nextLine().toCharArray();
		}
		
		List<String> ans = findOrders(mat);
		for (String line : ans) {
			System.out.println(line);
		}
		
		PrintWriter pw = new PrintWriter("frameup.out");
		for (String line : ans) {
			pw.println(line);
		}
		pw.close();
	}
	
	static List<String> findOrders(char[][] mat) {
		Map<Character, Rect> rects = new HashMap<>();

		for (int row = 0; row < mat.length; row++) {
			for (int col = 0; col < mat[0].length; col++) {
				char c = mat[row][col];
				if (c == '.')
					continue;
				
				if (rects.containsKey(c)) {
					Rect rect = rects.get(c);
					rect.minCol = Math.min(rect.minCol, col);
					rect.maxCol = Math.max(rect.maxCol, col);
					rect.minRow = Math.min(rect.minRow, row);
					rect.maxRow = Math.max(rect.maxRow, row);
				} else {
					rects.put(c, new Rect(row,row,col,col));
				}
			}
		}

		boolean[][] graph = buildGraph(mat, rects);
		List<String> ans = getTopologySorts(graph, new ArrayList<>(rects.keySet()));
		Collections.sort(ans);
		return ans;
	}
	
	static List<String> getTopologySorts(boolean[][] graph, List<Character> charList) {
		List<String> ans = new ArrayList<>();
		int[] indegree = new int[26];
		for (int i = 0; i < 26; i++) {
			for (int j = 0; j < 26; j++) {
				if (graph[i][j]) {
					indegree[j]++;
				}
			}
		}
		
		boolean[] visited = new boolean[26];
		char[] temp = new char[charList.size()];
		
		dfs(graph, charList, indegree, visited, temp, ans, 0);
		
		return ans;
	}
	
	static void dfs(boolean[][] graph, List<Character> charList, int[] indegree,
			boolean[] visited, char[] temp, List<String> ans, int index) {
		
		if (index == temp.length) {
			ans.add(new String(temp));
			return;
		}
		
		for (char c : charList) {
			if (!visited[c-'A'] && indegree[c-'A'] == 0) {
				visited[c-'A'] = true;
				temp[index] = c;
				
				List<Character> adjNodes = new ArrayList<>();
				for (char adjC : charList) {
					if (graph[c-'A'][adjC-'A'] && indegree[adjC-'A'] > 0) {
						adjNodes.add(adjC);
						indegree[adjC-'A']--;
					}
				}
				
				dfs(graph, charList, indegree, visited, temp, ans, index+1);
				
				// reset
				for (char adjC : adjNodes) {
					indegree[adjC-'A']++;
				}
				visited[c-'A'] = false;
			}
		}
	}
	
	
	static boolean[][] buildGraph(char[][] mat, Map<Character, Rect> rects) {
		boolean[][] graph = new boolean[26][26];
		
		for (Map.Entry<Character, Rect> entry : rects.entrySet()) {
			char c = entry.getKey();
			Rect rect = entry.getValue();
			
			// top
			for (int col = rect.minCol; col <= rect.maxCol; col++) {
				if (mat[rect.minRow][col] != c) {
					graph[c-'A'][mat[rect.minRow][col]-'A'] = true;
				}
			}

			// bottom
			for (int col = rect.minCol; col <= rect.maxCol; col++) {
				if (mat[rect.maxRow][col] != c) {
					graph[c-'A'][mat[rect.maxRow][col]-'A'] = true;
				}
			}

			// left
			for (int row = rect.minRow; row <= rect.maxRow; row++) {
				if (mat[row][rect.minCol] != c) {
					graph[c-'A'][mat[row][rect.minCol]-'A'] = true;
				}
			}

			// right
			for (int row = rect.minRow; row <= rect.maxRow; row++) {
				if (mat[row][rect.maxCol] != c) {
					graph[c-'A'][mat[row][rect.maxCol]-'A'] = true;
				}
			}
		}
		
		return graph;
	}
	
	static class Rect {
		int minRow, maxRow, minCol, maxCol;
		
		public Rect(int minRow, int maxRow, int minCol, int maxCol) {
			this.minRow = minRow;
			this.maxRow = maxRow;
			this.minCol = minCol;
			this.maxCol = maxCol;
		}
		
		@Override
		public String toString() {
			return "row[" + minRow + "," + maxRow + "], col[" + 
					minCol + "," + maxCol + "]";
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
