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
		char[] ss = (beads + beads).toCharArray();
		
		int[] leftRed = new int[ss.length + 1];
		int[] leftBlue = new int[ss.length + 1];
		int[] rightRed = new int[ss.length + 1];
		int[] rightBlue = new int[ss.length + 1];
		
		for (int i = 1; i < ss.length; i++) {
			if (ss[i] == 'w') {
				leftRed[i] = leftRed[i - 1] + 1;
				leftBlue[i] = leftBlue[i - 1] + 1;
			}
			else if (ss[i] == 'r') {
				leftRed[i] = leftRed[i - 1] + 1;
				leftBlue[i] = 0;
			}
			else {
				leftRed[i] = 0;
				leftBlue[i] =  leftBlue[i - 1] + 1;
			}
		}
		
		for (int i = ss.length - 1; i >= 0; i--) {
			if (ss[i] == 'w') {
				rightRed[i] = rightRed[i + 1] + 1;
				rightBlue[i] = rightBlue[i + 1] + 1;
			}
			else if (ss[i] == 'r') {
				rightRed[i] = rightRed[i + 1] + 1;
				rightBlue[i] = 0;
			}
			else {
				rightRed[i] = 0;
				rightBlue[i] = rightBlue[i + 1] + 1;
			}
		}
		
		int max = 0;
		for (int p = 1; p < ss.length; p++) {
			int localMax = Math.max(leftRed[p - 1], leftBlue[p - 1])
					+ Math.max(rightRed[p], rightBlue[p]);
			max = Math.max(max, localMax);
		}
		
		return Math.min(max, beads.length());
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
