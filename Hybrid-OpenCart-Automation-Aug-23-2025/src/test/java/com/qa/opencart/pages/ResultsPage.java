package com.qa.opencart.pages;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.utilities.Constants;
import com.qa.opencart.utilities.TimeUtils;
import com.qa.opencart.utilities.WebDriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ResultsPage extends WebDriverUtils{
    public ResultsPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(css="div[id='content'] h1")
    private WebElement searchResultsHeader;

    @FindBy(xpath="//*[@id='product-search']/ul/li[2]/a")
    private WebElement searchBreadCrumb;

    @FindBy(css="div[class*='product-layout product-grid']")
    private List<WebElement> searchProductList;

      @FindBy(xpath="//span[normalize-space()='My Account']")
    private WebElement myAccountMenu;

    @FindBy(xpath="//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='My Account']")
    private WebElement myAccountOption;

    @FindBy(xpath="//i[@class='fa fa-home']")
    private WebElement homeIcon;

       public String getResultsPageTitle(){
        return getTitle();
    }

      public void clickMyAccountMenu(){
       try{
           ChainTestListener.log("Click on my account menu");
           click(myAccountMenu);
           ChainTestListener.log("Click on my account option");
           click(myAccountOption);
       }catch(NoSuchElementException|InterruptedException ex){
           ChainTestListener.log("Unable to click on my account menu");
           ex.printStackTrace();
       }
    }

    public boolean isSearchResultsHeaderExists(){
           return searchResultsHeader.isDisplayed();
    }
    public boolean isSearchResultsBreadCrumbExists(){
        return searchBreadCrumb.isDisplayed();
    }

    public void selectProduct(String productName) throws InterruptedException{
           try{

               ChainTestListener.log("selecting the desired product from results:"+productName);
               click(getElement(By.linkText(productName)));
               TimeUtils.smallWait();
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

    public int getProductListSize(){
ChainTestListener.log("fetch the total number of products count");
return searchProductList.size();


    }


}
