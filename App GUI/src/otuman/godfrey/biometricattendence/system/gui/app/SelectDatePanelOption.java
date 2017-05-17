/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otuman.godfrey.biometricattendence.system.gui.app;

import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import otuman.godfrey.biometricattendence.system.gui.serialComConnection.SerialCommunication;

/**
 *
 * @author Otoman
 */
public class SelectDatePanelOption extends JPanel {

    private final JLabel serialConToFP;
    private static final long serialVersionUID = 1L;
    private static final int height = 28;
    private static JButton submitSelectedDate;
    private static JYearChooser year;
    private static JMonthChooser month;
    private static JDayChooser dayv;
    private static  JComboBox day;
    private final JLabel daylabel;
    private final Font font;
    private static  String d;
    private static String selectedDate = "";
    private final SerialCommunication sConnCom;
    private GridBagConstraints gbc;

    public SelectDatePanelOption() {

        sConnCom = new SerialCommunication();
        font = new Font("New Times Roman", Font.PLAIN, 16);
        serialConToFP = new JLabel("COM Port connection : ");
        day = new JComboBox();
        day.setFont(font);
        getAvailableDate();
        daylabel = new JLabel("Please select date to view report");
        daylabel.setFont(font);
        selectedDate = new String();
        dayv = new JDayChooser();
        year = new JYearChooser();
        year.setFont(font);
        year.setPreferredSize(new Dimension(95, 30));
        month = new JMonthChooser();
        month.setFont(font);
        month.setPreferredSize(new Dimension(100, 30));
        submitSelectedDate = new JButton("Select");
        submitSelectedDate.setVisible(true);
        submitSelectedDate.setFont(font);

        setMinimumSize(new Dimension(200, 75));
        setPreferredSize(new Dimension(200, 75));
        setMaximumSize(new Dimension(200, 75));
        setLayout(new GridBagLayout());
        // setBackground(new Color(0, 204, 204));
        setBackground(new Color(233, 229, 229));
        submitSelectedDate();
        System.out.println(getSelectedDate());
        gridBagItems();

        Border innerBorder = BorderFactory.createTitledBorder(null, "Date to View Report",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font(
                        "New Times Roman", Font.ROMAN_BASELINE, 15), Color.DARK_GRAY);
        //Border innerBorder  = BorderFactory.createTitledBorder(inLineTitle);
        Border outBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(innerBorder, outBorder));
        setVisible(true);

    }

    private void gridBagItems() {

        gbc = new GridBagConstraints();
        gbc.gridwidth = 4;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 0, 0, 0); //Insets == "It does specify the spacing between the items Top, Bottom, Left, Right", 
        add(daylabel, gbc);

        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 0, 0, 0); //Insets == "It does specify the spacing between the items Top, Bottom, Left, Right", 
        add(day, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 0, 0, 0); //Insets == "It does specify the spacing between the items Top, Bottom, Left, Right", 
        add(month, gbc);

        gbc.gridx++;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(year, gbc);

        gbc.gridx++;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(submitSelectedDate, gbc);
    }

    private ArrayList<String> getAvailableComPort() {
        return sConnCom.availbleComPort();
    }

    private JComboBox getAvailableDate() {
        int i;
        for (i = 1; i < 32; i++) {
          day.addItem(String.format("%02d", i));
        }
        return day;
    }

    public static void submitSelectedDate() {
        submitSelectedDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date;
                String d = (String)day.getSelectedItem();
                int m = month.getMonth() + 1;
                int y = year.getYear();
                if (m < 10) {
                    date = String.valueOf(y) + "-0" + String.valueOf(m) + "-" + String.valueOf(d);
                    setDateSelected(date);
                } else {
                    date = String.valueOf(y) + "-" + String.valueOf(m) + "-" + String.valueOf(d);
                    setDateSelected(date);
                }
                    
          ToolBarPanel.d.dispose();
            }
        });
    }

    public static void setDateSelected(String date) {
        SelectDatePanelOption.selectedDate = date;
    }

    public static String getSelectedDate() {

        return selectedDate;
    }
}
