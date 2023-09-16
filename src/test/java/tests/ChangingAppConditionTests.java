package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests for condition")
public class ChangingAppConditionTests extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value = "Condition"),@Feature(value = "Article")})
    @DisplayName("Check search results after rotate screen")
    @Description("We searching articles and check results page after rotation")
    @Step("Starting test testChangeScreenOrientationOnSearchResults")
    @Severity(value = SeverityLevel.MINOR)
    public void testChangeScreenOrientationOnSearchResults() {
        if (Platform.getInstance().isMW()) {
            return;
        }
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        String title_before_rotation = articlePageObject.getArticleTitle();

        this.rotateScreenLandscape();

        String title_after_rotation = articlePageObject.getArticleTitle();
        Assert.assertEquals(
                "Article description have been change after rotation",
                title_before_rotation,
                title_after_rotation
        );

        this.rotateScreenPortrait();

        String title_after_second_rotation = articlePageObject.getArticleTitle();
        Assert.assertEquals(
                "Article description have been change after rotation",
                title_before_rotation,
                title_after_second_rotation
        );
    }

    @Test
    @Features(value = {@Feature(value = "Condition"),@Feature(value = "Article")})
    @DisplayName("Check search results after move app to background")
    @Description("We searching articles and check results page after back app from background")
    @Step("Starting test testCheckSearchArticleInBackground")
    @Severity(value = SeverityLevel.MINOR)
    public void testCheckSearchArticleInBackground() {
        if (Platform.getInstance().isMW()) {
            return;
        }
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundApp(2);
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }
}
