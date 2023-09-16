package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListPageObjectFactory;
import lib.ui.factories.NavigationPageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests for saved list")
public class MyListsTest extends CoreTestCase {

    private final static String name_of_folder = "Learning programming";
    private final static String login = "83yuliya83";
    private final static String password = "!qazxsw@";

    @Test
    @Features(value = {@Feature(value = "Search"),@Feature(value = "Article"), @Feature(value = "Saved List")})
    @DisplayName("Save article to my list")
    @Description("We add article to saved list and delete it")
    @Step("Starting test testSaveFirstArticleToMyList")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testSaveFirstArticleToMyList() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI navigationUI = NavigationPageObjectFactory.get(driver);
        MyListsPageObject myListsPageObject = MyListPageObjectFactory.get(driver);
        AuthorizationPageObject auth = new AuthorizationPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(name_of_folder);
            navigationUI.clickMyList();
        } else if (Platform.getInstance().isIOS()) {
            articlePageObject.addArticleToMySaved();
            articlePageObject.closeArticle();
            navigationUI.clickMyList();
            myListsPageObject.closeSyncPopup();
        } else {
            articlePageObject.addArticleToMySavedList();
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();

            articlePageObject.waitForTitleElement();
            Assert.assertEquals(
                    "We are not on the same page after login",
                    article_title,
                    articlePageObject.getArticleTitle());
            navigationUI.openNavigation();
            navigationUI.clickMyList();
        }
        myListsPageObject.swipeByArticleToDelete(article_title);
    }
}
