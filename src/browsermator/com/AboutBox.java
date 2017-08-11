/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author pcalkins
 */
public class AboutBox extends JFrame{
 String version = "";
 JLabel titleJLabel;
 JPanel mainPanel;
 JLabel Creator;
 JLabel URL;
 JLabel Company;
 JLabel Contact;
    AboutBox(String in_version)
 {
     super("Browsermator");
     version = in_version;
     titleJLabel = new JLabel("Browsermator - Version " + version );
     Creator = new JLabel("Author: Pete Calkins");
     Company = new JLabel("Publisher: Petey's Perfect Apps");
     Contact = new JLabel("Contact: pcalkins@mvd.com");
     URL = new JLabel("https://www.browsermator.com");
     mainPanel = new JPanel();
     
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
     mainPanel.add(titleJLabel);
     mainPanel.add(Creator);
     mainPanel.add(Company);
     mainPanel.add(Contact);
     mainPanel.add(URL);
     
     this.add(mainPanel);
     this.pack();
     this.setVisible(true);
          
 }
 
}
