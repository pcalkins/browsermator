/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author pcalkins
 */
public class SendReceiveData {
  String charset = "UTF-8";
String param = "15000";
String UID = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
String CRLF = "\r\n"; // Line separator required by multipart/form-data.
UserParamHash ParamsHashMap;  
String url;
//  String rootURL = "http://localhost";
 String rootURL = "https://www.browsermator.com";

    SendReceiveData(String in_url, UserParamHash userparams)
    {
             url = in_url;

 ParamsHashMap = userparams;

     
    }
   
    public String SendParams() throws MalformedURLException, IOException
    {
            String ret_val="<HTML><HEAD></HEAD><BODY>DEFAULT</BODY></HTML>";
                  
String charset = "UTF-8";
String param = "15000";

String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
String CRLF = "\r\n"; // Line separator required by multipart/form-data.

URLConnection connection = new URL(url).openConnection();
connection.setDoOutput(true);
connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);


    OutputStream output = connection.getOutputStream();
    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);

    // Send normal param.
    
     if (ParamsHashMap.UserParamHashMap.containsKey("name"))
           {
               param = ParamsHashMap.UserParamHashMap.get("name");
               
        writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"loginName\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
    writer.append(CRLF).append(param).append(CRLF).flush();        
           }
        if (ParamsHashMap.UserParamHashMap.containsKey("password"))
           {
               param = ParamsHashMap.UserParamHashMap.get("password");
               
        writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"loginPassword\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
    writer.append(CRLF).append(param).append(CRLF).flush();        
           }
             if (ParamsHashMap.UserParamHashMap.containsKey("email"))
           {
               param = ParamsHashMap.UserParamHashMap.get("email");
               
        writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"loginEmail\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
    writer.append(CRLF).append(param).append(CRLF).flush();        
           }

    // End of multipart/form-data.
    writer.append("--" + boundary + "--").append(CRLF).flush();


// Request is lazily fired whenever you need to obtain information about response.
//int responseCode = ((HttpURLConnection) connection).getResponseCode();
// String responsetext = ((HttpURLConnection) connection).getResponseMessage();
// System.out.println(responseCode); // Should be 200 
// ShowHTMLWindow(responsetext);
  
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                   ((HttpURLConnection) connection).getInputStream()));
        String inputLine;
        ret_val="";
        while ((inputLine = in.readLine()) != null) 
          ret_val += inputLine;
        in.close();
         
 
   
        return ret_val;
   
    }
      public File UpdateFile(String file_id, File file) throws MalformedURLException, IOException
    {
     String ret_val="<HTML><HEAD></HEAD><BODY>DEFAULT</BODY></HTML>";
     File clean_file = CleanFile(file);
            
                
String charset = "UTF-8";
String param = "15000";
String isUpdate = "true";
String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
String CRLF = "\r\n"; // Line separator required by multipart/form-data.
URLConnection connection = new URL(rootURL + "/submit_file.php").openConnection();
connection.setDoOutput(true);
connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);


    OutputStream output = connection.getOutputStream();
    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);

    // Send normal param.
      writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"isUpdate\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
    writer.append(CRLF).append(isUpdate).append(CRLF).flush(); 
    
       writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"file_id\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
    writer.append(CRLF).append(file_id).append(CRLF).flush(); 
    
    
    
    
     if (ParamsHashMap.UserParamHashMap.containsKey("isUpdate"))
     {
         param = ParamsHashMap.UserParamHashMap.get("isUpdate");
             writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"isUpdate\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
    writer.append(CRLF).append(param).append(CRLF).flush();        
     }
     if (ParamsHashMap.UserParamHashMap.containsKey("name"))
           {
               param = ParamsHashMap.UserParamHashMap.get("name");
               
        writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"loginName\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
    writer.append(CRLF).append(param).append(CRLF).flush();        
           }
        if (ParamsHashMap.UserParamHashMap.containsKey("password"))
           {
               param = ParamsHashMap.UserParamHashMap.get("password");
               
        writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"loginPassword\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
    writer.append(CRLF).append(param).append(CRLF).flush();        
           }
             if (ParamsHashMap.UserParamHashMap.containsKey("email"))
           {
               param = ParamsHashMap.UserParamHashMap.get("email");
               
        writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"loginEmail\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
    writer.append(CRLF).append(param).append(CRLF).flush();        
           }

    // Send text file.
    writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"browsermationFile\"; filename=\"" + clean_file.getName() + "\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
    writer.append(CRLF).flush();
    Files.copy(clean_file.toPath(), output);
    output.flush(); // Important before continuing with writer!
    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

    // Send binary file.
