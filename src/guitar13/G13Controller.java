package guitar13;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import javafx.beans.value.*;
import javafx.event.EventHandler; 
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;


/**
 *
 * @author Neil
 */
public class G13Controller implements Initializable {
    
    // Temporary buffer objects / variables
    private ScatterChart<?, ?> targetChart;
    
    private ScatterChart<?, ?>[] chartList;
    
    byte keyAsByte = 0;
    
    byte currentTrack = 10;
    
    ChoiceBox[] startCBs = new ChoiceBox[2];
    ChoiceBox[] endCBs = new ChoiceBox[2];
    
    int[] startMemory = new int[2];
    int[] endMemory = new int[2];
    
    String clickedButtonName = "";
    
    BarStarts[] barStarts;
    
    
    // - - - - -
    
    public String[] filePath = new String[2];
    
    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    
    @FXML
    private ScatterChart<?, ?> tab1Chart;
    @FXML
    private ScatterChart<?, ?> tab2Chart;
    @FXML
    private AnchorPane tabSelect;
    @FXML
    private AnchorPane tabSelect1;
    @FXML
    private AnchorPane tabSelect11;
    @FXML
    private NumberAxis y1;
    @FXML
    private CategoryAxis x1;
    @FXML
    private ChoiceBox<String> trackOneStart;
    @FXML
    private ChoiceBox<String> trackTwoStart;
    @FXML
    private ChoiceBox<String> trackOneEnd;
    @FXML
    private ChoiceBox<String> trackTwoEnd;
    @FXML
    private ImageView circleOfFifths;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Label key0;
    @FXML
    private Label key1;
    @FXML
    private Label key2;
    @FXML
    private Label key3;
    @FXML
    private Label key4;
    @FXML
    private Label key5;
    @FXML
    private Label key6;
    @FXML
    private Label key7;
    @FXML
    private Label key8;
    @FXML
    private Label key9;
    @FXML
    private Label key10;
    @FXML
    private Label key11;
    @FXML
    private ChoiceBox<String> keyDropDown;
    @FXML
    private Button topTabButton;
    @FXML
    private Button bottomTabButton;
    @FXML
    private Label bottomTabLabel;
    @FXML
    private Circle circleBG;
    
    void e36700(ActionEvent event) {
        //
    }

    
    @FXML
    private Label topTabLabel;
    

