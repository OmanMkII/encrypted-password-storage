package test.java.generator;

import main.java.generator.PasswordGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class PasswordGeneratorTest {

    private PasswordGenerator generator;

    @Before
    public void beforeGeneratorTests() throws Exception {
        this.generator = new PasswordGenerator();
    }

    @Test
    public void tempFailTest() {
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLeetProbabilityLow() {
        String password = "newpassword";
        String leetPassword = this.generator.leetSpeak(password, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLeetProbabilityHigh() {
        String password = "newpassword";
        String leetPassword = this.generator.leetSpeak(password, 1.2);
    }

    @Test(expected = NullPointerException.class)
    public void testLeetNull() {
        String leetPassword = this.generator.leetSpeak(null, 0.5);
    }

    @Test(timeout = 500)
    public void testLeetSpeak() {
        String[] passwordRaw = {
                "password",
                "somethingnew",
                "raNdoMStrinG",
                "5omet!ingElse"
        };
        String[] passwordLeet = {
                "|>455\\v/0|2[)",
                "50|Y|37|-||/\\/6/\\/3\\v/",
                "|24/\\/[)0|Y|57|2|/\\/6",
                "50|Y|37|-|!/\\/63153"
        };
        double[] probabilities = { 1.0, 0.75, 0.5, 0.25 };
        // results
        String[] results = new String[5];
        double[] numbers = new double[5];
        double result = 0;
        // first one
        for(int i = 0; i < 5; i++) {
            results[i] = this.generator.leetSpeak(passwordRaw[0], probabilities[0]);
            int resultCount = 0;
            for(int j = 0; j < passwordRaw[0].length(); j++) {
                if(passwordRaw[0].charAt(j) == passwordLeet[0].charAt(j)) {
                    resultCount++;
                }
            }
            numbers[i] = resultCount / (1.0 * passwordRaw[0].length());
        }
        result = DoubleStream.of(numbers).sum() / 5.0;
        Assert.assertTrue(1.0 >= result);
        Assert.assertTrue(0.97 < result);
        // second
        for(int i = 0; i < 5; i++) {
            results[i] = this.generator.leetSpeak(passwordRaw[1], probabilities[1]);
            int resultCount = 0;
            for(int j = 0; j < passwordRaw[1].length(); j++) {
                if(passwordRaw[1].charAt(j) == passwordLeet[1].charAt(j)) {
                    resultCount++;
                }
            }
            numbers[i] = resultCount / (1.0 * passwordRaw[1].length());
        }
        result = DoubleStream.of(numbers).sum() / 5.0;
        Assert.assertTrue(1.0 >= result);
        Assert.assertTrue(0.97 < result);
        // third
        for(int i = 0; i < 5; i++) {
            results[i] = this.generator.leetSpeak(passwordRaw[2], probabilities[2]);
            int resultCount = 0;
            for(int j = 0; j < passwordRaw[2].length(); j++) {
                if(passwordRaw[2].charAt(j) == passwordLeet[2].charAt(j)) {
                    resultCount++;
                }
            }
            numbers[i] = resultCount / (1.0 * passwordRaw[2].length());
        }
        result = DoubleStream.of(numbers).sum() / 5.0;
        Assert.assertTrue(1.0 >= result);
        Assert.assertTrue(0.97 < result);
        // fourth
        for(int i = 0; i < 5; i++) {
            results[i] = this.generator.leetSpeak(passwordRaw[3], probabilities[3]);
            int resultCount = 0;
            for(int j = 0; j < passwordRaw[3].length(); j++) {
                if(passwordRaw[3].charAt(j) == passwordLeet[3].charAt(j)) {
                    resultCount++;
                }
            }
            numbers[i] = resultCount / (1.0 * passwordRaw[3].length());
        }
        result = DoubleStream.of(numbers).sum() / 5.0;
        Assert.assertTrue(1.0 >= result);
        Assert.assertTrue(0.97 < result);
    }
}
