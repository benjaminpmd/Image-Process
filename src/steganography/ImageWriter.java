package steganography;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;

public class ImageWriter extends Image {

	public ImageWriter(String path) throws IOException {
        super(path);
	}

    public String StringToBinary(String message) {
        String binaryMessage = "";
        for (int i = 0; i < message.length(); i++) {
            int character = (int)message.charAt(i);
            String binaryCharacter = Integer.toBinaryString(character);
            while (binaryCharacter.length() < 8) {
                binaryCharacter = '0' + binaryCharacter;
            }
            binaryMessage += binaryCharacter;
        }
        return binaryMessage;
    }

    public boolean hideMessage(String message) throws IOException {
        message += stopKey;
        String binaryMessage = StringToBinary(message);
        int index = 0;
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {
                Color pixelColor = new Color(bufferedImage.getRGB(i, j));
                int green = pixelColor.getGreen();
                String binaryGreen = newLsbValue(Integer.toBinaryString(green), binaryMessage.charAt(index));
                index++;
                int newGreen = Integer.parseInt(binaryGreen, 2);
                Color newPixelColor = new Color(pixelColor.getRed(), pixelColor.getBlue(), newGreen, pixelColor.getAlpha());
                bufferedImage.setRGB(i, j, newPixelColor.getRGB());
                if (index >= binaryMessage.length()) {
                    i = imageWidth;
                    j = imageHeight;
                    return true;
                }
            }
        }
        return false;
    }

    public String newLsbValue(String initialValue, char binaryAddition) {
        return initialValue.substring(0, initialValue.length()-1) + binaryAddition;
    }

    public boolean saveImage() throws IOException {
        return ImageIO.write(bufferedImage, imageType, imageFile);
    }
}

