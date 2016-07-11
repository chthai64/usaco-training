/*
ID: conchim1
LANG: JAVA
TASK: job
 */

package section_4_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class job {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("job.in");

		int N = in.nextInt();
		int M1 = in.nextInt();
		int M2 = in.nextInt();

		int[] A = new int[M1];
		for (int i = 0; i < A.length; i++) {
			A[i] = in.nextInt();
		}

		int[] B = new int[M2];
		for (int i = 0; i < B.length; i++) {
			B[i] = in.nextInt();
		}
		
		String result = solve(N, A, B);

		PrintWriter pw = new PrintWriter(new File("job.out"));
		pw.println(result);
		pw.close();
		in.close();
	}

	static String solve(int N, int[] timeA, int[] timeB) {
		int[] finishA = new int[timeA.length];
		int[] finishB = new int[timeB.length];
		
		int[] jobA = new int[N];
		int[] jobB = new int[N];
		
		process(jobA, timeA, finishA);
		process(jobB, timeB, finishB);
		
		int t1 = 0;
		for (int i : finishA) {
			t1 = Math.max(t1, i);
		}
		
		int t = 0;
		for (int i = 0; i < N; i++) {
			t = Math.max(t, jobA[i] + jobB[N - 1 - i]);
		}
		
		return t1 + " " + t;
	}
	
	private static void process(int[] job, int[] time, int[] finish) {
		for (int i = 0; i < job.length; i++) {
			int minTime = Integer.MAX_VALUE;
			int minIndex = 0;
			
			for (int j = 0; j < finish.length; j++) {
				int t = finish[j] + time[j];
				if (t < minTime) {
					minIndex = j;
					minTime = finish[j] + time[j];
				}
			}
			
			finish[minIndex] = minTime;
			job[i] = minTime;
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
