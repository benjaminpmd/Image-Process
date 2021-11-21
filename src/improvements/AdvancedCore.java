package improvements;

/**
 * This is a developpement class
 * More complex solutions are stored here for further cli improvements
 * @author Benjamin PAUMARD
 * @version 2021.11.21-dev (0.0.1b)
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AdvancedCore {
    public void printHelp() {
        Path helpFile = Paths.get("assets/HelpContent.txt");
        try {
            String helpContent = Files.readString(helpFile);
            System.out.println(helpContent);
        } catch (IOException e) {
            System.err.println("Something wrong happened, we can't reach the help content");
            System.err.println(e);
        }
    }

    
}

