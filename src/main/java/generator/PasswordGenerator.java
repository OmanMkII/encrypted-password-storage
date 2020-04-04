package main.java.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * A class to house the generation of passwords based on common methods, including
 * the ever present random letters and numbers, the more secure dictionary based
 * amalgamation, and a variety of other methods for fun and profits.
 *
 * Each style of password has a little commentary from myself, a self-proclaimed
 * person of importance in general knowledge of nothing in particular.
 *
 * @author Eamon O'Brien.
 * @version 1.0
 */
public class PasswordGenerator {

	/**
	 * An interior class defined to handle the reading of a dictionary file for
	 * retrieving words to generate new passwords with: operates based on the
	 * assumption that the cost of reading a few (hundred) lines is rather cheap,
	 * compared to constructing a Trie and importing literally every word in the
	 * English language.
	 *
	 * Requires the file to be of the format:
	 *
	 * word1\n
	 * word2\n
	 * ...
	 */
	private static class FileReader {

		private int filesize = -1;
		private int lineno = 0;
		private String path;
		private Scanner file;

		/**
		 * Constructs a new Files Reader class based on a file from the given path.
		 *
		 * @param path	the path to the dictionary file.
		 * @throws FileNotFoundException
		 * 				if the given file cannot be opened.
		 */
		public FileReader(String path) throws FileNotFoundException {
			this.path = path;
			this.file = new Scanner(new File(this.path));
		}

		/**
		 * Constructs a new Files Reader class based on a file from the given path
		 * and opens at a specific line number.
		 *
		 * @param path	the path to the dictionary file.
		 * @throws FileNotFoundException
		 * 				if the given file cannot be opened.
		 */
		public FileReader(String path, int lineno) throws IOException {
			this(path);
			skipToLine(lineno);
		}

		/**
		 * Skips to the specified line of the file currently being read: if already
		 * beyond said line will re-open the file and read the line specified.
		 *
		 * @param nthLine	the line number requested, indexed from 0.
		 * @throws IOException
		 * 					if the given line number does not exist
		 */
		private void skipToLine(int nthLine) throws IOException {
			// ensure it exists
			if(nthLine >= getFileSize()) {
				throw new IOException("Line request exceeds file size.");
			}
			// case: already passed
			if(nthLine < this.lineno) {
				this.file.close();
				this.file = new Scanner(new File(this.path));
			}
			// read until that line
			while(this.lineno != nthLine && this.file.hasNextLine()) {
				this.lineno++;
				this.file.nextLine();
			}
			// ensure
			if(!this.file.hasNextLine()) {
				throw new IOException("Unexpected EOF");
			}
		}

		/**
		 * Gets the number of lines in the existing file to ensure that the program
		 * will not exceed the EOF unexpectedly.
		 *
		 * @return the number of lines in the opened file.
		 * @throws IOException
		 * 				because Java told me it might.
		 */
		public int getFileSize() throws IOException {
			if(this.filesize == -1) {
				int lineno = this.lineno;
				while(this.file.hasNextLine()) {
					this.file.nextLine();
				}
				// open file again
				int size = this.lineno;
				skipToLine(lineno);
				return size;
			} else {
				return this.filesize;
			}
		}

		/**
		 * Reads the line following the line last read.
		 *
		 * @return the next line of the file.
		 */
		public String readNextLine() {
			this.lineno++;
			return file.nextLine();
		}

		/**
		 * Reads a specific line from the file, either continuing until that line
		 * is found, or re-opening the file to reach it again.
		 *
		 * @param nthLine	the line number requested.
		 * @return the word existing on the nth line.
		 * @throws IOException
		 * 					if the given number exceeds the file limit, or is
		 * 					below 0.
		 */
		public String readLine(int nthLine) throws IOException {
			if(nthLine < 0) {
				throw new IOException("Must be a positive integer.");
			} else {
				skipToLine(nthLine);
				return readNextLine();
			}
		}

		/**
		 * Returns a random word from the English dictionary, based on the text
		 * file that was given to the constructor of this class.
		 *
		 * @return a random word from the file source.
		 */
		public String newRandomWord() {
			String word = null;
			while(word == null) {
				try {
					word = readLine((int) (Math.random() * this.filesize));
				} catch(IOException e) {
					// retry
				}
			}
			return word;
		}
		
	}

	// static location of inbuilt dictionary
	public static final String DICTIONARY_PATH = null;

	// local variables
	private FileReader reader;

	/**
	 *
	 * @param filepath
	 * @throws IOException
	 */
	public PasswordGenerator(String filepath) throws IOException {
//		this.reader = new FileReader(filepath);
	}

	public PasswordGenerator() throws IOException {
		this(DICTIONARY_PATH);
	}

	private static final int ASCII_OFFSET = 33;
	private static final int ASCII_RANGE = 126 - 33;

	/**
	 * Generates a rnadom string of ASCII text for a given number of characters,
	 * does not attempt to ensure complexity in any manner.
	 *
	 * Common hearsay dictates that although randomly generated strings are difficult
	 * to remember (and thus difficult for people to steal), having to write such
	 * passwords down opens them to physical theft, and their often shorter length
	 * makes raw attacks more possible.
	 *
	 * @param size	the raw numbers of characters requested.
	 * @return a collection of random characters from the ASCII primitive array.
	 */
	public static String randomChars(int size) {
		char[] password = new char[size];
		for(int i = 0; i < size; i++) {
			password[i] = (char) (ASCII_RANGE * Math.random() + ASCII_OFFSET);
		}
		return new String(password);
	}
	
