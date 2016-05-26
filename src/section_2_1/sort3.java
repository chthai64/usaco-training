/*
ID: conchim1
LANG: JAVA
TASK: sort3
 */

package section_2_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class sort3 {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("sort3.in");
		
		int N = in.nextInt();
		int[] a = new int[N];
		for (int i = 0; i < N; i++) {
			a[i] = in.nextInt();
		}
		
		int result = solve(a, N);
		
		PrintWriter pw = new PrintWriter(new File("sort3.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static int solve(int[] a, int N) {
		int swapCount = 0;
		int[] count = new int[4];
		
		for (int n : a) {
			count[n]++;
		}
		
		Queue<Integer> oneInTwo = new LinkedList<Integer>();
		Queue<Integer> oneInThree = new LinkedList<Integer>();
		
		for (int i = count[1]; i < N; i++) {
			if (a[i] == 1) {
				if (i < count[1] + count[2]) {
					oneInTwo.add(i);
				} else {
					oneInThree.add(i);
				}
			}
		}
		
		// count in one positions
		for (int i = 0; i < count[1]; i++) {
			if (a[i] != 1) {
				swapCount++;
				
				if (a[i] == 2) {
					if (!oneInTwo.isEmpty()) {
						swap(a, i, oneInTwo.poll());
					} else {
						swap(a, i, oneInThree.poll());
					}
				} else {
					if (!oneInThree.isEmpty()) {
						swap(a, i, oneInThree.poll());
					} else {
						swap(a, i, oneInTwo.poll());
					}
				}
			}
		}
		
		for (int i = count[1]; i < count[1] + count[2]; i++) {
			if (a[i] != 2) {
				swapCount++;
			}
		}
		
		return swapCount;
	}

	static void swap(int[] a, int left, int right) {
		int temp = a[left];
		a[left] = a[right];
		a[right] = temp;
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
