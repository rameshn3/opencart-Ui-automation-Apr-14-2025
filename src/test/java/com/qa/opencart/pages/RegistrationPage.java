package com.qa.opencart.pages;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.utilities.WebDriverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistrationPage extends WebDriverUtils{
    private Logger log =LogManager.getLogger(RegistrationPage.class);

    public RegistrationPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(css="#content > h1")
    private WebElement registerAccountHeader;
    @FindBy(css="#content > p > a")
    private WebElement registerAccountLoginLink;

    @FindBy(css="#account > legend")
    private WebElement personalDetailsLegendTxt;

    @FindBy(id="input-firstname")
    private WebElement firstNameEditbox;
    @FindBy(name="lastname")
    private WebElement lastNameEditbox;
    @FindBy(id="input-email")
    private WebElement emailEditbox;

    @FindBy(name="telephone")
    private WebElement telePhoneEditbox;

    @FindBy(xpath="//i[@class='fa fa-home']")
    private WebElement homeIcon;

    @FindBy(css="#content > form > fieldset:nth-child(2) > legend")
    private WebElement passwordLegenedText;

    @FindBy(id = "input-password")
    private WebElement passwordEditbox;
    @FindBy(name = "confirm")
    private WebElement confirmPasswordEditbox;

    @FindBy(xpath="//input[@name='newsletter' and @value = '1']")
    private WebElement subscribeYesRadioBtn;

    @FindBy(css="input[name='newsletter'][value='0']")
    private WebElement subscribeNoRadioBtn;

    @FindBy(name = "agree")
    private WebElement privacyPolicyCheckbox;
    @FindBy(css = "input[type='submit'][value='Continue']")
    private WebElement continueBtn;

    @FindBy(css=".alert.alert-danger.alert-dismissible")
    private WebElement agreeErrorMsg;

    @FindBy(xpath = "//ul[@class='breadcrumb']//a[normalize-space()='Register']")
    private WebElement registerBreadCrumb;

    @FindBy(xpath = "//div[@class='alert alert-danger alert-dismissible']")
    private WebElement emailExistsErrorMsg;

    @FindBy(css="div#content > h1")
    private WebElement accountCreatedHeader;

    @FindBy(css = "div#content > p")
    private WebElement accountCreatedSuccMsg;

    @FindBy(css = "#common-success > ul > li:nth-child(3) > a")
    private WebElement accountCreatedBreadCrumbSuccessLink;

    @FindBy(css = "a.btn.btn-primary")
    private WebElement accountCreatedContinueBtn;

    @FindBy(xpath = "//a[normalize-space()='contact us']")
    private WebElement contactusLink;

    public void clickOnContinueButton(){
        try{
            ChainTestListener.log("click on continue button");
            if(continueBtn.isDisplayed()&&continueBtn.isEnabled())
                click(continueBtn);
        }catch(NoSuchElementException ex){
            ChainTestListener.log("Element not found"+ex.getMessage());
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }
    }
    public void checkAgreeCheckbox(){
        try{
            ChainTestListener.log("Select agree checkbox button");
            if(!privacyPolicyCheckbox.isSelected())
                click(privacyPolicyCheckbox);
        }catch(NoSuchElementException ex){
            ChainTestListener.log("Element not found"+ex.getMessage());
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    public void selectSubscribe(String option) throws InterruptedException{
        try{
            ChainTestListener.log("Select NewsLetter subscription yes or no");
            if(option.equalsIgnoreCase("Yes")){
                ChainTestListener.log("Select Yes radio button");
                click(subscribeYesRadioBtn);
            }else{
                ChainTestListener.log("Select No radio button");
                click(subscribeNoRadioBtn);
            }
        }catch(NoSuchElementException ex){
            ChainTestListener.log("Element not found"+ex.getMessage());
        }
    }
    public String getRegisterPageTitle(){
        return getTitle();
    }

    public WebElement getAccountCreatedHeaderElement() throws InterruptedException{
        return accountCreatedHeader;
    }
    public String getAccountCreatedHeader() throws InterruptedException{
        return getText(accountCreatedHeader);
    }

    public String getAccountCreatedSuccessMsg() throws InterruptedException{
        return getText(accountCreatedSuccMsg);
    }

    public WebElement getAccountCreatedBreadCrumbSuccessLink(){
        return accountCreatedBreadCrumbSuccessLink;
    }

    public WebElement getAccountCreatedSuccMsg(){
        return accountCreatedSuccMsg;
    }

    public boolean isAccountCreatedSuccMsgExist(){
        return isDisplayed(accountCreatedSuccMsg);
    }

    public boolean isAccountCreatedBreadCrumbSuccLinkExist(){
        return isDisplayed(accountCreatedBreadCrumbSuccessLink);
    }
    public void clickOnAccountHasBeenCreatedContinueBtn() throws InterruptedException{
        try{
            ChainTestListener.log("click on accountCreatedContinueBtn");
            click(accountCreatedContinueBtn);
        }catch(NoSuchElementException ex){
            ChainTestListener.log("unable to perform click action on AccountCreatedContinueBtn");
            ex.printStackTrace();
        }
    }

    public void clickOnContactUsLink() throws InterruptedException{
        ChainTestListener.log("click on contactus link");
        click(contactusLink);
    }

    public void clickOnLoginPageLink() throws InterruptedException{
        click(registerAccountLoginLink);
    }
    public void clickBreadCrumbHomeIcon() throws InterruptedException{
        ChainTestListener.log("click on home icon in breadcrumb");
        click(homeIcon);
    }
    public String getFirstNameEditbox() throws InterruptedException{
        return getElementAttributeValue(firstNameEditbox,"value");
    }

    public void setFirstNameEditbox(String firstName) throws InterruptedException{
        type(firstNameEditbox,firstName);
    }

    public String getLastNameEditbox() throws InterruptedException{
        return getElementAttributeValue(lastNameEditbox,"value");
    }

    public void setLastNameEditbox(String lastName) throws InterruptedException{
        type(lastNameEditbox,lastName);
    }

    public String getEmailEditbox() throws InterruptedException{
        return getElementAttributeValue(emailEditbox,"value");
    }

    public void setEmailEditbox(String email) throws InterruptedException{
        type(emailEditbox,email);
    }

    public String getTelePhoneEditbox() throws InterruptedException{
        return getElementAttributeValue(telePhoneEditbox,"value");
    }

    public void setTelePhoneEditbox(String telePhone) throws InterruptedException{
        type(telePhoneEditbox,telePhone);
    }

    public String getPasswordEditbox() throws InterruptedException{
        return getElementAttributeValue(passwordEditbox,"value");
    }

    public void setPasswordEditbox(String password) throws InterruptedException{
        type(passwordEditbox,password);
    }

    public String getConfirmPasswordEditbox() throws InterruptedException{
        return getElementAttributeValue(confirmPasswordEditbox,"value");
    }

    public void setConfirmPasswordEditbox(String confirmPassword) throws InterruptedException{
        type(confirmPasswordEditbox,confirmPassword);
    }

    public void setPersonalDetails(String fname,String lname,String email,String tel) throws InterruptedException{
        setFirstNameEditbox(fname);
        setLastNameEditbox(lname);
        setEmailEditbox(email);
        setTelePhoneEditbox(tel);
    }

    public void setPassword(String pwd,String confirmPwd) throws InterruptedException{
        setPasswordEditbox(pwd);
        setConfirmPasswordEditbox(confirmPwd);
    }

}
