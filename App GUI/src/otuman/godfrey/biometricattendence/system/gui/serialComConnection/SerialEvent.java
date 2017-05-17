/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otuman.godfrey.biometricattendence.system.gui.serialComConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Otoman
 */
public class SerialEvent{
    
    private ArrayList<Integer> arrivedData;

   
  public SerialEvent(){
      
      this.arrivedData = new ArrayList<>();
  }
  
  public ArrayList<Integer> getArrivedData() {
        return arrivedData;
    }

  public void addArrivedData(ArrayList<Integer> arraivedData) {
        this.arrivedData = arraivedData;
    }
  
 public int returnValue(){
  int i =3;
return i;
} 
 
 List<Integer> list = Arrays.asList( );
CharSequence[] cs = list.toArray(new CharSequence[list.size()]);

}
