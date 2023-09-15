package lib.ui.ios;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "id:Java (programming language)";
        DESCRIPTION = "id:Automation for Apps";
        FOOTER_ELEMENT = "id:View article in browser";
        SAVE_TO_MY_LIST_BUTTON = "id:Save for later";
        MY_LIST_TITLE_TPL = "xpath://*[contains(@text, '{TITLE}')]";

    }

    public IOSArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
