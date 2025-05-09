package com.qa.opencart.testcases;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.TestBase;
import com.qa.opencart.factory.WebDriverFactory;
import com.qa.opencart.pages.*;
import com.qa.opencart.utilities.Constants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class MyAccountPageTest extends TestBase{

    @BeforeClass
    public void pageClassInstantiationSetup() throws InterruptedException, IOException{
        homePg = new HomePage(driver);
        regPg = new RegistrationPage(driver);
        loginPg = new LoginPage(driver);
        myaccountPg = new MyAccountPage(driver);
        logoutPg = new LogoutPage(driver);
        resultPg = new SearchResultsPage(driver);
        ChainTestListener.log("navigate to login page");
        homePg.navigateToLoginPage();
        loginPg.doLogin(WebDriverFactory.readPropertyValue("username"),WebDriverFactory.readPropertyValue("pwd"));
        myaccountPg.waitForPageLoad(1000);
        ChainTestListener.log("verify my account page title");
        Assert.assertEquals(myaccountPg.getTitle(),Constants.MYACCOUNT_PAGE_TITLE);
    }

    @Test(description="TC01_Verify my account page title")
    public void TC01_Verify_myAccount_Page_Title_Test(){
        ChainTestListener.log("TC01_Verify my account page title test");
        Assert.assertEquals(myaccountPg.getTitle(),Constants.MYACCOUNT_PAGE_TITLE);

    }

    @Test(description="TC02_Verify myaccount page url test")
    public void TC02_verify_myAccount_page_url_Test(){
        ChainTestListener.log("TC02_Verify myaccount page url test");
        Assert.assertTrue(myaccountPg.getMyAccountPageUrl().contains(Constants.MY_ACCOUNT_PAGE_FRACTION_URL));
    }

    @Test(description="TC03 Verify my account page elements test")
    public void TC03_verify_my_account_page_elements_Test(){
        ChainTestListener.log("TC03 Verify my account page elements test");
        Assert.assertTrue(myaccountPg.isAccountBreadCrumbExits());
        Assert.assertTrue(myaccountPg.isAccountPageDisplayed());

    }

    @Test(description="TC04 verify navigate to Edit account information page")
    public void TC04_verify_navigate_to_edit_account_information_page_test() throws InterruptedException{
        ChainTestListener.log("TC04 verify navigate to Edit account information page");
        myaccountPg.clickEditAccountInfoLink();
        myaccountPg.waitForPageLoad(1000);
        ChainTestListener.log("Verify EditAccountInfo page");
        Assert.assertTrue(myaccountPg.isMyAccountInformationHeaderDisplayed());
        ChainTestListener.log("navigate back to my account page");
        myaccountPg.clickBackButtonInEditAccountInfoPage();
        myaccountPg.waitForPageLoad(1000);
        ChainTestListener.log("Verify my account page title test");
        Assert.assertEquals(myaccountPg.getTitle(),Constants.MYACCOUNT_PAGE_TITLE);
    }


    @Test(description="TC05 verify navigate to change password page")
    public void TC05_verify_navigate_to_change_password_page_test() throws InterruptedException{
        ChainTestListener.log("TC05 verify navigate to change password page");
        myaccountPg.clickChangePasswordLink();
        myaccountPg.waitForPageLoad(1000);
        ChainTestListener.log("Verify change password page");
        Assert.assertTrue(myaccountPg.isChangePasswordHeaderDisplayed());
        ChainTestListener.log("navigate back to my account page");
        myaccountPg.clickBackButtonInEditAccountInfoPage();
        myaccountPg.waitForPageLoad(1000);
        ChainTestListener.log("Verify my account page title test");
        Assert.assertEquals(myaccountPg.getTitle(),Constants.MYACCOUNT_PAGE_TITLE);
    }
    @Test(description="TC06_Verify product search test",dataProvider="productTestData")
    public void TC06_Verify_product_Search_Test(String productName) throws InterruptedException, IOException{
        ChainTestListener.log("TC06_Verify_product_Search_Test()");
        myaccountPg.doProductSearch(productName);
        resultPg.waitForPageLoad(1000);
        ChainTestListener.log("verify results page title");
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(resultPg.getTitle(),"Search - "+productName);
        sa.assertTrue(resultPg.getProductListSize()>0);
        ChainTestListener.log("navigate back to my account page");
        resultPg.navigateToMyAccountPage();
        myaccountPg.waitForPageLoad(2000);
        sa.assertAll();
    }
    @DataProvider
    public Object[][] productTestData(){
        return  new Object[][]{
                {"MacBook"},
                {"Apple Cinema 30\""},
                {"Samsung"}
        };
    }

    @Test(description="TC07 verify logout from my account page test",dependsOnMethods={"TC06_Verify_product_Search_Test"})
    public void TC07_verify_logout_from_myaccountPage_test() throws InterruptedException, IOException{
        ChainTestListener.log("TC07 verify logout from my account page test");
       myaccountPg.doLogout();
       ChainTestListener.log(("verify logout page exists or not"));
       Assert.assertTrue(logoutPg.isAccountLoggedOffMsgExists());
        Assert.assertTrue(logoutPg.isAccountLogoutHeaderExists());
        Assert.assertTrue(logoutPg.isLogoutBreadCrumbExists());
        ChainTestListener.log(("click continue in logout page"));
        logoutPg.clickContinueBtn();
        homePg.waitForPageLoad(1000);
        Assert.assertEquals(homePg.getHomePageTitle(),Constants.HOME_PAGE_TITLE);
    }

}
