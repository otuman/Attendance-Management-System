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
public class Courses {

    private String  courseCode;
    private String  courseName;
    private int  intructorId;
  


   public Courses(String courseName,String courseCode,int intructorId){
        this.courseName         = courseName;
        this.courseCode         = courseCode;
        this.intructorId        = intructorId;
             
    }
   public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getIntructorId() {
        return intructorId;
    }

    public void setIntructorId(int intructorId) {
        this.intructorId = intructorId;
    }
    
    
}
