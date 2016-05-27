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
		Input in = fromFile("holstein.in");
		
		int V = in.nextInt();
		int[] req = new int[V];
		for (int i = 0; i < V; i++) {
			req[i] = in.nextInt();
		}
		
		int G = in.nextInt();
		Feed[] feeds = new Feed[G];
		
		for (int feed = 0; feed < G; feed++) {
			int[] a = new int[V];
			
			for (int i = 0; i < a.length; i++) {
				a[i] = in.nextInt();
			}
			
			feeds[feed] = new Feed(feed, a);
		}
		
		String result = solve(req, feeds, V, G);
		
		PrintWriter pw = new PrintWriter(new File("holstein.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static String solve(int[] req, Feed[] feeds, int V, int G) {
		int[] used = new int[G];
		int count = 0;
		
		for (int maxLevel = 1; maxLevel <= G; maxLevel++) {
			int[] tempUsed = new int[G];
			if (dfs(tempUsed, req.clone(), feeds, 0, 0, maxLevel)) {
				count = maxLevel;
				used = tempUsed;
				break;
			}
		}
		
		String result = count + " ";
		for (int i = 0; i < count; i++) {
			result += used[i] + 1;
			if (i != count - 1)
				result += " ";
		}

		return result;
	}
	
	static boolean dfs(int[] used, int[] remains, Feed[] feeds, int startIndex, int level, int maxLevel) {
		if (done(feeds, used, remains, level)) {
			return true;
		}
		
		if (level >= maxLevel)
			return false;
		
		for (int feedIndex = startIndex; feedIndex < feeds.length; feedIndex++) {
			used[level] = feedIndex;
			if (dfs(used, remains, feeds, feedIndex + 1, level + 1, maxLevel)) {
				return true;
			}
		}
		
		return false;
	}
	
	static boolean done(Feed[] feeds, int[] used, int[] remains, int level) {
		int[] r = remains.clone();
		
		for (int i = 0; i < level; i++) {
			Feed feed = feeds[used[i]];
			
			for (int vita = 0; vita < feeds[0].vitamins.length; vita++) {
				r[vita] -= feed.vitamins[vita];
			}
		}
		
		for (int value : r) {
			if (value > 0)
				return false;
		}
		
		return true;
	}
	
	static class Feed {
		int index;
		int[] vitamins;
		
		public Feed(int index, int[] vitamins) {
			this.vitamins = vitamins;
			this.index = index;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Feed) {
				Feed feed = (Feed) obj;
				return index == feed.index;
			}
			return false;
		}
	}
	
	static boolean done(int[] remains) {
		for (int i : remains) {
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
