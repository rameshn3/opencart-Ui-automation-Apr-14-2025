package basicprograms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.time.Duration;

public class LinkedinSectionLinksTest{
    private static WebDriver driver=null;

    public static void main(String[] args) throws InterruptedException{
        //launch the chrome browser
      //  driver=new ChromeDriver();
        driver=new FirefoxDriver();
        //maximize the window
        driver.manage().window().maximize();

        //add implicitwait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //open the url
        driver.get("https://www.linkedin.com/");

        //explicitwait
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofMillis(30000));

        wait.until(ExpectedConditions.titleContains("LinkedIn: Log In or Sign Up"));

        WebElement generalSection=driver.findElement(By.xpath("//*[contains(@class,'w-full flex')]/div[1]/ul"));
        List<WebElement>genSecLinksList = generalSection.findElements(By.xpath("//*[contains(@class,'w-full flex')]/div[1]/ul/li/a"));
        /**
         * //*[contains(@class,'w-full flex')]/div[1]/ul/li[1]/a
         * //*[contains(@class,'w-full flex')]/div[1]/ul/li[2]/a
         * //*[contains(@class,'w-full flex')]/div[1]/ul/li[3]/a
         *
         * approach1) split the xpath into two string variables
         * String xpath1="//*[contains(@class,'w-full flex')]/div[1]/ul/li["'
         * String xpath2 ="]/a"
         * xpath1+i+xpath2
         * appraoch2) directly parameterise the xpath with a variable
         * //*[contains(@class,'w-full flex')]/div[1]/ul/li["+i+"]/a
         */

        String xpath1="//*[contains(@class,'w-full flex')]/div[1]/ul/li[";
        String xpath2 ="]/a";
         //String finalXpath xpath1+i+xpath2
        for(int i=1;i<genSecLinksList.size()+1;i++){
            System.out.println("*************************************************************");
            //fetch the link name
            String linkName= driver.findElement(By.xpath("//*[contains(@class,'w-full flex')]/div[1]/ul/li["+i+"]/a")).getText();
            System.out.println(i +"th link name is:"+linkName);
            //fetch the link url
            String linkUrl = driver.findElement(By.xpath(xpath1+i+xpath2)).getAttribute("href");
            System.out.println(linkName+ " url is :"+linkUrl);
            //click on each link
            driver.findElement(By.xpath(xpath1+i+xpath2)).click();
            Thread.sleep(2000);
            //fetch linkname page title
            System.out.println(linkName+" page title is:"+driver.getTitle());
            //navigate back to previous page
            driver.navigate().back();
           wait.until(ExpectedConditions.titleContains("LinkedIn: Log In or Sign Up"));
        }

        driver.quit();
    }

}
