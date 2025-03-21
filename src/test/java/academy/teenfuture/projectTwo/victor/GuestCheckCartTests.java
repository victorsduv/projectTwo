package academy.teenfuture.projectTwo.victor;

import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.WaitForOptions;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.WaitForSelectorState;

import academy.teenfuture.projectTwo.GuestBase;

public class GuestCheckCartTests extends GuestBase {
    @BeforeAll 
    private static void openCartPanel() throws InterruptedException {
        currentClass = "GuestCheckCartTests";
        action.addProduct(page);
    }
    
    @BeforeEach
    private void waitJS() throws InterruptedException {
        Thread.sleep(1000);
    }
    
    @AfterEach
    private void teardown() throws InterruptedException {
        Thread.sleep(1000);
    }

    @Test
    void checkPromotionBanner() {
        ExtentTest test = extent.createTest("Check promotion banner");

        try {
            Locator promotionBanner = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='club-promotion-body']").all().getLast();
            Locator signUpBtn = promotionBanner.all().getFirst();
            Locator signInBtn = promotionBanner.all().getLast();

            // sign up btn
            signUpBtn.click();
            Locator loginModal = page.locator("//div[@class='login-container']");
            loginModal.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            Locator body = page.locator("//body");
            assertThat(body).hasClass(Pattern.compile(".*login-modal-open.*"));
            test.pass("Sign up btn check pass");
            Locator modalCloseBtn = loginModal.locator("//div[@class='login-button-container']");
            modalCloseBtn.click();

            // sign in btn
            signInBtn.click();
            loginModal.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            assertThat(body).hasClass(Pattern.compile(".*login-modal-open.*"));
            test.pass("Sign in btn check pass");
            modalCloseBtn.click();

        } catch (Exception e) {
            System.err.println(e);
            test.fail("Promotion banner check fail");
        }
    }

    @Test
    void checkVoucherModal() {
        ExtentTest test = extent.createTest("Check voucher modal");

        try {
            Locator voucherBtn = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@id='VOUCHER_WALLET_2024']");
            voucherBtn.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            voucherBtn.click();
            Locator modal = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='wallet-modal-container']");
            modal.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            Locator walletContext = modal.locator("//section[@class='voucher-wallet-list wallet-section']//div[@class='signup-btn-wrapper']");
            Locator signUpBtn = walletContext.locator("//button").all().getFirst();
            Locator signInBtn = walletContext.locator("//button").all().getLast();
            
            // sign up btn
            signUpBtn.click();
            Locator loginModal = page.locator("//div[@class='login-container']");
            loginModal.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            Locator body = page.locator("//body");
            assertThat(body).hasClass(Pattern.compile(".*login-modal-open.*"));
            test.pass("Sign up btn check pass");
            Locator modalCloseBtn = loginModal.locator("//div[@class='login-button-container']");
            modalCloseBtn.click();

            // sign in btn
            signInBtn.click();
            loginModal.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            assertThat(body).hasClass(Pattern.compile(".*login-modal-open.*"));
            test.pass("Sign in btn check pass");
            modalCloseBtn.click();

        } catch (Exception e) {
            System.err.println(e);
            test.fail("Voucher modal check fail");
        } 
    }
}
