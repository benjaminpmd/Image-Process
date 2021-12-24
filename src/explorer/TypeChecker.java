package explorer;

import java.io.IOException;

import com.drew.imaging.ImageProcessingException;

import exif.ExifReader;

/**
 * Used is to check if a file is an image. Does not throw any exception, as those are managed by the calling classes.
 * @author Alice MABILLE
 * @version 1.0.0
 * @since Decemeber 3rd, 2021
 */
public class TypeChecker {
	private String[] imageTypes;

	/**
	 * Constructor of the TypeChecker class, it initialises the allowed files extensions.
	 */
	public TypeChecker() {
    	imageTypes = new String[]{"png", "jpeg", "jpg"};
	}
	
	/**
	 * @param path the path of the file you want to check
	 * @return boolean checks if the end of the specified path is an image-type filename extension.
	 */
	public boolean isImage(String path) {
		 for(int i=0; i<imageTypes.length; i++){
			if (path.endsWith(imageTypes[i]) | path.endsWith(imageTypes[i].toUpperCase())){
				return true;
			}
		}
		return false;
	}

	/**
	 * @param path the file you want to get the extension type of.
	 * @return the extension type of file.
	 */
	public String getType(String path) {
		try {
			return path.substring(path.length()-3).toLowerCase();
		} catch (Exception e) {
			return "no type";
		}
	}

	/**
	 * @param path the file you want to get the MIME type of.
	 * @return the mime type of file.
	 */
	public String getMimeType(String path) {
		ExifReader exifReader;
		try {
			exifReader = new ExifReader(path);
			return exifReader.getMimeType();
		} catch (IllegalArgumentException | ImageProcessingException | IOException e) {
			return null;
		}
	}
}
