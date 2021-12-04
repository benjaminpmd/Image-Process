package tests;

import java.io.IOException;

import steganography.ImageWriter;

public class TestImageWriter {
	public static void main(String[] args) {
		try {
			System.out.println("=============WRITING=========");
			String message = "hiu";
			ImageWriter writer = new ImageWriter("src/tests/assets/test.jpg");
			System.out.println("Hidding message: " + message);
			writer.hideMessage(message);
			writer.saveImage();
			System.out.println("message successfully hidden.");
		} catch (IOException e) {
			System.err.println(e);
		}
	}

}
