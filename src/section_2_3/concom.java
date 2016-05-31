/*
ID: conchim1
LANG: JAVA
TASK: concom
 */

package section_2_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;


public class concom {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("C:/Users/Chau Thai/Desktop/concom.in");
		
		int n = in.nextInt();
		int[][] mat = new int[6][6];
		
		for (int i = 0; i < n; i++) {
			String line = in.nextLine();
			String[] split = line.split(" ");
			int com1 = Integer.parseInt(split[0]);
			int com2 = Integer.parseInt(split[1]);
			int per = Integer.parseInt(split[2]);
			
			mat[com1][com2] = per;
		}
		
		String result = solve(mat);
		
//		PrintWriter pw = new PrintWriter(new File("concom.out"));
//		pw.println(result);
//		pw.close();
//		in.close();
		
		System.out.println(result);
	}
	
	static String solve(int[][] mat) {
		StringBuilder sb = new StringBuilder();
		
		for (int com1 = 1; com1 < mat.length; com1++) {
			for (int com2 = 1; com2 < mat.length; com2++) {
				
				if (mat[com1][com2] > 50) {
					for (int com = 1; com < mat.length; com++) {
						if (com != com1 && mat[com][com1] > 50) {
							mat[com][com2] += mat[com1][com2];
						}
					}
				}
			}
		}
		
//		for (int[] a : mat) {
//			System.out.println(Arrays.toString(a));
//		}
		
		for (int com1 = 1; com1 < mat.length; com1++) {
			for (int com2 = 1; com2 < mat.length; com2++) {
				if (com1 != com2 && mat[com1][com2] > 50) {
					sb.append(com1 + " " + com2 + "\n");
				}
			}
		}
		
		return sb.substring(0, sb.length() - 1).toString();
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
