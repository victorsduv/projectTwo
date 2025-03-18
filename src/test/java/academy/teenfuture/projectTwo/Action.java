package academy.teenfuture.projectTwo;

import com.microsoft.playwright.Locator;


public class Action {

    // login

    // logout

    // add product
    public void addProduct() throws InterruptedException {
        GuestBase.page.locator("//div[@id='PDP_2025_PRODUCT_ACTION_ADD_TO_CART_BTN']").click();
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
