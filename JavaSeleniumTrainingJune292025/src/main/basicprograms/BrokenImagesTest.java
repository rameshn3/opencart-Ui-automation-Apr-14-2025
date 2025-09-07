package basicprograms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class BrokenImagesTest{
    private static WebDriver driver=null;

    public static void main(String[] args) throws InterruptedException, IOException{
        //launch the chrome browser
        driver=new ChromeDriver();

        //maximize the window
        driver.manage().window().maximize();

        //add implicitwait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //open the url
        driver.get("https://the-internet.herokuapp.com/broken_images");

        //explicitwait
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofMillis(30000));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='example'] h3")));
        //fetch all the images into a list collection
        List<WebElement>imagesList=driver.findElements(By.tagName("img"));
        for(WebElement img:imagesList){
          String imgsrc =  img.getAttribute("src");
          if(imgsrc!=null){
              validateUrls(imgsrc);
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