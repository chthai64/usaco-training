/*
ID: conchim1
LANG: JAVA
TASK: fence9
 */

package section_3_4;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class fence9 {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("fence9.in");
		int n = in.nextInt();
		int m = in.nextInt();
		int p = in.nextInt();
	
		int result = solve(n, m, p);
		
		PrintWriter pw = new PrintWriter(new File("fence9.out"));
		pw.println(result);
		pw.close();
		in.close();
		
	}
	
	static final int BOX_SIZE = 100;
	
	/*
    box array {a,b,c,d}
	d ____ c
	 |    |
	 |____|
	a      b 
	*/
	static int solve(int n, int m, int p) {
		if (m <= 1 || p <= 1)
			return 0;
		
		Triangle triangle = new Triangle(n, m, p);
		int total = 0;
		
		for (int boxStartY = 0; boxStartY <= m; boxStartY += BOX_SIZE + 1) {
			for (int boxStartX = 0; boxStartX <= Math.max(p, n); boxStartX += BOX_SIZE + 1) {
				Point[] box = {
						new Point(boxStartX, boxStartY),
						new Point(boxStartX + BOX_SIZE, boxStartY),
						new Point(boxStartX + BOX_SIZE, boxStartY + BOX_SIZE),
						new Point(boxStartX, boxStartY + BOX_SIZE)
				};
				
				int insideCount = 0;
				for (Point boxPoint : box) {
					if (inside(boxPoint, triangle)) {
						insideCount++;
					}
				}
				
				// box is inside the triangle
				if (insideCount == 4) {
					total += (BOX_SIZE + 1) * (BOX_SIZE + 1);
					
					if (boxStartY == 0) {
						total -= BOX_SIZE + 1;
						
						if (boxStartX == 0 && n == 0) {
							total -= BOX_SIZE;
						}
						
						else if (boxStartX + BOX_SIZE == p && n == p) {
							total -= BOX_SIZE;
						}
						
					} else {
						if (boxStartX == 0 && n == 0) {
							total -= BOX_SIZE + 1;
						}
						else if (boxStartX + BOX_SIZE == p && n == p) {
							total -= BOX_SIZE;
						} else {
						
							if (touch(box[2], triangle.A, triangle.B)) {
								total--;
							}

							if (touch(box[3], triangle.A, triangle.B)) {
								total--;
							}
						}
					}
				}
				
				// box in partially inside the triangle
				else if (insideCount > 0 || intersect(box, triangle)) {
					for (int x = (int) box[0].x; x <= box[1].x; x++) {
						for (int y = (int) box[0].y; y <= box[3].y; y++) {
							Point point = new Point(x,y);
							boolean touchAB = touch(point, triangle.A, triangle.B);
							boolean touchBC = touch(point, triangle.B, triangle.C);
							boolean touchAC = touch(point, triangle.A, triangle.C);
							
							if (inside(point, triangle) && !touchAB && !touchBC && !touchAC) {
								total++;
							}
						}
					}
				}
			}
		}
		
		return total;
	}
	
	static boolean intersect(Point[] box, Triangle triangle) {
		for (int i = 0; i < 4; i++) {
			Point c = box[i];
			Point d = box[(i + 1) % 4];
			
			if (intersect(triangle.A, triangle.B, c, d))
				return true;
			
			if (intersect(triangle.B, triangle.C, c, d))
				return true;
			
			if (intersect(triangle.A, triangle.C, c, d))
				return true;
		}
		
		return false;
	}
	
	static boolean intersect(Point a, Point b, Point c, Point d) {
		return !sameSide(a, b, c, d) && !sameSide(c, d, a, b);
	}
	
	static boolean inside(Point p, Triangle triangle) {
		boolean sameSizeAB = sameSide(p, triangle.center, triangle.A, triangle.B);
		boolean sameSizeBC = sameSide(p, triangle.center, triangle.B, triangle.C);
		boolean sameSizeAC = sameSide(p, triangle.center, triangle.A, triangle.C);
		
		return sameSizeAB && sameSizeBC && sameSizeAC;
	}
	
	/**
	 * Check if point p touch the line ab.
	 */
	static boolean touch(Point p, Point a, Point b) {
		int px = (int) p.x;
		int py = (int) p.y;
		int ax = (int) a.x;
		int ay = (int) a.y;
		int bx = (int) b.x;
		int by = (int) b.y;
		
		int dist = (bx - ax) * (ay - py) - (ax - px) * (by - ay);
		return dist == 0;
	}
	
	/**
	 * Check if a and b is on the same side of the line cd.
	 */
	static boolean sameSide(Point a, Point b, Point c, Point d) {
		double z1 = (c.y - d.y) * (a.x - c.x) + (d.x - c.x) * (a.y - c.y);
		double z2 = (c.y - d.y) * (b.x - c.x) + (d.x - c.x) * (b.y - c.y);
		
		if (z1 == 0 || z2 == 0)
			return true;
		
		if (z1 > 0 && z2 > 0)
			return true;
		
		if (z1 < 0 && z2 < 0)
			return true;
		
		return false;
	}
	
	static class Triangle {
		Point A,B,C;
		Point center;
		
		public Triangle(int n, int m, int p) {
			A = new Point(0,0);
			B = new Point(n,m);
			C = new Point(p,0);
			
			double averageX = (A.x + B.x + C.x) / 3.0;
			double averageY = (A.y + B.y + C.y) / 3.0;
			center = new Point(averageX, averageY);
		}
	}

	static class Point {
		double x, y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Point) {
				Point p = (Point) obj;
				return p.x == x && p.y == y;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return (int) (x * 31 + y);
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}
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
