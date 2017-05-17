/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otuman.godfrey.biometricattendence.system.gui.app;

import java.util.EventListener;
import otuman.godfrey.biometricattendence.system.model.AttendanceRecords;

/**
 *
 * @author Otoman
 */
public  interface ClassSessionEventListener extends EventListener {
    public void ClassSessionActionPerformed(AttendanceRecords attRecord);
}
