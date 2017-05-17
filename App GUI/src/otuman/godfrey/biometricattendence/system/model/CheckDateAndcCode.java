/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otuman.godfrey.biometricattendence.system.model;

/**
 *
 * @author Otoman
 */
public class CheckDateAndcCode {
   private int    id; 
   private String cCode;
   private String date;
    
   
   public CheckDateAndcCode(int id, String cCode, String date){
       this.cCode = cCode;
       this.date  = date;
       this.id    = id;
       
   }
   
   public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getcCode() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
