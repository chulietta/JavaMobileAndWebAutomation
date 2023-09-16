package lib.ui.mobileWeb;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "css:#searchIcon";
        SEARCH_INPUT = "css:form.search-box>input";
        SEARCH_CANCEL_BUTTON = "css:div.header-action>button.cancel";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[@class = 'wikidata-description' and contains(text(), '{SUBSTRING}')]";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://div[@class='results']//ul/li[@title='{TITLE}']//div[@class='wikidata-description' and contains(text(), '{DESCRIPTION}')]";
        SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
        SKIP_ELEMENT = "css:#cn-toggle-box";
        SEARCH_RESULTS_LIST = "css:ul.page-list li";
        SEARCH_RESULT_BY_ORDER_NUMBER_TPL = "xpath://div[@class = 'results']//ul/li[1]//h3";
    }

    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
