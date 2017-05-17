/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otuman.godfrey.biometricattendence.system.gui.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import otuman.godfrey.biometricattendence.system.model.AttendanceRecords;

/**
 *
 * @author Otoman
 */
public class AttendanceSummaryPannel extends JPanel{
    
      public static JLabel statusMsg;
      public static JLabel statusMsg1;
      public static JLabel statusMsg2;
      private static final long serialVersionUID = 1L;
      private static final int height= 28;
      private final Font font;
     
    public AttendanceSummaryPannel(){
               
               font         = new Font("New Times Roman", Font.BOLD, 12);
               statusMsg1    = new JLabel(" ",JLabel.RIGHT);
               statusMsg1.setText("");
               statusMsg1.setForeground(Color.BLUE);
               statusMsg1.setFont(font);
               statusMsg1.setVisible(false);
               statusMsg2    = new JLabel(" ",JLabel.RIGHT);
               statusMsg2.setVisible(false);
               statusMsg2.setText("");
               statusMsg2.setForeground(Color.BLUE);
               statusMsg2.setFont(font);
               statusMsg    = new JLabel(" ",JLabel.RIGHT);
               statusMsg.setText("No action has been performed yet");
               statusMsg.setForeground(Color.BLUE);
               statusMsg.setFont(font);
               add(statusMsg);
               add(statusMsg1);
               add(statusMsg2);
                
                setMinimumSize(new Dimension(300, 50));
                setPreferredSize(new Dimension(300, 150));
                setLayout(new FlowLayout());
                setBackground(new Color(233, 229, 229));
                Border innerBorder    = BorderFactory.createTitledBorder(null, "Attendance Summary",
                        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font(
                                                "New Times Roman", Font.ROMAN_BASELINE, 15), Color.DARK_GRAY);
                        //Border innerBorder  = BorderFactory.createTitledBorder(inLineTitle);
                        Border outBorder    = BorderFactory.createEmptyBorder(5,5,5,5);
                        setBorder(BorderFactory.createCompoundBorder(innerBorder,outBorder));
                setVisible(true);
        
             
    }
    
  public static void StatusMsg(String msg){
          statusMsg.setText(msg);
  }
  public static void StatusMsg1(String msg){
          statusMsg1.setText(msg);
  }
  public static void StatusMsg2(String msg){
          statusMsg2.setText(msg);
  }
  
  
  public static void setAttendanceSummary(List<AttendanceRecords> attRecords) {
      int i, p=0;
      boolean present = true;
        for(i=0; i<attRecords.size(); i++){
        
        AttendanceRecords at = attRecords.get(i);
              
            if(present==at.isAttendStatus()){
                p = p +1;
             }
       
        }
      StatusMsg("Number of checked students : "+ p);
      StatusMsg1("Number of unchecked students : "+ (attRecords.size()-p));
      statusMsg1.setVisible(true);
      StatusMsg2("Total number of stundents : "+ attRecords.size());
      statusMsg2.setVisible(true);
    
  }
}