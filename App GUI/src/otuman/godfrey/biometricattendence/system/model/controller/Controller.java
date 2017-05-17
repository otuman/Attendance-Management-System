/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otuman.godfrey.biometricattendence.system.model.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import otuman.godfrey.biometricattendence.system.model.AssignedCourses;
import otuman.godfrey.biometricattendence.system.model.AttendanceRecords;
import otuman.godfrey.biometricattendence.system.model.CheckDateAndcCode;
import otuman.godfrey.biometricattendence.system.model.Database;
import otuman.godfrey.biometricattendence.system.model.Instructor;
import otuman.godfrey.biometricattendence.system.model.Person;
import otuman.godfrey.biometricattendence.system.model.SerialArrivedData;

/**
 *
 * @author Otoman
 */
public class Controller {
    
    Database db = new Database();
    SerialArrivedData sad =new SerialArrivedData();
    
    public void addPerson(Person person){
        db.addPerson(person);
     }
    public void loadAssignedProfCourse(int id) throws SQLException{
          db.loadAssignedProfCourse(id);
    }
    public void loadCourses(int id) throws SQLException{
         db.loadCourse(id);
    }
    public ArrayList getCourses(){
    return db.getCourses();
    }
    public void load(){
      db.loadStundentInformation();
    }
    public List<Person> getPeople(){
        return db.getPeople();
    }
    public void checkDateAndcCode(String date, String cCode ) throws SQLException{
     db.checkCourseCodeAndDate(date, cCode);
    }
    public void loadAttendanceStatus(String cCode) throws SQLException{
        db.loadAndInsertAttendanceStatus(cCode);
    }
   public void newloadAttendanceStatus(String cCode, String date) throws SQLException{
       db.loadAttendanceStatus(cCode, date);
    }
    public List<AttendanceRecords> getAttRecords(){
        return db.getAttRecords();
    }
    public void saveStudentInfo(Person person)throws SQLException{
        db.saveStudentInfo(person);
    }
    public void connect()throws Exception{
         db.connect();
    }
    public void updateAttendenceRecord(int rData) throws SQLException{
         db.updateAttendenceRecord(rData);
    }
    public void loadInstructorInfo() throws SQLException{
        db.loadInstructor();
    }
    public ArrayList<Instructor> getInstructors(){
        return db.getInstructors();
    }
    public ArrayList<AssignedCourses> getAssignedCourseses(){
    
        return db.getAssignedcourses();
    }
    public ArrayList<CheckDateAndcCode> getCheckDateAndcCode(){
         return db.getCheckDateAndcCode();
    }
}
