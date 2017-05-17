/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otuman.godfrey.biometricattendence.system.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import otuman.godfrey.biometricattendence.system.gui.app.ToolBarPanel;

public class Database {

    private final ArrayList<Person> people;
    private final ArrayList<AttendanceRecords> attRecords;
    private final ArrayList<CheckDateAndcCode> checkDateAndcCode;
    private final ArrayList<Instructor> instructors;
    private final ArrayList<Courses> courses;
    private final ArrayList<EnrollObject> enroll;
    private final ArrayList<AttendanceTableInfo> attendanceTInfo;

    private final ArrayList<AssignedCourses> assignedcourses;

    private Connection conn;

    public Database() {
        people = new ArrayList<>();
        attRecords = new ArrayList<>();
        instructors = new ArrayList<>();
        courses = new ArrayList<>();
        assignedcourses = new ArrayList<>();
        enroll = new ArrayList<>();
        attendanceTInfo = new ArrayList<>();
        checkDateAndcCode = new ArrayList<>();
    }

    public void addPerson(Person person) {
        people.add(person);
    }

    public void connect() throws Exception {
        if (conn != null) {
            return;
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new Exception("Drivers not found");
        }
        String url = "jdbc:mysql://localhost:3306/biometric_attendance_records";
        String user = "root";
        String password = "";
        conn = DriverManager.getConnection(url, user, password);

        //System.out.println("Connected: " + conn);

    }

    public void saveStudentInfo(Person person) throws SQLException {
        String CheckSql = "SELECT COUNT(*) as count FROM student WHERE student_id=?";
        PreparedStatement checkStmt = conn.prepareStatement(CheckSql);

        String insertSql = "INSERT INTO student(student_id,firstName,middleName,lastName,gender, course_id,finger_id) value(?,?,?,?,?,?,?)";
        PreparedStatement insertStatement = conn.prepareStatement(insertSql);

        String updateSql = "UPDATE student set firstName=?, middleName=?, lastName=?,gender=?, finger_id=? WHERE student_id=?";
        PreparedStatement updateStmt = conn.prepareStatement(updateSql);

            int id = person.getID();

            String fName  = person.getFirstName();
            String mName  = person.getMiddleName();
            String lName  = person.getFirstName();
            String gender = person.getGender();
            int fingerID =  person.getFinger_Id();

            checkStmt.setInt(1, id);
            ResultSet checkResult = checkStmt.executeQuery();
            checkResult.next();
            int count = checkResult.getInt(1);
           // System.out.println("Count for person with id " + id + " is " + count);

            if (count == 0) {
                int col = 1;
                insertStatement.setInt(col++, id);
                insertStatement.setString(col++, fName);
                insertStatement.setString(col++, mName);
                insertStatement.setString(col++, lName);
                insertStatement.setString(col++, gender);
                insertStatement.setInt(col++, fingerID);
                insertStatement.executeUpdate();
            } else {
                int col = 1;
                updateStmt.setInt(col++, id);
                updateStmt.setString(col++, fName);
                updateStmt.setString(col++, mName);
                updateStmt.setString(col++, lName);
                updateStmt.setString(col++, gender);
                updateStmt.setInt(col++, fingerID);
                updateStmt.executeUpdate();
                System.out.println("Count for person with id " + id + " is " +fName + " Finger ID " +fingerID);

            }
    }

    public void updateAttendenceRecord(int rData) throws SQLException {
        boolean status = true;

        String CheckSql = "SELECT * FROM student WHERE finger_id= " + rData + "";
        PreparedStatement checkStmt = conn.prepareStatement(CheckSql);
        ResultSet rs = checkStmt.executeQuery();
        int ID = 0;
        if (rs.next()) {
            ID = rs.getInt(1);
        }
        String updateSql = "UPDATE attendence_records set attend_status=" + status + " WHERE student_id=" + ID + "";
        PreparedStatement updateStmt = conn.prepareStatement(updateSql);
        updateStmt.executeUpdate();

    }

    public void loadStundentInformation() {
        people.clear();
        String sqlLoad = "SELECT * FROM student";

        try {
            ResultSet rs;
            try (PreparedStatement prepStmt = conn.prepareStatement(sqlLoad)) {
                rs = prepStmt.executeQuery();
                while (rs.next()) {
                    int ID = rs.getInt(1);
                    String fName = rs.getString(2);
                    String mName = rs.getString(3);
                    String lName = rs.getString(4);
                    String gender = rs.getString(5);
                    int fingerId = rs.getInt(6);

                    Person person = new Person(ID, fName, mName, lName, gender, fingerId);
                    people.add(person);
                }
            }
            rs.close();
        } catch (SQLException e) {
        }
    }

