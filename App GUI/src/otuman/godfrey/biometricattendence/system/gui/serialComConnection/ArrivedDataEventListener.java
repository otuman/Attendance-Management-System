/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otuman.godfrey.biometricattendence.system.gui.serialComConnection;

import java.util.EventListener;

/**
 *
 * @author Otoman
 */
public interface ArrivedDataEventListener extends EventListener {
      public void arrivedDataEventListener(SerialEvent se);
    
}
