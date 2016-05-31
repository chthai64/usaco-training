package section_2_3;

import static org.junit.Assert.*;

public class Test {

	@org.junit.Test
	public void test() {
		int N = 10;
		int[] coins = {1,2,5};
		
		assertEquals(10, money.solve(N, coins));
	}
}
