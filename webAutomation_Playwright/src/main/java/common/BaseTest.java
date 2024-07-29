package common;

import com.microsoft.playwright.Page;
import factory.BrowserFactory;
import factory.PageManager;
import org.testng.annotations.*;


import java.util.Properties;

public class BaseTest {
    BrowserFactory browserFactory;
    protected Properties properties;
    Page page;

    @BeforeMethod
    public void setup() {
        browserFactory = new BrowserFactory();
        properties = browserFactory.init_prop();
        page = browserFactory.initBrowser(properties);
    }

    @AfterMethod
    public void tearDown() {
        PageManager.closePage();
        PageManager.closeBrowser();
    }
}
