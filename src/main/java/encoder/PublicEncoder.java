package main.java.encoder;

import java.util.Arrays;

/**
 * The public section of the RSA key-pair set; this segment has been designed to
 * encrypt data for storage (rather than the common transport), and the paired
 * private key can decrypt the same data for display & use.
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
public class PublicEncoder extends Encoder {
	
	private int n;
	private int e;

	/**
	 * A primitive RSA public key for encrypting data streams ("plaintext") for
	 * storage or use in this program, takes the values of p, q, and e to generate
	 * a public key.
	 *
	 * @require	p, q are prime integers greater than 1, and are not equal.
	 * 			e is part of the set E, as per RSA guidelines.
	 * @ensure 	the two final values n, e satisfy the RSA encryption guidelines.
	 */
	public PublicEncoder(int p, int q, int e) {
		this.n = p * q;
		this.e = e;
	}

	/**
	 * A primitive RSA public key for encrypting data streams ("plaintext") for
	 * storage or use in this program, takes the values of n and e to generate
	 * a public key.
	 *
	 * @require	n and e suit the RSA encryption guidelines.
	 * @ensure 	the two final values n, e satisfy the RSA encryption guidelines.
	 */
	public PublicEncoder(int n, int e) {
		this.n = n;
		this.e = e;
	}

	@Override
	public String encryptText(String plaintext) {
		int i = 0;
		char[] ciphertext = new char[plaintext.length()];
		for(char ch : plaintext.toCharArray()) {
			// encode
			char ci = (char) (Math.pow(ch, e) % this.n);
			ciphertext[i++] = ci;
		}
		// return m^e % n
		return Arrays.toString(ciphertext);
	}

	@Override
	public String decryptCiphertext(String ciphertext)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the local value of n.
	 *
	 * @return the value of n.
	 */
	public int getN() {
		return this.n;
	}

	/**
	 * Returns the local value of e.
	 *
	 * @return the value of e.
	 */
	public int getE() {
		return this.e;
	}

}
