package steganography;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public abstract class Image {
	File imageFile;
	String imageType;
	BufferedImage bufferedImage;
	int[][] pixels;
	int imageWidth;
	int imageHeight;
	String stopKey = "//";

	public Image(String path) throws IOException {
		imageFile = new File(path);
		imageType = imageFile.getName().substring(imageFile.getName().length()-3);
		bufferedImage = ImageIO.read(imageFile);
		imageWidth = bufferedImage.getTileWidth();
		imageHeight = bufferedImage.getTileHeight();
		pixels = new int[imageWidth][imageHeight];
		extract();
	}

	private void extract() {
		for (int i = 0; i < imageWidth; i++) {
			for (int j = 0; j < imageHeight; j++) {
				pixels[i][j] = bufferedImage.getRGB(i, j);
			}
		}
	}

	public File getImageFile() {
		return imageFile;
	}
	
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
	
	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	
	public int getImageWidth() {
		return imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}
}
