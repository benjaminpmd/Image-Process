package tests;

import core.Core;

public class TestCore {
    public static void main(String[] args) {
        Core c = new Core();
        System.out.println("///////////////////////////////////////////////////////////////////////");
        System.out.println("HELP CONTENT TEST");
        System.out.println(c.getCliHelpContent());

        System.out.println("\n///////////////////////////////////////////////////////////////////////");
        System.out.println("EXIF CONTENT TEST");
        System.out.println(c.getExifContent("./src/tests/assets/test.jpg"));
        System.out.println(c.getExifContent("./src/tests/assets/test.png"));

        System.out.println("\n///////////////////////////////////////////////////////////////////////");
        System.out.println("MESSAGE HIDE");
        System.out.println(c.hideMessage("./src/tests/assets/test.jpg", "Coucou"));

        System.out.println("\n///////////////////////////////////////////////////////////////////////");
        System.out.println("MESSAGE READ");
        System.out.println(c.readMessage("./src/tests/assets/test.png"));
    }
}
