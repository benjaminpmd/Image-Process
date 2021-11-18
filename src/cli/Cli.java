/**
 * This class contains the running cli of the app
 */
package cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Benjamin PAUMARD
 *
 */

class Cli {
	public static void printHelp() {
		Path helpFile = Paths.get("HelpContent.txt");
		try {
			String helpContent = Files.readString(helpFile);
			System.out.println(helpContent);
		} catch (IOException e) {
			System.err.println("Something wrong happened :/");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			// If no options provided, show error and help option.
			System.err.println("You must pass at least one option, for more information, use -h or --help.");
		}
		else if ((args[0].equals("-h")) || (args[0].equals("--help"))) {
			printHelp();
		}
	}
}
