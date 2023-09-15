package lib.ui.android;

import lib.ui.NavigationUI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidNavigationUI extends NavigationUI {

    static {
        MY_LIST_OPEN_LINK = "xpath://*[contains(@text, 'View list')]";
    }

    public AndroidNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }
}
