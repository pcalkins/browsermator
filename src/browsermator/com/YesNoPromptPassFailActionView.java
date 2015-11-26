package browsermator.com;


import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class YesNoPromptPassFailActionView extends ActionView 
{
  
   YesNoPromptPassFailActionView()
   {
       this.JLabelVariable1 = new JLabel("Yes/No Question (Yes Passes Test):");
       this.JTextFieldVariable1.setSize(200, 200);
       
       
 this.JPanelAction.add(this.JLabelVariable1);
      this.JPanelAction.add(this.JTextFieldVariable1); 
       this.JPanelAction.add(this.JLabelPassFail);
        this.JPanelAction.add(this.JButtonOK);
    this.JPanelAction.add(this.JButtonDelete);
   }
  @Override  
 public void AddListeners(Action action, SeleniumTestTool Window, Procedure newbug, ProcedureView newbugview)
   {
   
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

   }    
 
   
}