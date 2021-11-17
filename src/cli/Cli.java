/**
 * This class contains the running cli of the app
 */
package cli;

/**
 * @author Benjamin PAUMARD
 *
 */

class Cli {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			// If no options provided, show error and help option.
			System.err.println("You must pass at least one option, for more information, use -h or --help.");
		}
		else if ((args[0] == "-h") || (args[0] == "--help")) {}		
	}
}
