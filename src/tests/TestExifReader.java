package tests;

import java.util.HashMap;

import exif.ExifReader;

public class TestExifReader {
    public static void main(String[] args) {
        HashMap<String, String> exifData;
        ExifReader reader = new ExifReader("./src/tests/test.png");
        try {
            exifData = reader.getExif();
            for (String key: exifData.keySet()) {
                System.out.println(key + " : " + exifData.get(key));
            }

        } catch (Exception e) {
            System.err.println(e);
        }    
    }    
}
