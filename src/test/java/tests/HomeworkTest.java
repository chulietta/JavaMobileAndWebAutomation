package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListPageObjectFactory;
import lib.ui.factories.NavigationPageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class HomeworkTest extends CoreTestCase {

    @Test
    public void testCheckSearchInputPlaceholderText() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.assertSearchInputPlaceholder("Search Wikipedia");
    }

    /* Ex3 */
    @Test
    public void testSearchResultsAfterCancelSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchListIsPresented();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForSearchListIsNotPresented();
    }

    @Test
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
    public void testCheckMyListAfterDeleteArticle() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        MyListsPageObject myListsPageObject = MyListPageObjectFactory.get(driver);
        NavigationUI navigationUI = NavigationPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        articlePageObject.waitForTitleElement();

        String name_of_folder = "Learning programming";
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(name_of_folder);
        } else {
            articlePageObject.addArticleToMySaved();
        }

        searchPageObject.clickSearchInput();
        searchPageObject.typeSearchLine("appium");
        searchPageObject.clickByArticleWithSubstring("Appium");
        articlePageObject.waitForDescriptionElement();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToExistingList(name_of_folder);
            navigationUI.clickMyList();
        } else {
            articlePageObject.addArticleToMySaved();
            articlePageObject.closeArticle();
            navigationUI.clickMyList();
            myListsPageObject.closeSyncPopup();
        }
        String article_for_delete_title = "Java (programming language)";
        String article_title_not_deleted = "Appium";
        String article_description_not_deleted = "Automation for Apps";

        myListsPageObject.swipeByArticleToDelete(article_for_delete_title);
        myListsPageObject.waitForArticleToAppearByTitle(article_title_not_deleted);
        myListsPageObject.clickArticleTitleInMyList(article_title_not_deleted);
        articlePageObject.waitForDescriptionElement();

        String current_article_description = articlePageObject.getArticleDescription();
        Assert.assertEquals(
                "We see unexpected description!",
                article_description_not_deleted,
                current_article_description
        );
    }
}
