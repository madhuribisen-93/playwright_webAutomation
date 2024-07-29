package factory;

import com.microsoft.playwright.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class BrowserFactory {
    static Playwright playwright;
    static Browser browser;
    static BrowserContext browserContext;
    static Page page;
    Properties properties;

    public Page initBrowser(Properties properties) {
        String browserName = properties.getProperty("browser").trim();
        System.out.println("browser name is : " + browserName);
        playwright = Playwright.create();
        PageManager.setPlaywright(playwright);

        switch (browserName.toLowerCase().trim()) {
            case "chromium":
                System.out.println("Create Chromium browser...");
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "chrome":
                System.out.println("Create Chrome browser...");
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
                break;
            case "firefox":
                System.out.println("Create Firefox browser...");
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "safari":
                System.out.println("Create Safari browser...");
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            default:
                System.out.println("Set default chrome browser...");
                break;
        }
        browserContext = browser.newContext(new Browser.NewContextOptions().setIsMobile(true).setUserAgent("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Mobile Safari/537.36"));
       // browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(375, 667).setUserAgent("Mozilla/5.0 (Linux; Android 11; Pixel 5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.91 Mobile Safari/537.36"));
        browserContext = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get("executionVideo/")).setRecordVideoSize(890,650));
        page = browserContext.newPage();
        PageManager.setBrowser(browser);
        PageManager.setBrowserContext(browserContext);
        PageManager.setPage(page);
        return page;
    }

    /**
     * this method is used to initialize the properties from config file
     */
    public Properties init_prop() {
        try {
            FileInputStream fileInputStream = new FileInputStream("./src/main/resources/config/config.properties");
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     *
     * @return
     */
    public static String takeScreenshot() {
        String path = System.getProperty("./Users/madhuribisen/Automation/webAutomation_Playwright/ScreenShot") + "/screenshots/" + System.currentTimeMillis() + ".png";
        PageManager.getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)));
        return path;
    }
}
