package steganography;

import java.nio.file.Files;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author benjamin
 *
 */
public class ImageReader {
	private File file;
	private byte[] bytes;

	public ImageReader(String path) throws IOException {
		file = new File(path);
		bytes = Files.readAllBytes(file.toPath());
	}
	
	public void printBytes() {
		for (int i=0; i<bytes.length; i++) {
			System.out.println(bytes[i]);
		}
	}
	
	public void print2() throws IOException {
		BufferedImage img = ImageIO.read(file);
		for (int i=0; i < img.getWidth(); i++) {
			for (int j=0; j < img.getHeight(); j++) {
				int RGBA = img.getRGB(i, j);
				System.out.println(RGBA);
			}
		}
	}
	public byte[] getBytes() {
		return bytes;
	}
}
