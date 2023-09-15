package lib.ui.ios;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSMyListPageObject extends MyListsPageObject {

    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeStaticText[contains(@name, '{TITLE}')]";
        CLOSE_SYNC_POPUP = "id:Close";
        SWIPE_ACTION_DELETE_BUTTON = "id:swipe action delete";
    }

    public IOSMyListPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
