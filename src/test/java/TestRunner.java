package test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.java.command.*;
import test.java.encoder.*;
import test.java.generator.*;

@RunWith(Suite.class)
@SuiteClasses({
		// main.java
		CrytographTest.class,
		// main.java.command
		CommandLineInterfaceTest.class,
		DatabaseInterfaceTest.class,
		UserTest.class,
		// main.java.encoder
		PrivateEncoderTest.class,
		PublicEncoderTest.class,
		UTFEncoderTest.class,
		EncoderGeneratorTest.class,
		// main.java.generator
		PasswordGeneratorTest.class
})
public class TestRunner {

}
