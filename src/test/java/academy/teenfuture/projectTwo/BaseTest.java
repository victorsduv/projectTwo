package academy.teenfuture.projectTwo;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public abstract class BaseTest {
    protected static ExtentReports extent = new ExtentReports();
    static String path;

    @BeforeAll
    public static void setUp() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String dateTimeSString = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        path = System.getProperty("user.dir") + "/testresult/index-" + dateTimeSString + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(path);
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("Automation Testing Report");
        extent.attachReporter(spark);
        System.setProperty("java.awt.headless", "false");
    }

    @AfterAll
    public static void close() throws IOException {
        extent.flush();
        Desktop.getDesktop().browse(new File(path).toURI());
    }
}
