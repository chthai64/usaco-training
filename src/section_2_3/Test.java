package section_2_3;

import static org.junit.Assert.*;

public class Test {

	@org.junit.Test
	public void test() {
		assertEquals(2, nocows.solve(5, 3));
	}

	@org.junit.Test
	public void test2() {
		assertEquals(6, nocows.solve(9, 4));
	}
	
	@org.junit.Test
	public void test3() {
		assertEquals(5024, nocows.solve(35, 7));
	}
}
