package basicprograms;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class CookiesTest{
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

        Cookie ckObj = new Cookie("testCookie","jhh^%&^978798lkjlknc");
        //add cookie to the browser
        driver.manage().addCookie(ckObj);
        //fetch all the cookies in the browser
        Set<Cookie> ckColl = driver.manage().getCookies();
        System.out.println("number of cookies :"+ckColl.size());
        //ietrate the Set collection
        for(Cookie cookie:ckColl){
            System.out.println("cookie name & Value:"+cookie.getName()+" - "+cookie.getValue());
            System.out.println("cookie path:"+cookie.getPath()+" - "+cookie.getDomain());
            System.out.println("cookie expiry date:"+cookie.getExpiry());
        }

        //delete particular cookies
        driver.manage().deleteCookieNamed("testCookie");
        Set<Cookie> ckColl1 = driver.manage().getCookies();
        System.out.println("number of cookies afetr delete one :"+ckColl1.size());
        //delete all the cookies
        driver.manage().deleteAllCookies();
        Set<Cookie> ckColl2 = driver.manage().getCookies();
        System.out.println("number of cookies afetr delete all :"+ckColl2.size());

        driver.quit();
    }

}
