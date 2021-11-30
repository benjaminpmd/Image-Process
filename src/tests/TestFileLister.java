package tests;

import java.util.ArrayList;

import explorer.FileLister;

public class TestFileLister {
    public static void main(String[] args) {
        ArrayList<String> images = new ArrayList<String>();
		FileLister lister = new FileLister(".");
		try {
			images = lister.exploreImages();
			for (String image : images) {
				System.out.println(image);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
    }
}
