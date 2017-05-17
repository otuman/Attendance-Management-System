package otuman.godfrey.biometricattendence.system;

import javax.swing.SwingUtilities;

import otuman.godfrey.biometricattendence.system.gui.login.AttendanceLogin;

public class AttentenceMgmtSysMain {

    public AttentenceMgmtSysMain() {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                AttendanceLogin alogin = new AttendanceLogin();
                alogin.setVisible(true);

            }
        });
    }
}
