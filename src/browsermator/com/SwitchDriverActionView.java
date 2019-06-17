package browsermator.com;


import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JLabel;



public class SwitchDriverActionView extends ActionView 
{
  
   SwitchDriverActionView()
   {

       this.JLabelVariable1 = new JLabel("Switch Driver To:");
       

        
         
     theseActionSettings.add(new ActionSettings(JLabelVariable1, 2, 1, 0.0, GridBagConstraints.WEST));
        theseActionSettings.add(new ActionSettings(jComboBoxDriver, 3, 3, 1.0, GridBagConstraints.WEST));
          theseActionSettings.add(new ActionSettings(JButtonOK, 8, 1, 0.0, GridBagConstraints.WEST));
            theseActionSettings.add(new ActionSettings(JButtonDelete, 9, 1, 0.0, GridBagConstraints.WEST));
       

   }
   @Override  
 public void AddListeners(BMAction action, SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData, Procedure newbug, ProcedureView newbugview)
   {
 
      AddDraggers(action, STAppFrame, STAppData, newbug, newbugview);
                           addJButtonDeleteActionActionListener((ActionEvent evt) -> {
                            STAppFrame.saveState();
                               STAppFrame.DeleteActionView(newbugview, action.index);
                          STAppData.DeleteAction(newbug, action.index);
                 
   });
  
 addjComboBoxDriverItemListener( new ItemListener() {
    
        public void itemStateChanged (ItemEvent e )
        {
         if ((e.getStateChange() == ItemEvent.SELECTED)) {
            Object ActionType = e.getItem();
            String TargetBrowser = ActionType.toString();
         action.setVariable1(TargetBrowser);
         action.setOSType(STAppData.OSType);
         }
        }
        
        });


 
     addJButtonOKActionActionListener((ActionEvent evt) -> {
             STAppFrame.saveState();
         String ACommand = evt.getActionCommand();
         
         if (ACommand.equals("Update"))
         {
             
             UpdateActionView();
             action.Locked= true;
             
         }
         if (ACommand.equals("Edit"))
         {
             EditActionView();
             action.Locked= false;
             
         } });        

   } 
 public void addjComboBoxDriverItemListener (ItemListener listener)
{
  jComboBoxDriver.addItemListener(listener);
}

}