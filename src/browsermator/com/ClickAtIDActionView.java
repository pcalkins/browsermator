package browsermator.com;


import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class ClickAtIDActionView extends ActionView 
{
  
   ClickAtIDActionView()
   {

       this.JLabelVariable1 = new JLabel("Click on ID:");
       

        
           this.JCheckBoxBoolVal1 = new JCheckBox("Right Click");
               this.JCheckBoxBoolVal2 = new JCheckBox("Multi-Click");
     theseActionSettings.add(new ActionSettings(JLabelVariable1, 2, 1, 0.0, GridBagConstraints.WEST));
        theseActionSettings.add(new ActionSettings(JTextFieldVariable1, 3, 3, 1.0, GridBagConstraints.WEST));
        theseActionSettings.add(new ActionSettings(JCheckBoxBoolVal1, 6, 1, 0.0, GridBagConstraints.WEST));
        theseActionSettings.add(new ActionSettings(JCheckBoxBoolVal2, 7, 1, 0.0, GridBagConstraints.WEST));
        theseActionSettings.add(new ActionSettings(JButtonOK, 8, 1, 0.0, GridBagConstraints.WEST));
        theseActionSettings.add(new ActionSettings(JButtonDelete, 9, 1, 0.0, GridBagConstraints.WEST));
       
//                this.JPanelAction.add(this.JLabelVariable1);
//      this.JPanelAction.add(this.JTextFieldVariable1); 
//               this.JPanelAction.add(this.JCheckBoxBoolVal1);
//               this.JPanelAction.add(this.JCheckBoxBoolVal2);
//               
//       
//      this.JPanelAction.add(this.JButtonOK);
//        this.JPanelAction.add(this.JButtonDelete);
   }
   @Override  
 public void AddListeners(Action action, SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData, Procedure newbug, ProcedureView newbugview)
   {
    addJCheckBoxBoolVal1ActionListener((ActionEvent e) -> {
             STAppFrame.saveState(); 
        action.setBoolVal1(JCheckBoxBoolVal1.isSelected());
          
       });
       addJCheckBoxBoolVal2ActionListener((ActionEvent e) -> {
           STAppFrame.saveState();
           action.setBoolVal2(JCheckBoxBoolVal2.isSelected());
          
       });
      AddDraggers(action, STAppFrame, STAppData, newbug, newbugview);
                           addJButtonDeleteActionActionListener((ActionEvent evt) -> {
                            STAppFrame.saveState();
                               STAppFrame.DeleteActionView(newbugview, action.index);
                          STAppData.DeleteAction(newbug, action.index);
                 
   });
  



     addJTextFieldVariable1DocListener(
             new DocumentListener()
           {
@Override
       public void changedUpdate(DocumentEvent documentEvent) {
   STAppFrame.saveState();
           action.setVariable1(JTextFieldVariable1.getText());

      }
@Override
      public void insertUpdate(DocumentEvent documentEvent) {
      action.setVariable1(JTextFieldVariable1.getText());
      }
@Override
      public void removeUpdate(DocumentEvent documentEvent) {
   STAppFrame.saveState();
          action.setVariable1(JTextFieldVariable1.getText());
      }
      }
                 );

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
 
}