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

@Epic("My homework")
public class HomeworkTest extends CoreTestCase {

    private final static String login = "83yuliya83";
    private final static String password = "!qazxsw@";

    @Test
    @Feature("Search")
    @DisplayName("Check search line has placeholder")
    @Description("We check search line placeholder text")
    @Step("Starting test testCheckSearchInputPlaceholderText")
    @Severity(value = SeverityLevel.TRIVIAL)
    public void testCheckSearchInputPlaceholderText() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.assertSearchInputPlaceholder("Search Wikipedia");
    }

    /* Ex3 */
    @Test
    @Feature("Search")
    @DisplayName("Check search results page content after cancel search")
    @Description("We check result page is cleared after cancel search")
    @Step("Starting test testSearchResultsAfterCancelSearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSearchResultsAfterCancelSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchListIsPresented();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForSearchListIsNotPresented();
    }

    @Test
    @Feature("Search")
    @DisplayName("Check search results has searched word in title")
    @Description("We check that search result has searched word in title")
    @Step("Starting test testSearchResultsHasSearchedWord")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testSearchResultsHasSearchedWord() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchListIsPresented();
        searchPageObject.assertSearchResultContainsSearchRequest("1");
        searchPageObject.assertSearchResultContainsSearchRequest("2");
        searchPageObject.assertSearchResultContainsSearchRequest("3");
    }

    /* Ex6 */
    @Test
    @Features(value = {@Feature(value = "Search"),@Feature(value = "Article")})
    @DisplayName("Check article title is visible after article open")
    @Description("We check article title is present after article open")
    @Step("Starting test testSearchResultsHasSearchedWord")
    @Severity(value = SeverityLevel.NORMAL)
    public void testTitleOfArticleVisibleAfterOpen() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        String search_line = "java";
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        articlePageObject.assertTitlePresent();
    }

    /* Ex5 */
    @Test
    @Features(value = {@Feature(value = "Search"),@Feature(value = "Article"), @Feature(value = "Saved List")})
    @DisplayName("Delete one article from my list and make sure that another article is in my list")
    @Description("We add two articles in saved list, delete one of them and make sure, that another article is in saved list")
    @Step("Starting test testCheckMyListAfterDeleteArticle")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testCheckMyListAfterDeleteArticle() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        MyListsPageObject myListsPageObject = MyListPageObjectFactory.get(driver);
        NavigationUI navigationUI = NavigationPageObjectFactory.get(driver);
        AuthorizationPageObject auth = new AuthorizationPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();

        String name_of_folder = "Learning programming";
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(name_of_folder);
        } else if (Platform.getInstance().isIOS()) {
            articlePageObject.addArticleToMySaved();
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
        }

        searchPageObject.clickSearchInput();
        searchPageObject.typeSearchLine("appium");
        searchPageObject.clickByArticleWithSubstring("Automation for Apps");
        articlePageObject.waitForTitleElement();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToExistingList(name_of_folder);
            navigationUI.clickMyList();
        } else if (Platform.getInstance().isIOS()) {
            articlePageObject.addArticleToMySaved();
            articlePageObject.closeArticle();
            navigationUI.clickMyList();
            myListsPageObject.closeSyncPopup();
        } else {
            articlePageObject.addArticleToMySavedList();
            navigationUI.openNavigation();
            navigationUI.clickMyList();
        }

        String article_for_delete_title = "Java (programming language)";
        String article_title_not_deleted = "Appium";
        String article_description_not_deleted = "Automation for Apps";

        myListsPageObject.swipeByArticleToDelete(article_for_delete_title);
        myListsPageObject.waitForArticleToAppearByTitle(article_title_not_deleted);
        myListsPageObject.clickArticleTitleInMyList(article_title_not_deleted);
        if (Platform.getInstance().isMW()) {
            articlePageObject.waitForTitleElement();
            String current_article_title = articlePageObject.getArticleTitle();
            Assert.assertEquals(
                    "We see unexpected title!",
                    article_title_not_deleted,
                    current_article_title
            );
        } else {
            articlePageObject.waitForDescriptionElement();
            String current_article_description = articlePageObject.getArticleDescription();
            Assert.assertEquals(
                    "We see unexpected description!",
                    article_description_not_deleted,
                    current_article_description
            );
        }
    }
}
