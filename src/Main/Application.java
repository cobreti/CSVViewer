package Main;

import javax.swing.*;
import javax.swing.UIManager.*;


public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		try {
			
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception ex) {
			 System.out.println("Error setting nimbus look&feel");
		}
		
		MainFrame	frame = new MainFrame();
		frame.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
