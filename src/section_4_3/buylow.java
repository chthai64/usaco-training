/*
ID: conchim1
LANG: JAVA
TASK: buylow
 */

package section_4_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class buylow {
	static final String PATH = "buylow.in";
	
	
	public static void main(String[] args) throws Exception {
		Input in = fromFile(PATH);
		int n = in.nextInt();
		
		int[] P = new int[n];
		for (int i = 0; i < n; i++) {
			P[i] = in.nextInt();
		}
		
		solve(P);
	}
	
	static void solve(int[] P) throws Exception {
		int[] length = new int[P.length];
		MyBigNum[] seqCount = new MyBigNum[P.length];
		boolean[] ignore = new boolean[P.length];
		
		for (int i = P.length - 1; i >= 0; i--) {
			int maxLength = 0;

			for (int j = P.length - 1; j > i; j--) {
				if (!ignore[j] && P[j] < P[i]) {
					maxLength = Math.max(maxLength, length[j]);
				}
			}
			length[i] = Math.max(maxLength + 1, 1);
			
			for (int j = P.length - 1; j > i; j--) {
				if (P[i] == P[j] && length[i] == length[j]) {
					ignore[j] = true;
				}
			}
			
			MyBigNum count = new MyBigNum();
			for (int j = P.length - 1; j > i; j--) {
				if (!ignore[j] && P[j] < P[i] && length[j] == maxLength) {
					count.add(seqCount[j]);
				}
			}
			
			seqCount[i] = maxThenClone(count, MyBigNum.ONE);
		}
		
		// print result
		int maxLength = -1;
		for (int l : length) {
			maxLength = Math.max(maxLength, l);
		}

		MyBigNum maxCount = new MyBigNum();
		for (int i = 0; i < P.length; i++) {
			if (length[i] == maxLength && !ignore[i]) {
				maxCount.add(seqCount[i]);
			}
		}

		String out = maxLength + " " + maxCount;
//		System.out.println(out);
		PrintWriter pw = new PrintWriter(new File("buylow.out"));
		pw.println(out);
		pw.close();
	}
	
	static MyBigNum max(MyBigNum a, MyBigNum b) {
		if (a.compareTo(b) < 0)
			return b;

		return a;
	}
	
	static MyBigNum maxThenClone(MyBigNum a, MyBigNum b) {
		MyBigNum max = max(a,b);
		return max.clone();
	}
	
	/* Only works on positive number */
	private static class MyBigNum implements Comparable<MyBigNum> {
		private List<Integer> digits = new ArrayList<>(); // store in reverse order
		static final MyBigNum ONE = new MyBigNum(1);
		
		public MyBigNum() {
			digits.add(0);
		}
		
		public MyBigNum(int n) {
			while (n > 0) {
				digits.add(n % 10);
				n /= 10;
			}
			
			if (digits.isEmpty()) {
				digits.add(0);
			}
		}
		
		protected MyBigNum clone() {
			MyBigNum big = new MyBigNum();
			big.digits = new ArrayList<>(digits);
			return big;
		}
		
		void add(MyBigNum bigNum) {
			List<Integer> digitsOther = bigNum.digits;
			List<Integer> ans = new ArrayList<>();
			
			int carry = 0;
			int i = 0;
			int maxI = Math.max(digits.size(), digitsOther.size());
			
			while (i < maxI || carry > 0) {
				int a = i < digits.size()? digits.get(i) : 0;
				int b = i < digitsOther.size()? digitsOther.get(i) : 0;
				int sum = a + b + carry;
				
				ans.add(sum % 10);
				carry = sum / 10;
				i++;
			}
			
			digits = ans;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof MyBigNum) {
				MyBigNum n = (MyBigNum) obj;
				
				if (digits.size() != n.digits.size()) {
					return false;
				}
				
				for (int i = 0; i < digits.size(); i++) {
					int a = digits.get(i);
					int b = n.digits.get(i);
					
					if (a != b)
						return false;
				}
			}
			return false;
		}
		
		@Override
		public int compareTo(MyBigNum n) {
			if (digits.size() != n.digits.size()) {
				return digits.size() - n.digits.size();
			}
			
			for (int i = digits.size() - 1; i >= 0; i--) {
				int d1 = digits.get(i);
				int d2 = n.digits.get(i);
				
				if (d1 != d2){
					return d1 - d2;
				}
			}
			
			return 0;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			
			for (int i = digits.size() - 1; i >= 0; i--) {
				sb.append(digits.get(i));
			}
			
			return sb.toString();
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
