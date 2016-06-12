/*
ID: conchim1
LANG: JAVA
TASK: spin
 */

package section_3_2;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class spin {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("spin.in");
		Wheel[] wheels = new Wheel[5];
		
		for (int i = 0; i < 5; i++) {
			int speed = in.nextInt();
			Wedge[] wedges = new Wedge[in.nextInt()];
			
			for (int n = 0; n < wedges.length; n++) {
				wedges[n] = new Wedge(in.nextInt(), in.nextInt());
			}
			
			wheels[i] = new Wheel(speed, wedges);
		}
		
		String result = solve(wheels);
		
		PrintWriter pw = new PrintWriter(new File("spin.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static String solve(Wheel[] wheels) {
		for (int sec = 0; sec <= 360; sec++) {
			if (aligned(wheels, sec)) 
				return sec + "";
			
			for (Wheel wheel : wheels) {
				for (Wedge wedge : wheel.wedges) {
					wedge.start = (wedge.start + wheel.speed) % 360;
				}
			}
		}

		return "none";
	}
	
	static boolean aligned(Wheel[] wheels, int sec) {
		for (int angle = 0; angle < 360; angle++) {
			int wheelPass = 0;
			
			for (Wheel wheel : wheels) {
				boolean pass = false;
			
				for (Wedge wedge : wheel.wedges) {
					if (doesPass(wedge, angle)) {
						pass = true;
						break;
					}
				}
				
				if (pass)
					wheelPass++;
			}
			
			if (wheelPass == wheels.length) {
				return true;
			}
		}
		
		return false;
	}
	
	static boolean doesPass(Wedge wedge, int angle) {
		int end = (wedge.start + wedge.extend) % 360;
		
		if (wedge.start <= angle && angle <= end)
			return true;

		if (wedge.start > end) {
			if (angle <= end || wedge.start <= angle)
				return true;
		}
		
		return false;
	}
	
	static class Wheel {
		int speed;
		Wedge[] wedges;
		
		public Wheel(int speed, Wedge[] wedges) {
			this.speed = speed;
			this.wedges = wedges;
		}

		@Override
		public String toString() {
			return "Wheel [speed=" + speed + ", wedges=" + Arrays.toString(wedges) + "]";
		}
		
		
	}
	
	static class Wedge {
		int start;
		int extend;
		
		public Wedge(int start, int extend) {
			this.start = start;
			this.extend = extend;
		}
		
		@Override
		public String toString() {
			return "start: " + start + ", extend: " + extend;
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
