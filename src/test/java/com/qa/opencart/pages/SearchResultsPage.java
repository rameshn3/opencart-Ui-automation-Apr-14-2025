package com.qa.opencart.pages;
import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.utilities.WebDriverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import java.util.NoSuchElementException;

public class SearchResultsPage extends WebDriverUtils{
    private Logger log = LogManager.getLogger(SearchResultsPage.class);

    //constructor
    public SearchResultsPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(css="#content h1")
    private WebElement searchResultHeader;

    @FindBy(xpath="//ul[@class='breadcrumb']/li[2]/a")
    private WebElement searchBreadcrumb;

    @FindBy(css="div[class*='product-layout product-grid']")
    private List<WebElement> searchProductList;

    @FindBy(xpath="//i[@class='fa fa-home']")
    private WebElement homeIcon;

    @FindBy(css="a[title='My Account'] span[class='hidden-xs hidden-sm hidden-md']")
    private WebElement myAccountMenu;

    @FindBy(xpath="//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='My Account']")
    private WebElement myAccountMenuOption;

    public String getResultsPageTitle(){
        return getTitle();
    }

    public boolean isSearchResultHeaderExists(){
        return isDisplayed(searchResultHeader);
    }

    public boolean isSearchBreadcrumbExists(){
        return isDisplayed(searchBreadcrumb);
    }

    public void navigateToMyAccountPage() throws InterruptedException{
        try{
            ChainTestListener.log("click on my account menu");
            click(myAccountMenu);
            ChainTestListener.log("click on my account option");
            click(myAccountMenuOption);
        }catch(NoSuchElementException ex){
            ChainTestListener.log("not able to navigate to my account page");
            ex.printStackTrace();
        }

    }

    public void selectProduct(String productName) throws InterruptedException{
        try{
            ChainTestListener.log("selecting the desired product from results:"+productName);
            click(getElement(By.linkText(productName)));
         //   TimeUtils.smallWait();
        }catch(NoSuchElementException ex){

        }

    }

    public int getProductListSize() throws InterruptedException{
        ChainTestListener.log("fetch the total products count in search results page");
        return searchProductList.size();
    }

    public void navigateToHomePage() throws InterruptedException{
        try{
            ChainTestListener.log("navigate to home page");
            click(homeIcon);
            //TimeUtils.smallWait();
        }catch(NoSuchElementException ex){

        }

    }
}
