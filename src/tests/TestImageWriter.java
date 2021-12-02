package tests;

import java.io.IOException;

import steganography.ImageWriter;

public class TestImageWriter {
	public static void main(String[] args) {
		try {
			System.out.println("=============WRITING=========");
			String message = "Hello World";
			ImageWriter writer = new ImageWriter("build/T.png");
			System.out.println("Hidding message: " + message);
			writer.hideMessage(message);
			ImageWriter w = new ImageWriter("build/T.png");
			w.setBufferedImage(writer.getBufferedImage());
			w.saveImage();
			//writer.updateImage();
			System.out.println("message successfully hidden.");
		} catch (IOException e) {
			System.err.println(e);
		}
	}

}
