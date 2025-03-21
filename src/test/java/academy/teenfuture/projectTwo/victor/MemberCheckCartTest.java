package academy.teenfuture.projectTwo.victor;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.WaitForOptions;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.WaitForSelectorState;

import academy.teenfuture.projectTwo.Base;

public class MemberCheckCartTest extends Base {
    @BeforeAll 
    private static void openCartPanel() {
        currentClass = "MemberCheckCartTest";
        action.login(page, dotenv);
        Locator cartIcon = page.locator("(//a[@title='my cart'])[2]");
        cartIcon.click();
    }

    @BeforeEach
    private void waitJS() throws InterruptedException {
        Thread.sleep(1000);
    }

    private long barWidthPercent(int pointsInt) {
        double barWidthDouble = pointsInt / 200.0;
        long barWidth = Math.round(barWidthDouble*100);
        if (barWidth > 100) {
            barWidth = 100;
        }
        return barWidth;
    }

    @Test
    void checkPromotionBanner() throws InterruptedException {
        ExtentTest test = extent.createTest("Check promotion banner");

        try {
            // empty cart
            Locator promotionBanner = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='club-promotion-banner']");
            Locator progressChart = promotionBanner.locator("//div[@class='tier-bar']");
            // if cart is not empty, clear the cart
            Locator cartContainer = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='cart-item-container']");
            if (cartContainer.isVisible()) {
                cartContainer.highlight();
                action.removeProduct(page);
                cartContainer.waitFor(new WaitForOptions().setState(WaitForSelectorState.DETACHED));
                // System.out.println(cartContainer.isVisible());
                Thread.sleep(20000);
            }
            cartContainer.waitFor(new WaitForOptions().setState(WaitForSelectorState.DETACHED));
            assertThat(progressChart).hasCSS("--totalWidth", "0%");
            Locator promotionTitle = promotionBanner.locator("//p[@class='club-promotion-title']");
            assertThat(promotionTitle).containsText(Pattern.compile(".*CASETiFY Club.*"));
            test.pass("Promotion banner when empty cart check pass");

            // have product

            // basic to bronze
            page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='btn-back']").click();
            action.addProduct(page);

            Locator note = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='crm-qualifying-spending-note']");
            Locator points = note.locator("//div[@class='point-text']//span[@class='d-flex align-items-center']//span");
            int pointsInt = Integer.parseInt(points.all().getFirst().innerText().substring(1));
            int remaining = 50 - pointsInt;
            String regex = ".*" + remaining + ".*";
            promotionBanner = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='club-promotion-banner']");
            promotionTitle = promotionBanner.locator("//p[@class='club-promotion-title']");
            // assertThat(promotionTitle).containsText(Pattern.compile(regex));
            long barWidth = barWidthPercent(pointsInt);
            // System.out.println(points.all().getFirst().innerText());
            progressChart = promotionBanner.locator("//div[@class='tier-bar']");
            assertThat(progressChart).hasCSS("--totalWidth", barWidth+"%");
            test.pass("Promotion banner from basic to bronze check pass");
            
            // bronze to silver
            action.changeAmount(page, 4);
            points = note.locator("//div[@class='point-text']//span[@class='d-flex align-items-center']//span");
            pointsInt = Integer.parseInt(points.all().getFirst().innerText().substring(1));
            remaining = 120 - pointsInt;
            regex = ".*" + remaining + ".*";
            barWidth = barWidthPercent(pointsInt);
            promotionBanner = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='club-promotion-banner']");
            promotionTitle = promotionBanner.locator("//p[@class='club-promotion-title']");
            progressChart = promotionBanner.locator("//div[@class='tier-bar']");
            assertThat(promotionTitle).containsText(Pattern.compile(".*!.*!"));
            assertThat(progressChart).hasCSS("--totalWidth", barWidth+"%");
            Locator advanceHints = promotionBanner.locator("//div[@class='advance-upgrade-hints']//span");
            assertThat(advanceHints).containsText(Pattern.compile(regex));
            test.pass("Promotion banner from bronze to silver check pass");

            // silver to gold
            action.changeAmount(page, 7);
            points = note.locator("//div[@class='point-text']//span[@class='d-flex align-items-center']//span");
            pointsInt = Integer.parseInt(points.all().getFirst().innerText().substring(1));
            remaining = 200 - pointsInt;
            regex = ".*" + remaining + ".*";
            barWidth = barWidthPercent(pointsInt);

            promotionBanner = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='club-promotion-banner']");
            promotionTitle = promotionBanner.locator("//p[@class='club-promotion-title']");
            progressChart = promotionBanner.locator("//div[@class='tier-bar']");
            advanceHints = promotionBanner.locator("//div[@class='advance-upgrade-hints']//span");
            assertThat(promotionTitle).containsText(Pattern.compile(".*!.*!"));
            assertThat(progressChart).hasCSS("--totalWidth", barWidth+"%");
            assertThat(advanceHints).containsText(Pattern.compile(regex));
            test.pass("Promotion banner from silver to gold check pass");

            // reach gold
            action.changeAmount(page, 11);
            points = note.locator("//div[@class='point-text']//span[@class='d-flex align-items-center']//span");
            pointsInt = Integer.parseInt(points.all().getFirst().innerText().substring(1));
            barWidth = barWidthPercent(pointsInt);
            promotionBanner = page.locator("//nav[@class='nav-d-flex align-items-center justify-content-space-between position-relative header']//div[@class='club-promotion-banner']");
            promotionTitle = promotionBanner.locator("//p[@class='club-promotion-title']");
            progressChart = promotionBanner.locator("//div[@class='tier-bar']");
            assertThat(promotionTitle).containsText(Pattern.compile(".*!.*!"));
            assertThat(progressChart).hasCSS("--totalWidth", barWidth+"%");
            test.pass("Promotion banner when reaching gold check pass");

        } catch (Exception e) {
            System.err.println(e);
            test.fail("Promotion banner check fail");
        } finally {
            action.removeProduct(page);
        }
    }
}
