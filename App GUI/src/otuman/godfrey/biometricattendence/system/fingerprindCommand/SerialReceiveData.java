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
public class SerialReceiveData{
    
    private int dataReceived;
    
    public SerialReceiveData(int data){
          this.dataReceived = data;
    }
    public SerialReceiveData(){
    }
    public int getDataReceived() {
        return dataReceived;
    }

    public void setDataReceived(int dataReceived) {
        this.dataReceived = dataReceived;
    }
    
    
}
