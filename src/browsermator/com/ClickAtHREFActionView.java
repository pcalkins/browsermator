package browsermator.com;


import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class ClickAtHREFActionView extends ActionView 
{
  
   ClickAtHREFActionView()
   {
 
      
       this.JLabelVariable1 = new JLabel("Click on HREF:");
      
  //     AddToGrid(this.JLabelVariable1, 0, 2, 1, 1, 0, 0, 0, GridBagConstraints.WEST);

  //       AddToGrid(this.JTextFieldVariable1, 0, 3, 1, 1, 1.0, 0, 300, GridBagConstraints.WEST);
               this.JCheckBoxBoolVal1 = new JCheckBox("Right Click");
               this.JCheckBoxBoolVal2 = new JCheckBox("Multi-Click");
  //             AddToGrid(this.JCheckBoxBoolVal1, 0, 4, 1, 1, 0, 0, 0, GridBagConstraints.WEST);
  //             AddToGrid(this.JCheckBoxBoolVal2, 0, 5, 1, 1, 0, 0, 0, GridBagConstraints.WEST);
 // ActionPanelMiddle.add(JLabelVariable1);
 //  ActionPanelMiddle.add(JCheckBoxBoolVal1);
 //  ActionPanelMiddle.add(JCheckBoxBoolVal2);
 //  ActionPanelMiddle.add(JTextFieldVariable1);   
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
                            STAppFrame.UpdateScrollPane(newbugview);
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