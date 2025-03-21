package academy.teenfuture.projectTwo.victor;

import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.FilterOptions;
import com.microsoft.playwright.Locator.WaitForOptions;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.WaitForSelectorState;

import academy.teenfuture.projectTwo.Base;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class checkoutTests extends Base {
    Locator checkoutSection = page.locator("//div[@class='checkout-section-info']");
    Locator nextStateBtn = page.locator("//button[@class='dark-btn next-state-btn']");
    Locator checkoutInfo = page.locator("//div[@id='CHECKOUT_2024_SHIPPING']");
    Locator contactInfoSection = checkoutSection.locator("//div[@id='CHECKOUT_2024_CONTACT_INFORMATION']//div[@class='form-row']//div[@class='col-12']");
    Locator shippingAddress = checkoutSection.locator("//div[@id='CHECKOUT_2024_SHIPPING_ADRRESS']");
    Locator shippingMethod = checkoutInfo.locator("//div[@id='CHECKOUT_2024_SHIPPING_DELIVERY']");
    Locator paymentInfo = page.locator("//div[@id='CHECKOUT_2024_PAYMENT_INFO']");
    Locator billingAddress = page.locator("//div[@id='CHECKOUT_2024_BILLING_ADDRESS']//div[@class='billing-address-option-container']");

    @BeforeAll 
    private static void openCartPanel() throws InterruptedException {
        currentClass = "checkoutTests";
        
        action.addProduct(page);
        // System.out.println("Finish adding product");
        Locator payBtnField = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='sticky-cta-container']");
        Locator payBtn = payBtnField.locator("a");
        String regex = "**"+payBtn.getAttribute("href");
        // System.out.println(payBtn.innerText());
        payBtn.click();
        System.out.println("Finish clicking");
        page.waitForURL(regex);
        System.out.println("Finish navigation");
    }
    
    @BeforeEach
    private void waitJS() throws InterruptedException {
        Thread.sleep(1000);
    }

    @AfterEach
    private void waiting() throws InterruptedException {
        Thread.sleep(1000);
    }

    @Test
    @Order(1)
    void checkEmptyForm() {
        ExtentTest test = extent.createTest("Check empty form submittion");

        try {
            nextStateBtn.click();

            Locator emailInputField = contactInfoSection.locator("//div[@class='input-container']");
            Locator emailInput = page.locator("//div//input[@id='email']");
            Locator emailInputParent = emailInputField.locator("//div").filter(new FilterOptions().setHas(emailInput));

            emailInput.fill(dotenv.get("INVALID_EMAIL"));
            if (emailInput.inputValue().equals("")) {    
                assertThat(emailInputParent).hasClass(Pattern.compile(".*is-invalid.*"));
            }

            
            Locator phoneInputField = contactInfoSection.locator("//div[@class='dial-code-select input-container d-flex']");
            Locator phoneInput = page.locator("//div//input[@id='phone']");
            Locator phoneInputParent = phoneInputField.locator("//div").filter(new FilterOptions().setHas(phoneInput));

            phoneInput.fill(dotenv.get("INVALID_PHONE"));
            if (phoneInput.inputValue().equals("")) {    
                assertThat(phoneInputParent).hasClass(Pattern.compile(".*is-invalid.*"));
            }

            test.pass("Empty form submittion check pass");
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Empty form submittion check fail");
        }
    }
    
    @Test
    @Order(2)
    void checkEmailInput() {
        ExtentTest test = extent.createTest("Check email input");

        try {            
            Locator emailInputField = contactInfoSection.locator("//div[@class='input-container']");
            Locator emailInput = page.locator("//div//input[@id='email']");
            Locator emailInputParent = emailInputField.locator("//div").filter(new FilterOptions().setHas(emailInput));

            emailInput.fill(dotenv.get("INVALID_EMAIL"));
            contactInfoSection.click();
            assertThat(emailInputParent).hasClass(Pattern.compile(".*is-invalid.*"));
            test.pass("Email invalid input check pass");

            Thread.sleep(1000);

            emailInput.fill(dotenv.get("EMAIL"));
            contactInfoSection.click();
            assertThat(emailInputParent).not().hasClass(Pattern.compile(".*is-invalid.*"));
            test.pass("Email valid input check pass");
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Email input check fail");
        }

        // Locator phoneInput = checkoutSection.locator("//input[@id='phone']");
    }

    @Test
    @Order(3)
    void checkPhoneInput() {
        ExtentTest test = extent.createTest("Check phone input");

        try {            
            Locator phoneInputField = contactInfoSection.locator("//div[@class='dial-code-select input-container d-flex']");
            Locator phoneInput = page.locator("//div//input[@id='phone']");
            Locator phoneInputParent = phoneInputField.locator("//div").filter(new FilterOptions().setHas(phoneInput));

            phoneInput.fill(dotenv.get("INVALID_PHONE"));
            contactInfoSection.click();
            assertThat(phoneInputParent).hasClass(Pattern.compile(".*is-invalid.*"));
            test.pass("Phone invalid input check pass");

            Thread.sleep(1000);

            phoneInput.fill(dotenv.get("PHONE"));
            contactInfoSection.click();
            assertThat(phoneInputParent).not().hasClass(Pattern.compile(".*is-invalid.*"));
            test.pass("Phone valid input check pass");
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Phone input check fail");
        }

    }

    @Test
    @Order(4)
    void checkLastNameInput() {
        ExtentTest test = extent.createTest("Check last name input");
        
        try {
            Locator lastNameField = shippingAddress.locator("//div[@class='form-row']").all().getFirst()
                                                .locator("//div[@class='col-12 col-sm position-relative']").all().getFirst()
                                                .locator("//div[@class='input-container']");
            Locator lastNameInput = page.locator("//div//input[@id='last_name']");
            Locator lastNameParent = lastNameField.locator("//div").filter(new FilterOptions().setHas(lastNameInput));

            lastNameInput.type("last");
            Thread.sleep(1000);
            lastNameInput.clear();
            assertThat(lastNameParent).hasClass(Pattern.compile(".*is-invalid.*"));
            test.pass("Last name input empty check pass");

            Thread.sleep(1000);

            lastNameInput.type("last");
            assertThat(lastNameParent).not().hasClass(Pattern.compile(".*is-invalid.*"));
            test.pass("Last name input check pass");
            
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Last name input check fail");
        }
    }

    @Test
    @Order(5)
    void checkFirstNameInput() {
        ExtentTest test = extent.createTest("Check first name input");
        
        try {
            Locator firstNameField = shippingAddress.locator("//div[@class='form-row']").all().getFirst()
                                                .locator("//div[@class='col-12 col-sm position-relative']").all().getLast()
                                                .locator("//div[@class='input-container']");
            Locator firstNameInput = page.locator("//div//input[@id='first_name']");
            Locator firstNameParent = firstNameField.locator("//div").filter(new FilterOptions().setHas(firstNameInput));

            firstNameInput.type("first");
            Thread.sleep(1000);
            firstNameInput.clear();
            assertThat(firstNameParent).hasClass(Pattern.compile(".*is-invalid.*"));
            test.pass("First name input empty check pass");

            Thread.sleep(1000);

            firstNameInput.type("first");
            assertThat(firstNameParent).not().hasClass(Pattern.compile(".*is-invalid.*"));
            test.pass("First name input check pass");
            
        } catch (Exception e) {
            System.err.println(e);
            test.fail("First name input check fail");
        }
    }

    @Test
    @Order(6)
    void checkAddressInput() {
        ExtentTest test = extent.createTest("Check address input");
        
        try {
            Locator addressField = shippingAddress.locator("//div[@class='form-row']").all().get(1).locator("//div[@class='input-container']");
            Locator addressInput = page.locator("//div//input[@id='address_1']");
            Locator addressParent = addressField.locator("//div").filter(new FilterOptions().setHas(addressInput));

            addressInput.type("address");
            Thread.sleep(1000);
            addressInput.clear();
            assertThat(addressParent).hasClass(Pattern.compile(".*is-invalid.*"));
            test.pass("Address input empty check pass");
            
            Thread.sleep(1000);

            addressInput.type("address");
            assertThat(addressParent).not().hasClass(Pattern.compile(".*is-invalid.*"));
            test.pass("Address input check pass");
            
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Address input check fail");
        }
    }
    
    @Test
    @Order(7)
    void checkCityInput() {
        ExtentTest test = extent.createTest("Check city input");
        
        try {
            Locator cityField = shippingAddress.locator("//div[@class='form-row']").all().get(3).locator("//div[@class='input-container']");
            Locator cityInput = page.locator("//div//input[@id='address_city']");
            Locator cityParent = cityField.locator("//div").filter(new FilterOptions().setHas(cityInput));

            cityInput.type("city");
            Thread.sleep(1000);
            cityInput.clear();
            assertThat(cityParent).hasClass(Pattern.compile(".*is-invalid.*"));
            test.pass("City input empty check pass");
            
            Thread.sleep(1000);

            cityInput.type("city");
            assertThat(cityParent).not().hasClass(Pattern.compile(".*is-invalid.*"));
            test.pass("City input check pass");
            
        } catch (Exception e) {
            System.err.println(e);
            test.fail("City input check fail");
        }
    }

    @Test
    @Order(8)
    void checkFinishShippingBtn() {
        ExtentTest test = extent.createTest("Check finish shipping btn");

        try {
            nextStateBtn.click();
            shippingMethod.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            // shippingMethod.highlight();
            // Thread.sleep(10000);
            test.pass("Finish button check pass");
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Finish button check fail");
        }
    }

    @Test
    @Order(9)
    void checkShippingMethodRadio() {
        ExtentTest test = extent.createTest("Check shipping method radio");

        try {
            shippingMethod.scrollIntoViewIfNeeded();
            Locator honeyShipping = shippingMethod.locator("//div[@class='skeleton-container']");
            if (honeyShipping.isVisible()) {
                shippingMethod.highlight();
            }
            honeyShipping.waitFor(new WaitForOptions().setState(WaitForSelectorState.DETACHED));
            // shippingMethod.highlight();
            Locator lastShippingMethod = shippingMethod.locator("//div[@class='row-item']")
                                                        .all().getLast().locator("//label");
            
            System.out.println(lastShippingMethod.innerHTML());

            lastShippingMethod.click();
            String[] lastIdAry = lastShippingMethod.getAttribute("for").split("-");
            String lastId = lastIdAry[lastIdAry.length - 1];

            assertThat(checkoutInfo).hasAttribute("currentdeliveryoptionid", lastId);
            test.pass("Shipping method radio check pass");

            nextStateBtn.highlight();
            nextStateBtn.click();
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Shipping method radio check fail");
        }
    }

    @Test
    @Order(10)
    void checkPaymentOption() {
        ExtentTest test = extent.createTest("Check payment options");

        try {
            Locator honeyPayment = paymentInfo.locator("//div[@class='skeleton-container']");
            honeyPayment.waitFor(new WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            honeyPayment.waitFor(new WaitForOptions().setState(WaitForSelectorState.DETACHED));
            Locator paymentText = paymentInfo.locator("//p").all().getFirst();
            List<Locator> paymentOptionList = paymentInfo.locator("//div[@class='option-row']").all();

            // credit card
            System.out.println("Credit card");
            Locator creditCardInput = paymentOptionList.getFirst().locator("//input[@name='paymentOption']");
            String[] idAry = creditCardInput.getAttribute("id").split("-");
            String labelFor = "payment-option-" + idAry[idAry.length - 1];
            String labelXPath = "//label[@for='" + labelFor + "']";
            Locator creditCardLabel = paymentOptionList.getFirst().locator(labelXPath).first();
            creditCardLabel.click();
            assertThat(paymentText).containsText(Pattern.compile(".*braintree.*"));
            test.pass("Credit card option check pass");

            // credit card inputs
            FrameLocator ccFrame = paymentInfo.frameLocator("//iframe[@id='braintree-hosted-field-number']");
            Locator ccInput = ccFrame.locator("//input[@id='credit-card-number']");
            Locator ccFrameParent = paymentInfo.locator("//div[@id='braintree-card-number']");
            assertThat(ccInput).hasAttribute("maxlength", "22");
            assertThat(ccInput).hasAttribute("inputmode", "numeric");
            ccFrameParent.click();
            ccFrameParent.focus();
            ccInput.click();
            // ccInput.focus();
            ccInput.type(dotenv.get("INVALID_CREDIT_CARD"));
            paymentText.click();
            paymentText.focus();
            assertThat(ccFrameParent).hasClass(Pattern.compile(".*is-invalid.*"));
            Thread.sleep(2000);
            ccFrameParent.click();
            ccFrameParent.focus();
            ccInput.click();
            // ccInput.focus();
            ccInput.fill("");
            ccInput.type(dotenv.get("VALID_CREDIT_CARD"));
            paymentText.click();
            paymentText.focus();
            assertThat(ccFrameParent).hasClass(Pattern.compile(".*braintree-hosted-fields-valid.*"));
            test.pass("credit card number input check pass");

            Thread.sleep(2000);

            FrameLocator cvvFrame = paymentInfo.frameLocator("//iframe[@id='braintree-hosted-field-cvv']");
            Locator cvvInput = cvvFrame.locator("//input[@id='cvv']");
            Locator cvvFrameParent = paymentInfo.locator("//div[@id='braintree-cvv']");
            assertThat(cvvInput).hasAttribute("maxlength", "3");
            assertThat(cvvInput).hasAttribute("inputmode", "numeric");
            cvvFrameParent.focus();
            cvvInput.focus();
            cvvInput.fill("1");
            paymentText.click();
            assertThat(cvvFrameParent).hasClass(Pattern.compile(".*is-invalid.*"));
            Thread.sleep(2000);
            cvvFrameParent.focus();
            cvvInput.focus();
            cvvInput.fill("123");
            paymentText.click();
            assertThat(cvvFrameParent).hasClass(Pattern.compile(".*braintree-hosted-fields-valid.*"));
            test.pass("CVV input check pass");

            Thread.sleep(2000);

            FrameLocator dateFrame = paymentInfo.frameLocator("//iframe[@id='braintree-hosted-field-expirationDate']");
            Locator dateInput = dateFrame.locator("//input[@id='expiration']");
            Locator dateFrameParent = paymentInfo.locator("//div[@id='braintree-exp-date']");
            assertThat(dateInput).hasAttribute("maxlength", "9");
            assertThat(dateInput).hasAttribute("inputmode", "numeric");
            dateFrameParent.focus();
            dateInput.focus();
            dateInput.fill("1");
            paymentText.click();
            assertThat(dateFrameParent).hasClass(Pattern.compile(".*is-invalid.*"));
            Thread.sleep(2000);
            dateFrameParent.focus();
            dateInput.focus();
            dateInput.fill("03 / 2030");
            paymentText.click();
            assertThat(dateFrameParent).hasClass(Pattern.compile(".*braintree-hosted-fields-valid.*"));
            test.pass("Expire date input check pass");

            Thread.sleep(2000);

            // paypal
            System.out.println("Paypal");
            Locator paypalInput = paymentOptionList.get(1).locator("//input[@name='paymentOption']");
            idAry = paypalInput.getAttribute("id").split("-");
            labelFor = "payment-option-" + idAry[idAry.length - 1];
            labelXPath = "//label[@for='" + labelFor + "']";
            Locator paypalLabel = paymentOptionList.get(1).locator(labelXPath).first();
            paypalLabel.click();
            assertThat(paymentText).containsText(Pattern.compile(".*braintree.*"));
            test.pass("Paypal option check pass");

            Thread.sleep(2000);

            // payme
            System.out.println("PayMe");
            Locator paymeInput = paymentOptionList.get(2).locator("//input[@name='paymentOption']");
            idAry = paymeInput.getAttribute("id").split("-");
            labelFor = "payment-option-" + idAry[idAry.length - 1];
            labelXPath = "//label[@for='" + labelFor + "']";
            Locator paymeLabel = paymentOptionList.get(2).locator(labelXPath).first();
            paymeLabel.click();
            assertThat(paymentText).containsText(Pattern.compile(".*PayMe.*"));
            test.pass("PayMe option check pass");

            Thread.sleep(2000);

            // alipayHK
            System.out.println("AlipayHK");
            Locator alipayHKInput = paymentOptionList.get(3).locator("//input[@name='paymentOption']");
            idAry = alipayHKInput.getAttribute("id").split("-");
            labelFor = "payment-option-" + idAry[idAry.length - 1];
            labelXPath = "//label[@for='" + labelFor + "']";
            Locator alipayHKLabel = paymentOptionList.get(3).locator(labelXPath).first();
            alipayHKLabel.click();
            assertThat(paymentText).containsText(Pattern.compile(".*Oceanpayment.*"));
            test.pass("AlipayHK option check pass");

            Thread.sleep(2000);

            // alipay
            System.out.println("Alipay");
            Locator alipayInput = paymentOptionList.get(4).locator("//input[@name='paymentOption']");
            idAry = alipayInput.getAttribute("id").split("-");
            labelFor = "payment-option-" + idAry[idAry.length - 1];
            labelXPath = "//label[@for='" + labelFor + "']";
            Locator alipayLabel = paymentOptionList.get(4).locator(labelXPath).first();
            alipayLabel.click();
            assertThat(paymentText).containsText(Pattern.compile(".*Oceanpayment.*"));
            test.pass("Alipay option check pass");

            Thread.sleep(2000);

            // wechat
            System.out.println("WeChat");
            Locator weChatInput = paymentOptionList.getLast().locator("//input[@name='paymentOption']");
            idAry = weChatInput.getAttribute("id").split("-");
            labelFor = "payment-option-" + idAry[idAry.length - 1];
            labelXPath = "//label[@for='" + labelFor + "']";
            Locator waChatLabel = paymentOptionList.getLast().locator(labelXPath).first();
            waChatLabel.click();
            assertThat(paymentText).containsText(Pattern.compile(".*Oceanpayment.*"));
            test.pass("WeChat option check pass");

        } catch (Exception e) {
            System.err.println(e);
            System.out.println(paymentInfo.innerHTML());
            test.fail("Payment options check fail");
        }
    }

    @Test
    @Order(11)
    void checkBillingAddress() {
        ExtentTest test = extent.createTest("Check billing address");

        try {
            Locator sameAddress = billingAddress.locator("//div[@class='option-row d-flex']").first();
            Locator diffAddress = billingAddress.locator("//div[@class='option-row d-flex']").last();
            Locator sameAddressRadio = sameAddress.locator("//label[@for='shipping-address-as-billing-address-cb-true']").filter(new FilterOptions().setHasNotText(Pattern.compile(".+")));
            Locator diffAddressRadio = diffAddress.locator("//label[@for='shipping-address-as-billing-address-cb-false']").filter(new FilterOptions().setHasNotText(Pattern.compile(".+")));

            // diff address
            diffAddressRadio.click();
            int clientHeight = Integer.parseInt(page.evaluate("document.getElementsByClassName('option-details billing-address-container')[0].clientHeight").toString());
            if (clientHeight < 100) {
                throw new Exception("Different address radio btn check fail");
            }

            Thread.sleep(2000);

            // same address
            sameAddressRadio.click();
            clientHeight = Integer.parseInt(page.evaluate("document.getElementsByClassName('option-details billing-address-container')[0].clientHeight").toString());
            if (clientHeight > 100) {
                throw new Exception("Different address radio btn check fail");
            }

            test.pass("Billing address check pass");

        } catch (Exception e) {
            System.err.println(e);
            test.fail("Billing address check fail");
        }
    }
}
