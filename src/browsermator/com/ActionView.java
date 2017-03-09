package browsermator.com;


import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

public abstract class ActionView implements Listenable, Initializable{

     int index;
     int bugindex;
     JPanel JPanelAction;
          String ActionType;
   JPasswordField JTextFieldPassword;
   JTextField JTextFieldVariable1;
   JTextField JTextFieldVariable2;
   JTextField JTextFieldVariableVARINDEX;
   JLabel JLabelVariable1;
   JLabel JLabelVariable2;


   JButton JButtonOK;
   JButton JButtonDelete;
   JLabel JLabelIndex;
   JLabel JLabelPassFail;
   JCheckBox JCheckBoxBoolVal1;
   JCheckBox JCheckBoxBoolVal2;
   JButton JButtonBrowseForFile;
   JButton JButtonDragIt;
   Boolean Locked;

   ActionView()
   {
      this.Locked = false;
      this.JButtonDragIt = new JButton("=");
      this.JPanelAction = new JPanel();
      this.JLabelPassFail = new JLabel("");
      this.JTextFieldVariable1 =  new JTextField("", 15);
      this.JTextFieldVariable2 = new JTextField("", 15);
      this.JTextFieldVariableVARINDEX = new JTextField("", 15);
      this.JTextFieldPassword = new JPasswordField("", 15);
      this.JLabelVariable1 = new JLabel("Generic");
      this.JLabelVariable2 = new JLabel("Generic");
      this.JCheckBoxBoolVal1 = new JCheckBox("Press Enter Key");
      this.JCheckBoxBoolVal2 = new JCheckBox("Multi-Click");
    this.JButtonOK = new JButton("Disable");
  this.JButtonOK.setActionCommand("Update");
    this.JButtonDelete = new JButton("Remove");
   

String stringactionindex = Integer.toString(this.index+1);
        String stringbugindex = Integer.toString(this.bugindex+1);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
      this.JLabelIndex = new JLabel(bugdashactionindex);
    
      this.JPanelAction.add(this.JLabelIndex);
      this.JPanelAction.add(this.JButtonDragIt);
   

 this.ActionType = "";
                
  
         
   }
    public void setPassState(Boolean passvalue)
    {
   
    if (passvalue)
    {
  
   this.JButtonDragIt.setBackground(Color.green);   
    }
    else
    {

   this.JButtonDragIt.setBackground(Color.red);
    }
    
    }
    public void resetPassState()
    {
      this.JButtonDragIt.setBackground(Color.LIGHT_GRAY);
     
    }

     public void setFieldToStoredVariable(String stored_var_name, int field_num)
     {
       if (stored_var_name.contains("-"))
       {
           
      String stored_var_string = "[stored_varname-start]" + stored_var_name + "[stored_varname-end]";
     if (field_num==2)
     {
      JTextFieldVariable2.setText(stored_var_string);
     }
    if (field_num==1)
    {
      JTextFieldVariable1.setText(stored_var_string);  
    }
        
       }
     }
     public void setActionFieldToDataColumn (int field_number, int columnindex, String selected_name)
     {
         
         String data_field_string = "[dataloop-field-start]"+field_number+","+columnindex+"," + selected_name+"[dataloop-field-end]";
         switch (field_number)
         {
             
             case 1:
                 JTextFieldVariable1.setText(data_field_string);
                 break;
             case 2:
                 JTextFieldVariable2.setText(data_field_string);
                 break;
             case 3:
                 JTextFieldPassword.setText(data_field_string);
                 break;
                 
         }
              
     }
     public void setActionViewIndex (int newindex)
     {
         this.index = newindex;
     }

