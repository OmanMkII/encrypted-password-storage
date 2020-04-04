package test.java.encoder;

import main.java.encoder.PrivateEncoder;
import org.junit.*;

import main.java.encoder.Encoder;
import main.java.encoder.PublicEncoder;

public class PrivateEncoderTest {

	@Test
	public void testBadInitA() {
		PrivateEncoder privateKey;
		// bad p
		try {
			privateKey = new PrivateEncoder(0, 5, 5);
			Assert.fail();
		} catch(IllegalArgumentException e) {
			// success
		}
		// bad q
		try {
			privateKey = new PrivateEncoder(3, -1, 5);
			Assert.fail();
		} catch(IllegalArgumentException e) {
			// success
		}
		// equivalent p, q
		try {
			privateKey = new PrivateEncoder(5, 5, 5);
			Assert.fail();
		} catch(IllegalArgumentException e) {
			// success
		}
		// bad e
		try {
			privateKey = new PrivateEncoder(3, 5, 6);
			Assert.fail();
		} catch(IllegalArgumentException e) {
			// success
		}
	}

	@Test
	public void testBadInitB() {
		PrivateEncoder privateKey;
		// bad n
		try {
			privateKey = new PrivateEncoder(0, 5);
			Assert.fail();
		} catch(IllegalArgumentException e) {
			// success
		}
		// bad d
		try {
			privateKey = new PrivateEncoder(3, 0);
			Assert.fail();
		} catch(IllegalArgumentException e) {
			// success
		}
		// n == d
		try {
			privateKey = new PrivateEncoder(5, 5);
			Assert.fail();
		} catch(IllegalArgumentException e) {
			// success
		}
	}

	@Test
	public void testGetN() {
		int p = 5, q = 7;
		PrivateEncoder encoder = new PrivateEncoder(p, q, 65537);
		Assert.assertEquals(p * q, encoder.getN());
	}

	@Test
	public void testGetD() {
		int p = 5, q = 7;
		PrivateEncoder encoder = new PrivateEncoder(p, q, 65537);
		Assert.assertEquals((p - 1) * (q - 1), encoder.getD());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testDecryptCiphertext() {
		PrivateEncoder publicEncoder = new PrivateEncoder(5, 7, 65537);
		String plaintext = "text";
		String ciphertext = publicEncoder.encryptText(plaintext);
	}

	@Test
	public void testRawEncryption() {
		Assert.fail();
	}

	@Test
	public void testEncryption() {
		Assert.fail();
	}

}
