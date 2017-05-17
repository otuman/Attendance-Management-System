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
public class Instructor {
    
    private int iID;
    private String  firstName;
    private String  middleName;
    private String  lastName;
    private String  gender;
    private String  username;
    private String  password;
  

   public Instructor(){
   }

    public Instructor(int iID, String firstName, String middleName, String lastName, 
            String gender,String username, String password){
        this.iID              = iID;
        this.firstName       = firstName;
        this.middleName      = middleName;
        this.lastName        = lastName;
        this.gender          = gender;
        this.username        = username;
        this.password        = password;
       
        
    }
    
    public int getiID() {
        return iID;
    }

    public void setIID(int iID) {
        this.iID = iID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   
    
}