     public void addJTextFieldFocusListener(FocusListener focuslistener)
     {
        JTextFieldVariable1.addFocusListener(focuslistener);
     }
     public void addJTextField2FocusListener(FocusListener focuslistener)
     {
         JTextFieldVariable2.addFocusListener(focuslistener);
     }
      public void addJCheckBoxBoolVal1ActionListener(ActionListener actlistener)
     {
         JCheckBoxBoolVal1.addActionListener(actlistener);
     }
         public void addJCheckBoxBoolVal2ActionListener(ActionListener actlistener)
     {
         JCheckBoxBoolVal2.addActionListener(actlistener);
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
              public void addJTextFieldVariableVARINDEXDocListener(DocumentListener doclistener)
     {
         JTextFieldVariableVARINDEX.getDocument().addDocumentListener(doclistener);
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
       
        public void addJButtonBrowseForFile(ActionListener listener)
       {
           JButtonBrowseForFile.addActionListener(listener);
       }
   
 
        public void addJButtonDragItMouseAdapter(MouseAdapter listener)
       {
       JButtonDragIt.addMouseListener(listener);
           
       }
        public void removeJButtonDragItMouserAdapter(MouseAdapter listener)
        {
            JButtonDragIt.removeMouseListener(listener);
        }
     
          public void addJButtonDeleteActionActionListener(ActionListener listener)
       {
       JButtonDelete.addActionListener(listener);
   
       } 
 
       public void UpdateActionView()
       {
   this.JLabelVariable1.setEnabled(false);
   this.JTextFieldVariable1.setEnabled(false);
   this.JTextFieldVariable2.setEnabled(false);
   this.JTextFieldVariable1.setEditable(false);
   this.JTextFieldVariable2.setEditable(false);
   this.JTextFieldPassword.setEnabled(false);
   this.JTextFieldPassword.setEditable(false);
   this.JButtonOK.setText("Enable");
   this.JButtonOK.setActionCommand("Edit");
       }
       public void EditActionView()
       {
 this.JLabelVariable1.setEnabled(true);
    this.JTextFieldVariable1.setEnabled(true);
   this.JTextFieldVariable2.setEnabled(true);
   this.JTextFieldVariable1.setEditable(true);
   this.JTextFieldVariable2.setEditable(true);
   this.JTextFieldPassword.setEnabled(true);
   this.JTextFieldPassword.setEditable(true);
   this.JButtonOK.setText("Disable");
   this.JButtonOK.setActionCommand("Update");
  
       }
       @Override
       public void SetVars(String Variable1, String Variable2, String Password, Boolean BoolVal1, Boolean BoolVal2, Boolean LOCKED)
       {
         this.JTextFieldVariable1.setText(Variable1);
         this.JTextFieldVariable2.setText(Variable2);
         this.JTextFieldPassword.setText(Password);
         this.Locked = LOCKED;
         
         if (BoolVal1)
         {
             this.JCheckBoxBoolVal1.setSelected(true);
         }
          if (BoolVal2)
         {
             this.JCheckBoxBoolVal2.setSelected(true);
         }
         if (this.Locked)
         {
          UpdateActionView();
         }
       }
       public void SetIndexes(int bugindex, int actionindex)
       {
  
   
           this.index = actionindex;
           this.bugindex = bugindex;
            String stringactionindex = Integer.toString(this.index+1);
        String stringbugindex = Integer.toString(this.bugindex+1);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
           this.JLabelIndex.setText(bugdashactionindex);
          // this.JTextFieldVariableVARINDEX.setText(bugdashactionindex);
           
           
       }
       public void AddSetVarFocusListeners(SeleniumTestTool Window, Procedure newbug, Action action)
       {
          
       addJTextFieldFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
            
            
           
             Window.ShowPlaceStoredVariableButton(true, newbug.index, action.index, 1 );
            }

            @Override
            public void focusLost(FocusEvent e) {
          
               Window.ShowPlaceStoredVariableButton(false, newbug.index, action.index, 1);
            }
        });
      addJTextField2FocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
            
         
          
