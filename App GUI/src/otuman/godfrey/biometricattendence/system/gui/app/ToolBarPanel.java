package otuman.godfrey.biometricattendence.system.gui.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.JOptionPane;
import otuman.godfrey.biometricattendence.system.gui.login.AttendanceLogin;
import otuman.godfrey.biometricattendence.system.gui.serialComConnection.SerialCommunication;
import otuman.godfrey.biometricattendence.system.model.AssignedCourses;
import otuman.godfrey.biometricattendence.system.model.CheckDateAndcCode;
import otuman.godfrey.biometricattendence.system.model.Instructor;
import otuman.godfrey.biometricattendence.system.model.controller.Controller;

public final class ToolBarPanel extends JToolBar {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final int height = 35;
    private static boolean found = false;
    private Calendar cal;
    private final JButton enroll;
    private final JButton conEnroll;
    // private final JLabel connectedIcon;
    private final JLabel courseName;
    private final JComboBox coursecode;
    private final JLabel todayLabel;
    private final JLabel timeLabel;
    private final JLabel dateLabel;
    private static JButton activateAttends;
    private final JLabel courseNameLabel;
    private final JLabel emptyLabel;
    public static JDialog d;
    private final JButton updateButton;
    private final SerialCommunication sConnCom;
    private final Font font;
    AddNewPersonPannel addP;
    private static boolean connect;
    private static String courseCode;
    private static String setDate;
    private final int mc;
    public static boolean activate = false;
    private final Controller controller;
    private static boolean newRecord = true;

    public ToolBarPanel() {

        mc = JOptionPane.WARNING_MESSAGE;
        coursecode = new JComboBox();
        controller = new Controller();
        courseCode = new String();
        setDate = new String();
        connect = false;
        sConnCom = new SerialCommunication();
        font = new Font("New Times Roman", Font.PLAIN, 15);
        // connectedIcon = new JLabel(new ImagesUtilities().createIcon("/images/connected.png"));
        // connectedIcon.setPreferredSize(new Dimension(60,30));
        //connectedIcon.setVisible(false);
        todayLabel = new JLabel("", JLabel.CENTER);
        todayLabel.setPreferredSize(new Dimension(70, 50));
        todayLabel.setFont(font);
        todayLabel.setForeground(Color.MAGENTA);

        timeLabel = new JLabel("", JLabel.CENTER);
        timeLabel.setPreferredSize(new Dimension(90, 50));
        timeLabel.setFont(font);
        timeLabel.setForeground(Color.MAGENTA);

        dateLabel = new JLabel("", JLabel.CENTER);
        dateLabel.setPreferredSize(new Dimension(200, 50));
        dateLabel.setFont(font);
        dateLabel.setForeground(Color.MAGENTA);
        enroll = new JButton("Enroll");
        activateAttends = new JButton("Activate Attendance");
        conEnroll = new JButton("Con Enroll");
        courseNameLabel = new JLabel("Course code: ", JLabel.CENTER);
        courseNameLabel.setPreferredSize(new Dimension(100, 50));
        courseNameLabel.setFont(font);
        updateButton = new JButton("Update");

        courseName = new JLabel(" ", JLabel.LEFT);
        emptyLabel = new JLabel("                  ");
        emptyLabel.setPreferredSize(new Dimension(150, 50));

        getListOfCourseCode();   //this method return combox
        coursecode.setPreferredSize(new Dimension(125, 40));
        coursecode.setFont(font);

        setFloatable(false);
        add(dateLabel);
        add(timeLabel);
        add(todayLabel);
        add(coursecode);
        add(courseNameLabel);
        add(emptyLabel);
        add(updateButton);
        add(activateAttends);
        add(conEnroll);
        add(enroll);
        ToolBarButtons();

        TimeAndDate();
        //setBorder(BorderFactory.createRaisedSoftBevelBorder());
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        setRollover(true);
        setSize(new Dimension(getWidth(), height));
        setMinimumSize(new Dimension(getWidth(), height));
        setPreferredSize(new Dimension(getWidth(), height));
        setVisible(true);
        //listOfAvailableCom.setModel(new DefaultComboBoxModel(sConnCom.getAvalableList().toArray()));

    }

