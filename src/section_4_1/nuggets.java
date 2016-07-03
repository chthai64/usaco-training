/*
ID: conchim1
LANG: JAVA
TASK: nuggets
 */

package section_4_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class nuggets {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("nuggets.in");
		
		int N = in.nextInt();
		int[] boxes = new int[N];
		for (int i = 0; i < boxes.length; i++) {
			boxes[i] = in.nextInt();
		}
		
		int result = solve(boxes);
		
		PrintWriter pw = new PrintWriter(new File("nuggets.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static int solve(int[] boxes) {
		boxes = removeDup(boxes);
		if (noOdd(boxes)) {
			return 0;
		}
		
		int maxValue = 100000;
		
		boolean[] valid = new boolean[maxValue + 1];
		dfs(boxes, valid, maxValue, 0);
		
		for (int i = maxValue; i > 0; i--) {
			if (!valid[i]) {
				return i;
			}
		}
		
		return 0;
	}
	
	static void dfs(int[] boxes, boolean[] valid, int maxValue, int value) {
		if (value > maxValue || valid[value])
			return;
		
		valid[value] = true;
		
		for (int i = 0; i < boxes.length; i++) {
			dfs(boxes, valid, maxValue, value + boxes[i]);
		}
	}
	
	static boolean noOdd(int[] boxes) {
		for (int i : boxes) {
			if (i % 2 != 0)
				return false;
		}
		return true;
	}
	
	static int[] removeDup(int[] a) {
		Arrays.sort(a);
		Set<Integer> removeSet = new HashSet<Integer>();
		
		for (int i = a.length - 1; i >= 1; i--) {
			for (int j = i - 1; j >= 0; j--) {
				if (a[i] % a[j] == 0) {
					removeSet.add(a[i]);
				}
			}
		}
		
		List<Integer> list = new ArrayList<Integer>();
		for (int i : a) {
			if (!removeSet.contains(i)) {
				list.add(i);
			}
		}
		
		int[] ans = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ans[i] = list.get(i);
		}
		
		return ans;
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