    public void loadAndInsertAttendanceStatus(String cCode) throws SQLException {

        attRecords.clear();
        Calendar cal = new GregorianCalendar();
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        // int milsec = cal.get(Calendar.MILLISECOND);
        int hour = cal.get(Calendar.HOUR);
        String time = hour + ":" + minute + ":" + second;

        String sqlen = "SELECT * FROM enrollment";
        PreparedStatement stmt = conn.prepareStatement(sqlen);
        ResultSet rss = stmt.executeQuery();
        enroll.clear();
        while (rss.next()) {
            int eId = rss.getInt(1);
            String coursecode = rss.getString(2);
            String sId = rss.getString(3);
            String sem = rss.getString(4);
            EnrollObject enl = new EnrollObject(eId, sId, coursecode);
            enroll.add(enl);

        }
        for (int i = 0; i < enroll.size(); i++) {

            EnrollObject enr = enroll.get(i);
            System.out.println("The ID :" + enr.getEnroll_id());

        }
        System.out.println("Enroll id done");
        for (int i = 0; i < enroll.size(); i++) {

            EnrollObject enr = enroll.get(i);
            if (cCode.equals(enr.getCode_id())) {
                String sqls = "SELECT enrollment.enroll_id, "
                        + "student.student_id, student.firstName, student.middleName,"
                        + "student.lastName, student.gender,"
                        + "courses.course_code, courses.course_name "
                        + "FROM "
                        + "enrollment, student, courses "
                        + "WHERE "
                        + "enrollment.enroll_id =" + enr.getEnroll_id() + " "
                        + "AND "
                        + "enrollment.course_code = courses.course_code "
                        + "AND "
                        + "enrollment.student_id   = student.student_id ";
                PreparedStatement ps = conn.prepareStatement(sqls);
                ResultSet rsr = ps.executeQuery();

                while (rsr.next()) {
                    int col = 1;
                    int enrollID = rsr.getInt(col++);
                    String student_id = rsr.getString(col++);
                    String fName = rsr.getString(col++);
                    String mName = rsr.getString(col++);
                    String lName = rsr.getString(col++);
                    String gender = rsr.getString(col++);
                    String courseCode = rsr.getString(col++);
                    String courseName = rsr.getString(col++);
                    boolean attendStatus = false;

                    AttendanceRecords record = new AttendanceRecords(enrollID, fName, mName, lName, gender, courseCode, courseName, student_id, attendStatus);
                    attRecords.add(record);

                }

            }
        }

        if (ToolBarPanel.isNewAttendaceRecord()) {
            
            System.out.println(ToolBarPanel.isNewAttendaceRecord());
            boolean status = false;
            String insertAttends = "INSERT INTO attendence_records "
                    + "(attend_id, attend_status, time, date, course_code, student_id) "
                    + "VALUE (?,?,?,?,?,?)";
            PreparedStatement ins = conn.prepareStatement(insertAttends);
            HashSet<AttendanceRecords> uniq = new HashSet<>(attRecords);
            for (Iterator<AttendanceRecords> it = uniq.iterator(); it.hasNext();) {
                AttendanceRecords re = it.next();
                System.out.print(re.getAttendID());
                int atts = personMaxId();
                if (cCode.equals(re.getCourseCode())) {
                    ins.setInt(1, atts + 1);
                    ins.setBoolean(2, status);
                    ins.setString(3, time);
                    ins.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
                    ins.setString(5, re.getCourseCode());
                    ins.setString(6, re.getStudentId());
                    ins.executeUpdate();

                }
            }

        }
        

    }

