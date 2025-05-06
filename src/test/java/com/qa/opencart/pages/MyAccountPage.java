package com.qa.opencart.pages;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.utilities.Constants;
import com.qa.opencart.utilities.WebDriverUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyAccountPage extends WebDriverUtils{
WebDriverWait wait;
    public MyAccountPage(WebDriver driver){
        super(driver);
        wait = new WebDriverWait(driver,Duration.ofMillis(Constants.EXPLICIT_WAIT));
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath="//a[normalize-space()='Edit your account information']")
    private WebElement editAccountInfoLink;

    @FindBy(xpath="//h2[normalize-space()='My Account']")
    private WebElement myAccountHeader;

    @FindBy(xpath="//a[normalize-space()='Change your password']")
    private WebElement changePasswordLink;

    @FindBy(xpath="//a[normalize-space()='Address Book']")
    private WebElement addressBookLink;

    @FindBy(xpath="//a[normalize-space()='View your order history']")
    private WebElement orderHistoryLink;

    @FindBy(xpath="//a[normalize-space()='Your Transactions']")
    private WebElement transactionsLink;

    @FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Recurring payments']")
    private WebElement recurringPaymentsLink;

    @FindBy(xpath="//ul[@class='list-inline']//li[@class='dropdown']")
    private WebElement myAccountDropdown;

    @FindBy(xpath="//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Logout']")
    private WebElement logoutLink;

    @FindBy(xpath="//i[@class='fa fa-home']")
    private WebElement homeIcon;

    @FindBy(xpath="//ul[@class='breadcrumb']//a[normalize-space()='Account']")
    private WebElement accountBreadCrumb;

    @FindBy(xpath="//input[@placeholder='Search']")
    private WebElement searchEditbox;

    @FindBy(xpath="//h1[normalize-space()='My Account Information']")
    private WebElement myAccountInformationHeader;

    @FindBy(xpath="//h1[normalize-space()='Change Password']")
    private WebElement changePasswordHeader;
    @FindBy(xpath="//a[normalize-space()='Back']")
    private WebElement backBtn;

    @FindBy(xpath="//button[@class='btn btn-default btn-lg']")
    private WebElement searchTorchIcon;
    @Step("Get My Account Page Title")
    public String getMyAccountPageTitle(){
        return getTitle();
    }

    @Step("Verify Change password header")
  public boolean isChangePasswordHeaderDisplayed(){
      return changePasswordHeader.isDisplayed();
    }


    @Step("Get My Account Page url")
    public String getMyAccountPageUrl(){
        return waitForUrlContains(Constants.MY_ACCOUNT_PAGE_FRACTION_URL);
    }
    @Step("check if my account Page is displayed")
    public boolean isAccountPageDisplayed(){
        return myAccountHeader.isDisplayed();
    }

    @Step("check if my account information header is displayed")
    public boolean isMyAccountInformationHeaderDisplayed(){
        return myAccountInformationHeader.isDisplayed();
    }

    @Step("check my account breadcrumb is displayed")
    public boolean isAccountBreadCrumbExits(){
        return accountBreadCrumb.isDisplayed();
    }
    @Step("click on Edit Account information link")
    public void clickEditAccountInfoLink() throws InterruptedException{
        click(editAccountInfoLink);
    }

    @Step("click on back button in My Edit Account information page")
    public void clickBackButtonInEditAccountInfoPage() throws InterruptedException{
        click(backBtn);
    }

    @Step("click on change password link")
    public void clickChangePasswordLink() throws InterruptedException{
        ChainTestListener.log("clicking on changePasswordLink...");
        click(changePasswordLink);
    }
    @Step("click on Address book link")
    public void clickAddressBookLink() throws InterruptedException{
        ChainTestListener.log("click on addressBookLink..");
        click(addressBookLink);
    }

    @Step("click on OrderHistory link")
    public void clickOrderHistoryLink() throws InterruptedException{
        ChainTestListener.log("click on clickOrderHistoryLink");
        click(orderHistoryLink);
    }
    @Step("click on Transactions link")
    public void clickTransactionLink() throws InterruptedException{
        ChainTestListener.log("click on clickTransactionLink");
        click(transactionsLink);
    }

    @Step("click on Recurring payments link")
    public void clickRecurringPaymentsLink() throws InterruptedException{
        ChainTestListener.log("click on clickRecurringPaymentsLink");
        click(recurringPaymentsLink);
    }
    @Step("Logout from my Account")
    public HomePage  doLogout() throws InterruptedException{
        ChainTestListener.log("perform the logout actions..");
        click(myAccountDropdown);
        ChainTestListener.log("click logout option.");
     click(logoutLink);
       return new HomePage(driver);
    }

    @Step("Perform the search")
    public void doProductSearch(String productName) throws InterruptedException{
        ChainTestListener.log("perform the search for.."+productName);
        clearText(searchEditbox);
        type(searchEditbox,productName);
        ChainTestListener.log("clickon search torch icon...");
        click(searchTorchIcon);
    }

}
