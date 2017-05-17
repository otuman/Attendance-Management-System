package otuman.godfrey.biometricattendence.system.gui.app;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import static otuman.godfrey.biometricattendence.system.gui.app.ClassSessionViewPannel.jTable;
import otuman.godfrey.biometricattendence.system.model.AttendanceRecords;

public class DisplayRecordTableModel extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ArrayList columnNames;
    private static boolean attendstatus;
    private static List<AttendanceRecords> attendRecord;

    public DisplayRecordTableModel() {

    }

    public DisplayRecordTableModel(String no, String fullName, String id,String date,String time, String attStatus) {
        columnNames = new ArrayList<>();
        columnNames.add(no);
        columnNames.add(fullName);
        columnNames.add(id);
        columnNames.add(date);
        columnNames.add(time);
        columnNames.add(attStatus);

    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames.get(columnIndex).toString();
    }

    @Override
    public int getRowCount() {
        return attendRecord.size();
    }

    @Override
    public int getColumnCount() {
        // TODO Auto-generated method stub
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        AttendanceRecords attends = attendRecord.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return rowIndex +1;
            case 1:
                return (attends.getfName() + " " + attends.getmName() + " " + attends.getlName());
            case 2:
                return attends.getStudentId();
           case 3:
                return attends.getDate();
            case 4:
                return attends.getTime();
            case 5:
                setAttenanceStatus(attends.isAttendStatus());
                jTable.getColumnModel().getColumn(columnIndex).setCellRenderer(new Renderer());

                break;
                
        }
        return null;
    }

    //This method is implemented in both AttendenceMainWindow.java and in SerialCommunication.java
    public static void setAttendRecord(List<AttendanceRecords> attendRecord) {
        DisplayRecordTableModel.attendRecord = attendRecord;
    }

    class Renderer extends DefaultTableCellRenderer {

        ImageIcon img = new ImagesUtilities().createIcon("/images/tick.png");
        ImageIcon img1 = new ImagesUtilities().createIcon("/images/cross.png");
        JLabel imgLabe = new JLabel();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            if (isAttenanceStatus()) {
                imgLabe.setIcon(img);
                imgLabe.setSize(new Dimension(20, 20));
                return imgLabe;
            } else {
                
                imgLabe.setIcon(img1);
                imgLabe.setSize(new Dimension(20, 20));
                return imgLabe;
            }
        }

    }

    public static void setAttenanceStatus(boolean status) {
        DisplayRecordTableModel.attendstatus = status;
    }

    public static boolean isAttenanceStatus() {
        return DisplayRecordTableModel.attendstatus;
    }
    
}
