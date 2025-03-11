package academy.teenfuture.projectTwo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

@SpringBootTest
class ProjectTwoApplicationTests extends BaseTest {

	@Disabled
	@Test
	void contextLoads() throws InterruptedException {
		ExtentTest test = extent.createTest("Just an example");

		// test case
        // Start Playwright =================
        Playwright playwright = Playwright.create();
        BrowserType browserType = playwright.chromium();
        Page page = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false)).newContext().newPage();
        page.navigate("https://www.hko.gov.hk/");
        
        // Stop for a moment before end =================
        Thread.sleep(5000);
        test.pass("Successfully pass the ATest");
        
        // Close Playwright =================
        playwright.close();

		// pass
		test.pass("Successfully pass");

		// info
		test.info("Testing info");

		// fail
		test.fail("Test fail");
	}

}
