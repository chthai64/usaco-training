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
		
		List<Pair> allPairs = new ArrayList<Pair>();
		for (int i = 0; i < points.size() - 1; i++) {
			for (int j = i + 1; j < points.size(); j++) {
				Pair pair = new Pair(points.get(i), points.get(j));
				allPairs.add(pair);
			}
		}

		return generate(mapNext, allPairs, new ArrayList<Pair>(),
				new HashSet<Point>(), points, 0);
	}
	
	static int generate(Map<Point, Point> mapNext, List<Pair> allPairs,
			List<Pair> pairs, Set<Point> pointSet, List<Point> points, int index) {
		
		if (index == allPairs.size() || pointSet.size() == points.size()) {
			if (pointSet.size() == points.size()) {
				if (compute(mapNext, pairs, points))
					return 1;
			}
			return 0;
		}
		
		int count = 0;
		for (int i = index; i < allPairs.size(); i++) {
			Pair pair = allPairs.get(i);
			
			if (!pointSet.contains(pair.a) && !pointSet.contains(pair.b)) {
				pairs.add(pair);
				pointSet.add(pair.a);
				pointSet.add(pair.b);
				
				count += generate(mapNext, allPairs, pairs, pointSet, points, i + 1);
				
				pairs.remove(pairs.size() - 1);
				pointSet.remove(pair.a);
				pointSet.remove(pair.b);
			}
		}
		
		return count;
	}
	
	static boolean compute(Map<Point, Point> mapNext, List<Pair> pairs, List<Point> points) {
		Map<Point, Point> graph = new HashMap<Point, Point>();
		
		for (Pair pair : pairs) {
			graph.put(pair.a, pair.b);
			graph.put(pair.b, pair.a);
		}
		
		for (Point start : points) {
			Point curr = start;
			Point prev = mapNext.get(curr);
			Point next = graph.get(prev);
			
			while (next != null && next != start) {
				curr = next;
				prev = mapNext.get(curr);
				next = graph.get(prev);
			}
			
			if (next != null) {
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
	
	static class Pair {
		Point a, b;
		
		public Pair(Point a, Point b) {
			this.a = a;
			this.b = b;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Pair) {
				Pair pair = (Pair) obj;
				return (a == pair.a && b == pair.b) || 
						(a == pair.b && b == pair.a);
			}
			return false;
		}
		
		@Override	
		public int hashCode() {
			int[] array = {a.x, a.y, b.x, b.y};
			Arrays.sort(array);
			return Arrays.hashCode(array);
		}
		
		@Override
		public String toString() {
			return "(" + a.x + ", " + a.y + ") -> (" + b.x + ", " + b.y + ")";
		}
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
