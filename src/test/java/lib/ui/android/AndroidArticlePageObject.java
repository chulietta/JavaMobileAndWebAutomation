package lib.ui.android;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "xpath://*[@resource-id='pcs-edit-section-title-description']/preceding-sibling::*[1]";
        DESCRIPTION = "xpath://*[@resource-id='pcs-edit-section-title-description']";
        FOOTER_ELEMENT = "xpath://android.view.View[@content-desc='View article in browser']";
        SAVE_TO_MY_LIST_BUTTON = "id:org.wikipedia:id/page_save";
        ADD_TO_LIST_LINK = "xpath://*[contains(@text, 'Add to list')]";
        MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
        MY_LIST_OK_BUTTON = "xpath://*[@text='OK']";
        MY_LIST_TITLE_TPL = "xpath://*[contains(@text, '{TITLE}')]";
    }

    public AndroidArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
