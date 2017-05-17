package otuman.godfrey.biometricattendence.system.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import otuman.godfrey.biometricattendence.system.gui.serialComConnection.SerialCommunication;

public class DBConnect {
    SerialCommunication sCom ;
    public DBConnect() throws IOException {
        sCom = new SerialCommunication();
        ArrayList ava = sCom.availbleComPort();
        String selected = null;
        for (int i = 0; i < ava.size(); i++) {
            selected = (String) ava.get(i);
        }
        
        sCom.InitSerialConnection(selected);
        WriteFile();
    }

    public static void main(String[] args) throws Exception {
       Database db = new Database();
       db.connect();
       db.checkCourseCodeAndDate("2016-03-04", "ELEC 100054");
        //DBConnect dbcon = new DBConnect();

    }

    public void WriteFile() throws IOException {       
        try {
            FileReader in = new FileReader("G://FingerTemplete//course.txt");

            BufferedReader inStream = new BufferedReader(in);
                String a = inStream.readLine();
                //System.out.println(a);
                String rep = a.replace("[","");
                //System.out.println(rep);
                String rep1 = rep.replace("]","");
                // System.out.println(rep1);
                ArrayList<String> myList = new ArrayList<>(Arrays.asList(rep1.split(",")));
                //System.out.println(myList.toString());
                for (int i = 0; i < myList.size(); i++) {
                    String dd = myList.get(i);
                    try {
                        int data = Integer.parseInt(dd.trim());
                        sCom.SerialWritter(data);
                       //System.out.println(data);
                    } catch (NumberFormatException nfe) {
                        System.out.println("NumberFormatException: " + nfe.getMessage());
                    }

                }
                sCom.SerialWritter(-1);
            } catch (Exception e) {
            }

        }

    
}
