/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otuman.godfrey.biometricattendence.system.fingerprindCommand;

/**
 *
 * @author Otoman
 */
public class EnrollUser {
    
    private int data;

    
    public EnrollUser(int data){
        this.data = data;
    
    }
    public EnrollUser(){
    }
    public int getData() {
        return data;
    }

    public void setData(int data){
        this.data = data;
    }
    

}
