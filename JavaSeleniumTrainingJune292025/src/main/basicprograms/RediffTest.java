package basicprograms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.time.Duration;
import java.util.Set;

public class RediffTest{

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

        //fetch the current page title, current page absolute url, current page window id
        String t =driver.getTitle();
        System.out.println("current page title is:"+t);
        //fetch the current page absolute url
        String absUrl =driver.getCurrentUrl();
        System.out.println("current page absolute url is:"+absUrl);
        //fetch current page source code
        String src =driver.getPageSource();
        //fetch current window id
        String pid = driver.getWindowHandle();
        System.out.println("current window id:"+pid);
        //fetch all window ids
        Set<String> handles = driver.getWindowHandles();
        System.out.println("multiple windows ids:"+handles);
        //Click on Create account link
        WebElement createAccountLink=wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Create Account")));
        createAccountLink.click();
        wait.until(ExpectedConditions.titleIs("Rediffmail Free Unlimited Storage"));
        //wait for the createAccount header text
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("div[class='cnt'] h2"),"Create a Rediffmail account"));
//wait for the url
        wait.until(ExpectedConditions.urlContains("register/register.php?FormName=user_details"));

        //type the fullname in fullname editbox
        WebElement fullNameEditbox =driver.findElement(By.xpath("(//input[contains(@name,'name')])[1]"));
        //clear the content
        fullNameEditbox.clear();
        fullNameEditbox.sendKeys("Ramesh Ch");
        //select female radio button
        WebElement femaleRadioBtn =driver.findElement(By.cssSelector("input[value='f']"));
        femaleRadioBtn.click();
        //check whether female radio button is selected or not
        wait.until(ExpectedConditions.elementSelectionStateToBe(femaleRadioBtn,true));
        if(femaleRadioBtn.isSelected()){
            System.out.println("female rdio button is selected"+femaleRadioBtn.isSelected());
        }else{
            System.out.println("female rdio button is not selected"+femaleRadioBtn.isSelected());
        }

        /**select the day,month and year option from dropdowns**/

        //1)identify the dropdown and assign it to a WebElement type varaible
         selectOption(By.cssSelector("select[name*='DOB_Day']"),"10");
        //MONTH DROPDOWN
        //1)identify the dropdown and assign it to a WebElement type varaible
         selectOption(By.cssSelector("select[name^='DOB_Month']"),"AUG");
        //YEAR DROPDOWN
        //1)identify the dropdown and assign it to a WebElement type varaible
        selectOption(By.xpath("//select[starts-with(@name,'DOB_Year')]"),"2011");
        //wait for some time to observe the selection
        Thread.sleep(3000);
         //navigate back to previous page
        driver.navigate().back();
        wait.until(ExpectedConditions.titleContains("Rediffmail"));
        //refresh the page
        driver.navigate().refresh();
        Thread.sleep(3000);

        //navigate to create account page
        driver.navigate().forward();
        wait.until(ExpectedConditions.titleIs("Rediffmail Free Unlimited Storage"));
        //wait for the createAccount header text
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("div[class='cnt'] h2"),"Create a Rediffmail account"));
        //wait for the url
        wait.until(ExpectedConditions.urlContains("register/register.php?FormName=user_details"));
        //close the browser
        driver.close();
    }


    private static void selectOption(By locator,String optionToSelect){
        //1)identify the dropdown and assign it to a WebElement type varaible
        WebElement daydropdown=driver.findElement(locator);

        //2)fetch all the dropdown options using findElements API and tagName -option and store in List type collection
        List<WebElement>dayopts=daydropdown.findElements(By.tagName("option"));

        //for(datatype varname:collectionname){}
        for(WebElement o:dayopts){
            //print each dropdown option text
            System.out.println(o.getText());

            if(o.getText().equals(optionToSelect)){
                //select it
                o.click();
                break;
            }
        }
    }
}
