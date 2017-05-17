/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otuman.godfrey.biometricattendence.system.model;

import java.util.ArrayList;

/**
 *
 * @author Otoman
 */
public class SerialArrivedData {
   
    private ArrayList<Integer> arrivedData;
    
   
   public SerialArrivedData(){
        arrivedData = new  ArrayList<>();
    }
    
    public ArrayList<Integer> getArrivedData() {
        return arrivedData;
    }

  public void addArrivedData(ArrayList<Integer> arraivedData) {
        this.arrivedData = arraivedData;
    }
    

}
