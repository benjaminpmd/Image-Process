package cli;

import java.util.HashMap;

import exif.ExifReader;

/**
 * This class contains all the methods that manages the cli outputs
 * The main method of the cli calls the Core class methods
 * 
 * @authors Alice MABILLE, Benjamin PAUMARD
 * @version 2021.11.21d (0.1.2)
 * @since November, 21th 2021
 */


public class Core {
    public void printHelp() {
        System.out.println("\n\n"
        	+ "List of the existing commands\n\n"
        	+ "Use java -jar cli.jar\n"
        	+ "[-h or --help] Provide description of the available options for the application.\n"
        	+ "[-d <Directory path>] Display all images of type PNG, JPG and JPEG in the provided directory.\n"
        	+ "[-f <Image path>] Display the EXIF data of the provided image.\n"
        	+ "[-s <ASCII text>] Specify a text to hide in an image. (require -f option)\n"
        	+ "[-e] Extract a message from an image. (require -f option)\n"
        	+ "\n"
        	+ "You also can run the graphical version of the application by using java -jar gui.jar\n"
        );
    }

	public void printExif(String path) {
		HashMap<String, String> metadata = new HashMap<String, String>();
		try {
			ExifReader reader = new ExifReader(path);
			metadata = reader.getExif();
			String imageName = reader.getFilename();
			System.out.println("\n\nEXIF Data for the following file : " + imageName + "\n\n");
		} catch (Exception e) {
			System.err.println("Something went wrong");
			System.err.println(e);
		}
		for (String key: metadata.keySet()) {
			System.out.println(key + " : " + metadata.get(key));
		}
	}
}

