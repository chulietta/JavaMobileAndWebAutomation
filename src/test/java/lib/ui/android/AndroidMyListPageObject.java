package lib.ui.android;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidMyListPageObject extends MyListsPageObject {

    static {
        ARTICLE_BY_TITLE_TPL = "xpath://*[contains(@text, '{TITLE}')]";
    }

    public AndroidMyListPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
