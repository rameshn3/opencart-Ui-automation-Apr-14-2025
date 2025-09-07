package com.qa.opencart.testcases;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.TestBase;
import com.qa.opencart.factory.WebDriverFactory;
import com.qa.opencart.pages.*;
import com.qa.opencart.utilities.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.List;

public class MyAccountPageTest extends TestBase{
public HomePage homePage;
public RegisterPage registerPage;

public LoginPage loginPage;

public MyAccountPage myAccountPage;
public LogoutPage logoutPage;

public ResultsPage resultsPage;
    @BeforeClass
    public void pageClassesSetup() throws InterruptedException, IOException{
        PageFactoryManager pageFactory = new PageFactoryManager(driver);
        homePage= pageFactory.getHomePage();
        registerPage = pageFactory.getRegisterPage();
        loginPage = pageFactory.getLoginPage();
        myAccountPage = pageFactory.getMyAccountPage();
        logoutPage = pageFactory.getLogoutPage();
        resultsPage = pageFactory.getResultsPage();
    ChainTestListener.log("navigate to login page");
    homePage.navigateToLoginrPage();
        loginPage.doLogin(WebDriverFactory.readPropertyValue("username"),WebDriverFactory.readPropertyValue("pwd"));
        myAccountPage.waitForPageLoad(1000);

        ChainTestListener.log("verify the my account page title");
        Assert.assertEquals(myAccountPage.getMyAccountPageTitle(),Constants.MYACCOUNT_PAGE_TITLE);
    }

    @Test(description ="TC01_Verify_My_Account_page_title_Test")
 public void TC01_Verify_My_Account_page_title_Test(){
        ChainTestListener.log("TC01_Verify_My_Account_page_title_Test");
        Assert.assertEquals(myAccountPage.getMyAccountPageTitle(),Constants.MYACCOUNT_PAGE_TITLE);
    }

    @Test(description ="TC02_Verify_My_Account_page_URL_Test",dependsOnMethods={"TC01_Verify_My_Account_page_title_Test"})
    public void TC02_Verify_My_Account_page_URL_Test(){
        ChainTestListener.log("TC02_Verify_My_Account_page_URL_Test");
        Assert.assertTrue(myAccountPage.getMyAccountPageUrl().contains(Constants.MY_ACCOUNT_PAGE_FRACTION_URL),"lMy account page url is not loaded");
    }


    @Test(description =" TC03_Verify_My_Account_page_elemnts_existance_Test",dependsOnMethods={"TC02_Verify_My_Account_page_URL_Test"})
    public void TC03_Verify_My_Account_page_elemnts_existance_Test(){
        ChainTestListener.log(" TC03_Verify_My_Account_page_elemnts_existance_Test");
        Assert.assertTrue(myAccountPage.isMyAccountBreadCrumbExists(),"My Account breadcrumb exists ");
        Assert.assertTrue(myAccountPage.isLogoutExists(),"Logout exists under MyAccount enu");
       myAccountPage.pressEscapeKey();
       }

    @Test(description ="TC04_Verify my account menu options test",dependsOnMethods={"TC03_Verify_My_Account_page_elemnts_existance_Test"})
    public void TC04_Verify_my_account_Menu_Options_Test() throws InterruptedException{
        ChainTestListener.log("TC04_Verify_my_account_Menu_Options_Test");
        ChainTestListener.log("Verify My account menu options");
        Assert.assertEquals(myAccountPage.getMyAccountMenuOptionList(),Constants.EXPECTED_MYACCOUNT_MENU_OPTIONS_LIST);

    }

    @Test(description ="TC05_Verify_my_Account_Header_Options_List_Test",dependsOnMethods={"TC04_Verify_my_account_Menu_Options_Test"})
    public void TC05_Verify_my_Account_Header_Options_List_Test() throws InterruptedException{
        ChainTestListener.log("TC05_Verify_my_Account_Header_Options_List_Test");
        Assert.assertEquals(myAccountPage.getMyAccountHeaderOptionsList(),Constants.EXPECTED_MYACCOUNT_HEADER_OPTIONS_LIST);
    }

    @Test(description ="TC06_Verify_broken_links_Test",dependsOnMethods={"TC05_Verify_my_Account_Header_Options_List_Test"})
    public void TC06_Verify_broken_links_Test() throws InterruptedException, IOException{
        ChainTestListener.log("TC06_Verify_broken_links_Test");
        ChainTestListener.log("fetch all the links from webpage");
        List<WebElement> myAccountPageLinksList = driver.findElements(By.tagName("a"));
      for(WebElement link:myAccountPageLinksList){
          verifyUrls(link.getAttribute("href"));
      }
      myAccountPage.pressEscapeKey();
    }

    @Test(description ="TC07_Verify_Product_Search_Test",dataProvider="productTestData")
    public void TC07_Verify_Product_Search_Test(String productName) throws InterruptedException, IOException{
        ChainTestListener.log("TC07_Verify_Product_Search_Test");
        resultsPage = myAccountPage.doProductSearch(productName);

        resultsPage.waitForPageLoad(1000);
        SoftAssert sa = new SoftAssert();
        ChainTestListener.log("verify results page title");
        sa.assertEquals(resultsPage.getResultsPageTitle(),"Search - "+productName);
        sa.assertTrue(resultsPage.getProductListSize()>0);
        ChainTestListener.log("navigate back to my account page");
        resultsPage.clickMyAccountMenu();
        myAccountPage.waitForPageLoad(2000);
        sa.assertAll();
    }

    @DataProvider
    public Object[][] productTestData(){
        return new Object[][]{
                {"MacBook"},
                {"Apple Cinema 30\""},
                {"Samsung"}
        };
    }

    @Test(description="TC08_Verify_logout_from_My_Account_Page",dependsOnMethods={"TC07_Verify_Product_Search_Test"})
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

}
