package steganography;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Class used to read message contained in a PNG image
 * 
 * @author Benjamin PAUMARD, Alice MABILLE
 * @since December 1st 2021
 * @version 2021.12.09 (0.9.8)
 * @see Image
 */
public class ImageWriter extends Image {
    BufferedImage outputBufferedImage;

	public ImageWriter(String path) throws IOException {
        super(path);
	}

    /**
     * Convert a string to a binary string.
     * @param message - String the message you want to convert.
     * @return A String containing the binary value of the message.
     */
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

    /**
     * Takes a Integer value of a color and change last bits of the byte.
     * @param colorValue - Integer value of a color (0 <= x <= 255).
     * @param bits - bits to be encoded in the color value, will replace the last bits of a byte.
     * @return the new Integer value for the color.
     */
    public int changeLastBits(int colorValue, String bits) {
        String binaryColor = Integer.toBinaryString(colorValue);
        while (binaryColor.length() < 8) {
			binaryColor = 0 + binaryColor;
		}
        binaryColor = binaryColor.substring(0, binaryColor.length()-bits.length()) + bits;
        return Integer.parseInt(binaryColor, 2);
    }

    /**
     * Hide a message in an image using a BufferedImage.
     * @param message - String to hide in the image.
     * @throws IOException - in case of failure updating the BufferedImage used to store pixels values.
     */
    public void hideMessage(String message) throws IOException {
        message = beginKey + message + stopKey;
        String binaryMessage = stringToBinary(message);
        int index = 0;
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {
                if (index < binaryMessage.length()) {
                    Color pixelColor = new Color(bufferedImage.getRGB(i, j));
                    int alpha = changeLastBits(pixelColor.getAlpha(), binaryMessage.substring(index, index+2));
                    int red = changeLastBits(pixelColor.getRed(), binaryMessage.substring(index+2, index+4));
                    int green = changeLastBits(pixelColor.getGreen(), binaryMessage.substring(index+4, index+6));
                    int blue = changeLastBits(pixelColor.getBlue(), binaryMessage.substring(index+6, index+8));
                    index += 8;
                    Color newPixelColor = new Color(red, green, blue, alpha);
                    bufferedImage.setRGB(i, j, newPixelColor.getRGB());
                }
            }
        }
    }

    /**
     * Update the image with the message idden inside, if the image is not a PNG, a copy is created under PNG format to avoid compression.
     * @return if the save is succesfull or not
     * @throws IOException - if the save fail
     */
    public boolean saveImage() throws IOException {
        if (imageType.equals("jpg") | imageType.equals("jpeg")) {
            File outputImageFile = new File(path.substring(0, path.length()-3) + "png");
            return ImageIO.write(bufferedImage, "png", outputImageFile);
        }
        return ImageIO.write(bufferedImage, imageType, imageFile);
    }
}

