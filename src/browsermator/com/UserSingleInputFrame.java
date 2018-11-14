/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Pete
 */
public class UserSingleInputFrame extends JFrame implements ActionListener{
  
  
    UserSingleInputPanel[] UserSingleInputPanels;
     JPanel jPanelButtons; 
                JButton jButtonOK;
    //            JButton jButtonCancel;
            JPanel jPanelMain;
     Map<String, String> map;       
   public UserSingleInputFrame(UserSingleInputPanel[] in_user_single_input_panels, Map<String, String> in_map)
  {
     map = in_map;
                jButtonOK = new JButton("Send User Input(s)");
        //        jButtonCancel = new JButton("Close");
                jPanelButtons = new JPanel();
             
      UserSingleInputPanels = in_user_single_input_panels;
   
  }
   @Override
     public void actionPerformed(ActionEvent event)
     {
      
         Object source = event.getSource();
      if (source == jButtonOK)
{
ClickedOK();
}
    //     if (source == jButtonCancel)
    //     {
    //     ClickedCancel();
   //      }
       setVisible(false);
       dispose();
   
     }
       public void addLastPanelActionListener()
     {
         
     int lastframeindex = UserSingleInputPanels.length-1;
    
             UserSingleInputPanels[lastframeindex].addjTextFieldInputValueActionListener((ActionEvent evt) -> {
                       ClickedOK();      
                     
   
   });
     }
     public void ClickedOK()
     {
                String[] singleinputnames = new String[UserSingleInputPanels.length];
               String[] singleinputvalues = new String[UserSingleInputPanels.length];
                             
            int panel_counter = 0;      
        for (UserSingleInputPanel thisPanel: UserSingleInputPanels)
        {
            String thisname = thisPanel.GetInputName();
          String thisvalue = thisPanel.GetInputValue();
        
         map.put(thisname, thisvalue);
           
     
    
           
        }
      setVisible(false);
     dispose();
     }
     public void ClickedCancel()
     {
         dispose();
        
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
        //           jPanelButtons.add(jButtonCancel);  
                   jPanelButtons.setSize(600,200);
                    jPanelMain.add(jPanelButtons);
                      add(jPanelMain);
                      jButtonOK.addActionListener(this);
                      
        setTitle("Site Inputs");
        pack();
      setVisible(true);
      
       
   }
       public void addjButtonOKActionListener(ActionListener listener)
    {
        jButtonOK.addActionListener(listener);
    }
   //    public void addjButtonCancelActionListener(ActionListener listener)
  //     {
   //        jButtonCancel.addActionListener(listener);
   //    }
}
