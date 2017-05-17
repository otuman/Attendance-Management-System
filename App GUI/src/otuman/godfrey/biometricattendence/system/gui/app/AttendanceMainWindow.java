package otuman.godfrey.biometricattendence.system.gui.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import otuman.godfrey.biometricattendence.system.gui.serialComConnection.SerialCommunication;
import otuman.godfrey.biometricattendence.system.gui.serialComConnection.SerialEvent;
import otuman.godfrey.biometricattendence.system.model.Person;
import otuman.godfrey.biometricattendence.system.model.controller.Controller;

public class AttendanceMainWindow extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final Dimension dim;
    private JTabbedPane tabbed;
    private final int height, width;
    private final ToolBarPanel toolBarPanel;
    private JFileChooser fileChooser;
    //private final AddNewPersonPannel addNewPersonPanel;
    private DisplayInfoPanel displayInfoPanel;
    private AttendanceContainerLeftPanel attContain;
    private final JSplitPane splitPanel;
    private final JSplitPane splitAttendsPanel;
    private final Controller controller;
    private final LeftPaneContainer leftPaneContainer;
    private final ClassSessionViewPannel cSessionVPanel;
    private final SerialEvent se;
    private final SerialCommunication serialCom;

    public AttendanceMainWindow() {
        //This method is for look and feel
        GUILookAndFeel();
        
        leftPaneContainer = new LeftPaneContainer();
        serialCom  = new SerialCommunication();
        attContain = new AttendanceContainerLeftPanel();
        //addNewPersonPanel    AttendanceContainerLeftPanel          = new AddNewPersonPannel();
        displayInfoPanel = new DisplayInfoPanel();
        cSessionVPanel   = new ClassSessionViewPannel();
        splitAttendsPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, attContain, cSessionVPanel);
        splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPaneContainer, displayInfoPanel);
        tabbed     = new JTabbedPane();
        tabbed.add(splitAttendsPanel, "Attendence Section");
        tabbed.add(splitPanel,"Add Person");
        toolBarPanel = new ToolBarPanel();
        height = 700;
        width = 900;
        dim = new Dimension(width, height);
        controller = new Controller();
        se = new SerialEvent();
        
        /****************This is for connection tab  ****************************/
        displayInfoPanel.setData(controller.getPeople());
        AddNewPersonPannel.setAddPersonListener(new AddPersonEventListener() {
            @Override
            public void personEventListener(Person person) {
                controller.addPerson(person);
                try {
                    controller.connect();
                    controller.saveStudentInfo(person);
                } catch (Exception ex) {
                    Logger.getLogger(AttendanceMainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                retrieveData();
                displayInfoPanel.refresh();
            }
        });
         /****************This is for connection tab  ****************************/
        /****************This is for Attendance records tab  ****************************/
        
        cSessionVPanel.setAttendRecord(controller.getAttRecords());
        try {
            toolBarPanel.initiallyRecord();
            //loadAttendStatusRecord();
        } catch (Exception ex) {
            Logger.getLogger(AttendanceMainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
       /****************This is for Attendance records tab  ****************************/
        /*This section is used for other related objects*/
        retrieveData();
        setJMenuBar(CreateManuBar());
        setLayout(new BorderLayout());
        /*This section is used for JPannel related objects*/

        splitPanel.setOneTouchExpandable(true);
        add(toolBarPanel, BorderLayout.PAGE_START);
        add(tabbed, BorderLayout.CENTER);

        /*This section is used for JFrame related objects*/
        setTitle("Biometric Attendence Management System");
        setMinimumSize(dim);
        setPreferredSize(dim);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void GUILookAndFeel() {
        //Start
        UIManager.put("nimbusBase", new Color(115, 164, 20));
        UIManager.put("nimbusBlueGrey", new Color(111, 145, 138));
        UIManager.put("control", new Color(204, 211, 224));
       

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            //If Nimbus is not available, you can set the GUI to another look and feel.
        }

// End of look and feel
    }
    
    private void retrieveData() {
        try {
            controller.connect();
            controller.load();
            displayInfoPanel.refresh();
        } catch (Exception e) {
        }
    }
    private void loadAttendStatusRecord() throws Exception{
             controller.connect();
           // controller.loadAttendanceStatus();
            ClassSessionViewPannel.refresh();
    }

    private JMenuBar CreateManuBar() {
        // Menu bar object
        JMenuBar menuBar = new JMenuBar();

        //File Menu and Menu Item objects
        JMenu fileMenu = new JMenu("File");
        JMenuItem importItem = new JMenuItem("Import Data...");
        JMenuItem exportItem = new JMenuItem("Export Data...");
        JMenuItem exitItem = new JMenuItem("Exit this App");

        importItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (fileChooser.showOpenDialog(AttendanceMainWindow.this) == JFileChooser.APPROVE_OPTION) {
                    System.out.print(fileChooser.getSelectedFile());
                }
            }
        });

        exportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (fileChooser.showSaveDialog(AttendanceMainWindow.this) == JFileChooser.APPROVE_OPTION) {
                    System.out.print(fileChooser.getSelectedFile());
                }
            }
        });

        fileMenu.add(importItem);
        fileMenu.add(exportItem);
        fileMenu.add(exitItem);
        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int confirm = JOptionPane.showConfirmDialog(AttendanceMainWindow.this,
                        "Do you really want to exit the application?",
                        "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
                if (confirm == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        fileMenu.setMnemonic(KeyEvent.VK_F);

        //Window Menu and Menu Item objects 
        JMenu conMenu = new JMenu("Connection");
        JMenuItem connect = new JMenuItem("Connect");
        connect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               connectToCOM(); 
            }
        });
        conMenu.add(connect);

      
        //Help Menu and Menu Item objects
        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpItem = new JMenuItem("Help Content");

        helpMenu.add(helpItem);

        //Add menu to menubar
        menuBar.add(fileMenu);
        menuBar.add(conMenu);
        menuBar.add(helpMenu);
        return menuBar;

    }
    public JDialog connectToCOM(){
    JDialog d = new JDialog(this, true);
    d.setTitle("Connect to serial port");
    d.setSize(400, 500);
    SerialCommunicationPanel p = new SerialCommunicationPanel();
    d.setLayout(new BorderLayout());
    d.add(p, BorderLayout.CENTER);
    d.setLocationRelativeTo(this);
    d.setVisible(true);
    d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    return d; 
    }
}
