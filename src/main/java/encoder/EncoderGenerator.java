package main.java.encoder;

import main.java.Cryptograph;

public class EncoderGenerator {
	
	private int p;
	private int q;
	
	// E = 2^16 + 1; these values are chosen before computation
	// https://www.di-mgt.com.au/rsa_alg.html#note2
	private static final int E = 65537;
	
	private int n;
	private int d;
	private int fi;
	
	public EncoderGenerator(int p, int q) {
		// save p, q
		this.p = p;
		this.q = q;
		// calculate others
		this.n = this.p * this.q;
		this.fi = (this.p - 1) * (this.q - 1);
		this.d = Cryptograph.getModuloOne(EncoderGenerator.E, this.fi);
	}
	
	public PublicEncoder generatePublic() {
		return new PublicEncoder(this.n, EncoderGenerator.E);
	}
	
	public PrivateEncoder generatePrivate() {
		return new PrivateEncoder(this.n, this.d);
	}

}
