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
public class LeftPaneContainer extends JPanel {
     
    private final AddNewPersonPannel anpp;
    private final MessagingPannel msgp;
         
    public LeftPaneContainer(){
        msgp     = new MessagingPannel();
        anpp     = new AddNewPersonPannel();
        setMinimumSize(new Dimension(400, 600));
        setPreferredSize(new Dimension(400, 600));
        setLayout(new FlowLayout());
        //setBackground(new Color(0, 204, 204));
        setBackground(new Color(233, 229, 229));
        add(msgp);
        add(anpp);
       
        setVisible(true);
    }
}