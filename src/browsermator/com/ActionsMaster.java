/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.util.HashMap;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author pcalkins
 */

public class ActionsMaster {
    HashMap<String, BMAction> ActionHashMap;
    HashMap<String, BMAction> PassFailActionHashMap;
    HashMap<String, ActionView> ActionViewHashMap;
    HashMap<String, ActionView> PassFailActionViewHashMap;   
    WebDriverWait dummywait;
    ActionsMaster()
    {
        ActionHashMap = new <String, BMAction>HashMap();
  
    ActionViewHashMap = new <String, ActionView>HashMap();
      PassFailActionHashMap = new <String, BMAction>HashMap(); 
    
    PassFailActionViewHashMap = new <String, ActionView>HashMap();
    ActionViewHashMap.put("Back Button", new BackActionView());
    ActionViewHashMap.put("Click at Button Text", new ClickAtButtonTextActionView());
    ActionViewHashMap.put("Click at HREF", new ClickAtHREFActionView());
    ActionViewHashMap.put("Click at ID", new ClickAtIDActionView());
    ActionViewHashMap.put("Click at Image SRC", new ClickAtImageSRCActionView());
    ActionViewHashMap.put("Click at Link Text", new ClickAtLinkTextActionView());
    ActionViewHashMap.put("Click at Name", new ClickAtNameActionView());
    ActionViewHashMap.put("Click at XPATH", new ClickXPATHActionView());
    ActionViewHashMap.put("Close Current Tab or Window", new CloseCurrentTabOrWindowActionView());
    ActionViewHashMap.put("Down Arrow Key", new DownArrowKeyActionView());
    ActionViewHashMap.put("Drag From ID to ID", new DragAndDropActionView());
    ActionViewHashMap.put("Drag From XPATH Distance X and Y Pixels", new DragAndDropByActionView());
    ActionViewHashMap.put("Enter Key", new EnterKeyActionView());
    ActionViewHashMap.put("Escape Key", new EscapeKeyActionView());
    ActionViewHashMap.put("Execute Javascript", new ExecuteJavascriptActionView());
    ActionViewHashMap.put("Forward Action", new ForwardActionView());
    ActionViewHashMap.put("Go to URL", new GoActionView());
    ActionViewHashMap.put("Left Arrow Key", new LeftArrowKeyActionView());
    ActionViewHashMap.put("Next Tab", new NextTabActionView());
    ActionViewHashMap.put("Open New Tab", new OpenNewTabActionView());
    ActionViewHashMap.put("Pause", new PauseActionView());
    ActionViewHashMap.put("Pause with Continue Button", new PauseContinueActionView());
    ActionViewHashMap.put("Right Arrow Key", new RightArrowKeyActionView());
    ActionViewHashMap.put("Right-Click", new RightClickActionView());
    ActionViewHashMap.put("Set Cookie", new SetCookieActionView());
    
    ActionViewHashMap.put("Store Link as Variable by XPATH", new StoreLinkAsVarByXPATHActionView());
    ActionViewHashMap.put("Store Links as URL List by XPATH", new StoreLinksAsArrayByXPATHActionView());
    ActionViewHashMap.put("Switch Driver", new SwitchDriverActionView());
    ActionViewHashMap.put("Switch To Frame", new SwitchToFrameActionView());
    ActionViewHashMap.put("Switch To Tab or Window", new SwitchToTabOrWindowActionView());
    // legacy support for Type at ID
    ActionViewHashMap.put("Type at ID", new TypeAtIDActionView());
    ActionViewHashMap.put("Type at Input ID", new TypeAtIDActionView());
    ActionViewHashMap.put("Type at Input Name", new TypeAtInputNameActionView());
    ActionViewHashMap.put("Type at XPATH", new TypeAtXPATHActionView());
    ActionViewHashMap.put("Type Password at ID", new TypePasswordAtIDActionView());
    ActionViewHashMap.put("Type Password at Input Name", new TypePasswordAtInputNameActionView());
    ActionViewHashMap.put("Type Password at XPATH", new TypePasswordAtXPATHActionView());
    ActionViewHashMap.put("Up Arrow Key", new UpArrowKeyActionView());
    
  ActionHashMap.put("Back Button", new BackAction());
  ActionHashMap.put("Click at Button Text", new ClickAtButtonTextAction("", false, false));
  ActionHashMap.put("Click at HREF", new ClickAtHREFAction("", false, false));
  ActionHashMap.put("Click at ID", new ClickAtIDAction("", false, false));
  ActionHashMap.put("Click at Image SRC", new ClickAtImageSRCAction("", false, false));
  ActionHashMap.put("Click at Link Text", new ClickAtLinkTextAction("", false, false));
  ActionHashMap.put("Click at Name", new ClickAtNameAction("", false, false));
  ActionHashMap.put("Click at XPATH", new ClickXPATHAction("", false, false));
  ActionHashMap.put("Close Current Tab or Window", new CloseCurrentTabOrWindowAction());
  ActionHashMap.put("Down Arrow Key", new DownArrowKeyAction());
  ActionHashMap.put("Drag From ID to ID", new DragAndDropAction("", ""));
  ActionHashMap.put("Drag From XPATH Distance X and Y Pixels", new DragAndDropByAction("", ""));
  ActionHashMap.put("Enter Key", new EnterKeyAction());
  ActionHashMap.put("Escape Key", new EscapeKeyAction());
  ActionHashMap.put("Execute Javascript", new ExecuteJavascriptAction(""));
  ActionHashMap.put("Forward Action", new ForwardAction());
  ActionHashMap.put("Go to URL", new GoAction(""));
  ActionHashMap.put("Left Arrow Key", new LeftArrowKeyAction());
  ActionHashMap.put("Next Tab", new NextTabAction());
  ActionHashMap.put("Open New Tab", new OpenNewTabAction());
  ActionHashMap.put("Pause", new PauseAction("", ""));
  ActionHashMap.put("Pause with Continue Button", new PauseContinueAction());
  ActionHashMap.put("Right Arrow Key", new RightArrowKeyAction());
  ActionHashMap.put("Right-Click", new RightClickAction());
  ActionHashMap.put("Set Cookie", new SetCookieAction("", ""));
  ActionHashMap.put("Store Link as Variable by XPATH", new StoreLinkAsVarByXPATHAction("", ""));
  ActionHashMap.put("Store Links as URL List by XPATH", new StoreLinksAsArrayByXPATHAction("", ""));
  ActionHashMap.put("Switch Driver", new SwitchDriverAction());
  ActionHashMap.put("Switch To Frame", new SwitchToFrameAction(""));
  ActionHashMap.put("Switch To Tab or Window", new SwitchToTabOrWindowAction(""));
  //legacy support for Type at ID
  ActionHashMap.put("Type at ID", new TypeAtIDAction("", "", false));
  ActionHashMap.put("Type at Input ID", new TypeAtIDAction("", "", false));
  ActionHashMap.put("Type at Input Name", new TypeAtInputNameAction("","", false));
  ActionHashMap.put("Type at XPATH", new TypeAtXPATHAction("", "", false));
  ActionHashMap.put("Type Password at ID", new TypePasswordAtIDAction("", "", false));
  ActionHashMap.put("Type Password at Input Name", new TypePasswordAtInputNameAction("", "", false));
  ActionHashMap.put("Type Password at XPATH", new TypePasswordAtXPATHAction("", "", false));
  ActionHashMap.put("Up Arrow Key", new UpArrowKeyAction());
  
   PassFailActionViewHashMap.put("Yes/No Prompt", new YesNoPromptPassFailActionView());
   PassFailActionViewHashMap.put("Find Text", new FindTextPassFailActionView());
   PassFailActionViewHashMap.put("Find HREF", new FindHREFPassFailActionView());
   PassFailActionViewHashMap.put("Find IFrame SRC", new FindIFrameSRCPassFailActionView());
   PassFailActionViewHashMap.put("Find Image SRC", new FindImageSRCPassFailActionView());

   PassFailActionViewHashMap.put("Find Page Title", new FindPAGETITLEPassFailActionView());
   PassFailActionViewHashMap.put("Find XPATH", new FindXPATHPassFailActionView());
   PassFailActionViewHashMap.put("Do NOT Find Text", new NOTFindTextPassFailActionView());
   PassFailActionViewHashMap.put("Do NOT Find HREF", new NOTFindHREFPassFailActionView());
   PassFailActionViewHashMap.put("Do NOT Find IFrame SRC", new NOTFindIFrameSRCPassFailActionView());
 

 
   PassFailActionViewHashMap.put("Do NOT Find Image SRC", new NOTFindImageSRCPassFailActionView());
   PassFailActionViewHashMap.put("Do NOT Find Page Title", new NOTFindPAGETITLEPassFailActionView());
   PassFailActionViewHashMap.put("Do NOT Find XPATH", new NOTFindXPATHPassFailActionView());
   
 PassFailActionHashMap.put("Yes/No Prompt", new YesNoPromptPassFailAction(""));
 PassFailActionHashMap.put("Find Text", new FindTextPassFailAction("", false));
 PassFailActionHashMap.put("Find HREF", new FindHREFPassFailAction("", false));
 PassFailActionHashMap.put("Find IFrame SRC", new FindIFrameSRCPassFailAction("", false));
 PassFailActionHashMap.put("Find Image SRC", new FindImageSRCPassFailAction("", false));
 PassFailActionHashMap.put("Find Page Title", new FindPAGETITLEPassFailAction("", false));
 PassFailActionHashMap.put("Find XPATH", new FindXPATHPassFailAction("", false));
 PassFailActionHashMap.put("Do NOT Find Text", new FindTextPassFailAction("", true));
 PassFailActionHashMap.put("Do NOT Find HREF", new FindHREFPassFailAction("", true));
 PassFailActionHashMap.put("Do NOT Find IFrame SRC", new FindIFrameSRCPassFailAction("", true));
 PassFailActionHashMap.put("Do NOT Find Image SRC", new FindImageSRCPassFailAction("", true));
 PassFailActionHashMap.put("Do NOT Find Page Title", new FindPAGETITLEPassFailAction("", true));
 PassFailActionHashMap.put("Do NOT Find XPATH", new FindXPATHPassFailAction("", true));
  }
}