package steganography;

import java.awt.Color;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Class used to read message contained in an image
 * 
 * @author Benjamin PAUMARD, Alice MABILLE
 * @since December 1st 2021
 * @version 2021.12.03 (0.8.5)
 */
public class ImageReader extends Image {
    public ImageReader(String path) throws IOException {
        super(path);
    }

    public String readMessage() throws NoSuchElementException {
		String message = "";
		String octet = "";
		int indexOctet = 0;
		boolean messageExist = true;
		for (int i = 0; i < imageWidth; i++) {
			for (int j = 0; j < imageHeight; j++) {
				Color pixelColor = new Color(bufferedImage.getRGB(i, j));
				String binaryBlue = Integer.toBinaryString(pixelColor.getBlue());
				octet += binaryBlue.substring(binaryBlue.length()-1);
				indexOctet++;
				if (indexOctet == 8) {
					message += String.valueOf((char) Integer.parseInt(octet, 2));
					if (!messageExist) {
						if (message.equals("*")) {
							messageExist = true;
						}
						else throw new NoSuchElementException("This image does not contains any message.");
					}
					octet = "";
					indexOctet = 0;
                	if (message.length() >= stopKey.length()) {
                    	if (message.substring(message.length()-stopKey.length()).equals(stopKey)) {
							return message.substring(1, message.length()-stopKey.length());
						}
                	}
				}
			}
		}
		return message;
	}
}
