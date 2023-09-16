package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests for search")
public class SearchTests extends CoreTestCase {

    @Test
    @Feature("Search")
    @DisplayName("Make sure article can be founded by search")
    @Description("We put searched word into the search line and found an article")
    @Step("Starting test testSearch")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    @Test
    @Feature("Search")
    @DisplayName("Cancel search process")
    @Description("We put searched word into the search line and cancel search")
    @Step("Starting test testCancelSearch")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testCancelSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    @Feature("Search")
    @DisplayName("Make sure there is non empty results page after search")
    @Description("We put searched word into the search line and check search results page not empty")
    @Step("Starting test testAmountOfNonEmptySearch")
    @Severity(value = SeverityLevel.MINOR)
    public void testAmountOfNonEmptySearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        String search_line = "Linkin Park Discography";
        searchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = searchPageObject.getAmountOfFoundedArticles();

        Assert.assertTrue(
                "We found few results!",
                amount_of_search_results > 0
        );
    }

    @Test
    @Feature("Search")
    @DisplayName("Make sure there is empty results page after search")
    @Description("We put searched word into the search line and check search results page is empty")
    @Step("Starting test testAmountOfEmptySearch")
    @Severity(value = SeverityLevel.MINOR)
    public void testAmountOfEmptySearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        String search_line = "wsxcderfv";
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    @Feature("Search")
    @DisplayName("Make sure can find article by title and description")
    @Description("We put searched word into the search line and found articles by title and description")
    @Step("Starting test testSearchByTitleAndDescription")
    @Severity(value = SeverityLevel.MINOR)
    public void testSearchByTitleAndDescription() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForElementByTitleAndDescription("Java", "Island in Indonesia");
        searchPageObject.waitForElementByTitleAndDescription("Java (programming language)", "Object-oriented programming language");
        searchPageObject.waitForElementByTitleAndDescription("JavaScript", "High-level programming language");
    }
}
