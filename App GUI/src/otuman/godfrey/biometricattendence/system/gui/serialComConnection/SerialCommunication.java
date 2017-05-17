package otuman.godfrey.biometricattendence.system.gui.serialComConnection;

import java.io.*;
import javax.comm.*;
import java.util.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import otuman.godfrey.biometricattendence.system.gui.app.AddNewPersonPannel;
import otuman.godfrey.biometricattendence.system.gui.app.AttendanceSummaryPannel;
import otuman.godfrey.biometricattendence.system.gui.app.ClassSessionViewPannel;
import otuman.godfrey.biometricattendence.system.gui.app.DisplayRecordTableModel;
import otuman.godfrey.biometricattendence.system.gui.app.SelectDatePanelOption;
import otuman.godfrey.biometricattendence.system.gui.app.ToolBarPanel;
import otuman.godfrey.biometricattendence.system.model.controller.Controller;

public class SerialCommunication implements Runnable, SerialPortEventListener {

    public static CommPortIdentifier portId;
    public static Enumeration portList;
    private static InputStream inputStream;
    private static OutputStream outputStream;
    private static SerialPort serialPort;
    private static boolean frmfp;
    private static boolean portFound = false;
    ; 
    private Thread readThread;

    public SerialCommunication() {
        super();

    }

    public boolean InitSerialConnection(String portName) {

        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals(portName)) {
                    System.out.println("Found port: " + portName);
                    portFound = true;
                    ReadyToConnect();

                } else if (portId.isCurrentlyOwned()) {
                    System.out.println(portName + " is currently owned");
                    portFound = false;
                }
            }
        }
        setFromFP(portFound);
        if (!portFound) {
            System.out.println("port " + portName + " not found.");
        }
        return portFound;
    }

    public void ReadyToConnect() {
        try {
            serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
        } catch (PortInUseException e) {
        }
        try {
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {
        }
        try {
            outputStream = serialPort.getOutputStream();
        } catch (IOException e) {
        }
        try {
            serialPort.addEventListener(this);
        } catch (TooManyListenersException e) {
        }
        serialPort.notifyOnDataAvailable(true);
        try {
            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException e) {
        }

        readThread = new Thread(this);
        readThread.start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {

            case SerialPortEvent.BI:

            case SerialPortEvent.OE:

            case SerialPortEvent.FE:

            case SerialPortEvent.PE:

            case SerialPortEvent.CD:

            case SerialPortEvent.CTS:

            case SerialPortEvent.DSR:

            case SerialPortEvent.RI:

            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;

            case SerialPortEvent.DATA_AVAILABLE:
                SerialReader();
                break;

        }
    }

    public void SerialReader() {
        //byte[] readBuffer = new byte[1024];
        int received = 0;
        ArrayList<Integer> receivedData = new ArrayList<>();
        try {
            while (inputStream.available() > 0) {
                received = inputStream.read();
                receivedData.add(received);
                waiting(1);
            }
            checkReceivedData(received);
            if (receivedData.size() > 100) {
                WriteFile(receivedData);
            }
            System.out.print("This is it : ");
            System.out.print(received);
            System.out.println();

        } catch (IOException e) {
        } catch (Exception ex) {
            Logger.getLogger(SerialCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SerialWritter(int data) throws IOException {
        outputStream.write(data);
        System.out.println(data);
    }
   public static void disconnect() throws IOException {
        try {
            if (serialPort != null) {
                serialPort.getInputStream().close();
                serialPort.getOutputStream().close();
                serialPort.removeEventListener();
                serialPort.close();
                portFound = false;
                setFromFP(portFound);

            }

        } catch (Exception e) {

        }
    }

    public static void waiting(int n) {
        long t0, t1;

        t0 = System.currentTimeMillis();
        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < (n * 10));
    }

    public ArrayList<String> availbleComPort() {
        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        int i = 0;
        ArrayList<String> ra = new ArrayList<>();
        while (portEnum.hasMoreElements() && i < 100) {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                String r = portIdentifier.getName();
                ra.add(r);
            }

            i++;
        }
        return ra;
    }

    public static void setFromFP(boolean frmfp) {
        SerialCommunication.frmfp = frmfp;
    }

    public static boolean isFromFP() {
        return frmfp;
    }

    private void checkReceivedData(int rData) throws SQLException, Exception {
        String date;
        if (ToolBarPanel.isActivateAttendance()) {
            String cCode = ToolBarPanel.getActiveCourse();
           
           if(ToolBarPanel.isNewAttendaceRecord() && !ToolBarPanel.isFound()) {
                date = ToolBarPanel.getDate();
            }else if(!ToolBarPanel.isNewAttendaceRecord() && ToolBarPanel.isFound()){
               date = ToolBarPanel.getDate();
            }
           else{
                date = SelectDatePanelOption.getSelectedDate();
            }
            System.out.println(ToolBarPanel.isNewAttendaceRecord());
            System.out.println("The course code is : " + cCode);
            System.out.println("The date is : " +SelectDatePanelOption.getSelectedDate()+ date);
            //Send to data and retrieve from data update the attendence column
            Controller co = new Controller();
            co.connect();
            co.updateAttendenceRecord(rData);
            co.newloadAttendanceStatus(cCode, date);
            AttendanceSummaryPannel.setAttendanceSummary(co.getAttRecords());
            DisplayRecordTableModel.setAttendRecord(co.getAttRecords());
            ClassSessionViewPannel.refresh();
            System.out.println("The data is: " +rData);
        } else {
            AddNewPersonPannel.setArrivedData(rData);
        }

    }

    public void WriteFile(ArrayList arr) throws IOException {
        try {
            FileWriter outFile = new FileWriter("G://FingerTemplete//course.txt", true);
            try (BufferedWriter outStream = new BufferedWriter(outFile)) {
                outStream.write(arr.toString());
                outStream.newLine();
            }
        } catch (Exception e) {

        }

    }
}
