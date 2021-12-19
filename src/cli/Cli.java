package cli;

import core.Core;

/**
 * This class contains all the methods that manages the cli outputs
 * The main method of the cli calls the Core class methods
 * 
 * @author Benjamin PAUMARD, Alice MABILLE
 * @version 2021.12.19 (1.0.0)
 * @since November, 21th 2021
 */
class Cli {
	/**
	 * Static main method running the cli
	 * @param args arguments passed by the user
	 */
	public static void main(String[] args) {
		Core core = new Core();
		if (args.length == 0) {
			System.err.println("You must pass at least one option, for more information, use -h or --help.");
		}
		else if (args.length == 1) {
			if ((args[0].equals("-h")) || (args[0].equals("--help"))) {
				System.err.println(core.getCliHelpContent());
			}
			else if ((args[0].equals("-d")) || (args[0].equals("-f"))) {
				System.err.println("Error: An argument is missing. You can check available options using -h or --help");
			}
			else {
				System.err.println("Error: unknown option \"" + args[0] + "\". You can check available options using -h or --help");
			}
		}
		else if (args.length == 2) {
			if (args[0].equals("-f")) {
				System.err.println(core.getExifContent(args[1]));
			}
			else if (args[0].equals("-d")) {
				System.err.println(core.getExplorerContent(args[1]));
			}
			else {
				System.err.println("Error: unknown option \"" + args[1] + "\". You can check available options using -h or --help");
			}
		}
		else if (args.length == 3) {
			if (args[0].equals("-f")) {
				if (args[2].equals("-e")) {
					System.err.println(core.readMessage(args[1]));
				}
				else {
					System.err.println("Error: unknown option \"" + args[2] + "\". You can check available options using -h or --help");
				}
			}
		}
		else if (args.length == 4) {
			if ((args[0].equals("-f")) && (args[2].equals("-s"))) {
				System.err.println(core.hideMessage(args[1], args[3]));
			}
			else if ((args[0].equals("-s")) && (args[2].equals("-f"))) {
				System.err.println(core.hideMessage(args[3], args[1]));
			}
			else {
				System.err.println("Error: unknown option \"" + args[2] + "\". You can check available options using -h or --help");
			}
		}
		else {
			System.err.println("Error: too many arguments provided. You can check available options using -h or --help");
		}
	}
}