    public void loadAttendanceStatus(String cCode, String date) throws SQLException {
        attRecords.clear();
        attendanceTInfo.clear();

        String sqlen = "SELECT * FROM attendence_records";
        PreparedStatement stmt = conn.prepareStatement(sqlen);
        ResultSet rss = stmt.executeQuery();
        while (rss.next()) {
            int aId = rss.getInt(1);
            boolean attendanceStatus = rss.getBoolean(2);
            String time = rss.getString(3);
            Date dat = rss.getDate(4);
            String coursecode = rss.getString(5);
            String studentId = rss.getString(6);

            AttendanceTableInfo att = new AttendanceTableInfo(aId, attendanceStatus, coursecode, studentId, dat, time);
            attendanceTInfo.add(att);

        }
        for (int i = 0; i < attendanceTInfo.size(); i++) {
            AttendanceTableInfo atts = attendanceTInfo.get(i);

            if (cCode.equals(atts.getCourseCode())) {
                String sqls = "SELECT attendence_records.attend_id, attendence_records.attend_status, attendence_records.time, "
                        + "attendence_records.date, "
                        + " student.student_id, student.firstName, student.middleName, student.lastName, student.gender,"
                        + "courses.course_code, courses.course_name "
                        + "FROM "
                        + "attendence_records, student, courses "
                        + "WHERE "
                        + "attendence_records.attend_id=" + atts.getAttendID() + " "
                        + "AND "
                        + "attendence_records.course_code = courses.course_code "
                        + "AND "
                        + "attendence_records.student_id   = student.student_id ";
                PreparedStatement ps = conn.prepareStatement(sqls);
                ResultSet rsr = ps.executeQuery();

                while (rsr.next()) {
                    int col = 1;
                    int ID = rsr.getInt(col++);
                    boolean attendStatus = rsr.getBoolean(col++);
                    Time time = rsr.getTime(col++);
                    Date datee = rsr.getDate(col++);
                    String student_id = rsr.getString(col++);
                    String fName = rsr.getString(col++);
                    String mName = rsr.getString(col++);
                    String lName = rsr.getString(col++);
                    String gender = rsr.getString(col++);
                    String courseCode = rsr.getString(col++);
                    String courseName = rsr.getString(col++);
                    AttendanceRecords record = new AttendanceRecords(ID, fName, mName, lName, gender, courseCode, courseName, student_id, attendStatus, time, datee);
                    if (String.valueOf(datee).equals(date)) {
                        attRecords.add(record);
                    }
                }

            }

        }
        for (int i = 0; i < attRecords.size(); i++) {

            AttendanceRecords r = attRecords.get(i);

            System.out.print(r.getAttendID());
            System.out.print(" " + r.getCourseCode());
            System.out.print(" " + r.getStudentId());
            System.out.print(" " + r.getfName());
            System.out.println();

        }
    }

    public int personMaxId() throws SQLException {
        int id = 0;
        String maxId = "SELECT MAX(attend_id) FROM attendence_records ";
        ResultSet rs;
        try (PreparedStatement pstmt = conn.prepareStatement(maxId)) {
            rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        }
        rs.close();

        return id;
    }

    public void loadInstructor() throws SQLException {
        String sqlstmt = "SELECT * FROM instructors";

        PreparedStatement pStatement = conn.prepareStatement(sqlstmt);
        ResultSet rs = pStatement.executeQuery();

        while (rs.next()) {
            int col = 1;
            int iId = rs.getInt(col++);
            String fName = rs.getString(col++);
            String mName = rs.getString(col++);
            String lName = rs.getString(col++);
            String gender = rs.getString(col++);
            String uName = rs.getString(col++);
            String password = rs.getString(col++);
            Instructor inst = new Instructor(iId, fName, mName, lName, gender, uName, password);
            instructors.add(inst);
        }

    }

    public void loadAssignedProfCourse(int id) throws SQLException {
        String sqlstmt = "SELECT * FROM teaching_assingment WHERE instructor_id =" + id + "";
        PreparedStatement stmt = conn.prepareStatement(sqlstmt);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int ID = rs.getInt(1);
            String cCode = rs.getString(3);
            int instID = rs.getInt(2);
            AssignedCourses assign = new AssignedCourses(ID, cCode, instID);
            assignedcourses.add(assign);
        }

    }
    public void checkCourseCodeAndDate(String date, String cCode) throws SQLException{
           checkDateAndcCode.clear();
           String select ="SELECT * FROM attendence_records WHERE date='"+date+"'  AND course_code='"+cCode+"'" 
                   + "";
           PreparedStatement stmts = conn.prepareStatement(select);
           ResultSet rrs = stmts.executeQuery();

        while (rrs.next()){
            int ID = rrs.getInt(1);
            String dat = rrs.getString(4);
            String cCod = rrs.getString(5);
            CheckDateAndcCode check = new CheckDateAndcCode(ID, cCod, dat);
            checkDateAndcCode.add(check);
        }
    
    }
    
    public void loadCourse(int id) throws SQLException {
        String sqlstmt = "SELECT * FROM courses";

        PreparedStatement pStatement = conn.prepareStatement(sqlstmt);
        ResultSet rs = pStatement.executeQuery();

        while (rs.next()) {
            int col = 1;
            String cCode = rs.getString(col++);
            String cName = rs.getString(col++);
            int instructorId = rs.getInt(col++);

            Courses cou = new Courses(cCode, cName, instructorId);
            courses.add(cou);
        }

    }

    public ArrayList<Instructor> getInstructors() {
        return instructors;
    }

    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public List<Person> getPeople() {
        return people;
    }

    public List<AttendanceRecords> getAttRecords() {
        return attRecords;
    }

    public ArrayList<Courses> getCourses() {
        return courses;
    }

    public ArrayList<AssignedCourses> getAssignedcourses() {
        return assignedcourses;
    }

    public ArrayList<EnrollObject> getEnroll() {
        return enroll;
    }

    public ArrayList<AttendanceTableInfo> getAttendanceTInfo() {
        return attendanceTInfo;
    }
    
    public ArrayList<CheckDateAndcCode> getCheckDateAndcCode() {
        return checkDateAndcCode;
    }
    

}
