/*
ID: conchim1
LANG: JAVA
TASK: wormhole
 */

package section_1_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class wormhole {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("wormhole.in");

		int n = in.nextInt();
		List<Point> list = new ArrayList<Point>();
		for (int i = 0; i < n; i++) {
			Point point = new Point(in.nextInt(), in.nextInt());
			list.add(point);
		}
		
		int result = solve(list);
		
		PrintWriter pw = new PrintWriter(new File("wormhole.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	public static int solve(List<Point> points) {
		Map<Point, Point> mapNext = createMapNext(points);
		Map<Point, Point> partner = new HashMap<Point, Point>();
		return recurse(mapNext, partner, points);
	}
	
	static int recurse(Map<Point, Point> mapNext, Map<Point, Point> partner, List<Point> points) {
		int i = 0;
		while (i < points.size() && partner.containsKey(points.get(i))) {
			i++;
		}
		
		if (i == points.size()) {
			return isCycleExist(mapNext, partner, points)? 1 : 0;
		}
		
		int count = 0;
		for (int j = i + 1; j < points.size(); j++) {
			if (!partner.containsKey(points.get(j))) {
				partner.put(points.get(i), points.get(j));
				partner.put(points.get(j), points.get(i));
				
				count += recurse(mapNext, partner, points);
				
				partner.remove(points.get(i));
				partner.remove(points.get(j));
			}
		}
		
		return count;
	}
	
	static boolean isCycleExist(Map<Point, Point> mapNext, Map<Point, Point> partner, List<Point> points) {
		for (int i = 0; i < points.size(); i++) {
			Point curr = points.get(i);
			int n = 0;
			
			while (curr != null && n < points.size()) {
				curr = partner.get(mapNext.get(curr));
				n++;
			}
			
			if (n >= points.size()) {
				return true;
			}
		}
		
		return false;
	}
	
	static Map<Point, Point> createMapNext(List<Point> points) {
		Map<Integer, List<Point>> mapHorz = new HashMap<Integer, List<Point>>();
		
		for (Point p : points) {
			List<Point> list = (mapHorz.containsKey(p.y))? mapHorz.get(p.y) : new ArrayList<Point>();
			list.add(p);
			mapHorz.put(p.y, list);
		}
		
		Map<Point, Point> mapNext = new HashMap<Point, Point>();
		
		for (List<Point> list : mapHorz.values()) {
			Collections.sort(list, new Comparator<Point>() {
				@Override
				public int compare(Point p1, Point p2) {
					Integer a = p1.x;
					Integer b = p2.x;
					return a.compareTo(b);
				}
			});
			
			for (int i = 0; i < list.size(); i++) {
				Point next = (i + 1 < list.size())? list.get(i + 1) : null;
				mapNext.put(list.get(i), next);
			}
		}
		
		return mapNext;
	}
	
	static void swap(List<Point> points, int left, int right) {
		Point pLeft = points.get(left);
		points.set(left, points.get(right));
		points.set(right, pLeft);
	}
	
	static class Point {
		int x, y;
		
		public Point(int x, int y) {
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
			return Arrays.hashCode(new int[] {x,y});
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}
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
