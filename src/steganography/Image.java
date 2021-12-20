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
 * @version 2021.12.20 (1.1.0)
 */
public abstract class Image {
	private File imageFile;
	private String imageType;
	private BufferedImage bufferedImage;
	private int imageWidth;
	private int imageHeight;
	private String beginKey;
	private String stopKey;
	private String path;

	/**
	 * Constructor of the Image class
	 * @param path path of the image
	 * @throws IOException if the image could not be read.
	 * @throws IllegalArgumentException if the extension of the file is not an image.
	 */
	public Image(String path) throws IOException, IllegalArgumentException {
		TypeChecker typeChecker = new TypeChecker();
		if (!typeChecker.isImage(path)) throw new IllegalArgumentException("The path does not lead to an image");
		this.path = path;
		imageFile = new File(path);
		imageType = typeChecker.getMimeType(path);
		bufferedImage = ImageIO.read(imageFile);
		imageWidth = bufferedImage.getTileWidth();
		imageHeight = bufferedImage.getTileHeight();
		beginKey = "*";
		stopKey = "//";
	}

	@Override
	public String toString() {
		return "Image{" +
				"imageFile=" + imageFile +
				", imageType='" + imageType + '\'' +
				", bufferedImage=" + bufferedImage +
				", imageWidth=" + imageWidth +
				", imageHeight=" + imageHeight +
				", beginKey='" + beginKey + '\'' +
				", stopKey='" + stopKey + '\'' +
				", path='" + path + '\'' +
				'}';
	}

	public File getImageFile() {
		return imageFile;
	}

	public String getImageType() {
		return imageType;
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

	public String getBeginKey() {
		return beginKey;
	}

	public String getStopKey() {
		return stopKey;
	}

	public String getPath() {
		return path;
	}
}
