package steganography;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Class used to read message contained in a PNG image
 * @author Benjamin PAUMARD, Alice MABILLE
 * @since December 1st 2021
 * @version 2021.12.19 (1.0.5)
 * @see Image
 */
public class ImageWriter extends Image {
    private BufferedImage outputBufferedImage;

    /**
     * Constructor of the ImageWriter class
     * @param path the path of the image you want to check and hide a message inside.
     * @throws IOException if the image cannot be read
     * @throws IllegalArgumentException if the extension of the file is not an image.
     */
	public ImageWriter(String path) throws IllegalArgumentException, IOException {
        super(path);
        outputBufferedImage = new BufferedImage(getImageWidth(), getImageHeight(), BufferedImage.TYPE_INT_ARGB);
	}

    @Override
    public String toString() {
        return "ImageWriter{" +
                "outputBufferedImage=" + outputBufferedImage +
                "} " + super.toString();
    }

    /**
     * Convert a string to a binary string.
     * @param message String the message you want to convert.
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
     * Takes an Integer value of a color and change last bits of the byte.
     * @param colorValue Integer value of a color between 0 and 255.
     * @param bits bits to be encoded in the color value, will replace the last bits of a byte.
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
        message = getBeginKey() + message + getStopKey();
        String binaryMessage = stringToBinary(message);
        int index = 0;
        for (int i = 0; i < getImageWidth(); i++) {
            for (int j = 0; j < getImageHeight(); j++) {
                if (index < binaryMessage.length()) {
                    Color pixelColor = new Color(getBufferedImage().getRGB(i, j), true);
                    int alpha = changeLastBits(pixelColor.getAlpha(), binaryMessage.substring(index, index+2));
                    int red = changeLastBits(pixelColor.getRed(), binaryMessage.substring(index+2, index+4));
                    int green = changeLastBits(pixelColor.getGreen(), binaryMessage.substring(index+4, index+6));
                    int blue = changeLastBits(pixelColor.getBlue(), binaryMessage.substring(index+6, index+8));
                    index += 8;
                    Color newPixelColor = new Color(red, green, blue, alpha);
                    outputBufferedImage.setRGB(i, j, newPixelColor.getRGB());
                }
                else {
                    outputBufferedImage.setRGB(i, j, getBufferedImage().getRGB(i, j));
                }
            }
        }
    }

    /**
     * Update the image with the message hidden inside, if the image is not a PNG, a copy is created under PNG format to
     * avoid compression and enable transarency.
     * @return boolean, if the save is successful or not
     * @throws IOException if the save fail
     */
    public boolean saveImage() throws IOException {
        File outputImageFile;
        if (getImageType().equals("jpg") | getImageType().equals("jpeg")) {
            outputImageFile = new File(getPath().substring(0, getPath().length()-3) + "png");
        }
        else {
            outputImageFile = getImageFile();
        }
        return ImageIO.write(outputBufferedImage, "png", outputImageFile);
    }
}

