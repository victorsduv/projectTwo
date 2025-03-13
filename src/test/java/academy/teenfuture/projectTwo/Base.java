package academy.teenfuture.projectTwo;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public abstract class Base {
    protected static ExtentReports extent = new ExtentReports();
    static String path;
    static Playwright playwright;
    protected static Page page;
    static int counting = 0;

    @BeforeAll
    public static void setUp() {
        System.out.println("Staring...");
        // String userDataDir = "./user-data/victor/chromium";
        // Path path = FileSystems.getDefault().getPath(userDataDir);

        // Playwright playwright = Playwright.create();
        // BrowserType browserType = playwright.chromium();
        
        // BrowserContext bc = browserType.launchPersistentContext(path, new BrowserType.LaunchPersistentContextOptions().setHeadless(false));
        // page = bc.newPage();

        // page.navigate("https://www.casetify.com/");

        // try {
        //     Locator accept_button = page.locator("//div[@data-label='accept-all-cookies-button']");
        //     if (accept_button.count() > 0) {
        //         accept_button.click();
        //     }
        //     FrameLocator frame = page.frameLocator("//iframe[@id='cms-popup-iframe']");
        //     Locator cms_close_btn = frame.locator("//button[@aria-label='Close']");
        //     if (cms_close_btn.count() > 0) {
        //         cms_close_btn.click();    
        //     }
        // } catch (Exception e) {
        //     System.err.println(e);
        // }
    }

    @AfterAll
    public static void close() throws IOException {
        System.out.printf("After all counting: %d\n", ++counting);
        // playwright.close();
        // LocalDateTime currentDateTime = LocalDateTime.now();
        // String dateTimeSString = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        // path = System.getProperty("user.dir") + "/testresult/index-" + dateTimeSString + ".html";

        // ExtentSparkReporter spark = new ExtentSparkReporter(path);
        // spark.config().setTheme(Theme.STANDARD);
        // spark.config().setDocumentTitle("Automation Testing Report");
        // extent.attachReporter(spark);
        // System.setProperty("java.awt.headless", "false");
        // extent.flush();
        // Desktop.getDesktop().browse(new File(path).toURI());
    }

    // private static boolean checkFrame(Page page) {
    //     if (page.frameLocator("//iframe[@id='cms-popup-iframe']") == null) {
    //         return false;
    //     } else {
    //         return true;
    //     }
    // }
}
