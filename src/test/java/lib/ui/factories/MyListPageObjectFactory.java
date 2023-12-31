package lib.ui.factories;

import lib.Platform;
import lib.ui.MyListsPageObject;
import lib.ui.android.AndroidMyListPageObject;
import lib.ui.ios.IOSMyListPageObject;
import lib.ui.mobileWeb.MWMyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MyListPageObjectFactory {

    public static MyListsPageObject get(RemoteWebDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidMyListPageObject(driver);
        } else if (Platform.getInstance().isIOS()) {
            return new IOSMyListPageObject(driver);
        } else {
            return new MWMyListsPageObject(driver);
        }
    }
}
