package guitar13;

import java.nio.file.Paths;

/**
 *
 * @author Ning
 */
public class CurrentProjectDirectory {
    
    private static String filePath = Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\";
    
    public static String source(){
        return filePath;
    }
    
    public static String resources(){
        return filePath + "Resources\\";
    }
    
    
}
