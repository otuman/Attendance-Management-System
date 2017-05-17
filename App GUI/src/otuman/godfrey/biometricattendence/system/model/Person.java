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
public class Person {
    
    private int ID;
    private String  firstName;
    private String  middleName;
    private String  lastName;
    private String gender;
    private String courseCode;
    private int    finger_id;
   

   

    public Person(int sID, String firstName, String middleName, String lastName, 
            String gender, int finger_id){
        this.ID              = sID;
        this.firstName       = firstName;
        this.middleName      = middleName;
        this.lastName        = lastName;
        this.gender          = gender;
        this.finger_id       = finger_id;
        
        
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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
     public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseName) {
        this.courseCode = courseName;
    }

    public int getFinger_Id() {
        return finger_id;
    }

    public void setFinger_Id(int finger_id) {
        this.finger_id = finger_id;
   }
  
}
