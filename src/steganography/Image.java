package steganography;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import explorer.TypeChecker;

/**
 * Class containing all attributes needed to manipulate image, get informations to store or retrieve message hidden inside.
 * 
 * @author Benjamin PAUMARD, Alice MABILLE
 * @since December 1st 2021
 * @version 2021.12.15 (1.0.1)
 */
public abstract class Image {
	protected File imageFile;
	protected String imageType;
	protected BufferedImage bufferedImage;
	protected int imageWidth;
	protected int imageHeight;
	protected String beginKey;
	protected String stopKey;
	protected String path;
	

	/**
	 * Constructor of the Image class
	 * @param path path of the image
	 * @throws IOException if the image could not be read.
	 * @throws IllegalArgumentException if the extension of the file is not an image.
	 */
	public Image(String path) throws IOException, IllegalArgumentException {
		TypeChecker typeChecker = new TypeChecker();
		if (!typeChecker.isImage(path)) throw new IllegalArgumentException("The path does not lead to an image");
		imageFile = new File(path);
		imageType = typeChecker.getMimeType(path);
		bufferedImage = ImageIO.read(imageFile);
		imageWidth = bufferedImage.getTileWidth();
		imageHeight = bufferedImage.getTileHeight();
		this.path = path;
		beginKey = "*";
		stopKey = "//";
	}

	/**
	 * @return the image MIME type.
	 */
	public String getImageType() {
		return imageType;
	}
}
