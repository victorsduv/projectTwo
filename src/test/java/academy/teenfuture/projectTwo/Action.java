package academy.teenfuture.projectTwo;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.WaitForOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import io.github.cdimascio.dotenv.Dotenv;


public class Action {

    // login
    public void login(Page page, Dotenv dotenv) {
        page.locator("(//a[@aria-label='Sign Up'])[2]").click();    
        Locator loginModal = page.locator("//div[@class='login-container']");
        loginModal.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        Locator loginInput = loginModal.locator("//input[@id='landing-email']");
        loginInput.fill(dotenv.get("EMAIL"));
        Locator continueBtn = loginModal.locator("//div[@class='form-button']");
        continueBtn.click();
        Locator pwInput = loginModal.locator("//input[@id='log-in-password']");
        pwInput.waitFor(new WaitForOptions().setState(WaitForSelectorState.ATTACHED));
        pwInput.fill(dotenv.get("PW"));
        Locator loginBtn = loginModal.locator("//div[@class='form-button']");
        loginBtn.click();
        loginModal.waitFor(new WaitForOptions().setState(WaitForSelectorState.DETACHED));
        page.locator("//div[@class='page-optin-disagree']").click();
    }

    // logout

    // add product
    public void addProduct(Page page) throws InterruptedException {
        page.locator("//div[@id='PDP_2025_PRODUCT_ACTION_ADD_TO_CART_BTN']").click();
        Thread.sleep(5000);
        // System.out.println("Finish waiting");
    }
    
    // the following methods are based on the cart panel is opened


    // increase the amount of product
    public void changeAmount(Page page, int amount) throws InterruptedException {
        Locator input = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//input[@class='quantity-control']");
        input.fill(amount+"");
        page.locator("//span[@class='price-text']").last().click();
        Thread.sleep(10000);
    }

    // remove product
    public void removeProduct(Page page) {
        page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='cart-item-container']//a[@class='remove-btn']").click();
        System.out.println("Product is removed");
    }

    
    boolean checkLuhn(String cardNo) {
        int nDigits = cardNo.length();

        int nSum = 0;
        boolean isSecond = false;
        boolean pass = false;
        for (int i = nDigits - 1; i >= 0; i--) {

            int d = cardNo.charAt(i) - '0';

            if (isSecond == true)
                d = d * 2;

            // We add two digits to handle
            // cases that make two digits 
            // after doubling
            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        
        int remaining = nSum % 10;
        if (remaining == 0) {
            pass = true;
        } else {
            System.out.println(remaining);
        }
        return pass;
    }
}
