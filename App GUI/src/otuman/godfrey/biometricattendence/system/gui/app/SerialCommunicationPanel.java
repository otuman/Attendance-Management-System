package otuman.godfrey.biometricattendence.system.gui.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import otuman.godfrey.biometricattendence.system.gui.serialComConnection.SerialCommunication;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Otoman
 */
public class SerialCommunicationPanel extends JPanel {

    private final JLabel serialConToFP;
    private static final long serialVersionUID = 1L;
    private static final int height = 28;
    private final JButton connectButton;
    private final JButton disconnectButton;
    private final JComboBox listOfAvailableCom;
    private final Font font;
    private final String[] listComPort = {"COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8"};
    private final SerialCommunication sConnCom;
    AddNewPersonPannel addP;
    private static boolean connect;
    private final int mc;
    private GridBagConstraints gbc;

    public SerialCommunicationPanel() {
        mc = JOptionPane.WARNING_MESSAGE;
        connect = false;
        sConnCom = new SerialCommunication();
        font = new Font("New Times Roman", Font.PLAIN, 14);
        serialConToFP = new JLabel("COM Port : ");

        connectButton = new JButton("Connect");
        disconnectButton = new JButton("Disconnect");
        disconnectButton.setVisible(false);
        connectButton.setPreferredSize(new Dimension(90, 30));

        if (getAvailableComPort().isEmpty()) {
            listOfAvailableCom = new JComboBox<>(listComPort);
        } else {
            listOfAvailableCom = new JComboBox<>(getAvailableComPort().toArray());
        }
        listOfAvailableCom.setPreferredSize(new Dimension(76, 30));

        setMinimumSize(new Dimension(300, 75));
        setPreferredSize(new Dimension(300, 75));
        //setMaximumSize(new Dimension(300, 75));
        setLayout(new GridBagLayout());
        // setBackground(new Color(0, 204, 204));
        setBackground(new Color(233, 229, 229));
        GridBagItems();
        ButtonsAction();

        Border innerBorder = BorderFactory.createTitledBorder(null, "Connection",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font(
                        "New Times Roman", Font.ROMAN_BASELINE, 15), Color.DARK_GRAY);
        //Border innerBorder  = BorderFactory.createTitledBorder(inLineTitle);
        Border outBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(innerBorder, outBorder));
        setVisible(true);

    }

    private void ButtonsAction() {
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connect == false) {

                    String selected = (String) listOfAvailableCom.getSelectedItem();

                    try {
                        boolean con = sConnCom.InitSerialConnection(selected);
                        if (con) {
                            disconnectButton.setVisible(true);
                            connectButton.setVisible(false);
                            listOfAvailableCom.setEnabled(false);
                            connect = true;
                            MessagingPannel.StatusMsg("Connection is established through port " + selected);

                        } else {
                            JOptionPane.showMessageDialog(new DisplayInfoPanel(), "Port " + selected + " was not found, please check the free port");
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(ToolBarPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

        });
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //sConnCom = new SerialCommunication();
                if (connect == true) {
                    disconnectButton.setVisible(false);
                    connectButton.setVisible(true);
                    listOfAvailableCom.setEnabled(true);
                    connect = false;
                    MessagingPannel.StatusMsg("You have disconnected");

                    try {
                       SerialCommunication.disconnect();
                    } catch (IOException ex) {
                        Logger.getLogger(ToolBarPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });

    }

    public static boolean selectedCom() {
        return connect;
    }

    private void GridBagItems() {

        gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 0, 0, 0); //Insets == "It does specify the spacing between the items Top, Bottom, Left, Right", 
        serialConToFP.setFont(font);
        add(serialConToFP, gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 0, 0, 0); //Insets == "It does specify the spacing between the items Top, Bottom, Left, Right", 
        listOfAvailableCom.setFont(font);
        add(listOfAvailableCom, gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        connectButton.setFont(font);
        add(connectButton, gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        disconnectButton.setFont(font);
        add(disconnectButton, gbc);
    }

    private ArrayList<String> getAvailableComPort() {
        return sConnCom.availbleComPort();
    }
}
