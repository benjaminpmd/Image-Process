package steganography;

import java.awt.Color;
import java.io.IOException;

public class ImageReader extends Image {
    public ImageReader(String path) throws IOException {
        super(path);
    }

    public String readMessage() {
		String message = "";
		String octet = "";
		int indexOctet = 0;
		for (int i = 0; i < imageWidth; i++) {
			for (int j = 0; j < imageHeight; j++) {
				indexOctet++;
				Color pixelColor = new Color(bufferedImage.getRGB(i, j));
				int green = pixelColor.getGreen();
				String binaryGreen = Integer.toBinaryString(green);
				octet += binaryGreen.substring(binaryGreen.length()-1);
				if (indexOctet == 8) {
					message += String.valueOf((char) Integer.parseInt(octet, 2));
					octet = "";
					indexOctet = 0;
                	if (message.length() >= stopKey.length()) {
                    	if (message.substring(message.length()-stopKey.length()).equals(stopKey)) {
							return message.substring(0, message.length()-stopKey.length());
						}
                	}
				}
			}
		}
		return message;
	}
}
