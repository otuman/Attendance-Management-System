/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otuman.godfrey.biometricattendence.system.model;

import java.sql.Date;

/**
 *
 * @author Otoman
 */


public class AttendanceTableInfo {
    private int attendID;
    private boolean attendStatus;
    private String courseCode;
    private String studentId;
    private Date date;
    private String time;
    public AttendanceTableInfo(int attendID, boolean attendStatus, String courseCode,String studentId, Date date, String time ){
          this.attendID= attendID; 
          this.attendStatus= attendStatus; 
          this.studentId= studentId; 
          this.courseCode= courseCode; 
          this.date= date; 
          this.time= time; 
          
    
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

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
}
