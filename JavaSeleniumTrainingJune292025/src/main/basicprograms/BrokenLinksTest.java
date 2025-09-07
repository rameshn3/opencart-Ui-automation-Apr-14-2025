package basicprograms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class BrokenLinksTest{
    private static WebDriver driver=null;

    public static void main(String[] args) throws InterruptedException, IOException{
        //launch the chrome browser
        driver=new ChromeDriver();

        //maximize the window
        driver.manage().window().maximize();

        //add implicitwait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //open the url
        driver.get("https://testautomationpractice.blogspot.com/");

        //explicitwait
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofMillis(30000));

        wait.until(ExpectedConditions.titleContains("Automation Testing Practice"));
        //fetch all the links into a list collection
        List<WebElement> linksList=driver.findElements(By.tagName("a"));
        for(WebElement link:linksList){
          String hrefUrl =  link.getAttribute("href");
          if(hrefUrl!=null){
              validateUrls(hrefUrl);
          }
        }

        Thread.sleep(2000);
        driver.quit();
    }

    private static void validateUrls(String url) throws IOException{
        URL ul = new URL(url);
       HttpURLConnection hc =(HttpURLConnection)ul.openConnection();
       hc.connect();
       int respStatusCode = hc.getResponseCode();
       String respMsg = hc.getResponseMessage();
       if(respStatusCode==200){
           System.out.println(url+" is workign fine "+respStatusCode+" "+respMsg);
       }else if(respStatusCode>=400){

           System.out.println(url+" link is broken:  "+respStatusCode+" "+respMsg);
       }
       hc.disconnect();

    }
}