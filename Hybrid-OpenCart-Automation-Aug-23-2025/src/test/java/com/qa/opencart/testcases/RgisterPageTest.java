package com.qa.opencart.testcases;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.github.javafaker.Faker;
import com.qa.opencart.factory.TestBase;
import com.qa.opencart.factory.WebDriverFactory;
import com.qa.opencart.pages.*;
import com.qa.opencart.utilities.Constants;
import com.qa.opencart.utilities.ExcelUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;

public class RgisterPageTest extends TestBase{
public HomePage homePage;
public RegisterPage registerPage;

public LoginPage loginPage;
    public MyAccountPage myAccountPage;
    public LogoutPage logoutPage;
    String fname,lname,email,telephone;

    @BeforeTest
    public void testDataSetup(){
        ChainTestListener.log("Generating the Fake test data using Faker class");
        Faker fkObj = new Faker();
        fname = fkObj.address().firstName();
        lname = fkObj.address().lastName();
        email = fkObj.internet().emailAddress();
        telephone = fkObj.phoneNumber().phoneNumber();
    }

    @BeforeClass
    public void pageClassesSetup(){
        PageFactoryManager pageFactory = new PageFactoryManager(driver);
        homePage= pageFactory.getHomePage();
        registerPage = pageFactory.getRegisterPage();
        loginPage = pageFactory.getLoginPage();
        myAccountPage = pageFactory.getMyAccountPage();
        logoutPage = pageFactory.getLogoutPage();
    }

    @BeforeMethod
    public void navigate_to_register_page_from_home_page_Test() throws InterruptedException{
        ChainTestListener.log("TC04_Verify_navigate_to_register_page_from_home_page_Test");
        homePage.navigateToRegisterPage();
        registerPage.waitForPageLoad(1000);
        ChainTestListener.log("Verifyregister_page_title");
        Assert.assertEquals(registerPage.getRegisterPageTitle(),Constants.REGISTRATION_PAGE_TITLE);

    }

    @Test(description ="TC01_Verify register an account using faker data")
 public void TC01_Verify_register_an_account_using_faker_test_data_Test(){
        ChainTestListener.log("TC01_Verify_register_an_account_using_faker_test_data_Test");
       try{
           ChainTestListener.log("Enter personal details");
           registerPage.setpersonalDetails(fname,lname,email,telephone);
           ChainTestListener.log("Generate the password");
           String pwd =WebDriverFactory.randomAlphaNumeric();
           registerPage.setPasswordEditbox(pwd);
           registerPage.setConfirmPasswordEditbox(pwd);
           ChainTestListener.log("Select the subscribe option yes or no");
           registerPage.selectSubscribe("Yes");
           ChainTestListener.log("ceck the agree checkbox");
           registerPage.checkAgreeCheckbox();
           ChainTestListener.log("click on continue button");
           registerPage.clickOnContinueButton();
           registerPage.waitForPageLoad(2000);
           ChainTestListener.log("Verify account created success message and header text");
            wait.until(ExpectedConditions.visibilityOf(registerPage.getAccountCreatedHeaderExists()));
           wait.until(ExpectedConditions.visibilityOf(registerPage.getAccountCreatedBreadCrumbSuccessLinkExists()));
          ChainTestListener.log("Click on Continue button in account created register page");
           registerPage.clickOnAccountCreatedContinueButton();
           myAccountPage.waitForPageLoad(2000);
           ChainTestListener.log("verify the my account page title");
           Assert.assertEquals(myAccountPage.getMyAccountPageTitle(),Constants.MYACCOUNT_PAGE_TITLE);

       }catch(Exception e){
           ChainTestListener.log("Account created failed:"+e.getMessage());
           e.printStackTrace();
       }
    }
    @AfterMethod
    public void TC08_Verify_logout_from_My_Account_Page_Test() throws InterruptedException{
        ChainTestListener.log("TC08_Verify_logout_from_My_Account_Page_Test..has started");
        myAccountPage.clickLogoutLink();
        logoutPage.waitForPageLoad(2000);
        ChainTestListener.log("Verify logout page title");
        Assert.assertEquals(logoutPage.getLogoutPageTitle(),Constants.LOGOUT_PAGE_TITLE);
        ChainTestListener.log("click continue button in logout page..");
        logoutPage.clickContinueBtn();
        homePage.waitForPageLoad(1000);
        Assert.assertEquals(homePage.getHomePageTitle(),Constants.HOME_PAGE_TITLE);

    }
    @Test(description ="TC02_Verify_registering_an_account_with_excel_sheet_data_Test",dataProvider="excelTestData")
    public void TC02_Verify_registering_an_account_with_excel_sheet_data_Test(String fName,String lName,String tel,String pwd,String subscribe){
        ChainTestListener.log("TC02_Verify_registering_an_account_with_excel_sheet_data_Test");
       int iteration =1;
        try{
            ChainTestListener.log("Enter personal details");
            String randomEmail = WebDriverFactory.randomeString()+"@gmail.com";
            registerPage.setpersonalDetails(fname,lname,randomEmail,telephone);
            ChainTestListener.log("Generate the password");
            registerPage.setPasswordEditbox(pwd);
            registerPage.setConfirmPasswordEditbox(pwd);
            ChainTestListener.log("Select the subscribe option yes or no");
            registerPage.selectSubscribe("Yes");
            ChainTestListener.log("ceck the agree checkbox");
            registerPage.checkAgreeCheckbox();
            ChainTestListener.log("click on continue button");
            registerPage.clickOnContinueButton();
            registerPage.waitForPageLoad(2000);
            ChainTestListener.log("Verify account created success message and header text");
            wait.until(ExpectedConditions.visibilityOf(registerPage.getAccountCreatedHeaderExists()));
            wait.until(ExpectedConditions.visibilityOf(registerPage.getAccountCreatedBreadCrumbSuccessLinkExists()));
            ChainTestListener.log("Click on Continue button in account created register page");
            registerPage.clickOnAccountCreatedContinueButton();
            myAccountPage.waitForPageLoad(2000);
            ChainTestListener.log("verify the my account page title");
            Assert.assertEquals(myAccountPage.getMyAccountPageTitle(),Constants.MYACCOUNT_PAGE_TITLE);
            ChainTestListener.log("Accout "+iteration+" has been created successfully");
            iteration++;
        }catch(Exception e){
            ChainTestListener.log("Account created failed:"+e.getMessage());
            e.printStackTrace();
        }
    }


    @DataProvider
    public Object[][] excelTestData() throws IOException, InvalidFormatException{
        Object[][] data = new ExcelUtils().getTestData(Constants.TEST_DATA_FILE_PATH,Constants.REGISTER_SHEET_NAME);
   return data;
    }

}
