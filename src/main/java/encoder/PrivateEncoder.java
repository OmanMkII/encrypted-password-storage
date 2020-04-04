package main.java.encoder;

import main.java.Cryptograph;

import java.util.Arrays;

/**
 * The private half of a key-pair set for the RSA encryption algorithm; it allows
 * the user to decrypt given ciphertext assuming it was encrypted with the paired
 * public keyset. As per RSA documentation, the set of public/private keys are the
 * only tools capable of encrypting and fully decrypting data, and were designed
 * for use in technology such as networking.
 *
 * WARNING: this is an abstraction of the RSA theory, it has been designed for
 * 			use in this program, and this program ONLY. Do not under any circum-
 * 			stances assume that it is capable of encrypting data for common use.
 *
 * 			This was designed merely as a way for this author to learn more about
 * 			the concept, and I cannot guarantee that data will be safe.
 *
 * @see Encoder for more information
 *
 * @author Eamon O'Brien.
 */
public class PrivateEncoder extends Encoder {

	private static final int[] E_VALUES = { 3, 5, 17, 257, 65537 };

	private int n;
	private int d;

	/**
	 * A primitive RSA encryption algorithm; this private key enables the program
	 * to decode an encrypted data stream (ciphertext) and should be kept private
	 * at all times to disallow decryption from outside sources.
	 *
	 * TODO: effective check for prime (p, q) values.
	 *
	 * @param p	the value p for the RSA private key base.
	 * @param q	the value q for the RSA private key base.
	 * @param e	the value e for the RSA key modulus.
	 * @throws IllegalArgumentException
	 * 			if p, q are equal, non-prime, or not greater than zero.
	 * 			if e is not within the set of legal E values.
	 */
	public PrivateEncoder(int p, int q, int e) throws IllegalArgumentException {
		if(p == q || p <= 0 || q <= 0) {
			throw new IllegalArgumentException("Invalid (p, q) pair.");
		} else if(!Arrays.asList(E_VALUES).contains(e)) {
			throw new IllegalArgumentException("Invalid E.");
		}
//		if(!p.isPrime() || !q.isPrime()) {
//			throw new IllegalArgumentException("(p, q) must be prime.");
//		}
		this.n = p * q;
		int fi = (p - 1) * (q - 1);
		this.d = Cryptograph.getModuloOne(e, fi);
	}

	/**
	 * A primitive RSA encryption algorithm; this private key enables the program
	 * to decode an encrypted data stream (ciphertext) and should be kept private
	 * at all times to disallow decryption from outside sources.
	 *
	 * @param n	the n value of the RSA private key.
	 * @param d	the d value of the RSA private key.
	 * @throws IllegalArgumentException
	 * 			if (n, d) are less than or equal to zero, or equal.
	 */
	public PrivateEncoder(int n, int d) throws IllegalArgumentException {
		if(n == d || n <= 0 || d <= 0) {
			throw new IllegalArgumentException("Invalid (p, q) pair.");
		}
		this.n = n;
		this.d = d;
	}

	@Override
	public String encryptText(String plaintext) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String decryptCiphertext(String ciphertext) {
		int i = 0;
		char[] plaintext = new char[ciphertext.length()];
		for(char ci : ciphertext.toCharArray()) {
			char ch = (char) (Math.pow(ci, this.d) % this.n);
			plaintext[i++] = ch;
		}
		return new String(plaintext);
	}

	/**
	 * Returns the value of n.
	 *
	 * @return the value of n.
	 */
	public int getN() {
		return this.n;
	}

	/**
	 * Returns the value of d.
	 *
	 * @return the value of d.
	 */
	public int getD() {
		return this.d;
	}

}
