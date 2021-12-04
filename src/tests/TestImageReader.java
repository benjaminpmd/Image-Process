package tests;

import java.io.IOException;

import steganography.ImageReader;

public class TestImageReader {
	public static void main(String[] args) {
		try {
			System.out.println("=============READING=========");
			ImageReader reader = new ImageReader("src/tests/assets/test.jpg");
			System.out.println(reader.readMessage());
		} catch (IOException e) {
			System.err.println(e);
		}
	}

}
