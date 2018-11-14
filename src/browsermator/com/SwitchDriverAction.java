package browsermator.com;


import java.io.File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class SwitchDriverAction extends Action 
{
  String WEBDRIVERSDIR;
  String BrowsermatorAppFolder;

   SwitchDriverAction() 
    {
    
    BrowsermatorAppFolder =   System.getProperty("user.home")+File.separator+"BrowsermatorAppFolder"+File.separator;
   WEBDRIVERSDIR = BrowsermatorAppFolder + "Webdrivers" + File.separator;
     this.Type = "Switch Driver";   
    this.Variable1 = "Chrome";
}
   @Override
   public void SetGuts()
   {
      this.Guts = "    boolean closecaught = false;\n" +
"   \n" +
"\n" +
" try\n" +
" {\n" +
"   if (RunThread.driver!=null) {  RunThread.driver.close(); }\n" +
" }\n" +
" catch (Exception e)\n" +
" {\n" +
"     closecaught = true;\n" +
"     System.out.println(e.toString());\n" +
"     try {\n" +
"                RunThread.driver.quit();\n" +
"            }\n" +
"            catch (Exception exce)\n" +
"            {\n" +
"               \n" +
"                System.out.println(\"Exception quitting\" + exce.toString());\n" +
"            }\n" +
" }\n" +
" if (closecaught)\n" +
" {\n" +
" \n" +
" }\n" +
" else\n" +
" {\n" +
"     try\n" +
" {\n" +
"   RunThread.driver.quit();\n" +
" }\n" +
" catch (Exception ex)\n" +
" {\n" +
"     // don't worry it should close\n" +
" }\n" +
"  \n" +
" } \n" +
"\n" +
"  File thisDriver =  new File( WEBDRIVERSDIR+\"geckodriver-win32\"+File.separator+\"geckodriver.exe\");\n" +
"  switch (this.Variable1)\n" +
"  {\n" +
"      \n" +
"     case \"Firefox-Marionette\":\n" +
"     // legacy file support\n" +
"         \n" +
"         if (\"Windows\".equals(OSType))\n" +
"     {\n" +
"           thisDriver =  new File(WEBDRIVERSDIR+\"geckodriver-win32\"+File.separator+\"geckodriver.exe\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.gecko.driver\", thisDriver.getAbsolutePath());  \n" +
"    //   System.setProperty(\"webdriver.gecko.driver\", \"lib\"+File.separator+\"geckodriver-win32\"+File.separator+\"geckodriver.exe\");\n" +
"     }   \n" +
"     if (\"Windows32\".equals(OSType))\n" +
"     {\n" +
"         thisDriver =  new File(WEBDRIVERSDIR+\"geckodriver-win32\"+File.separator+\"geckodriver.exe\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.gecko.driver\", thisDriver.getAbsolutePath());   \n" +
"      // System.setProperty(\"webdriver.gecko.driver\", \"lib\"+File.separator+\"geckodriver-win32\"+File.separator+\"geckodriver.exe\");\n" +
"     }\n" +
"     if (\"Windows64\".equals(OSType))\n" +
"     {\n" +
"         thisDriver =  new File(WEBDRIVERSDIR+\"geckodriver-win64\"+File.separator+\"geckodriver.exe\");\n" +
"         setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.gecko.driver\", thisDriver.getAbsolutePath());  \n" +
"    //   System.setProperty(\"webdriver.gecko.driver\", \"lib\"+File.separator+\"geckodriver-win64\"+File.separator+\"geckodriver.exe\");\n" +
"     }\n" +
"     if (\"Mac\".equals(OSType))\n" +
"     {\n" +
"            thisDriver =  new File(WEBDRIVERSDIR+\"geckodriver-osx\"+File.separator+\"geckodriver\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.gecko.driver\", thisDriver.getAbsolutePath());     \n" +
"    //  System.setProperty(\"webdriver.gecko.driver\", \"lib\"+File.separator+\"geckodriver-osx\"+File.separator+\"geckodriver\");\n" +
"     }\n" +
"     if (\"Linux-32\".equals(OSType))\n" +
"     {\n" +
"              thisDriver =  new File(WEBDRIVERSDIR+\"geckodriver-linux32\"+File.separator+\"geckodriver\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.gecko.driver\", thisDriver.getAbsolutePath());  \n" +
"    //  System.setProperty(\"webdriver.gecko.driver\", \"lib\"+File.separator+\"geckodriver-linux32\"+File.separator+\"geckodriver\");\n" +
"     }\n" +
"     if (\"Linux-64\".equals(OSType))\n" +
"     {\n" +
"                thisDriver =  new File(WEBDRIVERSDIR+\"geckodriver-linux64\"+File.separator+\"geckodriver\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.gecko.driver\", thisDriver.getAbsolutePath());  \n" +
"   //   System.setProperty(\"webdriver.gecko.driver\", \"lib\"+File.separator+\"geckodriver-linux64\"+File.separator+\"geckodriver\");\n" +
"     }\n" +
"   \n" +
"    if (firefox_path!=null) {\n" +
"        System.setProperty(\"webdriver.firefox.bin\", firefox_path);\n" +
"    }\n" +
"\n" +
"    try\n" +
"    {\n" +
"\n" +
"// FirefoxProfile profile = new FirefoxProfile();\n" +
"\n" +
" //DesiredCapabilities cap = DesiredCapabilities.firefox();\n" +
"   //     cap.setJavascriptEnabled(true);\n" +
"   //     cap.setCapability(\"marionette\", false);\n" +
"        \n" +
"   //     profile.setPreference(\"dom.max_script_run_time\", 1);\n" +
"        RunThread.driver = new FirefoxDriver();\n" +
"    \n" +
"\n" +
"    //  driver =  new MarionetteDriver();\n" +
"    }\n" +
"    catch (Exception ex)\n" +
"    {\n" +
"        System.out.println (\"Exception launching Marionette driver... possibly XP or missing msvcr110.dll: \" + ex.toString());\n" +
"     \n" +
"         Prompter fallbackprompt = new Prompter (\"Driver Error\", \"Could not launch Marionette driver:\" + ex.toString(), false,0,0);\n" +
"     \n" +
"         \n" +
"          \n" +
"    }\n" +
"      \n" +
"     break;\n" +
"            \n" +
"    case \"Firefox\":\n" +
"   \n" +
"     if (\"Windows\".equals(OSType))\n" +
"     {\n" +
"        \n" +
"        thisDriver =  new File(WEBDRIVERSDIR+\"geckodriver-win32\"+File.separator+\"geckodriver.exe\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.gecko.driver\", thisDriver.getAbsolutePath());  \n" +
"     }\n" +
"     if (\"Windows32\".equals(OSType))\n" +
"     {\n" +
"         thisDriver =  new File(WEBDRIVERSDIR+\"geckodriver-win32\"+File.separator+\"geckodriver.exe\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.gecko.driver\", thisDriver.getAbsolutePath());  \n" +
"     }\n" +
"     if (\"Windows64\".equals(OSType))\n" +
"     {\n" +
"           thisDriver =  new File(WEBDRIVERSDIR+\"geckodriver-win64\"+File.separator+\"geckodriver.exe\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.gecko.driver\", thisDriver.getAbsolutePath());  \n" +
"   //    System.setProperty(\"webdriver.gecko.driver\", BMPATH+File.separator+\"lib\"+File.separator+\"geckodriver-win64\"+File.separator+\"geckodriver.exe\");\n" +
"     }\n" +
"     if (\"Mac\".equals(OSType))\n" +
"     {\n" +
"            thisDriver =  new File(WEBDRIVERSDIR+\"geckodriver-osx\"+File.separator+\"geckodriver\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.gecko.driver\", thisDriver.getAbsolutePath());  \n" +
"  //    System.setProperty(\"webdriver.gecko.driver\", BMPATH+File.separator+\"lib\"+File.separator+\"geckodriver-osx\"+File.separator+\"geckodriver\");\n" +
"     }\n" +
"     if (\"Linux-32\".equals(OSType))\n" +
"     {\n" +
"            thisDriver =  new File(WEBDRIVERSDIR+\"geckodriver-linux32\"+File.separator+\"geckodriver\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.gecko.driver\", thisDriver.getAbsolutePath());  \n" +
"  //    System.setProperty(\"webdriver.gecko.driver\",BMPATH+File.separator+ \"lib\"+File.separator+\"geckodriver-linux32\"+File.separator+\"geckodriver\");\n" +
"     }\n" +
"     if (\"Linux-64\".equals(OSType))\n" +
"     {\n" +
"           thisDriver =  new File(WEBDRIVERSDIR+\"geckodriver-linux64\"+File.separator+\"geckodriver\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.gecko.driver\", thisDriver.getAbsolutePath());  \n" +
"    //  System.setProperty(\"webdriver.gecko.driver\", BMPATH+File.separator+\"lib\"+File.separator+\"geckodriver-linux64\"+File.separator+\"geckodriver\");\n" +
"     }\n" +
"   \n" +
"    if (firefox_path!=null) {\n" +
"        System.setProperty(\"webdriver.firefox.bin\", firefox_path);\n" +
"    }\n" +
"\n" +
"    try\n" +
"    {\n" +
"// DesiredCapabilities cap = DesiredCapabilities.firefox();\n" +
"  //      cap.setJavascriptEnabled(false);\n" +
"\n" +
"  //     FirefoxProfile profile = new FirefoxProfile();\n" +
"\n" +
" // DesiredCapabilities cap = DesiredCapabilities.firefox();\n" +
"  //    cap.setJavascriptEnabled(true);\n" +
"  //     cap.setCapability(\"marionette\", true);\n" +
"        \n" +
" //      profile.setPreference(\"dom.max_script_run_time\", 30);\n" +
"        RunThread.driver = new FirefoxDriver();\n" +
"       \n" +
"\n" +
"    //  driver =  new MarionetteDriver();\n" +
"    }\n" +
"    catch (Exception ex)\n" +
"    {\n" +
"        System.out.println (\"Exception launching Marionette driver... possibly XP or missing msvcr110.dll: \" + ex.toString());\n" +
"     \n" +
"         Prompter fallbackprompt = new Prompter (\"Driver Error\", \"Could not launch the Marionette driver: \" + ex.toString(), false,0,0);\n" +
"      \n" +
"      \n" +
"    }\n" +
"      \n" +
"     break;\n" +
"     \n" +
"     case \"Silent Mode (HTMLUnit)\":\n" +
"  \n" +
"     RunThread.driver = new HtmlUnitDriver();  \n" +
"   if (RunThread.driver==null){System.out.println(\"driver null\");}\n" +
"     break;\n" +
"     \n" +
"     case \"Internet Explorer-32\":\n" +
"            thisDriver =  new File(WEBDRIVERSDIR+\"iedriverserver_win32\"+File.separator+\"IEDriverServer.exe\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.ie.driver\", thisDriver.getAbsolutePath());  \n" +
"    // System.setProperty(\"webdriver.ie.driver\", BMPATH+File.separator+\"lib\"+File.separator+\"iedriverserver_win32\"+File.separator+\"IEDriverServer.exe\");\n" +
"     try\n" +
"     {\n" +
"     RunThread.driver = new InternetExplorerDriver();\n" +
"     }\n" +
"     catch (Exception ex)\n" +
"     {\n" +
"    \n" +
"     \n" +
"         Prompter fallbackprompt = new Prompter (\"Driver Error\", \"Could not launch IEdriver:\" + ex.toString(), false,0,0);\n" +
"         \n" +
"          \n" +
"     }\n" +
"     break;\n" +
"     case \"Internet Explorer-64\":\n" +
"            thisDriver =  new File(WEBDRIVERSDIR+\"iedriverserver_win64\"+File.separator+\"IEDriverServer.exe\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.ie.driver\", thisDriver.getAbsolutePath());  \n" +
"      \n" +
"   //  System.setProperty(\"webdriver.ie.driver\",BMPATH+File.separator+ \"lib\"+File.separator+\"iedriverserver_win64\"+File.separator+\"IEDriverServer.exe\");\n" +
"     try\n" +
"     {\n" +
"     RunThread.driver = new InternetExplorerDriver();\n" +
"     }\n" +
"     catch (Exception ex)\n" +
"             {\n" +
"     \n" +
"    \n" +
"         Prompter fallbackprompt = new Prompter (\"Driver Error\", \"Could not launch IEdriver: \" +ex.toString(), false,0,0);\n" +
"       \n" +
"          \n" +
"             }\n" +
"     break;\n" +
"     case \"Chrome\":\n" +
"         //legacy support\n" +
"          ChromeOptions options = new ChromeOptions();  \n" +
"          \n" +
"                 prefs.put(\"profile.default_content_setting_values.notifications\", 2);\n" +
"                // prefs.put(\"--dns-prefetch-disable\", );\n" +
"                 \n" +
"                 options.setExperimentalOption(\"prefs\", prefs);  \n" +
"                 options.addArguments(\"--dns-prefetch-disable\");\n" +
"             if (chrome_main_path!=null) {\n" +
"            \n" +
"   \n" +
"options.setBinary(chrome_main_path);\n" +
"\n" +
"\n" +
"    }\n" +
"         if (\"Windows\".equals(OSType))\n" +
"     {\n" +
"            thisDriver =  new File(WEBDRIVERSDIR+\"chromedriver_win32\"+File.separator+\"chromedriver.exe\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.chrome.driver\", thisDriver.getAbsolutePath());  \n" +
"     //   System.setProperty(\"webdriver.chrome.driver\", BMPATH+File.separator+\"lib\"+File.separator+\"chromedriver_win32\"+File.separator+\"chromedriver.exe\");\n" +
"     }\n" +
"     if (\"Windows32\".equals(OSType))\n" +
"     {\n" +
"                 thisDriver =  new File(WEBDRIVERSDIR+\"chromedriver_win32\"+File.separator+\"chromedriver.exe\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.chrome.driver\", thisDriver.getAbsolutePath());  \n" +
" // System.setProperty(\"webdriver.chrome.driver\", BMPATH+File.separator+\"lib\"+File.separator+\"chromedriver_win32\"+File.separator+\"chromedriver.exe\");\n" +
" \n" +
"  \n" +
"     }\n" +
"       if (\"Windows64\".equals(OSType))\n" +
"     {\n" +
"                 thisDriver =  new File(WEBDRIVERSDIR+\"chromedriver_win32\"+File.separator+\"chromedriver.exe\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.chrome.driver\", thisDriver.getAbsolutePath());  \n" +
"   //  System.setProperty(\"webdriver.chrome.driver\", BMPATH+File.separator+\"lib\"+File.separator+\"chromedriver_win32\"+File.separator+\"chromedriver.exe\");\n" +
"     }\n" +
"     if (\"Mac\".equals(OSType))\n" +
"     {\n" +
"                 thisDriver =  new File(WEBDRIVERSDIR+\"chromedriver_mac64\"+File.separator+\"chromedriver\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.chrome.driver\", thisDriver.getAbsolutePath());  \n" +
"   //  System.setProperty(\"webdriver.chrome.driver\", BMPATH+File.separator+\"lib\"+File.separator+\"chromedriver_mac64\"+File.separator+\"chromedriver\");\n" +
"     }\n" +
"     if (\"Linux-32\".equals(OSType))\n" +
"     {\n" +
"                  thisDriver =  new File(WEBDRIVERSDIR+\"chromedriver_linux32\"+File.separator+\"chromedriver\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.chrome.driver\", thisDriver.getAbsolutePath());    \n" +
"   //  System.setProperty(\"webdriver.chrome.driver\",BMPATH+File.separator+ \"lib\"+File.separator+\"chromedriver_linux32\"+File.separator+\"chromedriver\");\n" +
"     }\n" +
"     if (\"Linux-64\".equals(OSType))\n" +
"     {\n" +
"                    thisDriver =  new File(WEBDRIVERSDIR+\"chromedriver_linux64\"+File.separator+\"chromedriver\");\n" +
"          setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.chrome.driver\", thisDriver.getAbsolutePath());  \n" +
"   //  System.setProperty(\"webdriver.chrome.driver\", BMPATH+File.separator+\"lib\"+File.separator+\"chromedriver_linux64\"+File.separator+\"chromedriver\");\n" +
"     }\n" +
"     try\n" +
"     {\n" +
"        RunThread.driver = new ChromeDriver(options);     \n" +
"     }\n" +
"   catch (Exception ex)\n" +
"   {\n" +
"       System.out.println (\"Problem launching Chromedriver: \" + ex.toString());\n" +
"     \n" +
"         Prompter fallbackprompt = new Prompter (\"Driver Error\", \"Could not launch the Chromedriver.\" + ex.toString(), false,0,0);\n" +
"  \n" +
"   }\n" +
"  \n" +
"      break;\n" +
"     \n" +
"     \n" +
"   case \"Chrome 49\":\n" +
"         ChromeOptions options49 = new ChromeOptions();\n" +
"                 prefs.put(\"profile.default_content_setting_values.notifications\", 2);\n" +
"                 options49.setExperimentalOption(\"prefs\", prefs);     \n" +
"      if (chrome_path!=null) {\n" +
"   \n" +
"          \n" +
"options49.setBinary(chrome_path);\n" +
"\n" +
"\n" +
"    }\n" +
"      // since new SElenium does not work with Chrome49, this was changed\n" +
"       //          thisDriver =  new File(WEBDRIVERSDIR+\"chromedriver_win32\"+File.separator+\"chromedriver-winxp.exe\");\n" +
"                 thisDriver =  new File(WEBDRIVERSDIR+\"chromedriver_win32\"+File.separator+\"chromedriver.exe\");\n" +
"      \n" +
"                 setPermissions(thisDriver);\n" +
"        System.setProperty(\"webdriver.chrome.driver\", thisDriver.getAbsolutePath());  \n" +
"    // System.setProperty(\"webdriver.chrome.driver\", BMPATH+File.separator+\"lib\"+File.separator+\"chromedriver_win32\"+File.separator+\"chromedriver-winxp.exe\");\n" +
"   \n" +
"    \n" +
"     try\n" +
"     {\n" +
"        RunThread.driver = new ChromeDriver(options49);     \n" +
"     }\n" +
"   catch (Exception ex)\n" +
"   {\n" +
"       System.out.println (\"Problem launching Chromedriver 49: \" + ex.toString());\n" +
"        Prompter fallbackprompt = new Prompter (\"Driver Error\", \"Could not launch the Chrome 49 driver.\" + ex.toString(), false,0, 0);\n" +
"          \n" +
"   }\n" +
" \n" +
"   \n" +
"     break;\n" +
"//   case \"Edge\":\n" +
"//                  thisDriver =  new File( WEBDRIVERSDIR+\"edgedriver\"+File.separator+\"MicrosoftWebDriver.exe\");\n" +
"//          setPermissions(thisDriver);\n" +
"//        System.setProperty(\"webdriver.edge.driver\", thisDriver.getAbsolutePath());  \n" +
"//  //   System.setProperty(\"webdriver.edge.driver\", BMPATH+File.separator+\"lib\"+File.separator+\"edgedriver\"+File.separator+\"MicrosoftWebDriver.exe\");\n" +
"//   try\n" +
"//   {\n" +
"//     driver = new EdgeDriver();  \n" +
"//   }\n" +
"//     catch (Exception ex)\n" +
"//   {\n" +
"//       System.out.println (\"Problem launching EdgeDriver: \" + ex.toString());\n" +
"//        Prompter fallbackprompt = new Prompter (\"Driver Error\", \"Could not launch the Edge Driver. \" + ex.toString(), false,0, 0);\n" +
"//  \n" +
"//   }\n" +
"//       break;\n" +
"//         \n" +
"          \n" +
"  }\n" +
"        this.Pass = true;";
   }
  @Override
    public void RunAction(RunAllTests RunThread)
    {
     boolean closecaught = false;
   

 try
 {
   if (RunThread.driver!=null) {  RunThread.driver.close(); }
 }
 catch (Exception e)
 {
     closecaught = true;
     System.out.println(e.toString());
     try {
                RunThread.driver.quit();
            }
            catch (Exception exce)
            {
               
                System.out.println("Exception quitting" + exce.toString());
            }
 }
 if (closecaught)
 {
 
 }
 else
 {
     try
 {
   RunThread.driver.quit();
 }
 catch (Exception ex)
 {
     // don't worry it should close
 }
  
 } 

  File thisDriver =  new File( WEBDRIVERSDIR+"geckodriver-win32"+File.separator+"geckodriver.exe");
  switch (this.Variable1)
  {
      
     case "Firefox-Marionette":
     // legacy file support
         
         if ("Windows".equals(OSType))
     {
           thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-win32"+File.separator+"geckodriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
    //   System.setProperty("webdriver.gecko.driver", "lib"+File.separator+"geckodriver-win32"+File.separator+"geckodriver.exe");
     }   
     if ("Windows32".equals(OSType))
     {
         thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-win32"+File.separator+"geckodriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());   
      // System.setProperty("webdriver.gecko.driver", "lib"+File.separator+"geckodriver-win32"+File.separator+"geckodriver.exe");
     }
     if ("Windows64".equals(OSType))
     {
         thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-win64"+File.separator+"geckodriver.exe");
         setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
    //   System.setProperty("webdriver.gecko.driver", "lib"+File.separator+"geckodriver-win64"+File.separator+"geckodriver.exe");
     }
     if ("Mac".equals(OSType))
     {
            thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-osx"+File.separator+"geckodriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());     
    //  System.setProperty("webdriver.gecko.driver", "lib"+File.separator+"geckodriver-osx"+File.separator+"geckodriver");
     }
     if ("Linux-32".equals(OSType))
     {
              thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-linux32"+File.separator+"geckodriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
    //  System.setProperty("webdriver.gecko.driver", "lib"+File.separator+"geckodriver-linux32"+File.separator+"geckodriver");
     }
     if ("Linux-64".equals(OSType))
     {
                thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-linux64"+File.separator+"geckodriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
   //   System.setProperty("webdriver.gecko.driver", "lib"+File.separator+"geckodriver-linux64"+File.separator+"geckodriver");
     }
   
    if (firefox_path!=null) {
        System.setProperty("webdriver.firefox.bin", firefox_path);
    }

    try
    {

// FirefoxProfile profile = new FirefoxProfile();

 //DesiredCapabilities cap = DesiredCapabilities.firefox();
   //     cap.setJavascriptEnabled(true);
   //     cap.setCapability("marionette", false);
        
   //     profile.setPreference("dom.max_script_run_time", 1);
        RunThread.driver = new FirefoxDriver();
    

    //  driver =  new MarionetteDriver();
    }
    catch (Exception ex)
    {
        System.out.println ("Exception launching Marionette driver... possibly XP or missing msvcr110.dll: " + ex.toString());
     
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch Marionette driver:" + ex.toString(), false,0,0);
     
         
          
    }
      
     break;
            
    case "Firefox":
   
     if ("Windows".equals(OSType))
     {
        
        thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-win32"+File.separator+"geckodriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
     }
     if ("Windows32".equals(OSType))
     {
         thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-win32"+File.separator+"geckodriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
     }
     if ("Windows64".equals(OSType))
     {
           thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-win64"+File.separator+"geckodriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
   //    System.setProperty("webdriver.gecko.driver", BMPATH+File.separator+"lib"+File.separator+"geckodriver-win64"+File.separator+"geckodriver.exe");
     }
     if ("Mac".equals(OSType))
     {
            thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-osx"+File.separator+"geckodriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
  //    System.setProperty("webdriver.gecko.driver", BMPATH+File.separator+"lib"+File.separator+"geckodriver-osx"+File.separator+"geckodriver");
     }
     if ("Linux-32".equals(OSType))
     {
            thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-linux32"+File.separator+"geckodriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
  //    System.setProperty("webdriver.gecko.driver",BMPATH+File.separator+ "lib"+File.separator+"geckodriver-linux32"+File.separator+"geckodriver");
     }
     if ("Linux-64".equals(OSType))
     {
           thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-linux64"+File.separator+"geckodriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
    //  System.setProperty("webdriver.gecko.driver", BMPATH+File.separator+"lib"+File.separator+"geckodriver-linux64"+File.separator+"geckodriver");
     }
   
    if (firefox_path!=null) {
        System.setProperty("webdriver.firefox.bin", firefox_path);
    }

    try
    {
// DesiredCapabilities cap = DesiredCapabilities.firefox();
  //      cap.setJavascriptEnabled(false);

  //     FirefoxProfile profile = new FirefoxProfile();

 // DesiredCapabilities cap = DesiredCapabilities.firefox();
  //    cap.setJavascriptEnabled(true);
  //     cap.setCapability("marionette", true);
        
 //      profile.setPreference("dom.max_script_run_time", 30);
        RunThread.driver = new FirefoxDriver();
       

    //  driver =  new MarionetteDriver();
    }
    catch (Exception ex)
    {
        System.out.println ("Exception launching Marionette driver... possibly XP or missing msvcr110.dll: " + ex.toString());
     
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Marionette driver: " + ex.toString(), false,0,0);
      
      
    }
      
     break;
     
     case "Silent Mode (HTMLUnit)":
  
     RunThread.driver = new HtmlUnitDriver();  
   if (RunThread.driver==null){System.out.println("driver null");}
     break;
     
     case "Internet Explorer-32":
            thisDriver =  new File(WEBDRIVERSDIR+"iedriverserver_win32"+File.separator+"IEDriverServer.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.ie.driver", thisDriver.getAbsolutePath());  
    // System.setProperty("webdriver.ie.driver", BMPATH+File.separator+"lib"+File.separator+"iedriverserver_win32"+File.separator+"IEDriverServer.exe");
     try
     {
     RunThread.driver = new InternetExplorerDriver();
     }
     catch (Exception ex)
     {
    
     
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch IEdriver:" + ex.toString(), false,0,0);
         
          
     }
     break;
     case "Internet Explorer-64":
            thisDriver =  new File(WEBDRIVERSDIR+"iedriverserver_win64"+File.separator+"IEDriverServer.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.ie.driver", thisDriver.getAbsolutePath());  
      
   //  System.setProperty("webdriver.ie.driver",BMPATH+File.separator+ "lib"+File.separator+"iedriverserver_win64"+File.separator+"IEDriverServer.exe");
     try
     {
     RunThread.driver = new InternetExplorerDriver();
     }
     catch (Exception ex)
             {
     
    
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch IEdriver: " +ex.toString(), false,0,0);
       
          
             }
     break;
     case "Chrome":
         //legacy support
          ChromeOptions options = new ChromeOptions();  
          
                 prefs.put("profile.default_content_setting_values.notifications", 2);
                // prefs.put("--dns-prefetch-disable", );
                 
                 options.setExperimentalOption("prefs", prefs);  
                 options.addArguments("--dns-prefetch-disable");
             if (chrome_main_path!=null) {
            
   
options.setBinary(chrome_main_path);


    }
         if ("Windows".equals(OSType))
     {
            thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_win32"+File.separator+"chromedriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.chrome.driver", thisDriver.getAbsolutePath());  
     //   System.setProperty("webdriver.chrome.driver", BMPATH+File.separator+"lib"+File.separator+"chromedriver_win32"+File.separator+"chromedriver.exe");
     }
     if ("Windows32".equals(OSType))
     {
                 thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_win32"+File.separator+"chromedriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.chrome.driver", thisDriver.getAbsolutePath());  
 // System.setProperty("webdriver.chrome.driver", BMPATH+File.separator+"lib"+File.separator+"chromedriver_win32"+File.separator+"chromedriver.exe");
 
  
     }
       if ("Windows64".equals(OSType))
     {
                 thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_win32"+File.separator+"chromedriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.chrome.driver", thisDriver.getAbsolutePath());  
   //  System.setProperty("webdriver.chrome.driver", BMPATH+File.separator+"lib"+File.separator+"chromedriver_win32"+File.separator+"chromedriver.exe");
     }
     if ("Mac".equals(OSType))
     {
                 thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_mac64"+File.separator+"chromedriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.chrome.driver", thisDriver.getAbsolutePath());  
   //  System.setProperty("webdriver.chrome.driver", BMPATH+File.separator+"lib"+File.separator+"chromedriver_mac64"+File.separator+"chromedriver");
     }
     if ("Linux-32".equals(OSType))
     {
                  thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_linux32"+File.separator+"chromedriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.chrome.driver", thisDriver.getAbsolutePath());    
   //  System.setProperty("webdriver.chrome.driver",BMPATH+File.separator+ "lib"+File.separator+"chromedriver_linux32"+File.separator+"chromedriver");
     }
     if ("Linux-64".equals(OSType))
     {
                    thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_linux64"+File.separator+"chromedriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.chrome.driver", thisDriver.getAbsolutePath());  
   //  System.setProperty("webdriver.chrome.driver", BMPATH+File.separator+"lib"+File.separator+"chromedriver_linux64"+File.separator+"chromedriver");
     }
     try
     {
        RunThread.driver = new ChromeDriver(options);     
     }
   catch (Exception ex)
   {
       System.out.println ("Problem launching Chromedriver: " + ex.toString());
     
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chromedriver." + ex.toString(), false,0,0);
  
   }
  
      break;
     
     
   case "Chrome 49":
         ChromeOptions options49 = new ChromeOptions();
                 prefs.put("profile.default_content_setting_values.notifications", 2);
                 options49.setExperimentalOption("prefs", prefs);     
      if (chrome_path!=null) {
   
          
options49.setBinary(chrome_path);


    }
      // since new SElenium does not work with Chrome49, this was changed
       //          thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_win32"+File.separator+"chromedriver-winxp.exe");
                 thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_win32"+File.separator+"chromedriver.exe");
      
                 setPermissions(thisDriver);
        System.setProperty("webdriver.chrome.driver", thisDriver.getAbsolutePath());  
    // System.setProperty("webdriver.chrome.driver", BMPATH+File.separator+"lib"+File.separator+"chromedriver_win32"+File.separator+"chromedriver-winxp.exe");
   
    
     try
     {
        RunThread.driver = new ChromeDriver(options49);     
     }
   catch (Exception ex)
   {
       System.out.println ("Problem launching Chromedriver 49: " + ex.toString());
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chrome 49 driver." + ex.toString(), false,0, 0);
          
   }
 
   
     break;
//   case "Edge":
//                  thisDriver =  new File( WEBDRIVERSDIR+"edgedriver"+File.separator+"MicrosoftWebDriver.exe");
//          setPermissions(thisDriver);
//        System.setProperty("webdriver.edge.driver", thisDriver.getAbsolutePath());  
//  //   System.setProperty("webdriver.edge.driver", BMPATH+File.separator+"lib"+File.separator+"edgedriver"+File.separator+"MicrosoftWebDriver.exe");
//   try
//   {
//     driver = new EdgeDriver();  
//   }
//     catch (Exception ex)
//   {
//       System.out.println ("Problem launching EdgeDriver: " + ex.toString());
//        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Edge Driver. " + ex.toString(), false,0, 0);
//  
//   }
//       break;
//         
          
  }
        this.Pass = true;
    }

    
}