package explorer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author Alice MABILLE
 * @version 1.0.0
 */
public class FileLister {
	private String path;
	private ArrayList<String> files;

	/**
	 * Constructor of the FileLister class.
	 * @param path path of the file you want to check.
	 */
	public FileLister(String path) {
	    this.path = path;
	    files = new ArrayList<String>();
	}

	/**
	 * If no path is provided, the current one will be used.
	 */
	public FileLister() {
	    this(".");
	}

	/**
	 * Recursively browses all subdirectories and adds all image files' paths in the files attribute.
	 * @param directory a directory of type File.
	 */
	private void listFilesInDirectory(File directory) {
		TypeChecker typeChecker = new TypeChecker();
		for(String tempPath : directory.list()) {
			File tempFile = new File(directory + "/" + tempPath);
			if (tempFile.isDirectory()) {
				listFilesInDirectory(tempFile);
			}
			else if (typeChecker.isImage(directory + "/" + tempPath)) {
				if(directory.getName().equals(path)) {
					files.add(tempPath);
				}
				else {
					files.add(directory.getPath() + "/" + tempPath);
				}
			}
		}
	}
	
	/**
	 * @return ArrayList<String> of all images in the directory and subdirectories.
	 * @throws IllegalArgumentException in case the path is not a directory.
	 * @throws FileNotFoundException in case the path does not exist at all.
	 */
	public ArrayList<String> exploreImages() throws IllegalArgumentException, FileNotFoundException{
		File file = new File(path);
		if (file.isDirectory() && file.exists()){
			listFilesInDirectory(file);
		}
		else if (!file.isDirectory()) {
			throw new IllegalArgumentException(path + " is not a directory.");
		}
		else if(!file.exists()){
			throw new FileNotFoundException("The " + path + " file does not exist.");
		}
		return files;
	}
}
