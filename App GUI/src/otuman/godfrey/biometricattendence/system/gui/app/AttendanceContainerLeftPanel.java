/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otuman.godfrey.biometricattendence.system.gui.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;

/**
 *
 * @author Otoman
 */
public class AttendanceContainerLeftPanel extends JPanel{

    private final SerialCommunicationPanel anpp;
    private final MessagingPannel msgp;
    private final AttendanceSummaryPannel attsummary;
         
    public AttendanceContainerLeftPanel(){
        msgp     = new MessagingPannel();
        attsummary        = new AttendanceSummaryPannel();
        anpp     = new SerialCommunicationPanel();
        setMinimumSize(new Dimension(100, 600));
        setPreferredSize(new Dimension(300, 600));
        setLayout(new FlowLayout());
        //setBackground(new Color(0, 204, 204));
        setBackground(new Color(233, 229, 229));
        add(anpp);
        add(msgp);
        add(attsummary);
       
        setVisible(true);
    }
}