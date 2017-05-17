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
public class AssignedCourses {

    private String courseCode;
    private int intructorId;
    private int ID;

    
    public AssignedCourses(int ID,String courseCode, int instructorId) {
        this.intructorId = instructorId;
        this.courseCode = courseCode;
        this.ID         = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getIntructorId() {
        return intructorId;
    }

    public void setIntructorId(int intructorId) {
        this.intructorId = intructorId;
    }

}
