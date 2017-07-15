package browsermator.com;


import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class ClickAtImageSRCActionView extends ActionView  {
  
   ClickAtImageSRCActionView()
   {
           this.JLabelVariable1 = new JLabel("Click at Image SRC:");
       
 this.JPanelAction.add(this.JLabelVariable1);
      this.JPanelAction.add(this.JTextFieldVariable1); 
     this.JCheckBoxBoolVal1 = new JCheckBox("Right Click");
               this.JCheckBoxBoolVal2 = new JCheckBox("Multi-Click");
               this.JPanelAction.add(this.JCheckBoxBoolVal1);
               this.JPanelAction.add(this.JCheckBoxBoolVal2);
     this.JPanelAction.add(this.JButtonOK);
         this.JPanelAction.add(this.JButtonDelete);
   }
   @Override  
public void AddListeners(Action action, SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData, Procedure newbug, ProcedureView newbugview)
   {
    addJCheckBoxBoolVal1ActionListener((ActionEvent e) -> {
          action.setBoolVal1(JCheckBoxBoolVal1.isSelected());
          
       });
       addJCheckBoxBoolVal2ActionListener((ActionEvent e) -> {
          action.setBoolVal2(JCheckBoxBoolVal2.isSelected());
          
       });
      AddDraggers(action, STAppFrame, STAppData, newbug, newbugview);
                           addJButtonDeleteActionActionListener((ActionEvent evt) -> {
                          STAppFrame.DeleteActionView(newbugview, action.index);
                          STAppData.DeleteAction(newbug, action.index);
                             STAppFrame.UpdateScrollPane(newbugview);
   });



     addJTextFieldVariable1DocListener(
             new DocumentListener()
           {
@Override
       public void changedUpdate(DocumentEvent documentEvent) {
      action.setVariable1(JTextFieldVariable1.getText());

      }
@Override
      public void insertUpdate(DocumentEvent documentEvent) {
      action.setVariable1(JTextFieldVariable1.getText());
      }
@Override
      public void removeUpdate(DocumentEvent documentEvent) {
     action.setVariable1(JTextFieldVariable1.getText());
      }
      }
                 );

     addJButtonOKActionActionListener((ActionEvent evt) -> {
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