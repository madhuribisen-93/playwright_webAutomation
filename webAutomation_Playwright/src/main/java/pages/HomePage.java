package pages;

import com.microsoft.playwright.Page;
import factory.BrowserFactory;

public class HomePage {

    Page page;
    BrowserFactory browserFactory;

    //Login page locators
    private String dashboardIcon = "(//*[@class='col s2 icon-Dashboard'])[1]";
    private String dashboardText = "(//span[normalize-space()='Dashboard'])[1]";
    private String rewardsPay = "(//span[normalize-space()='RewardsPay'])[1]";
    private String offers = "//a[normalize-space()='Offers']";
    private String bankOffer = "//a[normalize-space()='Bank Offers']";
    private String brandStoreAnalytics = "//a[normalize-space()='Brand Store Analytics']";
    private String sellerHelpDesk = "//span[normalize-space()='Seller Helpdesk']";

    //page constructor
    public HomePage(Page _page){
      page = _page;
      browserFactory = new BrowserFactory();
    }

    //actions methods
    public void navigateToStagURL(){
    page.isVisible(dashboardIcon);

    }
}
