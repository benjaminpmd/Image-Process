package explorer;


public class TypeChecker {
	private String[] imageTypes;
	
	public TypeChecker() {
    imageTypes = new String[]{".png", ".jpeg", ".jpg", ".PNG", ".JPEG", ".JPG"};
	}
	
	public boolean image(String path) {
		 for(int i=0; i<imageTypes.length; i++){
			if (path.endsWith(imageTypes[i])){
				return true;
			}
		}
		return false;
	}
}
