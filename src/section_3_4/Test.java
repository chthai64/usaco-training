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
}
