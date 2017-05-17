package otuman.godfrey.biometricattendence.system.gui.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import otuman.godfrey.biometricattendence.system.model.Person;
import otuman.godfrey.biometricattendence.system.model.controller.Controller;

public class AddNewPersonPannel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final Dimension dim;
    private final int height, width;
    private final GridBagLayout gb;
    public static JTextField IDField;
    public static JTextField firstNameField;
    public static JTextField middleNameField;
    public static JTextField lastNameField;
    private final JLabel middleNameLabel;
    private JLabel firstNameLabel;
    private final JLabel genderLabel;
    public static JRadioButton maleRadioButton;
    public static JRadioButton femaleRadioButton;
    public static ButtonGroup genderGroup;
    private final JLabel IDLabel;
    private final JLabel lastNameLabel;
    private final JLabel fingerIDLabel;
    public static JTextField fingerIDField;
    private final String inLineTitle;
    private final JCheckBox activateRegistration;
    private final JButton submitButton;
    private static AddPersonEventListener addPersonListener;
    private final Font font;
    private final GridBagConstraints gbc;

    public AddNewPersonPannel() {

        font = new Font("New Times Roman", Font.PLAIN, 16);
        gbc = new GridBagConstraints();
        activateRegistration = new JCheckBox();
        height = 400;
        width = 400;
        dim = new Dimension(width, height);
        gb = new GridBagLayout();
        IDField = new JTextField();
        firstNameField = new JTextField();
        genderGroup = new ButtonGroup();
        femaleRadioButton = new JRadioButton("Female");
        maleRadioButton = new JRadioButton("Male");
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        middleNameField = new JTextField();
        lastNameField = new JTextField();
        IDLabel = new JLabel();
        firstNameLabel = new JLabel();
        genderLabel = new JLabel("Gender");
        firstNameLabel = new JLabel();
        middleNameLabel = new JLabel();
        lastNameLabel = new JLabel();
        fingerIDLabel = new JLabel();
        fingerIDField = new JTextField();
       // fingerIDField.setEditable(false);
        fingerIDField.setEnabled(false);
        submitButton = new JButton("Add Person");
        inLineTitle = "Add person";

        setMinimumSize(dim);
        setPreferredSize(dim);
        Border innerBorder = BorderFactory.createTitledBorder(null, inLineTitle,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font(
                        "New Times Roman", Font.ROMAN_BASELINE, 15), Color.DARK_GRAY);
        //Border innerBorder  = BorderFactory.createTitledBorder(inLineTitle);
        Border outBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(innerBorder, outBorder));
        setLayout(gb);
        //The method that contains a form for registration
        EnrollPersonForm();
        ListenToButtons();
	        //setBackground(new Color(111,145,138));
        //setBackground(new Color(0, 204, 204));
        setBackground(new Color(233, 229, 229));
        setVisible(true);

    }

    private void EnrollPersonForm() {
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy  = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 100, 0, 5); //Insets == "It does specify the spacing between the items Top, Bottom, Left, Right", 
        activateRegistration.setFont(font);
        activateRegistration.setText("Activate Registration");
        this.add(activateRegistration, gbc);

        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 0, 0, 5); //Insets == "It does specify the spacing between the items Top, Bottom, Left, Right", 
        firstNameLabel.setFont(font);
        firstNameLabel.setText("First name : ");
        this.add(firstNameLabel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        firstNameField.setPreferredSize(new Dimension(180, 30));
        firstNameField.setFont(font);
        firstNameField.setEnabled(false);
        this.add(firstNameField, gbc);

		   ////////////Next row //////////////////
        gbc.weightx = 1;
        gbc.weighty = 0.5;

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 0, 0, 5); //Insets == "It does specify the spacing between the items Top, Bottom, Left, Right", 
        middleNameLabel.setFont(font);
        middleNameLabel.setText("Middle name : ");
        this.add(middleNameLabel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        middleNameField.setFont(font);
        middleNameField.setPreferredSize(new Dimension(180, 30));
        middleNameField.setEnabled(false);
        add(middleNameField, gbc);
        ////////////Next row ////////////////// 
        gbc.weightx = 1;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 0, 0, 5); //Insets == "It does specify the spacing between the items Top, Bottom, Left, Right", 
        lastNameLabel.setFont(font);
        lastNameLabel.setText("Last name : ");
        this.add(lastNameLabel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        lastNameField.setFont(font);
        lastNameField.setPreferredSize(new Dimension(180, 30));
        lastNameField.setEnabled(false);
        this.add(lastNameField, gbc);

	       ////////////Next row //////////////////     
        gbc.weightx = 1;
        gbc.weighty = 0.5;

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 0, 0, 5); //Insets == "It does specify the spacing between the items Top, Bottom, Left, Right", 
        IDLabel.setFont(font);
        IDLabel.setText("Student ID : ");

        this.add(IDLabel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        IDField.setPreferredSize(new Dimension(120, 30));
        IDField.setFont(font);
        IDField.setEnabled(false);
        this.add(IDField, gbc);

        //Next row
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.1;
        gbc.weighty = 0.5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        genderLabel.setFont(font);
        this.add(genderLabel, gbc);

        gbc.weightx = 0.1;
        gbc.weighty = 0.7;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 90, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        maleRadioButton.setFont(font);
        maleRadioButton.setEnabled(false);
        this.add(maleRadioButton, gbc);

        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 160, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        femaleRadioButton.setFont(font);
        femaleRadioButton.setEnabled(false);
        this.add(femaleRadioButton, gbc);

        /**
         * ************** next row **************************
         */
        gbc.weightx = 1;
        gbc.weighty = 0.8;
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 0, 0, 5); //Insets == "It does specify the spacing between the items Top, Bottom, Left, Right", 
        fingerIDLabel.setFont(font);
        fingerIDLabel.setText("Finger ID : ");
        this.add(fingerIDLabel, gbc);

        gbc.insets = new Insets(0, 90, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        fingerIDField.setPreferredSize(new Dimension(100, 30));
        fingerIDField.setFont(font);
        this.add(fingerIDField, gbc);

             ////////////Next row //////////////////   
        //Next row
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.2;
        gbc.weighty = 0.7;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 200, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        submitButton.setFont(font);
        submitButton.setEnabled(false);
        this.add(submitButton, gbc);

    }

    private void ListenToButtons() {
          activateRegistration.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
               if(activateRegistration.isSelected()){
                  firstNameField.setEnabled(true);
                  middleNameField.setEnabled(true);
                  lastNameField.setEnabled(true);
                  femaleRadioButton.setEnabled(true);
                  maleRadioButton.setEnabled(true);
                  IDField.setEnabled(true);
                  fingerIDField.setEnabled(true);
                  submitButton.setEnabled(true);
               }
               else{
                  firstNameField.setEnabled(false);
                  middleNameField.setEnabled(false);
                  lastNameField.setEnabled(false);
                  femaleRadioButton.setEnabled(false);
                  maleRadioButton.setEnabled(false);
                  IDField.setEnabled(false); 
                  fingerIDField.setEnabled(false);
                  submitButton.setEnabled(false);
                   
               }
                  
              
              }
          });
       
          submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = IDField.getText();
                String firstName = firstNameField.getText();
                String middleName = middleNameField.getText();
                String lastName = lastNameField.getText();
                String gender = null;
                String fingerIDs = fingerIDField.getText();

                if (maleRadioButton.isSelected()) {

                    gender = "male";
                } else if (femaleRadioButton.isSelected()) {

                    gender = "female";
                }
                

                if ((ID.equals("")) || (firstName.equals("")) || (middleName.equals("")) || (lastName.equals("")) || (fingerIDs.equals(""))) {
                    JOptionPane.showMessageDialog(AddNewPersonPannel.this, "You must fill all the field to continue");
                } else {
                    //ToolBarPanel toolBar   = new ToolBarPanel();
                    boolean com = SerialCommunicationPanel.selectedCom();
                   if (com == true) {
                        int sID = Integer.parseInt(ID);
                        int fg = Integer.parseInt(fingerIDs);
                       Person p = new Person(sID, firstName, middleName, lastName, gender,fg);

                        if (addPersonListener != null) {
                            addPersonListener.personEventListener(p);
                        }
                    } else {
                        JOptionPane.showMessageDialog(AddNewPersonPannel.this, "You must establish connection first to continue");
                    }
                }

            }
        });
    }

    public static void setAddPersonListener(AddPersonEventListener apel) {
        AddNewPersonPannel.addPersonListener = apel;
    }

    public static int setArrivedData(int data) {

        fingerIDField.setText(String.valueOf(data));
        return data;

    }

  public static void DataFromTable(int id) {
       Controller cont = new Controller();
        try {
            cont.connect();
            cont.load();
            List list = cont.getPeople();
            for (Iterator it = list.iterator(); it.hasNext();) {
                Person person = (Person) it.next();
                if (person.getID() == id) {
                    
                    firstNameField.setText(person.getFirstName());
                    middleNameField.setText(person.getMiddleName());
                    lastNameField.setText(person.getLastName());
                    if(person.getGender().equals("male")){
                    maleRadioButton.setSelected(true);
                    }else{
                    femaleRadioButton.setSelected(true);
                    }
                    IDField.setText(String.valueOf(id));
                    fingerIDField.setText(String.valueOf(person.getFinger_Id()));
                
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(DisplayInfoPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

       
    }

}