    // Opens FileChooser window
    void fileButton(ActionEvent event) throws Exception {
        
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            topTabLabel.setText(selectedFile.getName());
            //System.out.println(selectedFile.getAbsolutePath());
            filePath[currentTrack] = selectedFile.getAbsolutePath();
        } 
    }
    
    
   

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateKeyDropDown();
        //keyDropDown.setValue("E");
        startCBs[0] = trackOneStart;
        startCBs[1] = trackTwoStart;
        endCBs[0] = trackOneEnd;
        endCBs[1] = trackTwoEnd;
        chartList = new ScatterChart<?, ?>[2];
        chartList[0] = tab1Chart;
        chartList[1] = tab2Chart;
        barStarts = new BarStarts[2];
        barStarts[0] = new BarStarts();
        barStarts[1] = new BarStarts();
        
        keyDropDown.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                    if (currentTrack != 10) {
                        loadData();
                    }
                    
                    String key = KeyDropDown.getKeys(keyDropDown.getSelectionModel().getSelectedIndex());
                    File file = new File(CurrentProjectDirectory.resources() + key + ".png");
                    Image image;
                    image = new Image(file.toURI().toString());
                    //circleOfFifths.setImage(image);
                    
                    circleBG.setFill(new ImagePattern(image));
                    
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        
        keyDropDown.getSelectionModel().select(0);
        
        for (ChoiceBox box : startCBs){
            box.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                    if(box == startCBs[0]){
                        currentTrack = 0;
                    } else {
                        currentTrack = 1;
                    }
                    startMemory[currentTrack] = t1.intValue();
                    if (clickedButtonName.equals("")){
                        System.out.println("Was empty, now start");
                        clickedButtonName = ButtonNames.startEnd;
                    }
                    if (clickedButtonName.equals(ButtonNames.startEnd)){
                        loadData();
                    }
                    
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        }
        
        for (ChoiceBox box : endCBs){
            box.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                    if(box == endCBs[0]){
                        currentTrack = 0;
                    } else {
                        currentTrack = 1;
                    }
                    endMemory[currentTrack] = t1.intValue();
                    if (clickedButtonName.equals("")){
                        System.out.println("Was empty, now end");
                        clickedButtonName = ButtonNames.startEnd;
                    }
                    if (clickedButtonName.equals(ButtonNames.startEnd)){
                        loadData();
                    }
                    
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        }
    } // end of init
    
    
    
    public void loadData(){
        try {
            loadData2();
        } catch (Exception e) {
            System.out.println(e.toString());
            //
        }
    } // End of loadData()
    
    public void loadData2() throws Exception {
        
        targetChart = chartList[currentTrack];
        
        XYChart.Series set0 = new XYChart.Series<>();
        XYChart.Series set1 = new XYChart.Series<>();
        XYChart.Series set2 = new XYChart.Series<>();
        XYChart.Series set3 = new XYChart.Series<>();
        XYChart.Series set4 = new XYChart.Series<>();
        XYChart.Series set5 = new XYChart.Series<>();
        XYChart.Series set6 = new XYChart.Series<>();
        XYChart.Series set7 = new XYChart.Series<>();
        XYChart.Series set8 = new XYChart.Series<>();
        XYChart.Series set9 = new XYChart.Series<>();
        XYChart.Series set10 = new XYChart.Series<>();
        XYChart.Series set11 = new XYChart.Series<>();
        
        //G13.getIntro();
        if (ButtonNames.fileOpen.equals(clickedButtonName)){
            G13.Beats.barStarts.clear();
            G13.getIntro(filePath[currentTrack]);
            populateDropDownsOne();
            System.out.println(" - - Populated drop downs in loadData 2. - -");
            barStarts[currentTrack] = new BarStarts();
            barStarts[currentTrack].set(G13.Beats.barStarts);
            barStarts[currentTrack].setEnd(G13.beats.size()-1);
        }
        
        //System.out.println(filePath[currentTrack]);
        targetChart.getData().clear();
        int blank = 20; // This is for when we don't have a note (silence) and need to pad the dataset out with blocks, but up top / off screen
        //int length = 38; //Stave length
        //int length = G13.beats.size();
        //int counter = 0;
        
        
        // When to start the graph from
        int start = 0;
        System.out.println("StartMemory: " + Integer.toString(startMemory[currentTrack]));
        if (clickedButtonName.equals(ButtonNames.fileOpen)){
            start = barStarts[currentTrack].get(0);
        } else {
            start = barStarts[currentTrack].get(startMemory[currentTrack]);
        }
        System.out.println("Start: " + Integer.toString(start));
        
        // When to end the graph.
        int end;
        System.out.println("EndMemory: " + Integer.toString(endMemory[currentTrack]));
        int choiceBoxMax = endCBs[currentTrack].getItems().size()-1;
        System.out.println("choiceBoxMax: " + Integer.toString(choiceBoxMax));
        
        if(clickedButtonName.equals(ButtonNames.fileOpen)){
            if (barStarts[currentTrack].size() > 2){
                end = barStarts[currentTrack].get(1);
            } else {
                end = barStarts[currentTrack].getEnd();
            }
        } else if (endMemory[currentTrack] == choiceBoxMax){
            end = G13.beats.size();
        } else {
            end = barStarts[currentTrack].get(endMemory[currentTrack]+1);
        }
        System.out.println("End: " + Integer.toString(end));
        
        Boolean minor = keyDropDown.getSelectionModel().getSelectedIndex() > 12;

        int buffer;
        int transpose;
        transpose = keyDropDown.getSelectionModel().getSelectedIndex();
        if (minor){
            transpose %= 12;
            transpose -= 3;
        }
        
        System.out.println("Start/end used: " + Integer.toString(start) + " / " + Integer.toString(end));
        
        // Root
        for (int i=start; i<end; i++){
            buffer = G13.beats.get(i).getNote();
            buffer += (12 - transpose);
            buffer %= 12;
            if ( G13.beats.get(i).isNote() && buffer == 0 ) {
                set0.getData().add(new XYChart.Data(Integer.toString(i-start), buffer));
                set0.getData().add(new XYChart.Data(Integer.toString(i-start), 12));
                //System.out.println(buffer);
            } else {
                set0.getData().add(new XYChart.Data(Integer.toString(i-start), blank));
            }
        }
        
        
        // 2nd
        for (int i=start; i<end; i++){
            buffer = G13.beats.get(i).getNote();
            buffer += (12 - transpose);
            buffer %= 12;
            if (G13.beats.get(i).isNote() && buffer == 2){
                set1.getData().add(new XYChart.Data(Integer.toString(i-start), buffer));
                //System.out.println(buffer);
            } else {
                set1.getData().add(new XYChart.Data(Integer.toString(i-start), blank));
            }
        }
        
        
        if (minor){
            // Minor 3rd
            for (int i=start; i<end; i++){
                buffer = G13.beats.get(i).getNote();
                buffer += (12 - transpose);
                buffer %= 12;
                if (G13.beats.get(i).isNote() && buffer == 3){
                    set2.getData().add(new XYChart.Data(Integer.toString(i-start), buffer));
                    //System.out.println(buffer);
                } else {
                    set2.getData().add(new XYChart.Data(Integer.toString(i-start), blank));
                }
            }
        } else {
            // Major 3rd
            for (int i=start; i<end; i++){
                buffer = G13.beats.get(i).getNote();
                buffer += (12 - transpose);
                buffer %= 12;
                if (G13.beats.get(i).isNote() && buffer == 4){
                    set2.getData().add(new XYChart.Data(Integer.toString(i-start), buffer));
                    //System.out.println(buffer);
                } else {
                    set2.getData().add(new XYChart.Data(Integer.toString(i-start), blank));
                }
            }
        }
        
        
        
        // 4th
        for (int i=start; i<end; i++){
            buffer = G13.beats.get(i).getNote();
            buffer += (12 - transpose);
            buffer %= 12;
            if (G13.beats.get(i).isNote() && buffer == 5){
                set3.getData().add(new XYChart.Data(Integer.toString(i-start), buffer));
                //System.out.println(buffer);
            } else {
                set3.getData().add(new XYChart.Data(Integer.toString(i-start), blank));
            }
        }
        
        // 5th
        for (int i=start; i<end; i++){
            buffer = G13.beats.get(i).getNote();
            buffer += (12 - transpose);
            buffer %= 12;
            if (G13.beats.get(i).isNote() && buffer == 7){
                set4.getData().add(new XYChart.Data(Integer.toString(i-start), buffer));
                //System.out.println(buffer);
            } else {
                set4.getData().add(new XYChart.Data(Integer.toString(i-start), blank));
            }
        }
        
        if (minor) {
            // minor 6th
            for (int i=start; i<end; i++){
                buffer = G13.beats.get(i).getNote();
                buffer += (12 - transpose);
                buffer %= 12;
                if (G13.beats.get(i).isNote() && buffer == 8){
                    set5.getData().add(new XYChart.Data(Integer.toString(i-start), buffer));
                    //System.out.println(buffer);
                } else {
                    set5.getData().add(new XYChart.Data(Integer.toString(i-start), blank));
                }
            }
        } else {
            // major 6th
            for (int i=start; i<end; i++){
                buffer = G13.beats.get(i).getNote();
                buffer += (12 - transpose);
                buffer %= 12;
                if (G13.beats.get(i).isNote() && buffer == 9){
                    set5.getData().add(new XYChart.Data(Integer.toString(i-start), buffer));
                    //System.out.println(buffer);
                } else {
                    set5.getData().add(new XYChart.Data(Integer.toString(i-start), blank));
                }
            }
        }
        
        
        if (minor){
            // minor 7th
            for (int i=start; i<end; i++){
                buffer = G13.beats.get(i).getNote();
                buffer += (12 - transpose);
                buffer %= 12;
                if (G13.beats.get(i).isNote() && buffer == 10){
                    set6.getData().add(new XYChart.Data(Integer.toString(i-start), buffer));
                    //System.out.println(buffer);
                } else {
                    set6.getData().add(new XYChart.Data(Integer.toString(i-start), blank));
                }
            }
        } else {
            // major 7th
            for (int i=start; i<end; i++){
                buffer = G13.beats.get(i).getNote();
                buffer += (12 - transpose);
                buffer %= 12;
                if (G13.beats.get(i).isNote() && buffer == 11){
                    set6.getData().add(new XYChart.Data(Integer.toString(i-start), buffer));
                    //System.out.println(buffer);
                } else {
                    set6.getData().add(new XYChart.Data(Integer.toString(i-start), blank));
                }
            }
        }
        
        if (minor){
            // minor offkey
            for (int i=start; i<end; i++){
                buffer = G13.beats.get(i).getNote();
                buffer += (12 - transpose);
                buffer %= 12;
                if (G13.beats.get(i).isNote() &&
                        ( (buffer == 1) ||
                        (buffer == 4) ||
                        (buffer == 6) ||
                        (buffer == 9) ||
                        (buffer == 11) )){
                    set7.getData().add(new XYChart.Data(Integer.toString(i-start), buffer));
                } else {
                    set7.getData().add(new XYChart.Data(Integer.toString(i-start), blank));
                }
            }
        } else {
            // Major offkey
            for (int i=start; i<end; i++){
                buffer = G13.beats.get(i).getNote();
                buffer += (12 - transpose);
                buffer %= 12;
                if (G13.beats.get(i).isNote() &&
                        ( (buffer == 1) ||
                        (buffer == 3) ||
                        (buffer == 6) ||
                        (buffer == 8) ||
                        (buffer == 10) )){
                    set7.getData().add(new XYChart.Data(Integer.toString(i-start), buffer));
                } else {
                    set7.getData().add(new XYChart.Data(Integer.toString(i-start), blank));
                }
            }
        }
        
        
        targetChart.getData().addAll(set0);
        targetChart.getData().addAll(set1);
        targetChart.getData().addAll(set2);
        targetChart.getData().addAll(set3);
        targetChart.getData().addAll(set4);
        targetChart.getData().addAll(set5);
        targetChart.getData().addAll(set6);
        targetChart.getData().addAll(set7);
        targetChart.getData().addAll(set8);
        targetChart.getData().addAll(set9);
        targetChart.getData().addAll(set10);
        targetChart.getData().addAll(set11);
        targetChart.getXAxis().setTickMarkVisible(false);
        targetChart.getXAxis().setOpacity(0);
        //set1.getNode().getStyleClass().add("series-set1");
        keyPercent();
        String test = Arrays.toString(barStarts[currentTrack].getArray());
        System.out.println("Bar Starts: " + test);
        clickedButtonName = "";
    }

    private void loadDataTest0(ActionEvent event) {
        clickedButtonName = ButtonNames.fileOpen;
        currentTrack = 0;
        targetChart = tab1Chart;
        filePath[currentTrack] = TestTab.get(0);
        //System.out.println(TestTab.get(0));
    }

    private void loadDataTest1(ActionEvent event) {
        clickedButtonName = ButtonNames.fileOpen;
        currentTrack = 1;
        targetChart = tab2Chart;
        filePath[currentTrack] = TestTab.get(1);
        //System.out.println(TestTab.get(1));
    }

    private void loadDataTest2(ActionEvent event) {
        clickedButtonName = ButtonNames.fileOpen;
        currentTrack = 0;
        targetChart = tab1Chart;
        filePath[currentTrack] = TestTab.get(2);
        //System.out.println(TestTab.get(2));
    }
    
    
    public void populateDropDownsOne(){
        System.out.println(" - - populateDropDownsOne - -");
        
        ChoiceBox<String> bufferStart;
        ChoiceBox<String> bufferEnd;
        
        if (currentTrack == 0){
            bufferStart = trackOneStart;
            bufferEnd = trackOneEnd;
        } else {
            bufferStart = trackTwoStart;
            bufferEnd = trackTwoEnd;
        }
        
        bufferStart.getItems().clear();
        bufferEnd.getItems().clear();
        
        String barCount = Integer.toString(G13.Beats.barStarts.size());
        
        for (int i=0; i<G13.Beats.barStarts.size(); i++){
            String item = Integer.toString(i+1);
            bufferStart.getItems().add(item);
            bufferEnd.getItems().add(item);
        }
        
        bufferStart.setValue("1");
        bufferEnd.setValue(barCount);
    }
    
    public void populateKeyDropDown(){        
        for (int i=0; i<24; i++){
            keyDropDown.getItems().add(KeyDropDown.getKeys(i));
        }
    }
    
    // Works out how many notes "hit" in each key, and populates each Label with the percentage
    public void keyPercent(){
        int[] totalNoteChordCount = new int[12];
        int totalNotesInBeats = 0;
        int[] keyHitCount = new int[12];
        Label[] keyLabels = {key0, key1, key2, key3, key4, key5, key6, key7, key8, key9, key10, key11};
        
        for(int i=0; i<12; i++){
            totalNoteChordCount[i] += G13.Beats.noteCounter[i];
            totalNoteChordCount[i] += G13.Beats.chordCounter[i];
            totalNotesInBeats += totalNoteChordCount[i];
        }
        //System.out.println("Note count array: " + Arrays.toString(totalNoteChordCount));
        //System.out.println("Total notes: " + Integer.toString(totalNotesInBeats));
        
        int[] shiftedNotes = totalNoteChordCount;
        boolean[] keyPattern = {true, false, true, false, true, true, false, true, false, true, false, true};
        
        // i loop goes through each different key
        for (int i=0; i<12; i++){
            // j loop goes through each boolean in that key pattern
            for (int j=0; j<12; j++){
                if(keyPattern[j] == true){
                    keyHitCount[i] += shiftedNotes[j];
                }
            }            
            shiftedNotes = shiftedHitCount(shiftedNotes);
        }
        
        for (int k=0; k<12; k++){
            double percentage = (keyHitCount[k]*100)/totalNotesInBeats;
            int concatPercent = (int)percentage;
            keyLabels[k].setText(concatPercent + " %");
            keyLabels[k].setStyle("<font-weight>: regular");
            keyLabels[k].setTextFill(Color.web("#44739c"));
            if (percentage > 80){
                keyLabels[k].setTextFill(Color.web("#e36700"));
                keyLabels[k].setStyle("-fx-font-weight: bold");
            } else if (percentage > 50) {
                keyLabels[k].setTextFill(Color.web("#6493bc"));
            }
        }
    }
    
    //Shift every value down one index
    public int[] shiftedHitCount(int[] input){
        int[] output = new int[12];
        output[11] = input[0];
        for(int i=0; i<11; i++){
            output[i] = input[i+1];
        }
        return output;
    }

    @FXML
    private void topFileChooser(ActionEvent event) {
        currentTrack = 0;
        targetChart = tab1Chart;
        fileChooserMaster();
    }

    @FXML
    private void bottomFileChooser(ActionEvent event) {
        currentTrack = 1;
        targetChart = tab2Chart;
        fileChooserMaster();
    }
    
    private void fileChooserMaster(){
        System.out.println(" - - fileChooserMaster() - -");
        clickedButtonName = ButtonNames.fileOpen;
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        Label label;
        
        if (currentTrack == 0){
            label = topTabLabel;
        } else {
            label = bottomTabLabel;
        }

        if (selectedFile != null) {
            label.setText(selectedFile.getName());
            //System.out.println(selectedFile.getAbsolutePath());
            filePath[currentTrack] = selectedFile.getAbsolutePath();
        }
        
        loadData();
    }

    @FXML
    private void openCoF(MouseEvent event) {
        Stage window = new Stage();
        window.setTitle("Circle of Fifths");
        window.setMinWidth(300);
        window.setMinHeight(300);
        
        Label label = new Label();
        label.setText("I'm a label");
        
        //System.out.println(CurrentProjectDirectory.resources() + "D" + ".png");
        String key = KeyDropDown.getKeys(keyDropDown.getSelectionModel().getSelectedIndex());
        File file = new File(CurrentProjectDirectory.resources() + key + ".png");
        Image image;
        image = new Image(file.toURI().toString());
        ImageView img = new ImageView();
        img.setImage(image);
        
        img.minHeight(300);
        img.minWidth(300);
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(img);
        
        Scene scene2 = new Scene(layout);
        
        window.setScene(scene2);
        window.show();
        
    }
    
} // end of controller


   
    
    