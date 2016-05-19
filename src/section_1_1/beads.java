/*
ID: conchim1
LANG: JAVA
TASK: beads
 */

package section_1_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class beads {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("beads.in");
		in.nextInt();
		String beads = in.nextString();
		int result = maxBeads(beads);
		
		PrintWriter pw = new PrintWriter(new File("beads.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	public static int maxBeads(String beads) {
		int max = 0;
		
		for (int i = 0; i < beads.length() - 1; i++) {
			max = Math.max(max, compute(beads, i, i + 1));
		}
		
		max = Math.max(max, compute(beads, beads.length() - 1, 0));
		return max;
	}
	
	static int compute(String beads, int left, int right) {
		int count = 0;
		boolean[] visited = new boolean[beads.length()];
		
		char lastLeft = beads.charAt(left);
		boolean doneLeft = false;
		
		while (!visited[left] && !doneLeft) {
			char c = beads.charAt(left);
			if (c == lastLeft || lastLeft == 'w' || c == 'w') {
				if (lastLeft == 'w')
					lastLeft = c;
				
				visited[left] = true;
				count++;
			} else {
				doneLeft = true;
			}
			
			left = (--left < 0)? beads.length() - 1 : left;
		}
		
		char lastRight = beads.charAt(right);
		boolean doneRight = false;
		
		while (!visited[right] && !doneRight) {
			char c = beads.charAt(right);
			if (c == lastRight || lastRight == 'w' || c == 'w') {
				if (lastRight == 'w')
					lastRight = c;
				
				visited[right] = true;
				count++;
			} else {
				doneRight = true;
			}
			
			right = (right + 1) % beads.length();
		}
		
		return count;
	}
	
	private static Input fromFile(String path) throws IOException {
		return new Input(new FileInputStream(new File(path)));
	}

	static class Input {
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
