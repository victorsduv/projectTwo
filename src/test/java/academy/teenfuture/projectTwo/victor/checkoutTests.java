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
import com.microsoft.playwright.Locator.FilterOptions;
import com.microsoft.playwright.Locator.WaitForOptions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.WaitForSelectorState;

import academy.teenfuture.projectTwo.GuestBase;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class checkoutTests extends GuestBase {
    Locator checkoutSection = page.locator("//div[@class='checkout-section-info']");
    Locator nextStateBtn = page.locator("//button[@class='dark-btn next-state-btn']");
    Locator checkoutInfo = page.locator("//div[@id='CHECKOUT_2024_SHIPPING']");
    Locator contactInfoSection = checkoutSection.locator("//div[@id='CHECKOUT_2024_CONTACT_INFORMATION']//div[@class='form-row']//div[@class='col-12']");
    Locator shippingAddress = checkoutSection.locator("//div[@id='CHECKOUT_2024_SHIPPING_ADRRESS']");
    Locator shippingMethod = checkoutSection.locator("//div[@id='CHECKOUT_2024_SHIPPING_DELIVERY']");
    Locator paymentInfo = page.locator("//div[@id='CHECKOUT_2024_PAYMENT_INFO']");

    @BeforeAll 
    private static void openCartPanel() throws InterruptedException {
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
            assertThat(emailInputParent).hasClass(Pattern.compile(".*is-invalid.*"));
            test.pass("Email invalid input check pass");

            emailInput.fill(dotenv.get("EMAIL"));
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
            System.out.println(lastNameField.innerHTML());

            // lastNameInput.focus();
            // lastNameInput.highlight();
            // lastNameInput.click();
            lastNameInput.type("last");
            Thread.sleep(1000);
            lastNameInput.clear();
            assertThat(lastNameParent).hasClass(Pattern.compile(".*is-invalid.*"));
            test.pass("Last name input empty check pass");

            Thread.sleep(1000);

            // lastNameInput.focus();
            // lastNameInput.highlight();
            // lastNameInput.click();
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
            System.out.println(firstNameField.innerHTML());

            // firstNameInput.focus();
            // firstNameInput.highlight();
            // firstNameInput.click();
            firstNameInput.type("first");
            Thread.sleep(1000);
            firstNameInput.clear();
            assertThat(firstNameParent).hasClass(Pattern.compile(".*is-invalid.*"));
            test.pass("First name input empty check pass");

            Thread.sleep(1000);

            // firstNameInput.focus();
            // firstNameInput.highlight();
            // firstNameInput.click();
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
            System.out.println(addressField.innerHTML());

            // addressInput.focus();
            // addressInput.highlight();
            // addressInput.click();
            addressInput.type("address");
            Thread.sleep(1000);
            addressInput.clear();
            assertThat(addressParent).hasClass(Pattern.compile(".*is-invalid.*"));
            test.pass("Address input empty check pass");
            
            Thread.sleep(1000);

            // addressInput.focus();
            // addressInput.highlight();
            // addressInput.click();
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
            System.out.println(cityField.innerHTML());

            // cityInput.focus();
            // cityInput.highlight();
            // cityInput.click();
            cityInput.type("city");
            Thread.sleep(1000);
            cityInput.clear();
            assertThat(cityParent).hasClass(Pattern.compile(".*is-invalid.*"));
            test.pass("City input empty check pass");
            
            Thread.sleep(1000);

            // cityInput.focus();
            // cityInput.highlight();
            // cityInput.click();
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
            Locator shippingMethodList = shippingMethod.locator("//div[@class='row-item']");
            Locator lastShippingMethod = shippingMethodList.all().getLast().locator("//label");

            lastShippingMethod.click();
            String[] lastIdAry = lastShippingMethod.getAttribute("for").split("-");
            String lastId = lastIdAry[lastIdAry.length - 1];
            assertThat(checkoutInfo).hasAttribute("currentdeliveryoptionid", lastId);
            test.pass("Shipping method radio check pass");

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
            
        } catch (Exception e) {
            System.err.println(e);
            test.fail("Payment options check fail");
        }
    }

    // @Test
    // @Order(11)
    // void checkBillingAddress() {

    // }
}
