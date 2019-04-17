package automate_instance_wakeup;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class RW_utils {
	
	public void write_file(HashMap<String,String[]> cred_map) {
		try {
			FileOutputStream fos = new FileOutputStream("hashmap.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(cred_map);
			oos.close();
			fos.close();
			}
		catch(IOException ioe) {
			ioe.printStackTrace();
			}
		}
	public HashMap<String, String[]> read_file() { 
		
		HashMap<String, String[]> deser_map = null;
		try {
	         FileInputStream fis = new FileInputStream("hashmap.ser");
	         ObjectInputStream ois = new ObjectInputStream(fis);
	         deser_map = (HashMap<String, String[]>) ois.readObject();
	         ois.close();
	         fis.close();
	      }
		
	      catch(IOException ioe) {
	         ioe.printStackTrace();
	      }
	      
	      catch(ClassNotFoundException c) {
	         System.out.println("Class not found");
	         c.printStackTrace();
	      }
		return deser_map;
		}
	}