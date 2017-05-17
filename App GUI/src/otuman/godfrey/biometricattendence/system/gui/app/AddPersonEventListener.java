/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otuman.godfrey.biometricattendence.system.gui.app;


import java.util.EventListener;
import otuman.godfrey.biometricattendence.system.model.Person;

/**
 *
 * @author Otoman
 */
public interface AddPersonEventListener extends EventListener{
    
    public void personEventListener(Person api);
}

