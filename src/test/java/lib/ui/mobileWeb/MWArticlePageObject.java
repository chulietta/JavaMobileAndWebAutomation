package lib.ui.mobileWeb;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "css:h1#firstHeading";
        FOOTER_ELEMENT = "css:footer";
        SAVE_TO_MY_LIST_BUTTON = "css:ul#page-actions li#page-actions-watch a[data-event-name='menu.watch']";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "css:#page-actions li#page-actions-watch a[title='Remove this page from your watchlist']";
        //MY_LIST_TITLE_TPL = "xpath://*[contains(@text, '{TITLE}')]";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
