package steganography;

import java.awt.Color;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Class used to read message contained in a PNG image
 * @author Benjamin PAUMARD, Alice MABILLE
 * @since December 1st 2021
 * @version 2021.12.15 (1.0.1)
 * @see Image
 */
public class ImageReader extends Image {
	/**
	 * @param path the image you want to read a message from.
	 * @throws IOException if the image cannot be read
	 * @throws IllegalArgumentException if the extension of the file is not an image.
	 */
    public ImageReader(String path) throws IllegalArgumentException, IOException {
        super(path);
		if(!getImageType().equals("png")) throw new IllegalArgumentException("Image is not a PNG file");
    }


	/**
	 * Extract the last 2 bits of the binary value of a color.
	 * @param colorValue Integer value of a color between 0 and 255.
	 * @return the last 2 bits of the binary code of the color.
	 */
	public String extractLastBits(int colorValue) {
		String binaryColor = Integer.toBinaryString(colorValue);
		while (binaryColor.length() < 8) {
			binaryColor = 0 + binaryColor;
		}
		return binaryColor.substring(binaryColor.length()-2);
	}

	/**
	 * Extract a character hidden in the last bits of colors in a pixel.
	 * @param pixelValue Integer value of a pixel color.
	 * @return character hidden in the pixel.
	 */
	public char characterExtractor(int pixelValue) {
		Color pixelColor = new Color(pixelValue, true);
		String byteValue = extractLastBits(pixelColor.getAlpha()) +
			extractLastBits(pixelColor.getRed()) +
			extractLastBits(pixelColor.getGreen()) +
			extractLastBits(pixelColor.getBlue());
		return (char) Integer.parseInt(byteValue, 2);
	}

	/**
	 * Read and return the hidden message if it exists.
	 * @return the hidden message
	 * @throws NoSuchElementException The message does not exist.
	 */
    public String readMessage() throws NoSuchElementException {
		String message = "";
		boolean messageExist = false;
		for (int i = 0; i < getImageWidth(); i++) {
			for (int j = 0; j < getImageHeight(); j++) {
				message += characterExtractor(getBufferedImage().getRGB(i, j));
				if (!messageExist) {
					if (message.equals("*")) {
						messageExist = true; // check if the first char indicate the presence of a message
					}
					else {
						throw new NoSuchElementException("This image does not contains any message.");
					}
				}
                if (message.endsWith(getStopKey())) { // check if the last chars are the stop key
					return message.substring(1, message.length()-getStopKey().length());
				}
			}
		}
		return message;
	}
}
