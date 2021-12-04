package steganography;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import explorer.TypeChecker;

/**
 * Class containing all attributes needed to hide or read hidden message in pixels bits.
 * 
 * @author Benjamin PAUMARD, Alice MABILLE
 * @since December 1st 2021
 * @version 2021.12.03 (0.9.0)
 */
public abstract class Image {
	File imageFile;
	String imageType;
	BufferedImage bufferedImage;
	int imageWidth;
	int imageHeight;
	String beginKey = "*";
	String stopKey = "//";

	public Image(String path) throws IOException, IllegalArgumentException {
		TypeChecker typeChecker = new TypeChecker();
		if (!typeChecker.isImage(path)) throw new IllegalArgumentException("The path does not lead to an image");
		imageFile = new File(path);
		imageType = typeChecker.getMimeType(path);
		bufferedImage = ImageIO.read(imageFile);
		imageWidth = bufferedImage.getTileWidth();
		imageHeight = bufferedImage.getTileHeight();
	}

	public File getImageFile() {
		return imageFile;
	}
	
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}
}
