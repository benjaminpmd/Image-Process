package tests;

import java.io.IOException;

import steganography.ImageWriter;

public class TestImageWriter {
	public static void main(String[] args) {
		try {
			System.out.println("=============WRITING=========");
			String message = "QUELLE JOIE oui";
			ImageWriter writer = new ImageWriter("test.png");
			System.out.println("Hidding message: " + message);
			writer.hideMessage(message);
			writer.saveImage();
			//writer.updateImage();
			System.out.println("message successfully hidden.");
		} catch (IOException e) {
			System.err.println(e);
		}
	}

}