    private void TimeAndDate() {

        Thread thread;
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    String dayName = "";
                    String monthName = "";
                    for (;;) {
                        cal = new GregorianCalendar();
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        int month = cal.get(Calendar.MONTH);
                        int year = cal.get(Calendar.YEAR);

                        int second = cal.get(Calendar.SECOND);
                        int minute = cal.get(Calendar.MINUTE);
                        int hour = cal.get(Calendar.HOUR);
                        int today = cal.get(Calendar.DAY_OF_WEEK);

                        switch (today) {
                            case 1:
                                dayName = "Sunday";
                                break;
                            case 2:
                                dayName = "Monday";
                                break;
                            case 3:
                                dayName = "Tuesday";
                                break;
                            case 4:
                                dayName = "Wednesday";
                                break;
                            case 5:
                                dayName = "Thursday";
                                break;
                            case 6:
                                dayName = "Friday";
                                break;
                            case 7:
                                dayName = "Saturday";
                                break;

                        }
                        switch (month) {
                            case 0:
                                monthName = "January";
                                break;
                            case 1:
                                monthName = "February";
                                break;
                            case 2:
                                monthName = "March";
                                break;
                            case 3:
                                monthName = "April";
                                break;
                            case 4:
                                monthName = "May";
                                break;
                            case 5:
                                monthName = "June";
                                break;
                            case 6:
                                monthName = "July";
                                break;
                            case 7:
                                monthName = "August";
                                break;
                            case 8:
                                monthName = "September";
                                break;
                            case 9:
                                monthName = "October";
                                break;
                            case 10:
                                monthName = "November";
                                break;
                            case 11:
                                monthName = "December";
                                break;

                        }
                        todayLabel.setText("Today : ");
                        int am_pm = cal.get(Calendar.AM_PM);
                        if (hour == 0) {
                            hour = 12;
                        }
                        if (am_pm == 1) {
                            timeLabel.setText(String.format("%02d", hour) + ":" + String.format("%02d", minute) + ":" + String.format("%02d", second) + " PM");
                        } else if (am_pm == 0) {
                            timeLabel.setText(String.format("%02d", hour) + ":" + String.format("%02d", minute) + ":" + String.format("%02d", second) + " AM");
                        }
                        dateLabel.setText(dayName + ", " + monthName + " " + String.format("%02d", day) + ", " + year);

                        Thread.sleep(1000);
                    }
                } catch (InterruptedException ie) {
                }

            }
        };
        thread.start();
    }

    public final void ToolBarButtons() {
        enroll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = JOptionPane.showInputDialog(new DisplayInfoPanel(), "A", "o", JOptionPane.OK_OPTION);
                if (!id.equals("") && id.length() > 0) {
                    int rcvd = Integer.parseInt(id);
                    try {
                        sConnCom.SerialWritter(rcvd);
                    } catch (IOException ex) {
                        Logger.getLogger(ToolBarPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        activateAttends.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SerialCommunication.isFromFP()) {
                    if (false == activate) {

                        activate = true;
                        activateAttends.setText("Close attendance");

                    } else {
                        activate = false;
                        activateAttends.setText("Open Attendance");
                    }
                } else {
                    JOptionPane.showMessageDialog(ToolBarPanel.this, "There is no connection");
                }

            }
        });
        coursecode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                found = false;
                String date = "date";
                String cCode = (String) coursecode.getSelectedItem();
                Date d = java.sql.Date.valueOf(LocalDate.now());
                
                int cornfim = JOptionPane.showConfirmDialog(ToolBarPanel.this, "Do you want to add new attendance record?", "Cornfim", JOptionPane.YES_NO_CANCEL_OPTION);
                if (cornfim == JOptionPane.YES_OPTION) {
                    date = String.valueOf(d);
                    try {
                        controller.checkDateAndcCode(date, cCode);
                        System.out.println("Ndohiii "+date + " " + cCode);
                        for (CheckDateAndcCode check : controller.getCheckDateAndcCode()) {
                            if (check.getDate().equals(date) && check.getcCode().equals(cCode)) {
                                found = true;
                            }
                        }
                        if (found) {
                            JOptionPane.showMessageDialog(ToolBarPanel.this, "The attendance record for code : " + cCode + " and \n date : " + date + " is already in the system,"
                                    + "\n Just activate the attendance ");
                            ToolBarPanel.setNewAttendaceRecord(false);
                            setFound(found);
                            //found = false;
                        } else {
                            ToolBarPanel.setNewAttendaceRecord(true);
                            setFound(found);
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(ToolBarPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ToolBarPanel.setDate(date);

                } else if (cornfim == JOptionPane.NO_OPTION) {
                    ToolBarPanel.setNewAttendaceRecord(false);
                    selectDateAndCodeOptions();
                    date = SelectDatePanelOption.getSelectedDate();
                }
                setActiveCourse(cCode);
                //System.out.println(date + " " + cCode);
                if (!cCode.isEmpty()) {
                    controller.getAttRecords().clear();
                    try {
                        controller.connect();
                        if (isNewAttendaceRecord()) {

                            controller.loadAttendanceStatus(cCode);
                            controller.newloadAttendanceStatus(cCode, date);

                        } else {

                            controller.newloadAttendanceStatus(cCode, date);
                        }
                        AttendanceSummaryPannel.setAttendanceSummary(controller.getAttRecords());
                        DisplayRecordTableModel.setAttendRecord(controller.getAttRecords());
                        ClassSessionViewPannel.refresh();

                    } catch (Exception ex) {
                        Logger.getLogger(ToolBarPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

    }

    public void initiallyRecord() throws Exception {
        found = false;
        String cCode;
        String username = AttendanceLogin.getCurrentuser();
        ArrayList list = looadInstructorInfo(username);
        Instructor inst;
        int id = 0;
        for (int i = 0; i < list.size(); i++) {
            inst = (Instructor) list.get(i);
            if (username.equals(inst.getUsername())) {

                id = inst.getiID();

            }
        }

        controller.loadAssignedProfCourse(id);
        list.clear();
        list = controller.getAssignedCourseses();
        ArrayList<String> l = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            AssignedCourses asgn = (AssignedCourses) list.get(i);
            if (id == asgn.getIntructorId()) {
                cCode = asgn.getCourseCode();
                l.add(cCode);

            }
        }

        Date d = java.sql.Date.valueOf(LocalDate.now());
        String date = String.valueOf(d);
        controller.checkDateAndcCode(date, l.get(0));
        for (CheckDateAndcCode check : controller.getCheckDateAndcCode()) {
            if (check.getDate().equals(date) && check.getcCode().equals(l.get(0))) {
                found = true;
            }
        }
        if (found) {
            ToolBarPanel.setNewAttendaceRecord(false);
            setFound(found);
           
        } else {
            ToolBarPanel.setNewAttendaceRecord(true);
            setFound(found);
        }
        ToolBarPanel.setDate(date);
        controller.getAttRecords().clear();
        controller.connect();
        if(isNewAttendaceRecord()){
            controller.loadAttendanceStatus(l.get(0));
            controller.newloadAttendanceStatus(l.get(0), date);
        }else{
            controller.newloadAttendanceStatus(l.get(0), date);
        }
        setActiveCourse(l.get(0));
        AttendanceSummaryPannel.setAttendanceSummary(controller.getAttRecords());
        DisplayRecordTableModel.setAttendRecord(controller.getAttRecords());
        ClassSessionViewPannel.refresh();

    }

    public final ArrayList looadInstructorInfo(String username) throws Exception {

        ArrayList list;

        controller.connect();
        controller.loadInstructorInfo();
        list = controller.getInstructors();

        return list;
    }

    public void setActivateAttendance(boolean act) {
        ToolBarPanel.activate = act;
    }

    public static boolean isActivateAttendance() {
        return activate;
    }

    public static String getActiveCourse() {
        return courseCode;
    }

    public void setActiveCourse(String cCode) {
        ToolBarPanel.courseCode = cCode;

    }

    public static void setNewAttendaceRecord(boolean newRecord) {
        ToolBarPanel.newRecord = newRecord;
    }

    public static boolean isNewAttendaceRecord() {
        return ToolBarPanel.newRecord;
    }

    public static void setDate(String date) {
        ToolBarPanel.setDate = date;

    }

    public static String getDate() {

        return ToolBarPanel.setDate;
    }

    public JComboBox getListOfCourseCode() {
        try {
            String username = AttendanceLogin.getCurrentuser();
            ArrayList list = looadInstructorInfo(username);
            Instructor inst;
            int id = 0;
            for (int i = 0; i < list.size(); i++) {
                inst = (Instructor) list.get(i);
                if (username.equals(inst.getUsername())) {

                    id = inst.getiID();

                }
            }
            if (id != 0) {
                String cCode = null;
                controller.loadAssignedProfCourse(id);
                list.clear();
                list = controller.getAssignedCourseses();
                for (int i = 0; i < list.size(); i++) {
                    AssignedCourses asgn = (AssignedCourses) list.get(i);
                    if (id == asgn.getIntructorId()) {
                        cCode = asgn.getCourseCode();
                        coursecode.addItem(cCode);

                    }
                }

            }

        } catch (Exception ex) {
            Logger.getLogger(ToolBarPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return coursecode;
    }

    public static JDialog selectDateAndCodeOptions() {

        d = new JDialog();
        d.setModal(true);
        d.setTitle("Connect to serial port");
        d.setSize(400, 200);
        SelectDatePanelOption p = new SelectDatePanelOption();
        d.setLayout(new BorderLayout());
        d.add(p, BorderLayout.CENTER);
        d.setVisible(true);
        d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        return d;
    }

    public void setFound(boolean found) {
        ToolBarPanel.found = found;
    }

    public static boolean isFound() {
        return ToolBarPanel.found;
    }
}
