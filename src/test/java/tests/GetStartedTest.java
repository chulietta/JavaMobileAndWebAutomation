package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;

@Epic("Tests for Welcome Page")
public class GetStartedTest extends CoreTestCase {

    @Test
    @Feature("Welcome Page")
    @DisplayName("Pass through Welcome screens")
    @Description("We pass through welcome screens")
    @Step("Starting test testPassThroughWelcome")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testPassThroughWelcome() {
        if ((Platform.getInstance().isAndroid() || Platform.getInstance().isMW())) {
            return;
        }
        WelcomePageObject welcomePage = new WelcomePageObject(driver);

        welcomePage.waitForLearnMoreLink();
        welcomePage.clickNextButton();

        welcomePage.waitForNewWaysToExploreText();
        welcomePage.clickNextButton();

        welcomePage.waitForAddOrEditPreferredLanguagesText();
        welcomePage.clickNextButton();

        welcomePage.waitForLearnMoreAboutDataCollectedText();
        welcomePage.clickGetStartedButton();
    }
}
