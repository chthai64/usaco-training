package section_3_4;

import static org.junit.Assert.*;

public class Test {

	@org.junit.Test
	public void test1() {
		int[] songs = {4, 3, 4, 2};
		int T = 5;
		int M = 2;
		
		assertEquals(3, rockers.solve(songs, M, T));
	}
	
	@org.junit.Test
	public void test2() {
		int[] songs = {18, 15, 16, 10, 2, 20, 14, 17,
				3, 7, 16, 15, 18, 16, 20, 16, 13, 9, 4, 16};
		int T = 20;
		int M = 10;
		
		assertEquals(14, rockers.solve(songs, M, T));
	}
}
