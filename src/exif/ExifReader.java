package exif;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import explorer.TypeChecker;

/**
 * @author Benjamin PAUMARD
 * @version 2021.11.30 (0.7.0b)
 * @since November, 18th, 2021
 * ExifReader contains all methods to get EXIF data of an image
 */
public class ExifReader {
    private File imageFile;
    private HashMap<String, String> exifData;
    private TypeChecker typeChecker;
    
    public ExifReader(String path) {
        imageFile = new File(path);
        exifData = new HashMap<String, String>();
        typeChecker = new TypeChecker();
    }

    /**
     * Returns the name of the image
     * @return String The name of the current image
     * @throws FileNotFoundException
     * @throws IllegalArgumentException
     */
    public String getFilename() throws FileNotFoundException, IllegalArgumentException {
        if (!imageFile.isFile()) {
            throw new FileNotFoundException();
        }
        else if (!typeChecker.image(imageFile.getPath())) {
            throw new IllegalArgumentException();
        }
        return imageFile.getName();
    }

    /**
     * Returns the EXIF data the image passed during the creation of the ExifReader object.
     * @return HashMap<String, String> The EXIF data of an image.
     * @throws IllegalArgumentException
     * @throws ImageProcessingException 
     * @throws IOException 
     */
    
    public HashMap<String, String> getExif() throws IllegalArgumentException, ImageProcessingException, IOException {
        if (!imageFile.isFile()) {
            throw new FileNotFoundException("The path provided does not lead to an image.");
        }
        else if (!typeChecker.image(imageFile.getPath())) {
            throw new IllegalArgumentException("The path provided does not lead to an image.");
        }
        String[] requiredData =  {
        	"Image Width",
            "Image Height", 
            "Detected MIME Type",
            "Color Type",
            "Color Space",
            "File Modified Date",
        };
        // Trying to get Metadata then storing them into a HashMap
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
            for (Directory dir : metadata.getDirectories()) {
                for (Tag tag : dir.getTags()) {
                    for (String requiredTag : requiredData) {
                        if (requiredTag.equals(tag.getTagName())) {
                            exifData.put(tag.getTagName(), tag.getDescription());
                        }
                    }
                }
            }
        } catch (ImageProcessingException e) {
            throw new ImageProcessingException(e.getMessage());
        } catch (IOException e) {
        	throw new IOException(e.getMessage());
        }
        if (exifData.get("Detected MIME Type").startsWith("image")) {
            return exifData;
        }
        else {
            throw new IllegalArgumentException("The path provided does not lead to an image.");
        }
    }
}
