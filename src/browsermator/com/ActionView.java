package browsermator.com;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

public abstract class ActionView implements Listenable{

     int index;
     int bugindex;
     JPanel JPanelAction;
          String ActionType;
   JPasswordField JTextFieldPassword;
   JTextField JTextFieldVariable1;
   JTextField JTextFieldVariable2;
   JLabel JLabelVariable1;
   JLabel JLabelVariable2;

   JLabel JLabelPassFail;

   JButton JButtonOK;
   JButton JButtonDelete;
   JLabel JLabelIndex;
   JButton JButtonMoveUp;
   JButton JButtonMoveDown;
   
   ActionView()
   {

     
      this.JPanelAction = new JPanel();
      this.JLabelPassFail = new JLabel("");
      this.JTextFieldVariable1 =  new JTextField("", 15);
      this.JTextFieldVariable2 = new JTextField("", 15);
      this.JTextFieldPassword = new JPasswordField("", 15);
      this.JLabelVariable1 = new JLabel("Generic");
      this.JLabelVariable2 = new JLabel("Generic");
    this.JButtonOK = new JButton("Lock");
  this.JButtonOK.setActionCommand("Update");
    this.JButtonDelete = new JButton("Remove");
       this.JButtonMoveDown = new JButton("\\/");
       this.JButtonMoveUp = new JButton("/\\");
String stringactionindex = Integer.toString(this.index+1);
        String stringbugindex = Integer.toString(this.bugindex+1);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
      this.JLabelIndex = new JLabel(bugdashactionindex);
    
      this.JPanelAction.add(this.JLabelIndex);
             this.JPanelAction.add(this.JButtonMoveUp);
             this.JPanelAction.add(this.JButtonMoveDown);
 
         
   }
     public void setActionViewIndex (int newindex)
     {
         this.index = newindex;
     }
      public void addJTextFieldPasswordActionListener(ActionListener actlistener)
     {
         JTextFieldPassword.addActionListener(actlistener);
     }
      public void addJTextFieldVariable1ActionListener(ActionListener actlistener)
     {
         JTextFieldVariable1.addActionListener(actlistener);
     }
         public void addJTextFieldVariable1DocListener(DocumentListener doclistener)
     {
         JTextFieldVariable1.getDocument().addDocumentListener(doclistener);
     }
         public void addJTextFieldVariable2DocListener(DocumentListener doclistener)
         {
             JTextFieldVariable2.getDocument().addDocumentListener(doclistener);
         }
           public void addJTextFieldPasswordDocListener(DocumentListener doclistener)
         {
             JTextFieldPassword.getDocument().addDocumentListener(doclistener);
         }
       public void addJButtonOKActionActionListener(ActionListener listener)
       {
       JButtonOK.addActionListener(listener);
   
       }
       public void addJButtonMoveUpActionListener(ActionListener listener)
       {
           JButtonMoveUp.addActionListener(listener);
       }
       public void addJButtonMoveDownActionListener(ActionListener listener)
       {
           JButtonMoveDown.addActionListener(listener);
       }
          public void addJButtonDeleteActionActionListener(ActionListener listener)
       {
       JButtonDelete.addActionListener(listener);
   
       } 
 
       public void UpdateActionView()
       {
      
   this.JTextFieldVariable1.setEnabled(false);
   this.JTextFieldVariable2.setEnabled(false);
   this.JTextFieldVariable1.setEditable(false);
   this.JTextFieldVariable2.setEditable(false);
   this.JTextFieldPassword.setEnabled(false);
   this.JTextFieldPassword.setEditable(false);
   this.JButtonOK.setText("Edit");
   this.JButtonOK.setActionCommand("Edit");
       }
       public void EditActionView()
       {
 
    this.JTextFieldVariable1.setEnabled(true);
   this.JTextFieldVariable2.setEnabled(true);
   this.JTextFieldVariable1.setEditable(true);
   this.JTextFieldVariable2.setEditable(true);
   this.JTextFieldPassword.setEnabled(true);
   this.JTextFieldPassword.setEditable(true);
   this.JButtonOK.setText("Lock");
   this.JButtonOK.setActionCommand("Update");
  
       }
       public void SetIndexes(int bugindex, int actionindex)
       {
  
   
           this.index = actionindex;
           this.bugindex = bugindex;
            String stringactionindex = Integer.toString(this.index+1);
        String stringbugindex = Integer.toString(this.bugindex+1);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
           this.JLabelIndex.setText(bugdashactionindex);
           
       }
   @Override
   public void AddListeners(Action action, SeleniumTestTool Window, Procedure newbug, ProcedureView newbugview)
   {
   
   this.addJButtonMoveDownActionListener((ActionEvent evt) -> {
       Window.MoveAction(Window, newbug, newbugview, action.index, 1);
      
   });
     this.addJButtonMoveUpActionListener((ActionEvent evt) -> {
         Window.MoveAction(Window, newbug, newbugview, action.index, -1);
   });

                        this.addJButtonDeleteActionActionListener((ActionEvent evt) -> {
                          Window.DeleteAction(newbug, newbugview, action.index);
                            Window.UpdateDisplay();
   });
           

   } 
}