package cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Core {
    public void printHelp() {
        Path helpFile = Paths.get("HelpContent.txt");
        try {
            String helpContent = Files.readString(helpFile);
            System.out.println(helpContent);
        } catch (IOException e) {
            System.err.println("Something wrong happened, we can't reach the help content");
            System.err.println(e);
        }
    }

    
}