               Window.ShowPlaceStoredVariableButton(true, newbug.index, action.index, 2 );
                
            }

            @Override
            public void focusLost(FocusEvent e) {
          
               Window.ShowPlaceStoredVariableButton(false, newbug.index, action.index, 2);
            }
        });    
       }
       public void AddDraggers(Action action, SeleniumTestTool Window, Procedure newbug, ProcedureView newbugview)
       {
    
             this.addJButtonDragItMouseAdapter(new MouseAdapter() {

 
     Insets dragInsets = new Insets(0, 0, 0, 0);
	Dimension snapSize = new Dimension(380, 36);
	 Insets edgeInsets = new Insets(0, 0, 0, 0);
     boolean changeCursor = true;
     boolean autoLayout = true;

     Class destinationClass;
     Component destinationComponent;
     Component destination;
     Component source;

     Point pressed;
     Point location;

	Cursor originalCursor;
	boolean autoscrolls;
	 boolean potentialDrag;
       int places_moved;
       int original_locationY;
       

	public boolean isAutoLayout()
	{
		return autoLayout;
	}

	
	public void setAutoLayout(boolean autoLayout)
	{
		this.autoLayout = autoLayout;
	}

	
	public boolean isChangeCursor()
	{
		return changeCursor;
	}

	public void setChangeCursor(boolean changeCursor)
	{
		this.changeCursor = changeCursor;
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		destinationComponent = newbugview.ActionsViewList.get(action.index).JPanelAction;
	destination = newbugview.ActionsViewList.get(action.index).JButtonDragIt;
            source = newbugview.ActionsViewList.get(action.index).JButtonDragIt;
		int width  = source.getSize().width  - dragInsets.left - dragInsets.right;
		int height = source.getSize().height - dragInsets.top - dragInsets.bottom;
		Rectangle r = new Rectangle(dragInsets.left, dragInsets.top, width, height);

		if (r.contains(e.getPoint()))
			setupForDragging(e);
           
	}

	private void setupForDragging(MouseEvent e)
	{
            	destinationComponent = newbugview.ActionsViewList.get(action.index).JPanelAction;
	destination = newbugview.ActionsViewList.get(action.index).JPanelAction;
            source = newbugview.ActionsViewList.get(action.index).JButtonDragIt;

newbugview.ActionsViewList.get(action.index).JButtonDragIt.addMouseMotionListener(this);

                potentialDrag = true;

		//  Determine the component that will ultimately be moved

		if (destinationComponent != null)
		{
			destination = destinationComponent;
		}
		else if (destinationClass == null)
		{
			destination = source;
		}
		else  //  forward events to destination component
		{
//			destination = SwingUtilities.getAncestorOfClass(destinationClass, source);
		}

		pressed = e.getLocationOnScreen();
		location = destination.getLocation();

		if (changeCursor)
		{
			originalCursor = source.getCursor();
			source.setCursor( Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR) );
		}

		
		if (destination instanceof JComponent)
		{
			JComponent jc = (JComponent)destination;
			autoscrolls = jc.getAutoscrolls();
			jc.setAutoscrolls( false );
		}
                original_locationY = destination.getY();
	}


	@Override
	public void mouseDragged(MouseEvent e)
	{
          //      newbugview.ActionScrollPanel.setAutoscrolls(false);
          //      newbugview.ActionScrollPane.setAutoscrolls(false);
                newbugview.ActionsViewList.get(action.index).JPanelAction.setAutoscrolls(true);
          //      newbugview.ActionsViewList.get(action.index).JButtonDragIt.setAutoscrolls(false);
		Point dragged = e.getLocationOnScreen();
		int dragX = getDragDistance(dragged.x, pressed.x, snapSize.width);
		int dragY = getDragDistance(dragged.y, pressed.y, snapSize.height);
            
		int locationX = location.x + dragX;
		int locationY = location.y + dragY;
		while (locationX < edgeInsets.left)
     	        locationX += snapSize.width;

		while (locationY < edgeInsets.top)
			locationY += snapSize.height;

		Dimension d = getBoundingSize( newbugview.ActionsViewList.get(action.index).JPanelAction );

		while (locationX + destination.getSize().width + edgeInsets.right > d.width)
			locationX -= snapSize.width;

		while (locationY + destination.getSize().height + edgeInsets.bottom > d.height)
			locationY -= snapSize.height;
                
		 newbugview.ActionsViewList.get(action.index).JPanelAction.setLocation(locationX, locationY);
          
                int snapped_locationY = newbugview.ActionsViewList.get(action.index).JPanelAction.getY();

                places_moved = (original_locationY - snapped_locationY)/36;
     
                if (places_moved==1)
                {

                     Window.MoveAction(newbug, newbugview, action.index, -1);
                    
                     
                     original_locationY = snapped_locationY;
                     
  Window.UpdateScrollPane(newbugview);
 // UpdateScrollPane();
  scroll (newbugview.ActionsViewList.get(action.index).JPanelAction, "up");  
     

                }
              
                if (places_moved==-1)
                {
                         
                   Window.MoveAction(newbug, newbugview, action.index, 1); 
                 
          original_locationY = snapped_locationY;
  
  Window.UpdateScrollPane(newbugview);
 // UpdateScrollPane(); 
 scroll (newbugview.ActionsViewList.get(action.index).JPanelAction, "down");  
 
 
     }
  //   Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
  //     newbugview.ActionsViewList.get(action.index).JPanelAction.scrollRectToVisible(r);
   
	}
 
     public void scroll(JComponent c, String dir)
{
    Rectangle visible = c.getVisibleRect();
    Rectangle bounds = c.getBounds();
if ("up".equals(dir))
{
     visible.y = 0;
}
else
{
     visible.y = bounds.height - visible.height;  
}
 
//why does this work??
    c.scrollRectToVisible(visible);
    
}    

	private int getDragDistance(int larger, int smaller, int snapSize)
	{
		int halfway = snapSize / 2;
		int drag = larger - smaller;
		drag += (drag < 0) ? -halfway : halfway;
		drag = (drag / snapSize) * snapSize;

		return drag;
	}


	private Dimension getBoundingSize(Component source)
	{
		if (source instanceof Window)
		{
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Rectangle bounds = env.getMaximumWindowBounds();
			return new Dimension(bounds.width, bounds.height);
		}
		else
		{
              
			return source.getParent().getSize();
		}
	}


	@Override
	public void mouseReleased(MouseEvent e)
	{
              
if (!potentialDrag) return;

		source.removeMouseMotionListener( this );
		potentialDrag = false;

		if (changeCursor)
			source.setCursor( originalCursor );

		if (destination instanceof JComponent)
		{
			((JComponent)destination).setAutoscrolls( autoscrolls );
		}



		if (autoLayout)
		{
			if (destination instanceof JComponent)
			{
				((JComponent)destination).revalidate();
			}
			else
			{
				destination.validate();
			}
		}
 
	}



   });
       }

 @Override
   public void AddListeners(Action action, SeleniumTestTool Window, Procedure newbug, ProcedureView newbugview)
   {
   
    
    AddDraggers(action, Window, newbug, newbugview);
                        this.addJButtonDeleteActionActionListener((ActionEvent evt) -> {
                          Window.DeleteAction(newbug, newbugview, action.index);
                            Window.UpdateScrollPane(newbugview);
                     
   
   });
              
                        
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


  public void AddLoopListeners(Action action, SeleniumTestTool Window, Procedure newbug, ProcedureView newbugview)
   {
   if (Window.hasStoredVar)
   {
   AddSetVarFocusListeners(Window, newbug, action);
   }
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