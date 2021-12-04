package tests;

import java.util.HashMap;

import exif.ExifReader;

public class TestExifReader {
    public static void main(String[] args) {
        HashMap<String, String> exifData;
        try {
            ExifReader reader = new ExifReader("Hello.jpg");
            exifData = reader.getExif();
            for (String key: exifData.keySet()) {
                System.out.println(key + " : " + exifData.get(key));
            }

        } catch (Exception e) {
            System.err.println(e);
        }    
    }    
}
