/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Pete
 */
public class UserSingleInputFrame extends JFrame{
  
  
    UserSingleInputPanel[] UserSingleInputPanels;
     JPanel jPanelButtons; 
                JButton jButtonOK;
                JButton jButtonCancel;
            JPanel jPanelMain;
   public UserSingleInputFrame(UserSingleInputPanel[] in_user_single_input_panels)
  {
     
                jButtonOK = new JButton("Send User Input(s)");
                jButtonCancel = new JButton("Close");
                jPanelButtons = new JPanel();
             
      UserSingleInputPanels = in_user_single_input_panels;
    
  }
   public void InitializeFrame()
   {
     jPanelMain = new JPanel();
     jPanelMain.setLayout(new BoxLayout(jPanelMain, BoxLayout.PAGE_AXIS));
       for (UserSingleInputPanel thisPanel: UserSingleInputPanels)
       {
  //      thisPanel.CreatePanel();

          jPanelMain.add(thisPanel);
       }
      
                jPanelButtons.add(jButtonOK);
                   jPanelButtons.add(jButtonCancel);  
                   jPanelButtons.setSize(600,200);
                    jPanelMain.add(jPanelButtons);
                      add(jPanelMain);
        setTitle("Site Inputs");
        pack();
      setVisible(true);
      
       
   }
       public void addjButtonOKActionListener(ActionListener listener)
    {
        jButtonOK.addActionListener(listener);
    }
       public void addjButtonCancelActionListener(ActionListener listener)
       {
           jButtonCancel.addActionListener(listener);
       }
}
