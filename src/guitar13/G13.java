/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guitar13;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.nio.file.Paths;


/**
 *
 * @author Neil
 */
public class G13 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("G13FXML.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        scene.getStylesheets().add("guitar13/Chart.css");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    } // end of main
    
    

    
    public static ArrayList<Beats> beats;
    public static ArrayList<Beats2> beats2;

    
    public static void getIntro(String filePathAbs) throws Exception { 

        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        // File and variable setup
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        
        // Transfer .txt tab file to String
        
        
        File fileTab = new File(filePathAbs);
        BufferedReader br = new BufferedReader(new FileReader(fileTab)); 
        String strTab;
        // Every line, including junk to parse out
        ArrayList<String> linesAL = new ArrayList<String>();
        // ArrayList to store each Beats object
        beats = new ArrayList<Beats>();
        
        // Breaks each line in strTab up and stores them in linesAL as separate Strings
        while ((strTab = br.readLine()) != null) {
            linesAL.add(strTab);
        }
        br.close();

        // ArrayList of ArrayLists to save only valid guitar strings, parsed from the linesAL
        ArrayList<ArrayList<String>> barsALAL = new ArrayList<ArrayList<String>>();
        barsALAL.add(new ArrayList<String>());

        // Position marker for current line / string
        //int currentLine = 0;

                
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        // Scan through every line and pick out just valid staves of 6x strings
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        Iterator<String> itr = linesAL.iterator();

        // Scan for potential tab / staves
        while (itr.hasNext()) {
            // Convert current linesAL object to a string for checker method
            String tempLine = itr.next();
            // Amount of characters in potential low e|- string to compare the other 5 to
            int firstLineLength = tempLine.length();

            // If this is a potential starting point, check for 5 more valid strings
            if (validString(tempLine, firstLineLength)){
                int latestBar = barsALAL.size()-1;
                barsALAL.get(latestBar).add(tempLine);

                // Peek the next 5 strings
                for (int i=0; i<5; i++){
                    tempLine = itr.next();

                    if (!validString(tempLine, firstLineLength)){
                        // Turns out it wasn't a valid stave, clear current barsALAL layer and the Beats stave starting item
                        barsALAL.get(latestBar).clear();
                        if (Beats.barStarts.size() > 0){
                            Beats.barStarts.remove(Beats.barStarts.size() - 1);
                        }
                        break;
                    } else {
                        // Another valid string, add it to barsALAL
                        barsALAL.get(latestBar).add(tempLine);
                    } // End of IF/ELSE
                } // End of FOR loop

                // If we have added a full stave of 6x guitar strings to barsALAL, add new ArrayList to it for next check
                barsALAL.add(new ArrayList<String>());

            } // End of IF

        } // End of WHILE

        // Trim the last empty AL off to neaten up
        barsALAL.remove(barsALAL.size()-1);

        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        // We have finished parsing the tab and have the bars saved in barsALAL<String>, each item being a "stave" of 6x, and each item in a "stave" being a guitar string.
        // Now to convert String characters into Integers and store them in our Beats objects.
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -    

        // When does first '-' or note occur in current line? e.g. "Db|--7--" would be #3 on the dash

        // Keep track of when the finished beats end, and the new blank beats will have begun so we can populate them only
        int beatsObjectALLength = 0;

        // Loops through each block / stave of 6x guitar strings
        for (int i=0; i<barsALAL.size(); i++){
            Beats.barStarts.add(beatsObjectALLength);

            // Set the starting point for modifying Beats object data
            beatsObjectALLength = beats.size();

            
            for (int j=0; j<barsALAL.get(i).size(); j++){

                //int currentBeat = beatsObjectALLength + j;
                // Quickly make new blank Beats according to: [top e|- string length]
                if (j == 0){
                    for (int k=0; k<barsALAL.get(i).get(j).length(); k++){
                        beats.add(new Beats());
                        beatsObjectALLength++;
                        
                    }
                }
                ////System.out.println(beatsObjectALLength);

                // We now have some fresh blank beats added to the end of the current Beat AL, now to populate those beats with notes
                // Starting from the last breakpoint in Beats.barStarts and finishing at the end of its size

                // Loop through the [e|- string] for example and pick out valid notes, watch out for concatenated values
                for (int k=0; k<barsALAL.get(i).get(j).length(); k++){
                    // Current char at potential beat, is it an integer?
                    if (Character.isDigit(barsALAL.get(i).get(j).charAt(k))){
                        // Note in int form to pass to Beats.makeBeat builder
                        int noteAsInt = Character.getNumericValue(barsALAL.get(i).get(j).charAt(k));
                        // Check if we need to concatenate adjacent character e.g. [ e|--14-- ]
                        if (Character.isDigit(barsALAL.get(i).get(j).charAt(k+1))){
                            int concatNote = Character.getNumericValue(barsALAL.get(i).get(j).charAt(k));
                            // Raise the [1] in [14] to [10]
                            concatNote *= 10;
                            // Add the [4] from [14]
                            concatNote += Character.getNumericValue(barsALAL.get(i).get(j).charAt(k+1));
                            // Remove the [4] from [14] ... changes it to: [1-]
                            char[] modifyString = barsALAL.get(i).get(j).toCharArray();
                            modifyString[k+1] = '-';
                            barsALAL.get(i).set(j, String.valueOf(modifyString));
                            // Update int note
                            noteAsInt = concatNote;
                        } // End of IF concatenate 

                        // Use this as index in makeBeat
                        int indexForMakeBeat = Beats.barStarts.get(Beats.barStarts.size()-1);
                        indexForMakeBeat += k;
                        
                        beats.get(indexForMakeBeat).makeBeat(5-j, noteAsInt);
                    } // end of IF chat is digit
                } // End of FOR k
            } // End of FOR j
        } // End of FOR i

        for (int i=0; i<beats.size(); i++) {
            beats.get(i).printBeat();
            beats.get(i).processAll();
        }

        // Counter
        // 0 1  2 3 4  5 6  7 8 9 10 11
        // E F F# G G# A A# B C C# D D#
        int[] noteCounter = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] chordCounter = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        for (int i = 0; i < beats.size(); i++) {
            if (beats.get(i).isNote()){
                noteCounter[beats.get(i).getNote()]++;
            }
            if (beats.get(i).isChord()){
                chordCounter[beats.get(i).getNote()]++;
            }
        } // End of FOR adding up notes
        
        for (int i = 0; i<12; i++){
            Beats.calcTotalNotes(i, noteCounter[i]);
            Beats.calcTotalChords(i, chordCounter[i]);
        }
        ////System.out.println("\n" + Arrays.toString(noteCounter));
        ////System.out.println(Arrays.toString(chordCounter));   
    } // End of getIntro

    
    

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Custom methods
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    
    // Checks if string argument is potentially a line in a stave
    public static Boolean validString(String tempLine, int firstLineLength){
        int lineLength = tempLine.length();
        char dash = '-';
        int dashCounter =0;
        int dashThreshold = 3;

        
        // All lines must be same length to be valid
        if (lineLength != firstLineLength){
            return false;
        }

        for (int i=0; i<lineLength; i++){
            if (tempLine.charAt(i) == dash){
                dashCounter++;
                if (dashCounter > dashThreshold){
                    return true;
                }
            }
        }
        return false;
    } // End of validString method







    

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Custom classes
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  
    public class Beats2 extends Beats {
    }
    
    public static class Beats {
        
        public static ArrayList<Integer> barStarts = new ArrayList<Integer>();
        
        public static int[] noteCounter = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        public static int[] chordCounter = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        // Each note on each string
        private Integer[] rawNotes = {null, null, null, null, null, null};
        private Integer[] notesIfAllOnE = {null, null, null, null, null, null};
        private int[] stringIntervals = {0, 5, 10, 15, 19, 24};

        private int lowStringEquiv; // For example, "A|-2-" becomes "E|-7-"
        
        private Boolean hasNote = false;
        private int locationOfNote = 6;

        private Boolean hasChord = false;
        
        // - - - -
        
        public int transposedNote(int shift){
            int transposed = lowStringEquiv + shift;
            transposed %= 12;
            return transposed;
        }

        public void printBeat(){
            //System.out.println(Arrays.toString(rawNotes));
        }
        // - - - -

        // Updates rawNotes. Read: "Change POS to VAL"
        public void makeBeat(int pos, int val){
            rawNotes[pos] = val;
        }
        // - - - -

        public Boolean isNote(){
            return hasNote;
        }

        public Boolean isChord() {
            return hasChord;
        }
        // - - - -
        public int getNote(){
            return lowStringEquiv;
        }
        // - - - -

        public void processAll(){
            analyse();
            notesIfAllOnE = adjustAllStrings();
            lowStringEquiv = makeLowStringEquiv();
        }
        
        public static void calcTotalNotes(int index, int count){
            noteCounter[index] = count;
        }
        
        public static void calcTotalChords(int index, int count){
            chordCounter[index] = count;
        }

        // - - - -

        // Checks if there are multiple notes (chord) in this beat
        public void analyse(){
            int noteCount = 0;
            // We have a chord?
            for (int i=0; i<rawNotes.length; i++){
                if (rawNotes[i] != null){
                    noteCount++;
                    // Store location of first note
                    if (locationOfNote > 5){
                        locationOfNote = i;
                    }
                }
                // Chord triggers this
                if (noteCount > 1){
                    hasChord = true;
                    break;
                }
            } // End of FOR
            // Chord and/or note present, save root note as if it were on E|- string
            if (noteCount > 0){
                lowStringEquiv = (int)rawNotes[locationOfNote] + stringIntervals[locationOfNote];
            }
            // We have just a note?
            if (noteCount == 1){
                hasNote = true;
            }
        } // End of analyse

        // - - - -

        // Takes raw tab beat and gives them all their LowE equivalent, but still in an array!
        public Integer[] adjustAllStrings(){
            Integer[] adjusted = {null, null, null, null, null, null};
            for (int i=0; i<adjusted.length; i++){
                if (rawNotes[i] != null){
                    adjusted[i] = rawNotes[i] + stringIntervals[i];
                }
            } // End of FOR
            return adjusted;
        } // End of adjustAllStrings

        // - - - -

        // Finds the lowest note in the chord - must pass it notes adjusted for string intervals
        public int makeLowStringEquiv(){
            int lowestBuffer = 50;

            // Test each note in chord to find lowest
            for (int i=0; i< notesIfAllOnE.length; i++) {

                if (notesIfAllOnE[i] != null){
                    if (notesIfAllOnE[i] < lowestBuffer){
                        lowestBuffer = notesIfAllOnE[i];
                    }
                }
            } // End of FOR

            // Make it so the note sits in the first octave of Low E|- string
            lowestBuffer %= 12;
            return lowestBuffer;
        } // End of makeLowStringEquiv
        // - - - -
    } // End of Beats class
    

    
} // end of GuitarAnalyse
