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
import com.microsoft.playwright.Locator.WaitForOptions;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.WaitForSelectorState;

import academy.teenfuture.projectTwo.Base;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommonNonEmptyCartTests extends Base {
    @BeforeAll 
    private static void openCartPanel() throws InterruptedException {
        currentClass = "CommonNonEmptyCartTests";
        action.addProduct(page);
    }

    @BeforeEach
    private void waitJS() throws InterruptedException {
        Thread.sleep(1000);
    }
    
    // non empty cart

    @Test
    @Order(1)
    void checkNonEmptyCartCount() {
        ExtentTest test = extent.createTest("Check non-empty cart count");

        try {
            Locator cart_count = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//span[@class='cart-count-text']");
            if (cart_count.count() > 0) {
                // need to check cart count showing the same
                Locator summary = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='cta-summary']");
                String sumText = summary.locator("span").first().innerText().charAt(0) + "";
                String countText = cart_count.innerText();
                if (sumText.equals(countText)) {
                    test.pass("Cart count check pass");
                } else {
                    throw new Exception("Cart count not the same");
                }
            } else {
                throw new Exception("Element not found");
            }
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Cart count check fail");
        }
    }

    @Test
    @Order(2)
    void checkFreeShipping() throws InterruptedException {
        ExtentTest test = extent.createTest("Check free shipping cost");

        try {
            Locator freeShippingText = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='cart-free-shipping-text']");
            if (freeShippingText.count() > 0) {    
                Locator totalAmount = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//span[@class='nowrap']");
                String total = totalAmount.innerText().substring(3);

                // total amount < 279
                String amount = freeShippingText.locator("strong").innerText().substring(3);
                int remaining = 279 - Integer.parseInt(total) + 15;
                if (remaining == Integer.parseInt(amount)) {
                    test.pass("Correct free shipping text");
                } else {
                    System.out.printf("ramaining: %d, amount: %s\n", remaining, amount);
                    throw new Exception("Invalid free shipping text");
                }
                
                // total amount >= 279
                action.changeAmount(page, 4);
                totalAmount = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//span[@class='nowrap']");
                totalAmount.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
                total = totalAmount.innerText().substring(3);
                if (Integer.parseInt(total) >= 279) {
                    // total amount >= 279
                    Locator tag = page.locator("//div[@class='free-shipping-tag nowrap']");
                    if (tag.count() > 0) {
                        test.pass("Correct free shipping text");
                    } else {
                        throw new Exception("Invalid free shipping text");
                    }
                }
            } else {
                throw new Exception("Cannot find free shipping text");
            }
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Free shipping cost fail");
        } finally {
            action.changeAmount(page, 1);
        }
    }

    @Test
    @Order(3)
    void checkSpendingNote() {
        ExtentTest test = extent.createTest("Check qualifying spending note");

        try {
            Locator note = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='crm-qualifying-spending-note']");
            
            if (note.count() > 0) {
                // points calculation
                String points = note.locator("//div[@class='point-text']//span[@class='d-flex align-items-center']//span").all().getFirst().innerText().substring(1);
                String cost = page.locator("(//span[@class='uppercase nowrap'])[1]").innerText().substring(3);

                Locator voucher = page.locator("(//div[@class='entry-row voucher-discount-entry'])[2]");
                int discount = 0;
                if (voucher.count() > 0) {
                    String discountStr = voucher.all().getLast().innerText();
                    discount = Integer.parseInt(discountStr.substring(discountStr.length() - 2));
                }
                int calculated = (Integer.parseInt(cost) - discount)/8;

                if (Integer.parseInt(points) == calculated) {
                    test.pass("Correct point calculation");
                } else {
                    System.out.printf("Points: %s, Cost: %s, Discount: %d, calculated: %d\n", points, cost, discount, calculated);
                    throw new Exception("Incorrect point calculation");
                }


                // learn more btn and modal
                Locator learnBtn = note.locator("a");
                if (learnBtn.count() > 0 && learnBtn.isVisible()) {
                    learnBtn.click();

                    Locator modal = page.locator("//div[@class='v-info-modal-container']//button");
                    modal.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(10000));
                    modal.click();
                    modal.waitFor(new WaitForOptions().setState(WaitForSelectorState.DETACHED));
                    test.pass("\"What is Qualifying Point?\" modal check pass");
                }
            } else {
                throw new Exception("Qualifying spending note not found");
            }
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Qualifying spending note check fail");
        }
    }

    @Test
    @Order(4)
    void checkPaymentSummaryWithoutVoucher() throws InterruptedException {
        ExtentTest test = extent.createTest("Check payment summary without voucher");

        try {
            Locator paySummary = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='summary-wrapper']");
            paySummary.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));

            String subTotal = paySummary.locator("//div[@class='free-shipping-wrapper']//span").innerText().substring(3);
            String shipping = paySummary.locator("//div[@class='free-shipping-wrapper shipping-fee']//span").innerText().substring(3);
            String total = paySummary.locator("//div[@class='entry-row cart-total']//span[@class='nowrap']").innerText().substring(3);

            // check non-free shipping
            if (Integer.parseInt(total) == Integer.parseInt(subTotal) + Integer.parseInt(shipping)) {
                test.pass("Payment summary without voucher check pass");
            } else {
                System.out.printf("Subtotal: %s, Shipping: %s, Total: %s", subTotal, shipping, total);
                throw new Exception("Payment summary calculation error");
            }

            // check free shipping
            action.changeAmount(page, 2);
            if (shipping.equals("0")) {
                Locator tag = page.locator("//div[@class='free-shipping-tag nowrap']");
                tag.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
                test.pass("Free shipping tag check pass");
            }

        } catch (Exception e) {
            System.err.println(e);
            test.fail("Payment summary without voucher check fail");
        } finally {
            action.changeAmount(page, 1);
        }
    }

    @Test
    @Order(5)
    void checkNonEmptyCartPayBtn() {
        ExtentTest test = extent.createTest("Check non-empty cart pay btn");
        
        try {
            Locator payBtnField = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='sticky-cta-container']");
            payBtnField.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            Locator payBtn = payBtnField.locator("a");
            payBtn.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            assertThat(payBtn).hasAttribute("href", "/checkout/summary?r2020");
            test.pass("Non-empty cart pay btn check pass");
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Non-empty cart pay btn check fail");
        }
    }

    @Test
    @Order(6)
    void checkVoucher() {
        ExtentTest test = extent.createTest("Check voucher function");

        try {
            Locator voucherBtn = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@id='VOUCHER_WALLET_2024']");
            voucherBtn.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            voucherBtn.click();
            Locator modal = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='wallet-modal-container']");
            modal.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            Locator voucherApplyBtn = modal.locator("//button[@class='voucher-apply-btn']");
            Locator voucherInput = modal.locator("//input[@class='voucher-input-field']");
            Locator toaster = page.locator("//div[@id='toaster']");
            Locator invalidWrapper = modal.locator("//div[@class='voucher-input-invalid-wapper']");
            Locator closeModalBtn = modal.locator("//button[@class='wallet-close-btn']");
            
            // invalid voucher input
            voucherInput.fill("123");
            voucherApplyBtn.click();
            toaster.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            assertThat(toaster).hasClass(Pattern.compile(".*error-color.*"));
            invalidWrapper.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            test.pass("Invalid voucher check pass");

            closeModalBtn.click();
            action.changeAmount(page, 2);
            voucherBtn.click();

            // valid voucher input
            String validVoucher = "WELCOME10";
            voucherInput.fill(validVoucher);
            voucherApplyBtn.click();
            toaster.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            assertThat(toaster).hasClass(Pattern.compile(".*success-color.*"));
            Locator usedVoucher = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//span[@class='sub-item-desc']");
            assertThat(usedVoucher).hasText(validVoucher);
            voucherBtn.click();
            modal.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            Locator redeemCode = modal.locator("//span[@class='redeem-code']");
            assertThat(redeemCode).hasText(validVoucher);
            test.pass("Valid voucher check pass");

            // remove voucher from modal
            Locator removeVoucherBtn = modal.locator("//button[@class='redeem-action select-action selected-item']");
            removeVoucherBtn.click();
            redeemCode.waitFor(new WaitForOptions().setState(WaitForSelectorState.DETACHED));
            toaster.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            assertThat(toaster).hasClass(Pattern.compile(".*success-color.*"));
            test.pass("Successfully remove voucher from modal");

            // remove voucher modal
            Locator removeVoucherModalBtn = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='entry-row voucher-discount-entry']//a[@class='remove-btn']");
            Locator removeModal = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='v-remove-modal-container']");
            Locator confirmRemoveBtn = removeModal.locator("//button[@class='v-remove-btn black']");
            Locator cancelBtn = removeModal.locator("//div[@class='v-remove-modal-footer']//button");
            Locator checkbox = removeModal.locator("//label");

            // cancel btn
            voucherInput.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            voucherInput.fill(validVoucher);
            voucherApplyBtn.click();
            toaster.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            removeVoucherModalBtn.click();
            cancelBtn = cancelBtn.all().getLast();
            cancelBtn.click();
            removeModal.waitFor(new WaitForOptions().setState(WaitForSelectorState.DETACHED));
            assertThat(usedVoucher).hasText(validVoucher);

            // confirm btn
            removeVoucherModalBtn.click();
            confirmRemoveBtn.click();
            toaster.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            redeemCode.waitFor(new WaitForOptions().setState(WaitForSelectorState.DETACHED));
            assertThat(toaster).hasClass(Pattern.compile(".*success-color.*"));

            // not again checkbox
            voucherBtn.click();
            voucherInput.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            voucherInput.fill(validVoucher);
            voucherApplyBtn.click();
            toaster.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            removeVoucherModalBtn.click();
            checkbox.click();
            cancelBtn.click();
            removeVoucherModalBtn.click();
            toaster.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            redeemCode.waitFor(new WaitForOptions().setState(WaitForSelectorState.DETACHED));
            assertThat(toaster).hasClass(Pattern.compile(".*success-color.*"));

            test.pass("Remove voucher modal check pass");
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Voucher function check fail");
        }
    }

    @Test
    @Order(7)
    void checkRemoveProductBtn() {
        ExtentTest test = extent.createTest("Check remove product btn");

        try {
            Locator removeBtn = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='cart-item-container']//a[@class='remove-btn']");
            removeBtn.click();
            Locator payBtnField = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='sticky-cta-container']");
            payBtnField.waitFor(new WaitForOptions().setState(WaitForSelectorState.DETACHED));
            test.pass("Remove product btn check pass");
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Remove product btn check fail");
        }
    }
}
