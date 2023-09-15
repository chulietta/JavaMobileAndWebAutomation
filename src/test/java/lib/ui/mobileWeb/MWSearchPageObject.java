package lib.ui.mobileWeb;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "css:#searchIcon";
        SEARCH_INPUT = "css:form.search-box>input";
        SEARCH_CANCEL_BUTTON = "css:div.header-action>button.cancel";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[@class = 'wikidata-description' and contains(text(), '{SUBSTRING}')]";
        //SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://XCUIElementTypeStaticText[contains(@name, '{TITLE}')]/following-sibling::*[contains(@name,'{DESCRIPTION}')]";
        SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
        SKIP_ELEMENT = "css:#cn-toggle-box";
        //SEARCH_FROM_ARTICLE_BUTTON = "xpath://XCUIElementTypeButton[@name='Search Wikipedia']";
        //SEARCH_RESULTS_LIST = "xpath://XCUIElementTypeCollectionView";
        //SEARCH_RESULT_BY_ORDER_NUMBER_TPL = "xpath://XCUIElementTypeCollectionView/XCUIElementTypeCell[{NUMBER}]//XCUIElementTypeStaticText[1]";
    }

    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
