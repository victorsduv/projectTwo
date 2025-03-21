package academy.teenfuture.projectTwo;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.NavigateOptions;
import com.microsoft.playwright.Playwright;

import io.github.cdimascio.dotenv.Dotenv;

public class MemberBase {
    protected static ExtentReports extent;
    static String path;
    static Playwright playwright;
    public static Page page;
    protected static String currentClass = "";
    protected static Action action = new Action();
    protected static Dotenv dotenv = Dotenv.load();

    @BeforeAll
    public static void setUp() {
        extent = new ExtentReports();
        playwright = Playwright.create();
        BrowserType browserType = playwright.chromium();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = dimension.width;
        int height = dimension.height;
        
        page = browserType.launch(new BrowserType
                .LaunchOptions()
                .setHeadless(false))
                .newContext(new Browser.NewContextOptions().setViewportSize(width, height)).newPage();

        NavigateOptions options = new Page.NavigateOptions();

        page.navigate("https://www.casetify.com/product/camera-lens-protector#/16007248", options);

        try {
            Locator accept_button = page.locator("//div[@data-label='accept-all-cookies-button']");
            if (accept_button.count() > 0) {
                accept_button.click();
            }
            FrameLocator frame = page.frameLocator("//iframe[@id='cms-popup-iframe']");
            Locator cms_close_btn = frame.locator("//button[@aria-label='Close']");
            if (cms_close_btn.count() > 0) {
                cms_close_btn.click();    
            }
            action.login(page, dotenv);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @AfterAll
    public static void close() throws IOException {
        playwright.close();
        LocalDateTime currentDateTime = LocalDateTime.now();
        String dateTimeSString = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        path = System.getProperty("user.dir") + "/testresult/index-" + dateTimeSString + "_" + currentClass + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(path);
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("Automation Testing Report");
        extent.attachReporter(spark);
        System.setProperty("java.awt.headless", "false");
        extent.flush();
    }
}
