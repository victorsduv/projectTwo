package academy.teenfuture.projectTwo.victor;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import academy.teenfuture.projectTwo.GuestBase;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommonCartTests extends GuestBase {
    @BeforeAll 
    private static void openCartPanel() {
        currentClass = "CommonCartTests";
        Locator cartIcon = page.locator("(//a[@title='my cart'])[2]");
        cartIcon.click();
    }

    @BeforeEach
    private void waitJS() throws InterruptedException {
        Thread.sleep(1000);
    }

    // empty cart
    
    @Test
    @Order(1)
    void checkEventWrapper() {
        ExtentTest test = extent.createTest("Check event wrapper");

        try {
            Locator eventWrapperOne = page.locator("(//div[@class='event-ribbon flex v-center'])[3]");
            Locator eventWrapperTwo = page.locator("(//div[@class='event-ribbon flex v-center'])[4]");

            assertThat(eventWrapperOne).hasCSS("transition", Pattern.compile(".*"));
            assertThat(eventWrapperTwo).hasCSS("transition", Pattern.compile(".*"));

            test.pass("Event wrapper test pass");
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Event wrapper test fail");
        }
    }

    @Test
    @Order(2) 
    void checkAcceptPaymentList() {
        ExtentTest test = extent.createTest("Check accept payment list");

        try {
            Locator acceptPayment = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='items-container']");
            if (acceptPayment.locator("img").count() != 9) {
                int acceptPayment_img = acceptPayment.locator("img").count();
                throw new Exception(acceptPayment_img + "accepted payment image found");
            } else {
                test.pass("Check accept payment list");
            }
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Cannot find accept payment list");
        }
    }

    @Test
    @Order(3)
    void checkEmptyCartSpendingNote() {
        ExtentTest test = extent.createTest("Check spending note when cart is empty");

        try {
            Locator spendingNote = page.locator("(//div[@class='crm-qualifying-spending-note'])[2]");
            if (spendingNote.count() > 0) {
                throw new Exception("Spending note exist when cart is empty");
            } else {
                test.pass("Spending note not exist when cart is empty");
            }
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Spending note exist when cart is empty");
        }
    }

    @Test
    @Order(4)
    void checkEmptyCartPayBtn() {
        ExtentTest test = extent.createTest("Check pay btn when cart is empty");

        try {
            Locator payBtn = page.locator("(//a[@class='btn btn-primary btn-block checkout-btn'])[2]");
            if (payBtn.count() > 0) {
                throw new Exception("Pay btn exists when cart is empty");
            } else {
                test.pass("Pay btn does not exist when cart is empty");
            }
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Pay btn exists when cart is empty");
        }
    }

    @Test
    @Order(5)
    void checkEmptyCartFreeShippingText() {
        ExtentTest test = extent.createTest("Check free shipping text when cart is empty");

        try {
            Locator freeShippingText = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='cart-free-shipping-text']");
            if (freeShippingText.count() > 0) {    
                String amount = freeShippingText.locator("//strong[normalize-space()='HK$279']").innerText();
                if (amount.equals("HK$279")) {
                    test.pass("Correct free shipping text");
                } else {
                    throw new Exception("Invalid free shipping text");
                }
            } else {
                throw new Exception("Cannot find free shipping text");
            }
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Invalid free shipping text");
        }
    }

    @Test
    @Order(6)
    void checkClubPromotionHintsIcon() {
        ExtentTest test = extent.createTest("Check club promotion hints icon");

        try {
            Locator icon = page.locator("(//span[@class='club-promotion-hints ctg-club-icon hints-icon blue'])[2]");
            if (icon.count() > 0 && icon.isVisible()) {
                icon.click();
                Locator modal = page.locator("(//div[@class='intro-modal-container'])[1]");
                if (modal.count() > 0 && modal.isVisible()) {
                    test.pass("Club promotion hints icon check pass");
                } else {
                    throw new Exception("Club promotion hints modal does not exist");
                }
            } else {
                throw new Exception("Cannot find club promotion hints icon");
            }
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Club promotion hints icon check fail");
        }
    }

    @Test
    @Order(7)
    void checkClubPromotionDetailIcon() throws InterruptedException {
        ExtentTest test = extent.createTest("Check club promotion detail icon");

        try {
            Locator icon = page.locator("(//img[@src='https://cdn-stamplib.casetify.com/cms/image/4053d50f4c2bac61e12f0dc40de7a43f.svg'])[1]");
            Locator parent = page.locator("(//div[@data-label='table-title'])")
                                    .filter(new Locator.FilterOptions().setHas(icon)).all().getLast()
                                    .locator("//div[@class='button-container']");
            if (parent.count() > 0 && parent.isVisible()) {
                parent.click();
                Locator modal = page.locator("//div[@class='v-detail-modal-container']");
                Thread.sleep(1000);
                if (modal.count() > 0 && modal.isVisible()) {
                    test.pass("Club promotion detail check pass");
                    page.locator("(//button[@class='v-detail-close-btn'])[1]").click();
                } else {
                    throw new Exception("Club promotion detail modal does not exist");
                }
            } else {
                throw new Exception("Cannot find club promotion detail icon");
            }
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Club promotion detail icon check fail");
        } finally {
            page.locator("(//button[@class='intro-close-btn'])[1]").click();
        }
    }

    @Test
    @Order(8)
    void checkEmptyCartLink() {
        ExtentTest test = extent.createTest("Check empty cart link");

        try {
            Locator links = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='empty-cart-link-container']");
            if (links.count() > 0 && links.isVisible()) {
                Locator hrefs = links.locator("//a[@class='empty-cart-link']");
                if (hrefs.count() == 4) {
                    for (int i = 0; i < hrefs.count(); i++) {
                        if (!hrefs.all().get(i).getAttribute("href").matches(".+")) {
                            throw new Exception("Invalid empty cart links");
                        }
                    }
                    test.pass("Empty cart links check pass");
                } else {
                    throw new Exception("Empty cart links not found");
                }
            } else {
                throw new Exception("Empty cart links not found");
            }
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Empty cart link fail");
        }
    }

    @Test
    @Order(9)
    void checkSatisfactionGuaranteed() {
        ExtentTest test = extent.createTest("Check satisfaction guaranteed field");

        try {
            Locator field = page.locator("(//div[@class='satisfaction-guaranteed'])[2]");
            if (field.count() > 0) {
                test.pass("Satisfaction guaranteed check pass");
            } else {
                throw new Exception("Satisfaction guaranteed not found");
            }
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Satisfaction guaranteed check fail");
        }
    }

    @Test
    @Order(10)
    void checkEmptyCartCount() {
        ExtentTest test = extent.createTest("Check empty cart count");

        try {
            Locator cart_count = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//span[@class='cart-count-text']");
            if (cart_count.count() > 0) {
                throw new Exception("Cart count exist");
            } else {
                test.pass("Cart count not exist");
            }
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Cart count not found");
        }
    }

    @Test
    @Order(11)
    void checkBackBtn() {
        ExtentTest test = extent.createTest("Check back btn");

        try {
            Locator backBtn = page.locator("(//div[@class='btn-back'])[2]");
            backBtn.click();
            
            Locator show_cart = page.locator("//body");
            assertThat(show_cart).not().hasClass(Pattern.compile(".*show-cart-overlay.*"));

            test.pass("Back btn check pass");
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Back btn check fail");
        }
    }

    // end of empty cart

}