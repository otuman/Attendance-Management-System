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
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Otoman
 */
public class MessagingPannel extends JPanel{
    
        public static JLabel statusMsg;
    	private static final long serialVersionUID = 1L;
	private static final int height= 28;
        private final Font font;
     
    public MessagingPannel(){
               
               font         = new Font("New Times Roman", Font.BOLD, 12);
               statusMsg    = new JLabel(" ",JLabel.CENTER);
               statusMsg.setText("You are currently not connected to scanner");
               statusMsg.setForeground(Color.BLUE);
               statusMsg.setFont(font);
               add(statusMsg);
                
                setMinimumSize(new Dimension(300, 60));
                setPreferredSize(new Dimension(300, 70));
                setMaximumSize(new Dimension(400, 60));
                setLayout(new FlowLayout());
                setBackground(new Color(233, 229, 229));
                Border innerBorder    = BorderFactory.createTitledBorder(null, "Status Panel",
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
    
}
