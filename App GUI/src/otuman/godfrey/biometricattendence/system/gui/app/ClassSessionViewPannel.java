/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otuman.godfrey.biometricattendence.system.gui.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import otuman.godfrey.biometricattendence.system.model.AttendanceRecords;

/**
 *
 * @author Otoman
 */
public class ClassSessionViewPannel extends JPanel {

    private final Dimension dim;
    private static DisplayRecordTableModel displayRecord;
    public static JTable jTable;
    private final int height;
    private final int width;

    public ClassSessionViewPannel() {

        height = 600;
        width = 500;
        dim = new Dimension(width, height);

        displayRecord = new DisplayRecordTableModel("No", "Full Name", "Student ID","Date","Time", "Attendance Status");
        jTable = new JTable(displayRecord);
        jTable.setRowHeight(30);
        jTable.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        setLayout(new BorderLayout());
        add(new JScrollPane(jTable), BorderLayout.CENTER);
        setPreferredSize(dim);
        setBackground(new Color(248, 248, 255));
        //setBackground(new Color(0, 204, 204));
        setVisible(true);
    }

    public void setAttendRecord(List<AttendanceRecords> attendRecord) {
        DisplayRecordTableModel.setAttendRecord(attendRecord);
    }

    public static void refresh() {
        displayRecord.fireTableDataChanged();
    }

    public static void setAttendanceImageStatus() {

    }

    public static JTable getAttendanceImageStatus() {

        return ClassSessionViewPannel.jTable;

    }

   
}
