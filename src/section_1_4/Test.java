package section_1_4;

import static org.junit.Assert.*;

public class Test {

	@org.junit.Test
	public void test1() {
		int N = 5;
		int M = 7;
		String expected = "1 4\n37 4\n2 8\n29 8\n"
				+ "1 12\n5 12\n13 12\n17 12\n5 20\n2 24";
		assertEquals(expected, ariprog.solve(N, M));
	}

}
