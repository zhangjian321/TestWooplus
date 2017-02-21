import io.appium.java_client.android.AndroidDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Project: TestWooplus2
 * Author: Albert
 * Date: 2016-12-28 0028
 * Description: Just for testing appium API
 */
public class TestAppiumAPI {
    private static AndroidDriver driver;

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "167850c8");
        capabilities.setCapability("platformVersion", "5.0");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appPackage", "com.android.settings");
        capabilities.setCapability("appActivity", ".Settings");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        driver = null;
    }

    @Test
    public void test() {  //测试appium的一些API功能
        if (!driver.isLocked()) {
            driver.lockDevice();
            driver.pressKeyCode(26);
        }
    }

}
