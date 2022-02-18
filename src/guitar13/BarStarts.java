package guitar13;

import java.util.ArrayList;

/**
 *
 * @author Ning
 */
public class BarStarts {
    
    private int[] array;
    private int end;
    
    public void set(ArrayList<Integer> input){
        int size = input.size();
        array = new int[size];
        for (int i=0; i<size; i++){
            array[i] = input.get(i);
        }
    }
    
    public int get(int input){
        return array[input];
    }
    
    public int[] getArray(){
        return array;
    }
    
    public int size(){
        return array.length;
    }
    
    public void setEnd(int end){
        this.end = end;
    }
    
    public int getEnd(){
        return end;
    }
}
