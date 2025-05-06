package com.qa.opencart.testcases;
import com.aventstack.chaintest.plugins.ChainTestListener;
import com.github.javafaker.Faker;
import com.qa.opencart.factory.TestBase;
import com.qa.opencart.factory.WebDriverFactory;
import com.qa.opencart.pages.*;
import com.qa.opencart.utilities.Constants;
import com.qa.opencart.utilities.ExcelUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RegistrationPageTest extends TestBase{
    String fName,lName,email,telephone;
    @BeforeTest
    public void testDataSetup(){
        ChainTestListener.log("Generating the Fake test data for Registration using jav Faker class..");
        Faker fkobj = new Faker(new Locale("en-US"));
        fName = fkobj.address().firstName();
        lName = fkobj.address().lastName();
        email = fkobj.internet().emailAddress();
        telephone = fkobj.phoneNumber().phoneNumber();

    }

    @BeforeClass
    public void pageClassInstantiationSetUp() throws InterruptedException, IOException{
        homePg = new HomePage(driver);
        regPg = new RegistrationPage(driver);
        loginPg = new LoginPage(driver);
        myaccountPg = new MyAccountPage(driver);
        logoutPg = new LogoutPage(driver);
    }

    @BeforeMethod
    public void navigateToRegisterPage() throws InterruptedException{
        ChainTestListener.log("navigate toRegistration page");
        homePg.navigateToRegisterPage();
        myaccountPg.waitForPageLoad(2000);
        ChainTestListener.log("Verify registration page title");
        Assert.assertEquals(regPg.getTitle(),Constants.REGISTRATION_PAGE_TITLE);
    }

    @Test(description="TC01_register an account with Faker test data")
    public void TC01_Verify_registering_with_Faker_Test(){
        ChainTestListener.log("TC01_Register an account with faker data test");
        try{
            ChainTestListener.log("Enter personal details");
            regPg.setPersonalDetails(fName,lName,email,telephone);
            ChainTestListener.log("Set the password");
            String pwd = WebDriverFactory.randomAlphaNumeric();
            regPg.setPasswordEditbox(pwd);
            regPg.setConfirmPasswordEditbox(pwd);
            ChainTestListener.log("Select the subscribe option yes or no");
            regPg.selectSubscribe("Yes");
            ChainTestListener.log("Select the agree checkbox");
            regPg.checkAgreeCheckbox();
            ChainTestListener.log("click on Continue button");
            regPg.clickOnContinueButton();
            regPg.waitForPageLoad(2000);
            ChainTestListener.log("Verify the Account Creation success message and header text");
            wait.until(ExpectedConditions.visibilityOf(regPg.getAccountCreatedBreadCrumbSuccessLink()));
            wait.until(ExpectedConditions.visibilityOf(regPg.getAccountCreatedHeaderElement()));
            wait.until(ExpectedConditions.visibilityOf(regPg.getAccountCreatedSuccMsg()));
            Assert.assertEquals(regPg.getAccountCreatedHeader(),Constants.YOUR_ACCNT_CREATED_HEADER);
            Assert.assertEquals(regPg.getAccountCreatedSuccessMsg(),Constants.YOUR_ACCNT_CREATED_SUCC_MSG);
            ChainTestListener.log("Click on Continue button in account created success page");
            regPg.clickOnAccountHasBeenCreatedContinueBtn();
            ChainTestListener.log("wait for the My Account creation page");
            myaccountPg.waitForPageLoad(2000);
            Assert.assertTrue(myaccountPg.getMyAccountPageUrl().contains(Constants.MY_ACCOUNT_PAGE_FRACTION_URL));
        }catch(InterruptedException e){
            ChainTestListener.log("account creation failed");
            e.printStackTrace();
        }
    }
    @Test(description="TC02_register an account with excel test data",dataProvider="exceldata")
    public void TC02_Verify_registering_with_excel_Test(String fName,String lName,String telephone,String pwd,String subscribe){
        ChainTestListener.log("TC02_Register an account with faker data test");
        try{
            ChainTestListener.log("Enter personal details");
            String randomEmail = WebDriverFactory.randomeString()+"@gmail.com";
            regPg.setPersonalDetails(fName,lName,randomEmail,telephone);
            ChainTestListener.log("Set the password");
            regPg.setPasswordEditbox(pwd);
            regPg.setConfirmPasswordEditbox(pwd);
            ChainTestListener.log("Select the subscribe option yes or no");
            regPg.selectSubscribe(subscribe);
            ChainTestListener.log("Select the agree checkbox");
            regPg.checkAgreeCheckbox();
            ChainTestListener.log("click on Continue button");
            regPg.clickOnContinueButton();
            regPg.waitForPageLoad(2000);
            ChainTestListener.log("Verify the Account Creation success message and header text");
            wait.until(ExpectedConditions.visibilityOf(regPg.getAccountCreatedBreadCrumbSuccessLink()));
            wait.until(ExpectedConditions.visibilityOf(regPg.getAccountCreatedHeaderElement()));
            wait.until(ExpectedConditions.visibilityOf(regPg.getAccountCreatedSuccMsg()));
            Assert.assertEquals(regPg.getAccountCreatedHeader(),Constants.YOUR_ACCNT_CREATED_HEADER);
            Assert.assertEquals(regPg.getAccountCreatedSuccessMsg(),Constants.YOUR_ACCNT_CREATED_SUCC_MSG);
            ChainTestListener.log("Click on Continue button in account created success page");
            regPg.clickOnAccountHasBeenCreatedContinueBtn();
            ChainTestListener.log("wait for the My Account creation page");
            myaccountPg.waitForPageLoad(2000);
            Assert.assertTrue(myaccountPg.getMyAccountPageUrl().contains(Constants.MY_ACCOUNT_PAGE_FRACTION_URL));
        }catch(InterruptedException e){
            ChainTestListener.log("account creation failed");
            e.printStackTrace();
        }
    }

    @DataProvider(name="exceldata")
    public Object[][] excelTestData() throws IOException, InvalidFormatException{
        Object[][] data = new ExcelUtils().getTestData(Constants.TEST_DATA_FILE_PATH,Constants.REGISTER_SHEET_NAME);
        return data;
    }


    @AfterMethod
    public void TC03_Verify_logout_Test() throws InterruptedException, IOException{
        ChainTestListener.log("TC03_Verify_logout_Test()");
        myaccountPg.doLogout();
        logoutPg.waitForPageLoad(2000);
        ChainTestListener.log(" verify logout page title");
        Assert.assertEquals(logoutPg.getTitle(),Constants.LOGOUT_PAGE_TITLE);
        ChainTestListener.log(" click continue in logout page");
        logoutPg.clickContinueBtn();
        homePg.waitForPageLoad(2000);
        Assert.assertEquals(homePg.getHomePageTitle(),Constants.HOME_PAGE_TITLE);
    }
}
