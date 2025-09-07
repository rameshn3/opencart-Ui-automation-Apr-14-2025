package com.qa.opencart.pages;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.utilities.Constants;
import com.qa.opencart.utilities.WebDriverUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MyAccountPage extends WebDriverUtils{
    public MyAccountPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath="//a[@title='My Account']")
    private WebElement myAccountMenu;

    @FindBy(xpath="//input[@placeholder='Search']")
    private WebElement searchEditbox;

    @FindBy(xpath="//button[@class='btn btn-default btn-lg']")
    private WebElement searchTorchIcon;

    @FindBy(xpath="//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Logout']")
    private WebElement logoutLink;

    @FindBy(css="body div[id='account-account'] ul[class='breadcrumb'] li:nth-child(1) a:nth-child(1)")
    private WebElement accountBreadCrumb;

    @FindBy(css="#content h2")
    private List<WebElement> MyAccountHeaderList;

    @FindBy(xpath="//*[@id='top-links']/ul/li[2]/ul/li/a")
    private List<WebElement> myAccountMenuOptionsList;

    @FindBy(xpath="//i[@class='fa fa-home']")
    private WebElement homeIcon;

    @FindBy(xpath="//*[@id=\"account-account\"]/ul/li[2]/a[normalize-space()='Account']")
private WebElement myaccountBreadCrumb;
       public String getMyAccountPageTitle(){
        return getTitle();
    }

    public String getMyAccountPageUrl(){
           return  waitForUrlContains(Constants.MY_ACCOUNT_PAGE_FRACTION_URL);
    }
    public void clickMyAccountMenu(){
       try{
           ChainTestListener.log("Click on my account menu");
           click(myAccountMenu);
       }catch(NoSuchElementException|InterruptedException ex){
           ChainTestListener.log("Unable to click on my account menu");
           ex.printStackTrace();
       }
    }

    public boolean isLogoutExists(){
        clickMyAccountMenu();
           return logoutLink.isDisplayed();
    }
    public boolean isMyAccountBreadCrumbExists(){

        return myaccountBreadCrumb.isDisplayed();
    }

    public void clickLogoutLink() throws InterruptedException{
           try{
               if(isLogoutExists()){
                   ChainTestListener.log("Click on logout link under myaccount menu");
                   click(logoutLink);
               }
           }catch(NoSuchElementException ex){
               ex.printStackTrace();
           }

    }
    public void navigateToHomePage() throws InterruptedException{
        try{
            ChainTestListener.log("click on Home icon on breadcrumb");
            click(homeIcon);
        }catch(NoSuchElementException ex){
            ChainTestListener.log("Unable to click home icon");
        }

    }

    public List<String> getMyAccountMenuOptionList(){
List<String>myAccountMenuOptionsTextList = new ArrayList<>();
try{
    clickMyAccountMenu();
    for(WebElement option:myAccountMenuOptionsList){
        String text = option.getText();
        myAccountMenuOptionsTextList.add(text);
    }
}catch(Exception ex){
    ex.printStackTrace();
}
        return myAccountMenuOptionsTextList;
    }


    public List<String> getMyAccountHeaderOptionsList(){
        List<String>myAccountHeaderOptionsTextList = new ArrayList<>();
        try{
            clickMyAccountMenu();
            for(WebElement option:MyAccountHeaderList){
                String text = option.getText();
                myAccountHeaderOptionsTextList.add(text);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return myAccountHeaderOptionsTextList;
    }

    public ResultsPage doProductSearch(String productName){
           ChainTestListener.log("searching for the product:"+productName);
           try{
               ChainTestListener.log("type the product in  the search field");
               type(searchEditbox,productName);
               ChainTestListener.log("click on search torch icon");
               click(searchTorchIcon);
           }catch(InterruptedException e){
               throw new RuntimeException(e);
           }

           return new ResultsPage(driver);
    }

    public void pressEscapeKey(){
           Actions act = new Actions(driver);
        act.sendKeys(Keys.ESCAPE).perform();
    }

}
