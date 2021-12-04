package explorer;

/**
 * @author Alice MABILLE
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class FileLister {
	private String path;
	private ArrayList<String> files;
	
	public FileLister(String path) {
	    this.path = path;
	    files = new ArrayList<String>();
	}

	public FileLister() {
		//default path is current path
	    this(".");
	}
	
	/**
	 * @return ArrayList<String> of all images in the directory and subdirectories.
	 * @throws IllegalArgumentException
	 * @throws FileNotFoundException
	 */
	public ArrayList<String> exploreImages() throws IllegalArgumentException, FileNotFoundException{
		File file = new File(path);
		if (file.isDirectory() && file.exists()){
			listFilesInDirectory(file);
		}
		else if (!file.isDirectory()) {
			throw new IllegalArgumentException(path + " is not a directory.");
		}
		// since we only explore, the following exceptions is not needed
		else if(!file.exists()){
			throw new FileNotFoundException("The " + path + " file does not exist.");
		}
		return files;
	}
	
	/**
	 * Recursively browses all subdirectories and adds all image files' paths in the files attribute.
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
}
