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
 * This class contains all methods to get EXIF data of an image.
 * @author Benjamin PAUMARD
 * @version 2021.12.09 (1.0.1)
 * @since November 18th, 2021
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

    /**
     * Constructor of the object
     * @param path of the image you want to get the exif data from.
     * @throws IllegalArgumentException
     * @throws ImageProcessingException
     * @throws IOException
     */
    public ExifReader(String path) throws IllegalArgumentException, ImageProcessingException, IOException {
        TypeChecker typeChecker;
        imageFile = new File(path);
        exif = new HashMap<String, String>();
        typeChecker = new TypeChecker();
        if (imageFile.isFile() && typeChecker.isImage(path)) {
            extractExif();
        }
        else {
            throw new IllegalArgumentException("The path provided does not lead to an image.");
        }
    }

    /**
     * Returns the EXIF data the image passed during the creation of the ExifReader object.
     * @return HashMap<String, String> The EXIF data of an image.
     * @throws IllegalArgumentException the mime type indicate that the file is not an image.
     * @throws ImageProcessingException metadata could not be extracted.
     * @throws IOException an unknown error occurred.
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
            if (!exif.get("Detected MIME Type").startsWith("image")) {
                throw new IllegalArgumentException("MIME type does not correspond to an image.");
            }
        } catch (ImageProcessingException e) {
            throw new ImageProcessingException(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
        	throw new IOException(e.getMessage());
        }
    }

    /**
     * @return a HashMap<String, String> containing all the required EXIF data
     */
    public HashMap<String, String> getExif() {
        return exif;
    }

    /**
     * @return the name of the file.
     */
    public String getFilename()  {
        return imageFile.getName();
    }

    /**
     * @return the MIME type of image
     */
    public String getMimeType() {
        return exif.get("Detected File Type Name").toLowerCase();
    }
}
