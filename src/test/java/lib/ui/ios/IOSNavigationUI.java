package lib.ui.ios;

import lib.ui.NavigationUI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSNavigationUI extends NavigationUI {

    static {
        MY_LIST_OPEN_LINK = "id:Saved";
        HOME_LINK = "xpath://XCUIElementTypeNavigationBar[@name=\"W\"]";
    }
    public IOSNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }
}
