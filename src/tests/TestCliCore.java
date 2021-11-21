package tests;

import cli.Core;

public class TestCliCore {
    public static void main(String[] args) {
        Core core = new Core();
        System.out.println("========== Test of help display ==========");
        core.printHelp();

    }
}