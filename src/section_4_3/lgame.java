/*
ID: conchim1
LANG: JAVA
TASK: lgame
 */


package section_4_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class lgame {
//	private static final String PATH_DICT = "src/section_4_3/lgame.dict";
	private static final String PATH_DICT = "lgame.dict";
	private static final Map<Character, Integer> valueMap = new HashMap<>();
	static {
		valueMap.put('q',7);
		valueMap.put('w',6);
		valueMap.put('e',1);
		valueMap.put('r',2);
		valueMap.put('t',2);
		valueMap.put('y',5);
		valueMap.put('u',4);
		valueMap.put('i',1);
		valueMap.put('o',3);
		valueMap.put('p',5);
		valueMap.put('a',2);
		valueMap.put('s',1);
		valueMap.put('d',4);
		valueMap.put('f',6);
		valueMap.put('g',5);
		valueMap.put('h',5);
		valueMap.put('j',7);
		valueMap.put('k',6);
		valueMap.put('l',3);
		valueMap.put('z',7);
		valueMap.put('x',7);
		valueMap.put('c',4);
		valueMap.put('v',6);
		valueMap.put('b',5);
		valueMap.put('n',2);
		valueMap.put('m',5);
	}

	public static void main(String[] args) throws Exception {
		Input in = fromFile("lgame.in");
		solve(in.nextLine());
	}
	
	static void solve(String input) throws Exception {
		List<Word> words = getDicts();
		List<Word> shorts = new ArrayList<>();
		List<Word> longs = new ArrayList<>();
		
		for (Word word : words) {
			if (word.str.length() <= 4) {
				shorts.add(word);
			} else {
				longs.add(word);
			}
		}
		
		List<Pair> ans = new ArrayList<>();
		int maxScore = -1;
		
		// actually solve
		int[] inputCount = new int[26];
		for (char c : input.toCharArray()) {
			inputCount[c - 'a']++;
		}
		
		for (Word word : words) {
			if (maxScore <= word.score && valid(inputCount, word)) {
				if (word.score > maxScore) {
					ans.clear();
				}
//				ans.add(word.str);
				ans.add(new Pair(word.str));
				maxScore = word.score;
			}
		}
		
		if (input.length() >= 6) {
			for (int i = 0; i < shorts.size(); i++) {
				Word word1 = shorts.get(i);
				
				int[] countClone = inputCount.clone();
				boolean valid = true;
				
				for (int j = 0; j < 26; j++) {
					countClone[j] -= word1.count[j];
					if (countClone[j] < 0) {
						valid = false;
						break;
					}
				}
				
				if (!valid) {
					continue;
				}
				
				for (int j = i; j < shorts.size(); j++) {
					Word word2 = shorts.get(j);
					boolean validLocal = true;
					
					for (int k = 0; k < 26; k++) {
						if (countClone[k] < word2.count[k]) {
							validLocal = false;
							break;
						}
					}
					
					if (!validLocal) {
						continue;
					}
					
					int score = word1.score + word2.score;
					if (score >= maxScore) {
						if (score > maxScore) {
							ans.clear();
						}
						
						Pair p = new Pair(word1.str, word2.str);
						ans.add(p);
						maxScore = score;
					}
				}
			}
		}
		
		Collections.sort(ans);
		
		PrintWriter pw = new PrintWriter(new File("lgame.out"));
		pw.println(maxScore);
		for (Pair p : ans){
			pw.println(p);
		}
		
//		System.out.println(maxScore);
//		for (Pair p : ans) {
//			System.out.println(p);
//		}
	}
	
	private static boolean valid(int[] inputCount, Word word) {
		for (int i = 0; i < 26; i++) {
			if (inputCount[i] < word.count[i]) {
				return false;
			}
		}
		
		return true;
	}
	
	private static List<Word> getDicts() throws Exception {
		Input in = fromFile(PATH_DICT);
		List<Word> dict = new ArrayList<>();
		
		String s = in.nextLine();
		while (!s.equals(".")) {
			dict.add(new Word(s));
			s = in.nextLine();
		}
		
		in.close();
		return dict;
	}
	
	private static class Pair implements Comparable<Pair> {
		String word1;
		String word2;
		
		public Pair(String str) {
			word1 = str;
		}
		
		public Pair(String word1, String word2) {
			if (word1.compareTo(word2) > 0) {
				String temp = word1;
				word1 = word2;
				word2 = temp;
			}
			
			this.word1 = word1;
			this.word2 = word2;
		}
		
		@Override
		public int compareTo(Pair p) {
			int cmp = word1.compareTo(p.word1);
			if (cmp != 0) {
				return cmp;
			}

			if (word2 == null && p.word2 == null)
				return 0;
			
			if (word2 != null && p.word2 != null) {
				return word2.compareTo(p.word2);
			}
			
			if (word2 == null)
				return -1;
			
			return 1;
		}
		
		@Override
		public String toString() {
			if (word2 == null) {
				return word1;
			}
			
			return word1 + " " + word2;
		}
	}
	
	private static class Word {
		String str;
		int[] count = new int[26];
		int score = 0;
		
		public Word(String str) {
			this.str = str;
			compute();
		}
		
		private void compute() {
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				count[c - 'a']++;
				score += valueMap.get(c);
			}
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
