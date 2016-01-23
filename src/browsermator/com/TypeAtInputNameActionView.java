
package browsermator.com;

import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class TypeAtInputNameActionView extends ActionView
{
 TypeAtInputNameActionView()
   {
 
    
       this.JLabelVariable1 = new JLabel("Type At Input Name:");
    this.JLabelVariable2 = new JLabel("Words:");
  
    this.JPanelAction.add(this.JLabelVariable1);
      this.JPanelAction.add(this.JTextFieldVariable1); 
      this.JPanelAction.add(this.JLabelVariable2);
      this.JPanelAction.add(this.JTextFieldVariable2);
         this.JCheckBoxBoolVal1 = new JCheckBox("Press Enter Key");
      this.JPanelAction.add(this.JCheckBoxBoolVal1);
       this.JPanelAction.add(this.JButtonOK);
  this.JPanelAction.add(this.JButtonDelete);
  
   }
 @Override  
 public void AddListeners(Action action, SeleniumTestTool Window, Procedure newbug, ProcedureView newbugview)
   {
    addJCheckBoxBoolVal1ActionListener((ActionEvent e) -> {
          action.setBoolVal1(JCheckBoxBoolVal1.isSelected());
          
       });
   addJButtonMoveDownActionListener((ActionEvent evt) -> {
       Window.MoveAction(Window, newbug, newbugview, action.index, 1);
      
   });
     addJButtonMoveUpActionListener((ActionEvent evt) -> {
         Window.MoveAction(Window, newbug, newbugview, action.index, -1);
   });

                        addJButtonDeleteActionActionListener((ActionEvent evt) -> {
                          Window.DeleteAction(newbug, newbugview, action.index);
                            Window.UpdateDisplay();
   });
    addJTextFieldVariable1ActionListener((ActionEvent e) -> {
           UpdateActionView();
           action.Locked= true;
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
   addJTextFieldVariable2DocListener(
             new DocumentListener()
           {

       public void changedUpdate(DocumentEvent documentEvent) {
      action.setVariable2(JTextFieldVariable2.getText());
      }

      public void insertUpdate(DocumentEvent documentEvent) {
      action.setVariable2(JTextFieldVariable2.getText());
      }
      public void removeUpdate(DocumentEvent documentEvent) {
     action.setVariable2(JTextFieldVariable2.getText());
      }
      }
                 );
   } 
       
     
}