//    writer.append("--" + boundary).append(CRLF);
//    writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
//    writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
//    writer.append("Content-Transfer-Encoding: binary").append(CRLF);
//    writer.append(CRLF).flush();
//    Files.copy(binaryFile.toPath(), output);
//    output.flush(); // Important before continuing with writer!
//    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

    // End of multipart/form-data.
    writer.append("--" + boundary + "--").append(CRLF).flush();


// Request is lazily fired whenever you need to obtain information about response.
//int responseCode = ((HttpURLConnection) connection).getResponseCode();
// String responsetext = ((HttpURLConnection) connection).getResponseMessage();
// System.out.println(responseCode); // Should be 200 
// ShowHTMLWindow(responsetext);
  
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                   ((HttpURLConnection) connection).getInputStream()));
        String inputLine;
        ret_val="";
        while ((inputLine = in.readLine()) != null) 
          ret_val += inputLine;
        in.close();
         
 
   
        return clean_file;
   
     
    }
    public String UploadFile(String user_filename, String description, File file, String PrivateVal) throws MalformedURLException, IOException
    {
        String ret_val="<HTML><HEAD></HEAD><BODY>DEFAULT</BODY></HTML>";
                
String charset = "UTF-8";
String param = "15000";

String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
String CRLF = "\r\n"; // Line separator required by multipart/form-data.
URLConnection connection = new URL(rootURL + "/submit_file.php").openConnection();
connection.setDoOutput(true);
connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);


    OutputStream output = connection.getOutputStream();
    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);

    // Send normal param.
       writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"user_filename\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
    writer.append(CRLF).append(user_filename).append(CRLF).flush(); 
    
    writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"description\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
    writer.append(CRLF).append(description).append(CRLF).flush(); 
    
        writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"private_val\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
    writer.append(CRLF).append(PrivateVal).append(CRLF).flush(); 
     if (ParamsHashMap.UserParamHashMap.containsKey("isUpdate"))
     {
         param = ParamsHashMap.UserParamHashMap.get("isUpdate");
             writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"isUpdate\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
    writer.append(CRLF).append(param).append(CRLF).flush();        
     }
     if (ParamsHashMap.UserParamHashMap.containsKey("name"))
           {
               param = ParamsHashMap.UserParamHashMap.get("name");
               
        writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"loginName\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
    writer.append(CRLF).append(param).append(CRLF).flush();        
           }
        if (ParamsHashMap.UserParamHashMap.containsKey("password"))
           {
               param = ParamsHashMap.UserParamHashMap.get("password");
               
        writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"loginPassword\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
    writer.append(CRLF).append(param).append(CRLF).flush();        
           }
             if (ParamsHashMap.UserParamHashMap.containsKey("email"))
           {
               param = ParamsHashMap.UserParamHashMap.get("email");
               
        writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"loginEmail\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
    writer.append(CRLF).append(param).append(CRLF).flush();        
           }

    // Send text file.
    writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"browsermationFile\"; filename=\"" + file.getName() + "\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
    writer.append(CRLF).flush();
    Files.copy(file.toPath(), output);
    output.flush(); // Important before continuing with writer!
    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

    // Send binary file.
//    writer.append("--" + boundary).append(CRLF);
//    writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
//    writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
//    writer.append("Content-Transfer-Encoding: binary").append(CRLF);
//    writer.append(CRLF).flush();
//    Files.copy(binaryFile.toPath(), output);
//    output.flush(); // Important before continuing with writer!
//    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

    // End of multipart/form-data.
    writer.append("--" + boundary + "--").append(CRLF).flush();


