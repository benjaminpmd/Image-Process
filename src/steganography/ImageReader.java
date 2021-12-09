package steganography;

import java.awt.Color;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Class used to read message contained in a PNG image
 * 
 * @author Benjamin PAUMARD, Alice MABILLE
 * @since December 1st 2021
 * @version 2021.12.09 (0.9.7)
 * @see Image
 */
public class ImageReader extends Image {
    public ImageReader(String path) throws IllegalArgumentException, IOException {
        super(path);
		if(!imageType.equals("png")) throw new IllegalArgumentException("Image is not a PNG file");
    }


	/**
	 * Extract the last 2 bits of the binary value of a color.
	 * @param colorValue - Integer value of a color (0 <= x <= 255).
	 * @return the last 2 bits of the binary code of the color.
	 */
	public String lastBitsExtractor(int colorValue) {
		String binaryColor = Integer.toBinaryString(colorValue);
		while (binaryColor.length() < 8) {
			binaryColor = 0 + binaryColor;
		}
		return binaryColor.substring(binaryColor.length()-2);
	}

	/**
	 * Extract a character hidden in the last bits of colors in a pixel.
	 * @param pixelValue - Integer value of a pixel color.
	 * @return character hidden in the pixel.
	 */
	public char characterExtractor(int pixelValue) {
		Color pixelColor = new Color(pixelValue, true);
		String byteValue = lastBitsExtractor(pixelColor.getAlpha()) + lastBitsExtractor(pixelColor.getRed()) + lastBitsExtractor(pixelColor.getGreen()) + lastBitsExtractor(pixelColor.getBlue());
		return (char) Integer.parseInt(byteValue, 2);
	}

	/**
	 * Read and return the hidden message if it exist.
	 * @return the hidden message
	 * @throws NoSuchElementException The message does not exist.
	 */
    public String readMessage() throws NoSuchElementException {
		String message = "";
		boolean messageExist = false;
		for (int i = 0; i < imageWidth; i++) {
			for (int j = 0; j < imageHeight; j++) {
				message += characterExtractor(bufferedImage.getRGB(i, j));
				if (!messageExist) {
					if (message.equals("*")) {
						messageExist = true; // check if the first char indicate the presence of a message
					}
					else {
						throw new NoSuchElementException("This image does not contains any message.");
					}
				}
                if (message.endsWith(stopKey)) { 
					return message.substring(1, message.length()-stopKey.length()); // check if the last chars are the stop key
				}
			}
		}
		return message;
	}
}
