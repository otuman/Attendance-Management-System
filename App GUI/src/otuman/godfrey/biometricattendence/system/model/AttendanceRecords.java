/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otuman.godfrey.biometricattendence.system.model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Otoman
 */
public class AttendanceRecords {

    private int attendID;
    private boolean attendStatus;
    private String fName;
    private String mName;
    private String lName;
    private String gender;
    private String courseName;
    private String courseCode;
    private String studentId;
    private Date  date;
    private Time  time; 

    

    public AttendanceRecords(int attendID,String fName, String mName, String lName,
            String gender, String courseCode,String courseName, String studentId, boolean attendStatus, Time time, Date date) {
        this.attendID = attendID;
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.gender = gender;
        this.attendStatus = attendStatus;
        this.courseCode = courseCode;
        this.studentId = studentId;
        this.courseName = courseName;
        this.time = time;
        this.date = date;
    }
    public AttendanceRecords(int attendID,String fName, String mName, String lName,
            String gender, String courseCode,String courseName, String studentId, boolean attendStatus) {
        this.attendID = attendID;
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.gender = gender;
        this.attendStatus = attendStatus;
        this.courseCode = courseCode;
        this.studentId = studentId;
        this.courseName = courseName;
        
    }

    public int getAttendID() {
        return attendID;
    }

    public void setAttendID(int attendID) {
        this.attendID = attendID;
    }

    public boolean isAttendStatus() {
        return attendStatus;
    }

    public void setAttendStatus(boolean attendStatus) {
        this.attendStatus = attendStatus;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
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

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
   

}
