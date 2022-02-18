package guitar13;

/**
 *
 * @author Ning
 */
public class KeyDropDown {
    
    private static String[] Keys = {
        "E",
        "F",
        "F#",
        "G",
        "Ab",
        "A",
        "Bb",
        "B",
        "C",
        "Db",
        "D",
        "Eb",
        "C# min",
        "D min",
        "Eb min",
        "E min",
        "F min",
        "F# min",
        "G min",
        "G# min",
        "A min",
        "Bb min",
        "B min",
        "C min",
    };
    
    public static String getKeys(int i){
        return Keys[i];
    }
}
