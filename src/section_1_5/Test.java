package section_1_5;

import static org.junit.Assert.*;

public class Test {

	@org.junit.Test
	public void test() {
		int[][] mat = {
				{7,0,0,0,0},
				{3,8,0,0,0},
				{8,1,0,0,0},
				{2,7,4,4,0},
				{4,5,2,6,5}
		};
		int N = 5;
		assertEquals(30, numtri.solve(mat, N));
	}

}
