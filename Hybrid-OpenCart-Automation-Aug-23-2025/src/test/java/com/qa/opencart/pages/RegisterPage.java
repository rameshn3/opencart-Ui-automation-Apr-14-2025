package com.qa.opencart.pages;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.utilities.WebDriverUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage extends WebDriverUtils{
    public RegisterPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(css="div[id='content'] h1")
    private WebElement registerAccountHeader;

    @FindBy(css="div[id='content'] p a")
    private WebElement registerAccountLoginLink;

    @FindBy(css="fieldset[id='account'] legend")
    private WebElement personalDetailLegendText;

    @FindBy(id="input-firstname")
    private WebElement firstNameEditbox;

       @FindBy(name="lastname")
    private WebElement lastNameEditbox;

    @FindBy(css="#input-email")
    private WebElement emailEditbox;

    @FindBy(css="#input-telephone")
    private WebElement telephoneEditbox;
    @FindBy(id="input-password")
    private WebElement passwordEditbox;
    @FindBy(id="input-confirm")
    private WebElement confirmPasswordEditbox;
    @FindBy(xpath="//input[@name='newsletter' and @value='1']")
    private WebElement subscribeYesRadioButton;
    @FindBy(xpath="//i[@class='fa fa-home']")
    private WebElement homeIcon;
    @FindBy(xpath="//input[@name='newsletter' and @value='0']")
    private WebElement subscribeNoRadioButton;

    @FindBy(xpath="//input[@name='agree']")
    private WebElement privacypolicyCheckbox;

    @FindBy(xpath="//input[@value='Continue']")
    private WebElement continueButton;

    @FindBy(xpath="//div[@class='alert alert-danger alert-dismissible']")
    private WebElement agreeErrorMessage;

    @FindBy(xpath="//ul[@class='breadcrumb']//a[normalize-space()='Register']")
    private WebElement registerBreadCrumb;

    @FindBy(css="div[id='content'] h1")
    private WebElement accountCreatedHeader;

    @FindBy(xpath="//p[contains(text(),'Congratulations! Your new account has been success')]")
    private WebElement accountCreatedSucMsg;

    @FindBy(xpath="//*[@id='common-success']/ul/li[3]/a")
    private WebElement accountCreatedBreadCrumbSuccessLink;

    @FindBy(xpath="//*[@id='content']/div/div/a")
    private WebElement accountCreatedContinueBtn;

    public String getRegisterPageTitle(){
        return getTitle();
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

    public String getTelephoneEditbox() throws InterruptedException {
        return getElementAttributeValue(telephoneEditbox, "value");
    }

    public void setTelephoneEditbox(String telephone) throws InterruptedException {
        type(telephoneEditbox, telephone);
    }

    public String getPasswordEditbox() throws InterruptedException {
        return getElementAttributeValue(passwordEditbox, "value");
    }

    public void setPasswordEditbox(String password) throws InterruptedException {
        type(passwordEditbox, password);
    }

    public String getConfirmPasswordEditbox() throws InterruptedException {
        return getElementAttributeValue(confirmPasswordEditbox, "value");
    }

    public void setConfirmPasswordEditbox(String confirmPassword) throws InterruptedException {
        type(confirmPasswordEditbox, confirmPassword);
    }

    public void navigateToLoginPage(){
        try{
            ChainTestListener.log("click on login link");
            click(registerAccountLoginLink);
        }catch(NoSuchElementException|InterruptedException ex){
            ChainTestListener.log("unable to click on login link");
        }
    }


    public boolean isRegisterAccountHeaderExists(){
        return registerAccountHeader.isDisplayed();
    }

    public boolean isPersonalDetailLegendTextExists(){
        return personalDetailLegendText.isDisplayed();
    }

    public boolean isRegisterBreadCrumbExists(){
        return registerBreadCrumb.isDisplayed();
    }

    public boolean isAccountCreatedHeaderExists(){
        return accountCreatedHeader.isDisplayed();
    }

    public boolean isAccountCreatedBreadCrumbSuccessLinkExists(){
        return accountCreatedBreadCrumbSuccessLink.isDisplayed();
    }
    public WebElement getAccountCreatedBreadCrumbSuccessLinkExists(){
        return accountCreatedBreadCrumbSuccessLink;
    }

    public WebElement getAccountCreatedHeaderExists(){
        return accountCreatedHeader;
    }

    public void clickOnContinueButton() throws InterruptedException{
        ChainTestListener.log("click on continue button");
        try{
            if(continueButton.isDisplayed()&&continueButton.isEnabled()){
                click(continueButton);
            }

        }catch(NoSuchElementException ex){

            ex.printStackTrace();
        }

    }

    public void clickOnAccountCreatedContinueButton() throws InterruptedException{
        ChainTestListener.log("click on OnAccountCreated continue button");
        try{
            if(accountCreatedContinueBtn.isDisplayed()&&accountCreatedContinueBtn.isEnabled()){
                click(accountCreatedContinueBtn);
            }

        }catch(NoSuchElementException ex){

            ex.printStackTrace();
        }

    }

    public void selectSubscribe(String option) throws InterruptedException{
       try{
           ChainTestListener.log("Select newsletter subscription option - ys or no");
           if(option.equalsIgnoreCase("Yes")){
               ChainTestListener.log("select yes radio button");
               click(subscribeYesRadioButton);
           }else{
               ChainTestListener.log("select no radio button");
               click(subscribeNoRadioButton);
           }
       }catch(java.util.NoSuchElementException ex){
           ChainTestListener.log("Element not found :"+ex.getMessage());
       }
    }

    public void checkAgreeCheckbox() throws InterruptedException{
       try{
           ChainTestListener.log("Select agree checkbox button");
           if(!privacypolicyCheckbox.isSelected()){
               click(privacypolicyCheckbox);
           }
       }catch(NoSuchElementException ex){
          ChainTestListener.log("Element not found "+ex.getMessage());
       }catch(InterruptedException e){
           throw new RuntimeException(e);
       }
    }

    public void navigateToHomePage() throws InterruptedException{
        click(homeIcon);
    }

    public String getAgreeWarningErrorMsg(){
        return agreeErrorMessage.getText();
    }

    public String getAccountCreatedSucMsg(){
        return accountCreatedSucMsg.getText();
    }

    public void setpersonalDetails(String fname,String lname,String email,String tel) throws InterruptedException{
        setFirstNameEditbox(fname);
        setLastNameEditbox(lname);
        setEmailEditbox(email);
        setTelephoneEditbox(tel);
    }

    public void setPassword(String pwd, String confirmPwd) throws InterruptedException{
        setPasswordEditbox(pwd);
        setConfirmPasswordEditbox(confirmPwd);
    }
}
