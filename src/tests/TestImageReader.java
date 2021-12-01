package tests;

import java.io.IOException;

import steganography.ImageReader;

public class TestImageReader {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ImageReader reader=new ImageReader("./src/tests/assets/subdir/IMG_1.JPG");
			reader.print2();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
