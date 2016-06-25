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
public class VarMaster {
   HashMap<String, String> VarHashMap;
   
    VarMaster(String varname, String varvalue)
            
    {
        VarHashMap.put(varname, varvalue);
    }
}
