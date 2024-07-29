package pages;

import com.microsoft.playwright.Page;
import factory.BrowserFactory;

public class LoginPage {

    Page page;
    BrowserFactory browserFactory;

    //Login page locators
    private String inputEmail = "//*[@id='username']";
    private String inputPassword = "//input[@id='password']";
    private String continueButton = "//*[text() = 'Continue']";
    private String logIn = "(//*[text() = 'Log In'])[2]";

    //page constructor
    public LoginPage(Page _page){
      page = _page;
      browserFactory = new BrowserFactory();
    }

    //actions methods
    public void navigateToStagURL(){
        page.navigate(browserFactory.init_prop().getProperty("stagURL"));
        page.waitForLoadState();
    }

    public String getPageURL(){
        return page.url();
    }

    public void login(String email, String password){
        page.fill(inputEmail, email);
        page.click(continueButton);
        page.fill(inputPassword, password);
        page.click(logIn);
    }
}
