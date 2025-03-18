package academy.teenfuture.projectTwo.victor;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import academy.teenfuture.projectTwo.GuestBase;

public class OpenCartTest extends GuestBase {

      @Test
      void openCartTest() {
            ExtentTest test = extent.createTest("Open cart test");
            try {
                  Locator cartIcon = page.locator("(//a[@title='my cart'])[2]");
                  cartIcon.click();

                  Locator show_cart = page.locator("//body");
                  assertThat(show_cart).hasClass(Pattern.compile(".*show-cart-overlay.*"));
            
                  test.pass("Successfully show the cart panel");
            } catch (Exception e) {
                  System.err.println(e);
                  test.fail("Fail to show the cart panel");
            }
      }

      
}
