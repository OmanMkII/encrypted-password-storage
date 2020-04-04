package main.java.encoder;

import java.lang.reflect.Array;

/**
 * Abstract Package Encoder:
 * 
 * The primary concept of this project will be to base password file encoding on
 * existing RSA algorithms: by doing so, some master password will act as private
 * key to locally stored files that have been encrypted with a public key which
 * has also been stored locally.
 * 
 * This Encoder interface defines the abstract type of the encoding algorithm,
 * which can be split into private and public style sections, or combined for
 * one master segment.
 * 
 * @author Eamon O'Brien.
 */
public abstract class Encoder {

	private static final int[] SET_E = { 3, 5, 17, 257, 65537 };
	
	/**
	 * Takes a string of simple text and encrypts it given the parameters within
	 * the concrete encoder using standard RSA public/private encryption.
	 * 
	 * @require	the given text is simple readable characters in UTF8 or ASCII
	 * 			formats, is not empty and is a non null entry.
	 * @ensure	the returned ciphertext is non null and follows all rules of RSA
	 * 			encryption to allow third party decryption with ciphertext and
	 * 			private key pair.
	 * @throws UnsupportedOperationException
	 * 			where the user has no access to the public key to encrypt data
	 * 			with.
	 */
	public abstract String encryptText(String plaintext)
			throws UnsupportedOperationException;
	
	/**
	 * Takes ciphertext in the form of a primitive integer array and decrypts
	 * each into a character to form a new String using standard RSA public/
	 * private encryption.
	 * 
	 * @require	the given ciphertext is a non-negative, non-null array of
	 * 			integers
	 * @ensure	the proper original String is returned after being decoded
	 * @throws UnsupportedOperationException
	 * 			where the user has no access to the private key to decrypt data
	 * 			with.
	 */
	public abstract String decryptCiphertext(String ciphertext)
			throws UnsupportedOperationException;
	
	/**
	 * Takes an input of type T array and a fixed length to extend it to,
	 * and copies all data across to maintain continuity.
	 * @param <T>
	 * 
	 * @require	the input length is greater than the length of the input array,
	 * 			the input array is not null, and does not contain null values.
	 * @ensure	the output is an extended primitive int array containing the
	 * 			same values as the input, with excess cells set at zero.
	 */
	public static <T> T[] extendArray(Class<T> c, T[] input, int length) {
		@SuppressWarnings("unchecked")
		final T[] array = (T[]) Array.newInstance(c, length);
		for(int i = 0; i < input.length; i++) {
			array[i] = (T) input[i];
		}
		return array;
	}

}
