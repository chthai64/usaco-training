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
	static int[][] mat = new int[101][101];
	static boolean[][] control = new boolean[101][101];
	
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 101; i++) {
			control[i][i] = true;
		}
		
		Input in = fromFile("concom.in");
		int n = in.nextInt();
		
		for (int i = 0; i < n; i++) {
			String line = in.nextLine();
			String[] split = line.split(" ");
			int com1 = Integer.parseInt(split[0]);
			int com2 = Integer.parseInt(split[1]);
			int per = Integer.parseInt(split[2]);
			
			addOwner(com1, com2, per);
		}
		
		
		StringBuilder sb = new StringBuilder();
		for (int com1 = 1; com1 < mat.length; com1++) {
			for (int com2 = 1; com2 < mat.length; com2++) {
				if (com1 != com2 && control[com1][com2]) {
					sb.append(com1 + " " + com2 + "\n");
				}
			}
		}
		
		String result = sb.substring(0, sb.length() - 1).toString();
		
		PrintWriter pw = new PrintWriter(new File("concom.out"));
		pw.println(result);
		pw.close();
		in.close();
		
//		System.out.println(result);
	}
	
	static String solve(int[][] mat) {
//		boolean[][] marked = new boolean[101][101];
//		
//		for (int parent = 1; parent <= 100; parent++) {
//			for (int child = 1; child <= 100; child++) {
//				if (!marked[parent][child] && mat[parent][child] > 50) {
//					marked[parent][child] = true;
//					
//					for (int grandChild = 1; grandChild <= 100; grandChild++) {
//						mat[parent][grandChild] += mat[child][grandChild];
//						
//						if (marked[child][grandChild]) {
//							marked[parent][grandChild] = true;
//						}
//					}
//					
//					
//					child = 0;
//				}
//			}
//		}
		
		StringBuilder sb = new StringBuilder();
		for (int com1 = 1; com1 < mat.length; com1++) {
			for (int com2 = 1; com2 < mat.length; com2++) {
				if (com1 != com2 && mat[com1][com2] > 50) {
					sb.append(com1 + " " + com2 + "\n");
				}
			}
		}
		
		return sb.substring(0, sb.length() - 1).toString();
	}
	
	static void addControl(int parent, int child) {
		if (control[parent][child])
			return;
		
		control[parent][child] = true;
		
		for (int grandChild = 1; grandChild < 101; grandChild++) {
			mat[parent][grandChild] += mat[child][grandChild];
		}
		
		for (int grandParent = 1; grandParent < 101; grandParent++) {
			if (control[grandParent][parent]) {
				addControl(grandParent, child);
			}
		}
		
		for (int grandChild = 1; grandChild < 101; grandChild++) {
			if (mat[parent][grandChild] > 50) {
				addControl(parent, grandChild);
			}
		}
	}
	
	static void addOwner(int parent, int child, int p) {
		for (int grandParent = 1; grandParent < 101; grandParent++) {
			if (control[grandParent][parent]) {
				mat[grandParent][child] += p;
			}
		}
		
		for (int grandParent = 1; grandParent < 101; grandParent++) {
			if (mat[grandParent][child] > 50) {
				addControl(grandParent, child);
			}
		}
	}

	static class Pair {
		int a, b;
		
		public Pair(int a, int b) {
			this.a = a;
			this.b = b;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Pair) {
				Pair pair = (Pair) obj;
				return a == pair.a && b == pair.b;
			}
			
			return false;
		}
		
		@Override
		public int hashCode() {
			return Arrays.hashCode(new int[] {a,b});
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
