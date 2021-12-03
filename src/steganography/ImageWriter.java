package steganography;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageWriter extends Image {
	public ImageWriter(String path) throws IOException {
        super(path);
	}

    public String stringToBinary(String message) {
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

    public void hideMessage(String message) throws IOException {
        message = beginKey + message + stopKey;
        String binaryMessage = stringToBinary(message);
        System.out.println(binaryMessage);
        int index = 0;
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {
                Color pixelColor = new Color(bufferedImage.getRGB(i, j));
                String binaryBlue = Integer.toBinaryString(pixelColor.getBlue());
                String newBinaryBlue = newLsbValue(binaryBlue, binaryMessage.charAt(index));
                index++;
                int newBlue = Integer.parseInt(newBinaryBlue, 2);
                Color newPixelColor = new Color(pixelColor.getRed(), pixelColor.getGreen(), newBlue);
                bufferedImage.setRGB(i, j, newPixelColor.getRGB());
                if (index >= binaryMessage.length()) {
                    i = imageWidth;
                    j = imageHeight;
                }
            }
        }
    }

    public String newLsbValue(String initialValue, char binaryAddition) {
        return initialValue.substring(0, initialValue.length()-1) + binaryAddition;
    }

    public boolean saveImage() throws IOException {
        BufferedImage outputBufferedImage;
        if (imageType.equals("jpg") | imageType.equals("jpeg")) {
            outputBufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
            outputBufferedImage.getGraphics().drawImage(bufferedImage, 0, 0, null);
        }
        else outputBufferedImage = bufferedImage;
        return ImageIO.write(outputBufferedImage, imageType, imageFile);
    }
}

