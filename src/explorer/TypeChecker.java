package explorer;

/**
 * @author Alice MABILLE
 * Used is to check if a file is an image. Does not throw any exception, as those are managed by the calling classes.
 */
public class TypeChecker {
	private String[] imageTypes;
	
	public TypeChecker() {
    imageTypes = new String[]{".png", ".jpeg", ".jpg", ".PNG", ".JPEG", ".JPG"};
	}
	
	/**
	 * @return boolean : checks if the end of the specified path is an image-type filename extension.
	 */
	public boolean image(String path) {
		 for(int i=0; i<imageTypes.length; i++){
			if (path.endsWith(imageTypes[i])){
				return true;
			}
		}
		return false;
	}
}
