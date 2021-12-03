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
    private HashMap<String, String> exif;
    private String[] requiredData =  {
        "Image Width",
        "Image Height", 
        "Detected MIME Type",
        "Color Type",
        "Color Space",
        "File Modified Date",
        "File Size",
        "Model",
        "Date/Time",
        "Expected File Name Extension",
        "Detected File Type Name"
    };
    
    public ExifReader(String path) throws IllegalArgumentException, ImageProcessingException, IOException {
        TypeChecker typeChecker;
        imageFile = new File(path);
        exif = new HashMap<String, String>();
        typeChecker = new TypeChecker();
        if (!imageFile.isFile()) {
            throw new FileNotFoundException("The path provided does not lead to an image.");
        }
        else if (!typeChecker.isImage(imageFile.getPath())) {
            throw new IllegalArgumentException("The path provided does not lead to an image.");
        }
        extractExif();
    }

    /**
     * Returns the EXIF data the image passed during the creation of the ExifReader object.
     * @return HashMap<String, String> The EXIF data of an image.
     * @throws IllegalArgumentException
     * @throws ImageProcessingException 
     * @throws IOException 
     */
    private void extractExif() throws IllegalArgumentException, ImageProcessingException, IOException {
        // Trying to get Metadata then storing them into a HashMap
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
            for (Directory dir : metadata.getDirectories()) {
                for (Tag tag : dir.getTags()) {
                    for (String requiredTag : requiredData) {
                        if (requiredTag.equals(tag.getTagName())) {
                            exif.put(tag.getTagName(), tag.getDescription());
                        }
                    }
                }
            }
        } catch (ImageProcessingException e) {
            throw new ImageProcessingException(e.getMessage());
        } catch (IOException e) {
        	throw new IOException(e.getMessage());
        }
        if (!exif.get("Detected MIME Type").startsWith("image")) {
            throw new IllegalArgumentException("The path provided does not lead to an image.");
        }
    }

    public HashMap<String, String> getExif() {
        return exif;
    }

    public String getFilename()  {
        return imageFile.getName();
    }

    public String getMimeType() {
        return exif.get("Detected File Type Name").toLowerCase();
    }
}
