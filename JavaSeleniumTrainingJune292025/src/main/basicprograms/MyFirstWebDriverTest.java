package basicprograms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Set;

public class MyFirstWebDriverTest{
    public static void main(String[] args){
        //interface refvar = new implementedclass();
        //launch the chrome browser
        //WebDriver driver = new ChromeDriver();
       // WebDriver driver = new EdgeDriver();

        FirefoxOptions options = new FirefoxOptions();
        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        WebDriver driver = new FirefoxDriver(options);
        //open the url in chrome browser
        driver.get("https://linkedin.com");

        //fetch the current page title
        //datatype variablename = refvar.returntypenonstaticmethod();
        String t=driver.getTitle();
        System.out.println("current page title is:"+t);

        //fetch  absolute url of the current page
        String absUrl =driver.getCurrentUrl();
        System.out.println("current page absolute url:"+absUrl);
        //fetch current page source code
        String src = driver.getPageSource();
        //fetch current page window id
        String pid =driver.getWindowHandle();
        System.out.println("current windo id:"+pid);
        //fetch all window ids
        Set<String> handles = driver.getWindowHandles();
        System.out.println("all the window handles/ids:"+handles);
        //close the browser
        //driver.close();

        //quti all the windows
       driver.quit();

    }

}
