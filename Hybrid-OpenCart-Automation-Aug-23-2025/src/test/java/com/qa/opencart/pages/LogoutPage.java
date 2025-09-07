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

public class LogoutPage extends WebDriverUtils{
    public LogoutPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath="//h1[normalize-space()='Account Logout']")
    private WebElement accountLogoutHeader;

    @FindBy(xpath="//a[normalize-space()='Continue']")
    private WebElement continueBtn;

    @FindBy(css="#content p")
    private WebElement accountLoggedOffMsg;

    @FindBy(xpath="//*[@id='common-success']/ul/li[3]/a")
    private WebElement logoutBreadCrumb;


       public String getLogoutPageTitle(){
        return getTitle();
    }

    public String getLogoutPageUrl(){
           return  waitForUrlContains(Constants.LOGOUT_PAGE_FRACTION_URL);
    }
    public void clickContinueBtn(){
       try{
           ChainTestListener.log("Click on continue button");
           click(continueBtn);
       }catch(NoSuchElementException|InterruptedException ex){
           ChainTestListener.log("Unable to click on continue button");
           ex.printStackTrace();
       }
    }

    public boolean isLogoutBreadCrumbExists(){

           return logoutBreadCrumb.isDisplayed();
    }
    public boolean isAccountLogoutHeaderExists(){

        return accountLogoutHeader.isDisplayed();
    }

    public boolean isAccountLoggedOffMsgExists(){

        return accountLoggedOffMsg.isDisplayed();
    }



}
