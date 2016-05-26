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
		Input in = fromFile("/Users/idgmi_dc/Desktop/holstein.in");
		
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
		
//		String result = solve(req, feeds, V, G);
//		System.out.println(result);
		
		
//		PrintWriter pw = new PrintWriter(new File("holstein.out"));
//		pw.println(result);
//		pw.close();
//		in.close();
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
