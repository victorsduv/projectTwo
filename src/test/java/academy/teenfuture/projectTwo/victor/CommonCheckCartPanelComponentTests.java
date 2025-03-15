package academy.teenfuture.projectTwo.victor;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import academy.teenfuture.projectTwo.GuestBase;

public class CommonCheckCartPanelComponentTests extends GuestBase {
    @BeforeAll 
    private static void openCartPanel() {
        System.out.println("Before all for common check cart");
        Locator cartIcon = page.locator("(//a[@title='my cart'])[2]");
        cartIcon.click();
    }
    
    @Test
    void checkEventWrapper() {
        ExtentTest test = extent.createTest("Check event wrapper");

        try {
            Locator eventWrapperOne = page.locator("(//div[@class='event-ribbon flex v-center'])[3]");
            Locator eventWrapperTwo = page.locator("(//div[@class='event-ribbon flex v-center'])[4]");

            assertThat(eventWrapperOne).hasCSS("transition", Pattern.compile(".*"));
            assertThat(eventWrapperTwo).hasCSS("transition", Pattern.compile(".*"));

            test.pass("Event wrapper test pass");
        } catch (Exception e) {
            test.fail("Event wrapper test fail");
        }
    }

    @Test
    void checkNavbar() {
        ExtentTest test = extent.createTest("Check Nav bar");

        try {
            Locator cart_count = page.locator("(//span[@class='cart-count-text'][normalize-space()='1'])[2]");
        } catch (Exception e) {

        }
    }
}