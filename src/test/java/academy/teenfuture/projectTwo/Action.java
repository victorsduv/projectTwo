package academy.teenfuture.projectTwo;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.WaitForOptions;
import com.microsoft.playwright.options.WaitForSelectorState;


public class Action {

    // login
    public void login() {
        MemberBase.page.locator("(//a[@aria-label='Sign Up'])[2]").click();    
        Locator loginModal = MemberBase.page.locator("//div[@class='login-container']");
        loginModal.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        Locator loginInput = loginModal.locator("//input[@id='landing-email']");
        loginInput.fill("victorsduv@gmail.com");
        Locator continueBtn = loginModal.locator("//div[@class='form-button']");
        continueBtn.click();
        Locator pwInput = loginModal.locator("//input[@id='log-in-password']");
        pwInput.waitFor(new WaitForOptions().setState(WaitForSelectorState.ATTACHED));
        pwInput.fill("1234Qwer");
        Locator loginBtn = loginModal.locator("//div[@class='form-button']");
        loginBtn.click();
        loginModal.waitFor(new WaitForOptions().setState(WaitForSelectorState.DETACHED));
        MemberBase.page.locator("//div[@class='page-optin-disagree']").click();
    }

    // logout

    // add product
    public void addProduct() throws InterruptedException {
        GuestBase.page.locator("//div[@id='PDP_2025_PRODUCT_ACTION_ADD_TO_CART_BTN']").click();
        Thread.sleep(5000);
    }
    
    // the following methods are based on the cart panel is opened


    // increase the amount of product
    public void changeAmount(int amount) throws InterruptedException {
        Locator input = GuestBase.page.locator("(//input[@type='text'])[2]");
        input.fill(amount+"");
        GuestBase.page.locator("(//span[@class='price-text'])[2]").click();
        Thread.sleep(10000);
    }

    // remove product
    public void removeProduct() {
        GuestBase.page.getByText("移除").nth(1).click();
    }

    // add coupon

    // remove coupon

    // status checking

    // check cart empty
    public boolean isEmptyCart() {
        return false;
    }

    // check login status
    public boolean isLogin() {
        return false;
    }
}
