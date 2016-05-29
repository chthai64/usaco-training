package section_2_2;

import static org.junit.Assert.*;

import java.util.*;

public class Test {

	@org.junit.Test
	public void test1() {
		int N = 10;
		int C = 1;
		List<Integer> lampsOn = Arrays.asList();
		List<Integer> lampsOff = Arrays.asList(7);
		
		String actual = lamps.solve(N, C, lampsOn, lampsOff);
		String expected = 	"0000000000\n" +
							"0101010101\n" +
							"0110110110";
		
		assertEquals(expected, actual);
	}
	
	@org.junit.Test
	public void test2() {
		int N = 20;
		int C = 3;
		List<Integer> lampsOn = Arrays.asList();
		List<Integer> lampsOff = Arrays.asList(1,3,5);
		
		String actual = lamps.solve(N, C, lampsOn, lampsOff);
		String expected = 	"00000000000000000000\n" +
							"01010101010101010101";
		
		assertEquals(expected, actual);
	}
	
	@org.junit.Test
	public void test3() {
		int N = 50;
		int C = 100;
		List<Integer> lampsOn = Arrays.asList(1);
		List<Integer> lampsOff = Arrays.asList();
		
		String actual = lamps.solve(N, C, lampsOn, lampsOff);
		
		String expected = 	"10010010010010010010010010010010010010010010010010\n" 
						  + "10101010101010101010101010101010101010101010101010\n"
						  + "11000111000111000111000111000111000111000111000111\n"
						  + "11111111111111111111111111111111111111111111111111";
		
		assertEquals(expected, actual);
	}
}
