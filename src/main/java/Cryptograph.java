package main.java;

import main.java.command.CommandLineInterface;

/**
 * This is the main centre of the application; it will primarily run here from a
 * basic CLI, however it will be adapted later to integrate with an Android
 * Studio based graphical interface.
 * 
 * This application is primarily designed for both generating difficult passwords
 * and storing them locally in an encoded state: the user will then be able to
 * access their own passwords and decrypt them with their own 'master' password
 * and use it as needed.
 * 
 * @author Eamon O'Brien
 */
public class Cryptograph {
	
	/**
	 * Returns the greatest common divisor of the two integers, that is the value
	 * which divides both and has the highest possible value as per the Euclidean
	 * Algorithm.
	 * 
	 * @require	a, b are non-zero positive integers
	 * @ensure	the greatest common divisor is returned, or 1 if the two are
	 * 			co-prime
	 */
	public static int greatestCommonDivisor(int a, int b) {
		if(b > a) {
			return greatestCommonDivisor(b, a);
		} else if(a % b == 0) {
			return b;
		} else {
			return greatestCommonDivisor(b, a % b);
		}
	}
	
	/**
	 * Returns the integer y, such that the product of x and y (modulo n) is
	 * exactly 1.
	 * 
	 * @require	x and n are positive integers greater than 0
	 * @ensure	the product of x and y is exactly 1 (modulus n)
	 */
	public static int getModuloOne(int x, int n) {
		int y = 1;
		while((x * y) % n != 1) {
			y++;
		}
		return y;
	}
	
	// epicly long answer, but a great read:
	// https://stackoverflow.com/a/9704912
	public static long getNthPrime(int n) {
		// return base cases
		if(n < 2)
			return 2;
		if(n == 3)
			return 3;
		// set up calculations
		int limit, root, count = 1;
		// upper limit for nth prime
		limit = (int) (n * (Math.log(n) + Math.log(Math.log(n)))) + 3;
	    root = (int) Math.sqrt(limit) + 1;
	    // reduced limit
	    limit = (limit - 1) / 2;
	    root = root / 2 - 1;
	    // dynamic search for next prime
	    boolean[] sieve = new boolean[limit];
	    for(int i = 0; i < root; ++i) {
	        if (!sieve[i]) {
	            ++count;
	            for(int j = 2 * i * (i + 3) + 3, p = 2 * i + 3; j < limit; j += p) {
	                sieve[j] = true;
	            }
	        }
	    }
	    // get pth element, points to nth prime
	    int p;
	    for(p = root; count < n; ++p) {
	        if (!sieve[p]) {
	            ++count;
	        }
	    }
	    return 2 * p + 1;
	}
	
	/**
	 * The primary main of the program, spawns threads that run the main command
	 * line interface, and the database interface as well.
	 * 
	 * TODO: write up this
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Thread cli = new Thread(new CommandLineInterface());
		cli.start();
//		while(cli.isAlive());
	}

}
