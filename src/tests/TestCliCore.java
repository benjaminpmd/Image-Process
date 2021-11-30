package tests;

import cli.Core;

public class TestCliCore {
    public static void main(String[] args) {
        Core core = new Core();
        System.out.println("========== Test of help display ==========");
        core.printHelp();

        System.out.println("\n========== Test of Explorer display ==========");
        core.printExplorer("./src/tests/assets/");

        System.out.println("\n========== Test of EXIF data display ==========");
        core.printExif("./src/tests/assets/img.png");
    }
}