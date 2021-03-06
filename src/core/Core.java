package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import com.drew.imaging.ImageProcessingException;

import exif.ExifReader;
import explorer.FileLister;
import steganography.ImageReader;
import steganography.ImageWriter;

/**
 * This class contains all the methods that manages the cli outputs
 * The main method of the cli calls the Core class methods
 * 
 * @author Alice MABILLE, Benjamin PAUMARD
 * @version 2021.12.09 (1.0.0)
 * @since November, 21th 2021
 */
public class Core {
	/**
	 * The help of the cli
	 * @return the help content of the cli version of the app
	 */
    public String getCliHelpContent() {
        return ("\n"
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
	 * The help of the gui
	 * @return the help content of the gui version of the app
	 */
    public String getGUIHelp() {
        return ("\n"
        	+ "Write your message in the left rectangle and press the button \"hide the message.\"\n\n"
        	+ "If the message is correctly saved a confirmation will appear on the right panel.\n\n"
        	+ "In case the image you selected to hide a message inside is a JPEG type, a copy will be created in PNG format."
		);
    }

    /**
	 * Get a list of some of the EXIF data of the provided image.
	 * @param path the path of a PNG, JPG or JPEG image.
	 * @return the EXIF data of the path provided.
	 * @see exif.ExifReader
	 */
	public String getExifContent(String path) {
		HashMap<String, String> metadata = new HashMap<String, String>();
		String retuString;
		try {
			ExifReader reader = new ExifReader(path);
			metadata = reader.getExif();
			String imageName = reader.getFilename();
			retuString = "\n\nEXIF Data for the following file : " + imageName + "\n\n";
			for (String key: metadata.keySet()) {
				retuString += (key + " : " + metadata.get(key) + "\n");
			}
		} catch (ImageProcessingException e) {
			retuString = e.getMessage();
		} catch (IllegalArgumentException e) {
			retuString = e.getMessage();
		} catch (FileNotFoundException e) {
			retuString = e.getMessage();
		} catch (IOException e) {
			retuString = e.getMessage();
		}
		return retuString;
	}

	/**
	 * Method to get a list of all png, jpg and jpeg images in a directory.
	 * @param path the directory you want to check.
	 * @return all PNG, JPG and JPEG images in the provided directory.
	 * @see explorer.FileLister
	 */
	public String getExplorerContent(String path) {
		ArrayList<String> images = new ArrayList<String>();
		FileLister lister = new FileLister(path);
		String retuString;
		try {
			retuString = ("\n\n Here are all images in the directory: " + path + "\n");
			images = lister.exploreImages();
			for (String image : images) {
				retuString += (image + "\n");
			}
		} catch (IllegalArgumentException e) {
			retuString = e.getMessage();
		} catch (FileNotFoundException e) {
			retuString = e.getMessage();
		}
		return retuString;
	}

	/**
	 * This method allow to hide a message in an image, but warning, if the provided image is a JPG or JPEG image is provided,
	 * it will be copied into a new PNG image due to compression ratio of JPG images.
	 * @param path the path of a PNG, JPG or JPEG image.
	 * @param message the message you want to hide in the image.
	 * @return if the image update is succesfull or not.
	 * @see steganography.ImageWriter
	 */
	public String hideMessage(String path, String message) {
		String retuString;
		try {
			ImageWriter writer = new ImageWriter(path);
			writer.hideMessage(message);
			boolean succes = writer.saveImage();
			if (succes) {
				retuString = "Message succesfully hidden";
				if (!writer.getImageType().equals("png")) {
					retuString += "\nWARNING: due to compression limitation, in order to hide a message, a PNG image of the same name have been created.";
				}
			}
			else retuString = "Error: image update have failed.";
		} catch (IllegalArgumentException e) {
			retuString = e.getMessage();
		} catch (IOException e) {
			retuString = e.getMessage();
		}
		return retuString;
	}

	/**
	 * Method to get if the image contains a message, and if so, the message contained.
	 * @param path the path of a PNG, JPG or JPEG image.
	 * @return the message or an error.
	 * @see steganography.ImageReader
	 */
	public String readMessage(String path) {
		String retuString;
		try {
			ImageReader reader = new ImageReader(path);
			retuString = reader.readMessage();
		} catch (IllegalArgumentException e) {
			retuString = e.getMessage();
		} catch (NoSuchElementException e) {
			retuString = e.getMessage();
		} catch (IOException e) {
			retuString = e.getMessage();
		}
		return retuString;
	}
}

