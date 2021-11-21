package cli;

/**
 * This class contains all the methods that manages the cli outputs
 * The main method of the cli calls the Core class methods
 * 
 * @author Benjamin PAUMARD
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

    
}

