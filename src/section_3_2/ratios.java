/*
ID: conchim1
LANG: JAVA
TASK: ratios
 */

package section_3_2;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class ratios {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("ratios.in");
		
		Mixture golden = new Mixture(in.nextLine());
		Mixture[] mixtures = new Mixture[3];
		for (int i = 0; i < 3; i++) {
			mixtures[i] = new Mixture(in.nextLine());
		}
		
		String result = solve(mixtures, golden);
		
		PrintWriter pw = new PrintWriter(new File("ratios.out"));
		pw.println(result);
		pw.close();
		in.close();
	}

	static String solve(Mixture[] mixtures, Mixture golden) {
		if (golden.x == 0 && golden.y == 0 && golden.z == 0)
			return "0 0 0 1";
		
		int minTotal = Integer.MAX_VALUE;
		int mag1 = 0;
		int mag2 = 0;
		int mag3 = 0;
		int magGolden = 0;

		int sum = golden.x + golden.y + golden.z;

		for (int n1 = 0; n1 < 100; n1++) {
			for (int n2 = 0; n2 < 100; n2++) {
				for (int n3 = 0; n3 < 100; n3++) {
					if (n1 + n2 + n3 < minTotal) {
						int x = mixtures[0].x * n1 + mixtures[1].x * n2 + mixtures[2].x * n3;
						int y = mixtures[0].y * n1 + mixtures[1].y * n2 + mixtures[2].y * n3;
						int z = mixtures[0].z * n1 + mixtures[1].z * n2 + mixtures[2].z * n3;

						int magnitute = (x+y+z) / sum;
						boolean valid = magnitute != 0 
								&& magnitute * golden.x == x 
								&& magnitute * golden.y == y
								&& magnitute * golden.z == z;
						
						if (valid) {
							mag1 = n1;
							mag2 = n2;
							mag3 = n3;
							magGolden = magnitute;
							minTotal = n1 + n2 + n3;
						}
					}
				}
			}
		}

		if (minTotal == Integer.MAX_VALUE)
			return "NONE";	

		return mag1 + " " + mag2 + " " + mag3 + " " + magGolden;
	}

	static class Mixture {
		int x, y, z;
		
		public Mixture(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public Mixture(String s) {
			String[] split = s.split(" ");
			x = Integer.parseInt(split[0]);
			y = Integer.parseInt(split[1]);
			z = Integer.parseInt(split[2]);
		}

		@Override
		public String toString() {
			return "Mixture [x=" + x + ", y=" + y + ", z=" + z + "]";
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
