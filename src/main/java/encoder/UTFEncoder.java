package main.java.encoder;

/**
 * A local encoder primed for using UTF-16 text style to encode plaintext, and
 * then print to a text document or the like: in this program it will be sent to
 * a secondary database [I really hope they take UTF-16] for storage.
 *
 * The locally stored hashPassword will ensure that any further entries of the
 * password are correct before allowing the entry to be transformed to ciphertext
 * and sent for storage.
 *
 * @author Eamon O'Brien.
 */
public class UTFEncoder extends PrivateEncoder {

	private static final int E = 65537;
	
	// stores password locally as hashcode to speed up checking
	private String hashPassword;
	// a local copy of the public key
	PublicEncoder publicKey;
	
	/**
	 * Creates a new Public and Private keyset from a given public set (n, d),
	 * and a user given password, where (n, e) is found from the product of all
	 * chars in the password, in modulus of n.
	 * 
	 * This set is modified to ensure that UTF-16 encoded text is available for
	 * printing when required.
	 *
	 * @require	the password is not null and satisfies all higher order requirements
	 * 			for complexity.
	 * 			p and q satisfy all requirements regarding RSA p, q values.
	 * @ensure	the returned public and private keys allow for UTF-16 encoding,
	 * 			the private key remains safe, and the password will be stored
	 * 			as ciphertext.
	 */
	public UTFEncoder(String password, int p, int q) {
		super(p, q, E);
		this.publicKey = new PublicEncoder(this.getN(), E);
		this.hashPassword = publicKey.encryptText(password);
	}
	
	@Override
	public String decryptCiphertext(String ciphertext) {
		return super.decryptCiphertext(ciphertext);
	}
	
	@Override
	public String encryptText(String plaintext) {
		return this.publicKey.encryptText(plaintext);
	}

	/**
	 * Returns the hash encoded password to ensure that the user's password will
	 * not be distributed, yet allows for verification when required.
	 *
	 * @return the ciphertext of the user's password.
	 */
	public String getHashPassword() {
		return this.hashPassword;
	}

	/**
	 * Returns the public key to outside sources to allow for other classes to
	 * encode data when needed.
	 *
	 * @return the public key.
	 */
	public PublicEncoder getPublicKey() {
		return this.publicKey;
	}

}
