package browsermator.com;


import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class GoActionView extends ActionView
{
  
   GoActionView()
   {
      
       
       this.JLabelVariable1 = new JLabel("Go to URL:");
  this.JPanelAction.add(this.JLabelVariable1);
      this.JPanelAction.add(this.JTextFieldVariable1);
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
 @Override
   public void AddLoopListeners(Action action, SeleniumTestTool Window, Procedure newbug, ProcedureView newbugview)
   {

if (newbugview.myTable!=null)
{


     addJTextFieldFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
            
            
                newbugview.ShowFieldInstructions(true, 1, newbugview.index, action.index);
                newbugview.setLastSelectedField (1, newbugview.index, action.index);
            }

            @Override
            public void focusLost(FocusEvent e) {
            newbugview.ShowFieldInstructions(false, 1, newbugview.index, action.index);
               newbugview.clearLastSelectedValues();
            }
        });
      addJTextField2FocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
            
            
                newbugview.ShowFieldInstructions(true, 2, newbugview.index, action.index);
                newbugview.setLastSelectedField (2, newbugview.index, action.index);
            }

            @Override
            public void focusLost(FocusEvent e) {
            newbugview.ShowFieldInstructions(false, 2, newbugview.index, action.index);
               newbugview.clearLastSelectedValues();
            }
        });
   }
}  
}