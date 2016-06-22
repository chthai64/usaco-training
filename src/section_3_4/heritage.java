/*
ID: conchim1
LANG: JAVA
TASK: heritage
 */

package section_3_4;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class heritage {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("heritage.in");
		
		String line = in.nextLine();
		char[] inOrder = line.toCharArray();
		
		line = in.nextLine();
		char[] preOrder = line.toCharArray();
		
		String result = solve(preOrder, inOrder);
		
		PrintWriter pw = new PrintWriter(new File("heritage.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static int preOrderIndex = 0;
	
	static String solve(char[] preOrder, char[] inOrder) {
		StringBuilder sb = new StringBuilder();
		
		preOrderIndex = 0;
		travel(sb, preOrder, inOrder, 0, preOrder.length - 1);
		
		return sb.toString();
	}
	
	static void travel(StringBuilder sb, char[] preOrder, char[] inOrder, int leftIndex, int rightIndex) {
		if (leftIndex <= rightIndex) {
			char preOrderChar = preOrder[preOrderIndex++];

			int index = findIndex(inOrder, preOrderChar);
			travel(sb, preOrder, inOrder, leftIndex, index - 1);
			travel(sb, preOrder, inOrder, index + 1, rightIndex);

			sb.append(preOrderChar);
		}
	}
	
	static int findIndex(char[] a, char c) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] == c)
				return i;
		}
		
		return 0;
	}

	// Helper methods, classes
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
