package otuman.godfrey.biometricattendence.system.model;




import java.io.FileInputStream;
import java.io.InputStream;

public class WorkWithBuffer {
   public static void main(String[] args) throws Exception {
      
      InputStream is = null;
      byte[] buffer=new byte[460];
      char c;
      
      try{
         // new input stream created
         is = new FileInputStream("G://FingerTemplete//course.txt");
         
         System.out.println("Characters printed:");
         
         // read stream data into buffer
         is.read(buffer);
         
         // for each byte in the buffer
         for(byte b:buffer)
         {
            // convert byte to character
            c=(char)b;
            
            // prints character
            System.out.print(c);
         }
      }catch(Exception e){
      }finally{
         
         // releases system resources associated with this stream
         if(is!=null)
            is.close();
      }
   }
   
   public static int toInt( byte[] bytes ) {
    int result = 0;
    for (int i=0; i<4; i++) {
      result = ( result << 8 ) - Byte.MIN_VALUE + (int) bytes[i];
    }
  return result;
   }
}