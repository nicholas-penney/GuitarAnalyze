package guitar13;

import java.nio.file.Paths;

/**
 *
 * @author Ning
 */
public class TestTab {
    
    private static String filePath = Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\guitar13\\tabs\\";
    private static String[] tab = {
        filePath + "CArp.txt",
        filePath + "intro.txt",
        filePath + "iSeeFire.txt"
    };
    
    public static String get(int i){
        return tab[i];
    }
}
