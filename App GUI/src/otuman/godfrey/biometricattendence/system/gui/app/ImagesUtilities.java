/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otuman.godfrey.biometricattendence.system.gui.app;

import java.net.URL;

import javax.swing.ImageIcon;

public class ImagesUtilities {
	
	public static String getFileExtension(String name){
		
		try{
			int pointndex = name.lastIndexOf(".");
			if(pointndex == -1){
				
				 return null;
			}
			if(pointndex == name.length() -1){
				return null;
			}
			return name.substring(name.length() +1, name.length());
		}catch(Exception e){
			return e.toString();
		}
   }
   public ImageIcon createIcon(String path){
	   URL url = System.class.getResource(path);
	   if(url == null){
		 System.err.println("Unable to load the image "+path);  
	   }
	  ImageIcon icon = new ImageIcon(url);
	return icon;
	}
}