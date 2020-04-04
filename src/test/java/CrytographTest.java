package test.java;

import org.junit.*;

import main.java.Cryptograph;

public class CrytographTest {

	@Test(timeout = 500)
	public void testGCD() {
		// Easy
		int[] easyResults = { 5, 3, 7, 1 };
		int[] easyA = { 5, 6, 21, 7 };
		int[] easyB = { 10, 9, 28, 5 };
		for(int i = 0; i < 4; i++) {
			Assert.assertEquals(easyResults[i],
					Cryptograph.greatestCommonDivisor(easyA[i], easyB[i]));
		}
		// Longer checks
		int[] longResults = { 25, 3, 21, 10 };
		int[] longA = { 125, 69, 63, 1000 };
		int[] longB = { 25, 9, 105, 110 };
		for(int i = 0; i < 4; i++) {
			Assert.assertEquals(longResults[i],
					Cryptograph.greatestCommonDivisor(longA[i], longB[i]));
		}
		// Co-primes
		int[] coPrimeResults = { 1, 1, 1, 1 };
		int[] coPrimeA = { 5, 93, 21, 107 };
		int[] coPrimeB = { 27, 82, 25, 105 };
		for(int i = 0; i < 4; i++) {
			Assert.assertEquals(coPrimeResults[i],
					Cryptograph.greatestCommonDivisor(coPrimeA[i], coPrimeB[i]));
		}
		// Actual primes
		int[] primeResults = { 1, 1, 1, 1 };
		int[] primeA = { 5, 11, 91, 61 };
		int[] primeB = { 7, 31, 29, 13 };
		for(int i = 0; i < 4; i++) {
			Assert.assertEquals(primeResults[i],
					Cryptograph.greatestCommonDivisor(primeA[i], primeB[i]));
		}
	}
	
	@Test(timeout = 500)
	public void testGetModuloOne() {
		// TODO: figure out how to test this
		Assert.fail();
	}
	
	@Test(timeout = 500)
	public void testGetNthPrime() {
		// TODO: generate Nth prime and then use primitive means to ensure it
		// is actually prime
		Assert.fail();
	}
	
	// TODO: test simple IO
	// TODO: test help commands
	// TODO: test actual use
	// TODO: test variety, use testDatabase
	@Test
	public void testMain() {
		// TODO: test output streams for various IO methods, emulating a user's
		// input - kinda like Joel did for CSSE2310
		Assert.fail();
	}
	
}
