package test.java.encoder;

import org.junit.*;

import main.java.encoder.*;

import java.lang.reflect.Array;
import java.util.Arrays;

public class PublicEncoderTest {

	@Test
	public void testGetN() {
		// first type
		PublicEncoder publicEncoder = new PublicEncoder(5, 7, 65537);
		Assert.assertEquals(5 * 7, publicEncoder.getN());
		// second type
	}

	@Test
	public void testGetE() {
		PublicEncoder publicEncoder = new PublicEncoder(5, 7, 65537);
		Assert.assertEquals(65537, publicEncoder.getE());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testDecryptText() {
		PublicEncoder publicEncoder = new PublicEncoder(5, 7, 65537);
		int[] ciphertext = { 5, 8, 11, 23, 5, 4 };
		String plaintext = publicEncoder.decryptCiphertext(Arrays.toString(ciphertext));
	}

	@Test
	public void testRawDecryption() {
		Assert.fail();
	}

	@Test
	public void testDecryption() {
		Assert.fail();
	}
}
