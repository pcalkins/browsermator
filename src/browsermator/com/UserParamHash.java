/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.util.HashMap;
/**
 *
 * @author pcalkins
 */
public class UserParamHash {
  HashMap<String, String> UserParamHashMap;  

    
  UserParamHash(String name, String email, String password)
  {
          
       UserParamHashMap = new <String, String>HashMap();
       UserParamHashMap.put("name", name);
       UserParamHashMap.put("email", email);
       UserParamHashMap.put("password", password);
  
      
      
       
  }
}
