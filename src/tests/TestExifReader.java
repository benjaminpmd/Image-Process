package tests;

import exif.ExifReader;

public class TestExifReader {
    public static void main(String[] args) {
        ExifReader reader = new ExifReader("./src/tests/test.jpg");
        try {
            reader.getExif();
        } catch (Exception e) {
            System.err.println(e);
        }    
    }    
}
