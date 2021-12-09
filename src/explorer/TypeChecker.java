package explorer;

import java.io.IOException;

import com.drew.imaging.ImageProcessingException;

import exif.ExifReader;

/**
 * @author Alice MABILLE
 * Used is to check if a file is an image. Does not throw any exception, as those are managed by the calling classes.
 */
public class TypeChecker {
	private String[] imageTypes;
;
	
	public TypeChecker() {
    	imageTypes = new String[]{"png", "jpeg", "jpg"};
	}
	
	/**
	 * @return boolean : checks if the end of the specified path is an image-type filename extension.
	 */
	public boolean isImage(String path) {
		 for(int i=0; i<imageTypes.length; i++){
			if (path.endsWith(imageTypes[i]) | path.endsWith(imageTypes[i].toUpperCase())){
				return true;
			}
		}
		return false;
	}

	public String getType(String path) {
		try {
			return path.substring(path.length()-3).toLowerCase();
		} catch (Exception e) {
			return "no type";
		}
	}

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
