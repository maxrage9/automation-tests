package automate_instance_wakeup;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Snow_wakeup extends RW_utils{
	
	HashMap<String,String[]> instance_cred;
	JFrame master_frame;
	JButton wake_instance_button;
	JButton add_instance_button;
	JLabel credName_label;
	JTextField cred_name;
	JLabel username_label;
	JTextField user_name_field;
	JLabel password_label;
	JPasswordField user_password_field;
	JTable cred_table;
	JPanel combo_panel;
	JPanel table_panel;
	JPanel save_panel;
	JPanel button_panel;
	DefaultTableModel table_model;
	
	public Snow_wakeup() {
		master_frame = new JFrame("WakeNow");
		instance_cred  = new HashMap<String,String[]>();
		wake_instance_button = new JButton("WakeNow");
		add_instance_button = new JButton("Add Instance");
		credName_label = new JLabel("Credential Name");
		cred_name = new JTextField(10);
		username_label = new JLabel("Username");
		user_name_field = new JTextField(10);
		password_label = new JLabel("Password");
		user_password_field = new JPasswordField(10);
		table_model = new DefaultTableModel();
		cred_table = new JTable(table_model);
		//Object[] column_name = {"Credential Name", "Username"};
		table_model.addColumn("Credential Name");
		table_model.addColumn("Username");
		combo_panel = new JPanel();
		table_panel = new JPanel();
		save_panel = new JPanel();
		button_panel = new JPanel();
	}
	
	public void GUI() {
		master_frame.setSize(800, 600);
		master_frame.setLayout(new GridLayout(1,2));
		master_frame.setDefaultCloseOperation(3);
		combo_panel.setLayout(new GridLayout(2,1));
		save_panel.setLayout(new BoxLayout(save_panel, BoxLayout.Y_AXIS));
		table_panel.setLayout(new GridLayout(1,1));
		button_panel.setLayout(new FlowLayout());
		
		wake_instance_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				get_credentials(read_file());
			}
		});
		add_instance_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cred_name_value = cred_name.getText();
				insert_credentials(instance_cred, cred_name_value);
				cred_table_GUI();
			}
		});
		/*String column[]={"Instance Name","Username"};
		Iterator<Entry<String, String>> hm_iterator = instance_cred.entrySet().iterator(); 
		if(hm_iterator.hasNext()) {
			HashMap.Entry map_element = (HashMap.Entry)hm_iterator.next();
			get_credentials(map_element);
		}*/
		save_panel.add(credName_label);
		save_panel.add(cred_name);
		save_panel.add(username_label);
		save_panel.add(user_name_field);
		save_panel.add(password_label);
		save_panel.add(user_password_field);
		save_panel.add(add_instance_button);
		table_panel.add(cred_table);
		button_panel.add(wake_instance_button);
		combo_panel.add(table_panel);
		combo_panel.add(button_panel);
		master_frame.add(save_panel);
		master_frame.add(combo_panel);
		master_frame.setVisible(true);
	}
	
	public void cred_table_GUI() {
		Object[] row = new Object[2];
		Iterator<Entry<String, String[]>> hm_iterator = read_file().entrySet().iterator(); 
		while(hm_iterator.hasNext()) {
			HashMap.Entry<String, String[]> map_element = (HashMap.Entry<String, String[]>)hm_iterator.next();
			row[0] = map_element.getKey();
			row[1] = map_element.getValue()[0];
			table_model.addRow(row);
			//System.out.println(map_element.getKey());
			//System.out.println(map_element.getValue()[0]);
		}
		master_frame.repaint();
	}
	
	public void wakeup(String username,String password) {
		//System.setProperty("webdriver.gecko.driver", "D:\\Selenium\\geckodriver.exe");
		System.setProperty("webdriver.gecko.driver", "/home/azardeus/eclipse-workspace/Java_libs/geckodriver");
		WebDriver driver = new FirefoxDriver();
		driver.get("https://signon.service-now.com/ssologin.do");
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("submitButton")).click();
		while(driver.getTitle().compareTo("Home | ServiceNow Developers") != 0)
			driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
		driver.get("https://developer.servicenow.com/app.do#!/instance");
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		WebElement element= driver.findElement(By.id("instanceWakeUpBtn"));
		WebDriverWait wait = new WebDriverWait(driver, 5); 
		wait.until(ExpectedConditions.visibilityOf(element));
		element.click();
		/*JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("wakeUpInstance();",element);*/
	}
	
	public void get_credentials(HashMap<String,String[]> instance_cred) {
		Iterator<Entry<String, String[]>> hm_iterator = instance_cred.entrySet().iterator(); 
		while(hm_iterator.hasNext()) {
			HashMap.Entry<String, String[]> map_element = (HashMap.Entry<String, String[]>)hm_iterator.next();
			System.out.println(map_element.getKey());
			System.out.println(map_element.getValue()[0]);
		}
	}
	
	public void insert_credentials(HashMap<String,String[]> cred_map , String credential_name) {
		String user_name = user_name_field.getText(); 
		String user_password = new String(user_password_field.getPassword());
		String[] credential_array = new String[2];
		credential_array[0] = user_name;
		credential_array[1] = user_password;
		cred_map.put(credential_name, credential_array);
		write_file(cred_map);
	}
	
	public static void main(String[] args) {
		
		Snow_wakeup wake_up = new Snow_wakeup();
		wake_up.cred_table_GUI();
		wake_up.GUI();
	}
}