// Request is lazily fired whenever you need to obtain information about response.
//int responseCode = ((HttpURLConnection) connection).getResponseCode();
// String responsetext = ((HttpURLConnection) connection).getResponseMessage();
// System.out.println(responseCode); // Should be 200 
// ShowHTMLWindow(responsetext);
  
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                   ((HttpURLConnection) connection).getInputStream()));
        String inputLine;
        ret_val="";
        while ((inputLine = in.readLine()) != null) 
          ret_val += inputLine;
        in.close();
         
 
   
        return ret_val;
        
    }
  public File CleanFile(File file_to_clean)
  {
    Document doc=null;
    String filename_read = "";
try
{

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();
String file_path = file_to_clean.getAbsolutePath();

doc = builder.parse(file_path);

 
}
catch (ParserConfigurationException | SAXException | IOException e)
{
    System.out.println("DocumentBuilder error:" + e.toString());
   
}
    
finally 
{
    NamedNodeMap NewAttributes = doc.getElementsByTagName("BrowserMatorWindow").item(0).getAttributes(); 
   
   filename_read = NewAttributes.getNamedItem("Filename").getNodeValue();
    NodeList FileSettingsNode = doc.getElementsByTagName("FileSettings");
 
  String thisSettingsNodeName;
  String thisSettingsNodeValue;
  String stShowReport="false";
  String stEmailReport = "false";
  String stEmailReportFail = "false";
  String stExitAfter = "false";
  String SMTPHostname = "";
  String EmailLoginName = "";
  String stPromptToClose = "false";
  String TargetBrowser = "Firefox";
  String WaitTime = "3";
  String Sessions = "1";
  String OSType = "Windows32";
  String EmailPassword = "";
  String unepassword = "";
  String EmailTo = "";
  String EmailFrom = "";
  String EmailSubject = "";
  
try
{
    NodeList SettingsNodes = FileSettingsNode.item(0).getChildNodes();


    for (int k = 0; k<SettingsNodes.getLength(); k++)
    {
   thisSettingsNodeName = SettingsNodes.item(k).getNodeName();
   thisSettingsNodeValue = SettingsNodes.item(k).getTextContent();
    switch(thisSettingsNodeName)
    {
         case "SMTPHostname":
 SettingsNodes.item(k).setTextContent("");
            break;  

        case "EmailLoginName":
SettingsNodes.item(k).setTextContent("");
            break;  
       case "EmailPassword":
 EmailPassword = thisSettingsNodeValue;
    SettingsNodes.item(k).setTextContent("");
    break;  
      
       case "EmailTo":
  SettingsNodes.item(k).setTextContent("");
            break;    
      
       case "EmailFrom":
   SettingsNodes.item(k).setTextContent("");
            break;   
       
       case "EmailSubject":
   SettingsNodes.item(k).setTextContent("");
            break; 
    }

    }
    }
catch (Exception e)
        {
            System.out.println("Error loading filesettings: " + e.toString());
          
        }
try
{
   NodeList ProcedureList = doc.getElementsByTagName("Procedure");
   
for (int i = 0; i < ProcedureList.getLength(); ++i)
{
    
    Element Procedure = (Element) ProcedureList.item(i);

    NodeList ActionsList = Procedure.getElementsByTagName("Action");
  
    for (int j = 0; j <ActionsList.getLength(); j++)
    {
   Element Action = (Element) ActionsList.item(j);
   NodeList ActionNodes = Action.getChildNodes();
   String thisActionNodeName = "none";
   String thisActionNodeValue = "none";
   
   String Variable1 = "";
   String Variable2 = "";
   String LOCKED = "false";
   String BoolVal1 = "false";
    String TimeOfTest;
    String ActionType = "none";
    String ActionIndex;
    String ActionPass;
    String Password = "";
    
    
   Boolean RealBoolVal1 = false;
    Boolean isPasswordFlag = false;
    for (int k = 0; k<ActionNodes.getLength(); k++)
    {
   thisActionNodeName = ActionNodes.item(k).getNodeName();
   thisActionNodeValue = ActionNodes.item(k).getTextContent();
  
    switch(thisActionNodeName)
    {
        case "Pass":
            ActionPass = thisActionNodeValue;
            break;
        case "ActionIndex":
            ActionIndex = thisActionNodeValue;
            break;
        case "Type":
            ActionType = thisActionNodeValue;
              if (ActionType.contains("Password"))
   {
   isPasswordFlag = true;
       }  
            break;
        case "Variable1":
            Variable1 = thisActionNodeValue;
            break;
        case "Variable2":
            if (isPasswordFlag)
            {
             ActionNodes.item(k).setTextContent(""); 
             isPasswordFlag = false;
            }
            Variable2 = thisActionNodeValue;
            break;
        case "BoolVal1":
            BoolVal1 = thisActionNodeValue;
            if (BoolVal1.equals("true"))
                    {
                    RealBoolVal1 = true;
                    }
           break;
        case "LOCKED":
            LOCKED = thisActionNodeValue;
            break;
       

        case "TimeOfTest":
            TimeOfTest = thisActionNodeValue;
            break;
    }  
             
    } 
    
 
  
     
   }
 
        }   

    }
    
catch (Exception e)
        {
            System.out.println("Error loading procedure: " + e.toString());
          
        }

}
  File file_to_upload = DocToFile(doc, filename_read);
return file_to_upload;   

}

public File DocToFile(Document doc, String filename)
{
   File file = null;
      String[] left_side_of_dot =filename.split("\\.");
   String[] noslashes_name = left_side_of_dot[0].split("\\\\");
         int lastbit_index = noslashes_name.length-1;
         
   String noext_filename = noslashes_name[lastbit_index];
   
   try
   {
file= File.createTempFile(noext_filename, ".browsermation"); 
   }
catch (Exception ex)
{
    System.out.println("Exception saving doctofile temp: " + ex.toString());
}
try
   {
   TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(file);
                result.setSystemId(java.net.URLDecoder.decode(result.getSystemId(), "UTF-8"));
                	transformer.transform(source, result);

		System.out.println("File saved!");

	  } catch (Exception ex) {
		System.out.println("Exception doctofile:"+ ex.toString());
	  }
   return file;
   
}
}
