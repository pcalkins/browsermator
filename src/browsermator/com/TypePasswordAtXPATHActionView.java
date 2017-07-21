
package browsermator.com;

import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class TypePasswordAtXPATHActionView extends ActionView 
{
  
   TypePasswordAtXPATHActionView()
   {
   
       this.JLabelVariable1 = new JLabel("At XPATH:");
    this.JLabelVariable2 = new JLabel("Type password:");
    this.JTextFieldPassword = new JPasswordField("", 15);
 this.JPanelAction.add(this.JLabelVariable1);
      this.JPanelAction.add(this.JTextFieldVariable1); 
      this.JPanelAction.add(this.JLabelVariable2);
      this.JPanelAction.add(this.JTextFieldPassword);
       this.JCheckBoxBoolVal1 = new JCheckBox("Press Enter Key");
      this.JPanelAction.add(this.JCheckBoxBoolVal1);
       this.JPanelAction.add(this.JButtonOK);
      this.JPanelAction.add(this.JButtonDelete);
   }
   
 @Override  
  public void AddListeners(Action action, SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData, Procedure newbug, ProcedureView newbugview)
   {
 AddDraggers(action, STAppFrame, STAppData, newbug, newbugview);

                        addJButtonDeleteActionActionListener((ActionEvent evt) -> {
                             STAppFrame.saveState();
                            STAppFrame.DeleteActionView(newbugview, action.index);
                          STAppData.DeleteAction(newbug, action.index);
                           STAppFrame.UpdateScrollPane(newbugview);
   });
  

 addJCheckBoxBoolVal1ActionListener((ActionEvent e) -> {
          STAppFrame.saveState();  
     action.setBoolVal1(JCheckBoxBoolVal1.isSelected());
          
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
   
     addJTextFieldPasswordDocListener(
             new DocumentListener()
           {

       public void changedUpdate(DocumentEvent documentEvent) {
      String Password="";
     char[] temp;
     temp = JTextFieldPassword.getPassword();
     for (int x = 0; x<temp.length; x++)
     {
         Password = Password + temp[x];
     }
   //   try
   //   {
   //      Password = Protector.encrypt(Password);
          action.setVariable2(Password);
    
   //   }
   //   catch (Exception e)
   //   {
   //    System.out.println("Encrypt error: " + e.toString());
    //      action.setVariable2(Password);
    //  }
      }

      public void insertUpdate(DocumentEvent documentEvent) {
      String Password="";
     char[] temp;
     temp = JTextFieldPassword.getPassword();
    for (int x = 0; x<temp.length; x++)
     {
         Password = Password + temp[x];
     }
  //    try
  //    {
  //       Password = Protector.encrypt(Password);
          action.setVariable2(Password);
  //    }
    //  catch (Exception e)
    //  {
    //   System.out.println("Encrypt error: " + e.toString());
    //      action.setVariable2(Password);
    //  }
      }
      public void removeUpdate(DocumentEvent documentEvent) {
     String Password="";
     char[] temp;
     temp = JTextFieldPassword.getPassword();
       for (int x = 0; x<temp.length; x++)
     {
         Password = Password + temp[x];
     }
 
          action.setVariable2(Password);
   
      }
           }
                 );
   }    
 
   
}