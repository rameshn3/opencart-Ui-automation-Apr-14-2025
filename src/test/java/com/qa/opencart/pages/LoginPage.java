package com.qa.opencart.pages;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.utilities.Constants;
import com.qa.opencart.utilities.WebDriverUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LoginPage extends WebDriverUtils{
WebDriverWait wait;
    public LoginPage(WebDriver driver){
        super(driver);
        wait = new WebDriverWait(driver,Duration.ofMillis(Constants.EXPLICIT_WAIT));
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath="//a[normalize-space()='Continue']")
    private WebElement newCustomerContinueBtn;

    @FindBy(xpath="//h2[normalize-space()='New Customer']")
    private WebElement newCustomerHeader;

    @FindBy(xpath="//h2[normalize-space()='Returning Customer']")
    private WebElement returningCustomerHeader;

    @FindBy(css="#input-email")
    private WebElement emailAddressEditbox;

    @FindBy(css="#input-password")
    private WebElement passwordEditbox;

    @FindBy(css="input[value='Login'][type='submit]")
    private WebElement loginBtn;

    @FindBy(linkText="Forgotten Password")
    private WebElement forgottenPasswordLink;

    @FindBy(css=".alert.alert-danger.alert-dismissible")
    private WebElement emptyCredsErrorMessage;

    @FindBy(xpath="//i[@class='fa fa-home']")
    private WebElement homeIcon;

    @FindBy(xpath="//ul[@class='breadcrumb']//a[normalize-space()='Login']")
    private WebElement loginBreadCrumb;

    @Step("Get Login Page Title")
    public String getLoginPageTitle(){
        return getTitle();
    }

    @Step("Get Login Page url")
    public String getLoginPageUrl(){
        return waitForUrlContains(Constants.LOGIN_PAGE_FRACTION_URL);
    }
    @Step("is Login Page BreadCrumb exists")
    public boolean isLoginBreadCrumbExits(){
        return loginBreadCrumb.isDisplayed();
    }
    @Step("is new customer header exists")
    public boolean isNewCustomerHeaderExits(){
        return newCustomerHeader.isDisplayed();
    }
    @Step("is returning customer header exists")
    public boolean isReturningCustomerHeaderExits(){
        return returningCustomerHeader.isDisplayed();
    }

    @Step("click on new customer continue button")
    public void clickNewCustomerContinueBtn() throws InterruptedException{
        ChainTestListener.log("clicking on new customer continue btn...");
        click(newCustomerContinueBtn);
    }
    @Step("click on login button")
    public void clickOnLoginBtn() throws InterruptedException{
        ChainTestListener.log("click on login button...");
        click(loginBtn);
    }
    @Step("Do login with Credentails -Email: {0}, Password: {1}")
    public MyAccountPage  doLogin(String email,String pwd) throws InterruptedException{
        ChainTestListener.log("perform the login actions..");
        ChainTestListener.log("fill the username  details.");
       clearText(emailAddressEditbox);
       type(emailAddressEditbox,email);
        ChainTestListener.log("fill the password details.");
        clearText(passwordEditbox);
        type(passwordEditbox,pwd);
        ChainTestListener.log("click on login button...");
       clickOnLoginBtn();
       return new MyAccountPage();
    }

    @Step("Get Error Message")
    public String getErrorMessage(){
        return getText(emptyCredsErrorMessage);
    }
    @Step("click on forgot password link")
    public void clickOnForgotPasswordLink() throws InterruptedException{
        ChainTestListener.log("click on ForgotPasswordLink..");
        click(forgottenPasswordLink);
    }
}
