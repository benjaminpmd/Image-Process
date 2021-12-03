package cli;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import com.drew.imaging.ImageProcessingException;

import exif.ExifReader;
import explorer.FileLister;
import steganography.*;

/**
 * This class contains all the methods that manages the cli outputs
 * The main method of the cli calls the Core class methods
 * 
 * @authors Alice MABILLE, Benjamin PAUMARD
 * @version 2021.11.21d (0.1.2)
 * @since November, 21th 2021
 */


public class Core {
	/**
	 * Method to print the help content.
	 */
    public void printHelp() {
        System.out.println("\n"
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

    /**
     * print the EXIF data of an specified image.
     * @param path the path to the image you want to check
     */
	public void printExif(String path) {
		HashMap<String, String> metadata = new HashMap<String, String>();
		try {
			ExifReader reader = new ExifReader(path);
			metadata = reader.getExif();
			String imageName = reader.getFilename();
			System.out.println("\n\nEXIF Data for the following file : " + imageName + "\n\n");
		} catch (FileNotFoundException e) {
			System.err.println("The path provided does not lead to an image.");
		} catch (IllegalArgumentException e) {
			System.err.println("The path provided does not lead to an image.");
		}catch (ImageProcessingException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		for (String key: metadata.keySet()) {
			System.out.println(key + " : " + metadata.get(key));
		}
	}

	/**
	 * Method to print all png, jpg and jpeg images in a directory.
	 * @param path the directory you want to check
	 */
	public void printExplorer(String path) {
		ArrayList<String> images = new ArrayList<String>();
		FileLister lister = new FileLister(path);
		try {
			images = lister.exploreImages();
			for (String image : images) {
				System.out.println(image);
			}
		} catch (IllegalArgumentException e) {
			System.err.println("The path provived does not lead to a directory.");
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	public void hideMessage(String path, String message) {
		try {
			ImageWriter writer = new ImageWriter(path);
			writer.hideMessage(message);
			boolean succes = writer.saveImage();
			if (succes) {
				System.out.println("Message succesfully hidden in the image: " + path);
			}
			else System.out.println("Error: image update have failed.");
		} catch (NoSuchElementException e) {
			System.err.println("Error: This image does not contain any message.");
		} catch (IOException e) {
			System.err.println("Error: Image data retrieval have failed.");
		}
	}

	public void readMessage(String path) {
		try {
			ImageReader reader = new ImageReader(path);
			System.out.println(reader.readMessage());
		} catch (IOException e) {
			System.err.println("Error: Image data retrieval have failed.");
		}
	}
}

