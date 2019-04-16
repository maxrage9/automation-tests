package automate_instance_wakeup;

import java.awt.event.*;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Snow_wakeup {
	
	HashMap<String,String> instance_cred;
	JFrame master_frame;
	JButton wake_instance_button;
	JButton add_instance_button;
	
	public Snow_wakeup() {
		instance_cred  = new HashMap<String,String>();
		wake_instance_button = new JButton("WakeNow");
		add_instance_button = new JButton("Add Instance");
	}
	
	public void GUI() {
		master_frame = new JFrame("WakeNow");
		master_frame.setSize(800, 600);
		JPanel table_panel = new JPanel();
		table_panel.setLayout(new FlowLayout());
		JPanel button_panel = new JPanel();
		button_panel.setLayout(new FlowLayout());
		wake_instance_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wakeup("equinoxaedra@gmail.com","Equinox@edra1");
			}
		});
		add_instance_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add_cred_GUI();
			}
		});
		JButton add_instance_button = new JButton("Add Instance");
		/*String column[]={"Instance Name","Username","Password"};
		Iterator<Entry<String, String>> hm_iterator = instance_cred.entrySet().iterator(); 
		if(hm_iterator.hasNext()) {
			HashMap.Entry map_element = (HashMap.Entry)hm_iterator.next();
			get_credentials(map_element);
		}*/
		button_panel.add(wake_instance_button);
		button_panel.add(add_instance_button);
		master_frame.add(table_panel);
		master_frame.add(button_panel);
		master_frame.setVisible(true);
	}
	
	public void add_cred_GUI() {
		master_frame = new JFrame("WakeNow");
		JTextField user_name = new JTextField();
		JPasswordField user_pass = new JPasswordField();
		JPanel cred_input = new JPanel();
		cred_input.setLayout(new FlowLayout());
		cred_input.add(user_name);
		cred_input.add(user_pass);
		master_frame.add(cred_input);
		master_frame.setVisible(true);
	}
	
	public void wakeup(String username,String password) {
		System.setProperty("webdriver.gecko.driver", "/home/azardeus/eclipse-workspace/Java_libs/geckodriver");
		WebDriver driver = new FirefoxDriver();
		driver.get("https://signon.service-now.com/ssologin.do?RelayState=%252F%252Fapp%252Ftemplate_saml_2_0%252Fk317zlfESMUHAFZFXMVB%252Fsso%252Fsaml%253FRelayState%253Dhttps%25253A%25252F%25252Fdeveloper.servicenow.com%25252Fsaml_redirector.do%25253Fsysparm_nostack%25253Dtrue%252526sysparm_uri%25253D%2525252Fnav_to.do%2525253Furi%2525253D%252525252Fssologin.do%252525253F%252525252F%2526SAMLRequest%253DnZPdbuIwEIVfJfI9%25252BWNJqEWQ0rCoSKWLgFZVb5DrTFqrjp31OMDu028SaOFiS1d76%25252Fkyc%25252BacyQhZKcOKprV9VUv4WQNaZ19KhfRQSUhtFNUMBVLFSkBqOV2l81sauj6tjLaaa0mcFBGMFVplWmFdglmB2QoO98vbhLxaWyH1vBy2IHUFxsVDVemdy3VJnEkzVyjWNjjhJwjFi9LK1W%25252BWtbzHqsqzUFaSWdi0Qjfhxvfe%25252BkH8WxbfV%25252FP7m3T6NH2cP1x7iNprCeJMteHQLZqQgkkE4swmCVndZXFUxEPeH%25252BTBM%25252B%25252FzGAb%25252BIMrhikXhtzgIh1ED4oIhii2cPkWsYabQMmUTEvrBVc8Pe%25252F5w7Q%25252FpIKah7%25252Faj6Ik4i6NF10LlQr1c9vP5ACG9Wa8XvcWP1bprsBU5mLuG%25252FhcrH8BgZ2PTkIxHXYy0U2vOk70shL3HScZfpjHyzmccJ1a01TubLLQU%25252FJeTSql3mYEmr4RYU0MXR8ns5zICN%25252BheRN4rOpRCyYRM89wAIvE%25252BBh3PFvIu2%25252Bb%25252BLOytk%25252BmyYkZg6wTsGbcfXpxjmWw2XULxX85cxDjlbe%25252Fmub2cnTZ5ewnAG51rwxRW2th35%25252F6maHwsfrLfqXz%25252B647%25252FAA%25253D%25253D&redirectUri=&email=");
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
	
	public String[] get_credentials(HashMap.Entry<String, String> map_element) {
		String[] credential_array = new String[2];
		credential_array[0] = (String)map_element.getKey();
	    credential_array[1] = (String)map_element.getValue();
		return credential_array;
	}
	
	public void insert_credentials(HashMap<String,String> cred_map , String user_name, String user_password) {
		cred_map.put(user_name, user_password);
	}
	
	public static void main(String[] args) {
		
		Snow_wakeup wake_up = new Snow_wakeup();
		wake_up.GUI();
		}
}
