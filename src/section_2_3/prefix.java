/*
ID: conchim1
LANG: JAVA
TASK: prefix
 */

package section_2_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class prefix {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("prefix.in");
		
		Set<String> set = new HashSet<String>();
		
		String s = in.nextString();
		while (!s.equals(".")) {
			set.add(s);
			s = in.nextString();
		}

		s = in.nextString();
		StringBuilder sb = new StringBuilder();
		while (s != null) {
			sb.append(s);
			s = in.nextString();
		}
		
		int result = solve(set, sb.toString());
		
		PrintWriter pw = new PrintWriter(new File("prefix.out"));
		pw.println(result);
		pw.close();
		in.close();
		
		System.out.println(result);
	}
	
	static int solve(Set<String> set, String S) {
		Queue<Integer> queue = new LinkedList<Integer>();
		Set<Integer> visited = new HashSet<Integer>();
		queue.add(0);
		visited.add(0);
	
		int count = 0;
		
		while (!queue.isEmpty()) {
			int index = queue.poll();
			count = Math.max(index, count);
			
			for (String primitive : set) {
				if (primitive.length() + index - 1 < S.length() && !visited.contains(index + primitive.length())) {
					boolean match = true;
					
					for (int i = 0; i < primitive.length(); i++) {
						if (primitive.charAt(i) != S.charAt(index + i)) {
							match = false;
							break;
						}
					}
					
					if (match) {
						queue.add(index + primitive.length());
						visited.add(index + primitive.length());
					}
				}
			}
		}
		
		return count;
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
