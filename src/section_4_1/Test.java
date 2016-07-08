package section_4_1;

import static org.junit.Assert.*;

public class Test {

	@org.junit.Test
	public void test1() {
		int[] boxes = {252, 250, 254, 256};
		assertEquals(0, nuggets.solve(boxes));
	}

	@org.junit.Test
	public void test2() {
		int[] boxes = {251, 252, 250, 254, 256};
		assertEquals(10749, nuggets.solve(boxes));
	}

	@org.junit.Test
	public void test3() {
		int[] boxes = {255, 254};
		assertEquals(64261, nuggets.solve(boxes));
	}
}