	// a random string of characters from UTF U+0021 (!) to ..?
	// TODO: get all characters available on Android/iOS keyboards
	public static String advRandomChars(int size) {
		return null;
	}

	/**
	 * Retrieves an array of words from the dictionary for assembly into password
	 * types including raw arrays and combinations.
	 *
	 * @require	the integer words is greater than zero, and within reasonable
	 * 			memory bounds.
	 * @ensure	the String array returned has no duplicates, and minimal overlap
	 * 			such as common pre- or suffixes for maximum complexity.
	 */
	private List<String> dictionary(int words) {
		List<String> password = new ArrayList<String>();
		for(int i = 0; i < words; i++) {
			String newWord = this.reader.newRandomWord();
			while(password.contains(newWord)) {
				newWord = this.reader.newRandomWord();
			}
			password.add(newWord);
		}
		return password;
	}

	/**
	 * Builds a combination of dictionary words of a given number ensuring that
	 * each word has minimal similarity with problems such as extensions and prefixes.
	 *
	 * These passwords are often more complex and difficult for dictionary attacks
	 * to break, however their source being the English dictionary does have its
	 * own flaws. They are however much easier for humans to remember, making this
	 * style a more manageable password for the general population.
	 *
	 * @param words	the number of words for the password.
	 * @return a single string of concatenated words.
	 */
	public String dictionaryCombination(int words) {
		return String.join("", dictionary(words));
	}

	/**
	 * Enhances the simple concept of collective words to include random characters
	 * between words for additional strength against dictionary attacks.
	 *
	 * Although these are harder for humans to remember, their similarity to the
	 * concept of other approaches allows them to be better remembered, and the
	 * inclusion of random characters increases strength against dictionary attacks.
	 *
	 * @param minLength	the minimum length of the password.
	 * @param words		the number of words to build the password from.
	 * @return	a dictionary based password with other characters thrown in for
	 * 			good measure.
	 */
	public static String combination(int minLength, int words) {
		
		// given N words * M min characters fit inside L
		
		// generate N words with placement (End of last word + Random) < maximum
		//				--> [factor of N(words), N(char length)]
		//		given that all words can fit with >= 1(?) spaces
		//		generate characters for gaps
		//		return full password
		
		return null;
	}

	// the map of "L33t 5p3ak" characters, an outdated internet trend that was never cool
	private static final Map<Character, String> LEET_MAP;
	static {
		Map<Character, String> baseMap = new HashMap<Character, String>();
		// base help
		baseMap.put('a', "4");
		baseMap.put('b', "13");
		baseMap.put('c', "(");
		baseMap.put('d', "[)");
		baseMap.put('e', "3");
		baseMap.put('f', "|=");
		baseMap.put('g', "6");
		baseMap.put('h', "|-|");
		baseMap.put('i', "|");
		baseMap.put('j', ".]");
		baseMap.put('k', "|<");
		baseMap.put('l', "1");
		baseMap.put('m', "|Y|");
		baseMap.put('n', "/\\/");
		baseMap.put('o', "0");
		baseMap.put('p', "|>");
		baseMap.put('q', "0");
		baseMap.put('r', "|2");
		baseMap.put('s', "5");
		baseMap.put('t', "7");
		baseMap.put('u', "[_]");
		baseMap.put('v', "\\/");
		baseMap.put('w', "\\v/");
		baseMap.put('x', "}{");
		baseMap.put('y', "'/");
		baseMap.put('z', "2");

		LEET_MAP = Collections.unmodifiableMap(baseMap);
	}

	/**
	 * Transforms some existing characters in a string to L33t characters based
	 * on the given probability.
	 *
	 * Despite looking rather stupid, this internet trend sourced from the depths
	 * of embarrassment lends a hand due to its inherent cipher. Although merely
	 * a visual cipher, it's another handy element that can be added to these new
	 * passwords for a bit of extra oomph.
	 *
	 * I just hope rule #34 isn't true.
	 *
	 * @param password		the plaintext password to be transformed.
	 * @param probability	the given probability from 0 < p <= 1 that a character
	 *                      will be replaced.
	 * @return a transformed string of partial L33t 5p3ak. Ugh.
	 * @throws NullPointerException
	 * 						if the password given is null.
	 * @throws IllegalArgumentException
	 * 						if the probability given is not within the bounds.
	 */
	public static String leetSpeak(String password, double probability) throws IllegalArgumentException {
		if(password == null) {
			throw new NullPointerException("Password string required.");
		} else if(probability > 1.0 || probability <= 0.0) {
			throw new IllegalArgumentException(String.format("Invalid probability %f.", probability));
		}
		StringBuilder leetPassword = new StringBuilder();
		for(char c : password.toCharArray()) {
			double p = Math.random();
			if(p > probability && LEET_MAP.containsKey(Character.toString(c))) {
				leetPassword.append(LEET_MAP.get(c));
			} else {
				leetPassword.append(c);
			}
		}
		// return new password
		return leetPassword.toString();
	}
	
}
