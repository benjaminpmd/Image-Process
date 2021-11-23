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

/**
 * @author @Benjamin PAUMARD
 * @version 2021.11.23 (0.5.3b)
 * @since November, 18th, 2021
 * @brief ExifReader contains all methods to check the properties of an image of type PNG, JPG, JPEG
 */

public class ExifReader {
    private String pathString;
    private File imageFile;
    private HashMap<String, String> exifData;

    public ExifReader(String path) {
        pathString = path;
        imageFile = new File(path);
        exifData = new HashMap<String, String>();
    }

    public String getFilename() throws FileNotFoundException, IllegalArgumentException {
        if (!imageFile.isFile()) {
            throw new FileNotFoundException();
        }
        if ((!pathString.endsWith(".png")) && (!pathString.endsWith(".jpg")) && (!pathString.endsWith(".jpeg"))) {
            throw new IllegalArgumentException();
        }
        return imageFile.getName();
    }

    public HashMap<String, String> getExif() throws FileNotFoundException, IOException {
        // Checking if file exists
        if (!imageFile.isFile()) {
            throw new FileNotFoundException();
        }
        // Trying to get Metadata then storing them into a HashMap
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
            for (Directory dir : metadata.getDirectories()) {
                for (Tag tag : dir.getTags()) {
                    exifData.put(tag.getTagName(), tag.getDescription());
                }
            }
        } catch (ImageProcessingException e) {
            // Exception while manipulating the file
            System.err.println(e);
        } catch (IOException e) {
            // Other exceptions related to the file
            throw new IOException(e);
        }
        return exifData;
    }
}
