package steganography;


import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Color;

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

    public boolean hideMessage(String message) throws IOException {
        message += stopKey;
        String binaryMessage = stringToBinary(message);
        int index = 0;
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {
                Color pixelColor = new Color(pixels[i][j]);
                int green = pixelColor.getGreen();
                String binaryGreen = Integer.toBinaryString(green);
                String newBinaryGreen = newLsbValue(binaryGreen, binaryMessage.charAt(index));
                while (newBinaryGreen.length() < 8) {
                    newBinaryGreen = '0' + newBinaryGreen;
                }
                index++;
                int newGreen = Integer.parseInt(newBinaryGreen, 2);
                Color newPixelColor = new Color(pixelColor.getRed(), newGreen, pixelColor.getBlue(), pixelColor.getAlpha());
                System.out.println(Integer.toBinaryString(pixelColor.getRGB()) + " : " + Integer.toBinaryString(newPixelColor.getRGB()));
                pixels[i][j] = newPixelColor.getRGB();
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
        BufferedImage outputBuffer = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {
                outputBuffer.setRGB(i, j, pixels[i][j]);
                System.out.println(Integer.toBinaryString(outputBuffer.getRGB(i, j)) + " : " + Integer.toBinaryString(pixels[i][j]));
            }
        }
        return ImageIO.write(outputBuffer, imageType, imageFile);
    }
}

