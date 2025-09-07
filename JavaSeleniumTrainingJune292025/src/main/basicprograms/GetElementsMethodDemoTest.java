package basicprograms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class GetElementsMethodDemoTest{

    private static WebDriver driver=null;
    public static void main(String[] args) throws InterruptedException{
        //launch the chrome browser
        driver = new ChromeDriver();

        //maximize the window
        driver.manage().window().maximize();

        //add implicitwait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //open the url
        driver.get("https://www.rediff.com/");

        //explicitwait
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofMillis(30000));

        wait.until(ExpectedConditions.titleContains("Rediffmail"));

        //fethc top storeis text
        String topStoryTxt = driver.findElement(By.xpath("/html/body/div[7]/div[2]/h2/a")).getText();
        System.out.println("topstory text is:"+topStoryTxt);



        //Click on Create account link
        WebElement createAccountLink=wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Create Account")));

        //fetch the tooptip of create account link
        String tp = createAccountLink.getAttribute("title");
        System.out.println("create account link tooltip is:"+tp);

        //fetch create account link url
        String url = createAccountLink.getDomAttribute("href");

        System.out.println("create account link url is:"+url);

        //fetch css values
        String clr = createAccountLink.getCssValue("color");
        System.out.println("color of the link is:"+clr);
        //feth whetehr create account link is underline or not
        String ud = createAccountLink.getCssValue("text-decoration");
        System.out.println("createAccountLink is underline or not:"+ud);

        String fontfmly = createAccountLink.getCssValue("font-family");
        System.out.println("font family is:"+fontfmly);

        String fontsize = createAccountLink.getCssValue("font-size");
        System.out.println("font size is:"+fontsize);

        WebElement logo =driver.findElement(By.className("logo"));

        System.out.println("x coordinate:"+logo.getRect().getX());
        System.out.println("Y coordinate:"+logo.getRect().getY());

        System.out.println("height of the logo:"+logo.getRect().getHeight());
        System.out.println("width of the logo:"+logo.getRect().getWidth());

        System.out.println("tagname of the logo is:"+logo.getTagName());

        WebElement imgElement =driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/a/img"));
        System.out.println("source of the image is:"+imgElement.getAttribute("src"));

         //close the browser
        driver.close();
    }


}
