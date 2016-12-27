import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Albert on 2016-11-1 0001.
 * 测试wooplus登录功能
 */
public class TestLogIn {
    private static AppiumDriver driver;

   /* public static void main(String[] args) {
        System.out.println("Hello world.");
    }*/

    @BeforeClass //只需要在所有测试用例执行之前执行一次就可以了
    public static void setUp() throws MalformedURLException { //标有BeforeClass注解的方法必须是static的
        File userPath = new File(System.getProperty("user.dir"));
        File appPath = new File(userPath, "app");
        File app = new File(appPath, "WooPlus280(58_10-25).apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformName", "Android");//是启动安卓,还是IOS,还是Firefox ios
        capabilities.setCapability("deviceName", "127.0.0.1:62001 device");//启动的设备是真机还是模拟器，真机名称可在cmd中使用adb devices查看
        capabilities.setCapability("platformVersion", "6.0");//设置安卓系统版本
        capabilities.setCapability("noRset", true);
        capabilities.setCapability("appPackage", "com.mason.wooplus");//app的包名
        capabilities.setCapability("appActivity", "com.mason.wooplus.activity.SplashActivity");//入口activity
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        Wait.implicitlyWaitBySeconds(driver,3);
        //driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);//隐式等待，driver每次执行命令的超时时间
        //driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        /*try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }


    @AfterClass //只需在所有测试用例执行完之后执行一次就可以了
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        driver = null;
    }

    public void clearEditText(WebElement et) {
        et.click();
        String string = et.getText().trim();
        if (string != "") {
            et.clear();
        } else {
            System.out.println("This edittext is null");
        }
    }

    //@Test(expected = WebDriverException.class)
    @Test
    public void testLogIn() throws Exception { //断言

        driver.findElementById("com.mason.wooplus:id/sign_in").click();
        List<WebElement> webElements = driver.findElementsByClassName("android.widget.EditText");
        clearEditText(webElements.get(0));
        webElements.get(0).sendKeys("k98@gmail.com");
        webElements.get(1).sendKeys("kkkkkk");
        driver.findElementById("com.mason.wooplus:id/submit_btn").click();
        WebElement card1 = Wait.explicitlyWaitForID(driver,6,"item_header");//显示等待
        card1.click();
        WebElement like = driver.findElement(By.id("view_like_userpro"));
        like.click();
        Thread.sleep(1000);
        if(ifExist("like")) {
            WebElement like_btn = driver.findElementById("like");
            like_btn.click();
        }
        Thread.sleep(1000);
        if(ifExist("com.mason.wooplus:id/user_profile_btn")) {
            WebElement element = driver.findElement(By.id("com.mason.wooplus:id/cancel"));
            element.click();
        }
        WebElement card2 = Wait.explicitlyWaitForID(driver,2,"item_header");
        card2.click();
        WebElement pass = driver.findElement(By.id("view_pass_userpro"));
        pass.click();
        Thread.sleep(1000);
        if(ifExist("pass")) {
            WebElement pass_btn = driver.findElementById("pass");
            pass_btn.click();
        }
        WebElement like1 = Wait.explicitlyWaitForID(driver, 2, "like_btn");
        like1.click();
        if(ifExist("com.mason.wooplus:id/user_profile_btn")) {
            WebElement element = driver.findElement(By.id("com.mason.wooplus:id/cancel"));
            element.click();
        }
        WebElement pass1 = Wait.explicitlyWaitForID(driver, 1, "pass_btn");
        pass1.click();
        WebElement filter = Wait.explicitlyWaitForID(driver, 1, "filter_btn");
        filter.click();
        WebElement sex1 = Wait.explicitlyWaitForName(driver, 1, "Show Me");
        sex1.click();
        WebElement sex2 = Wait.explicitlyWaitForName(driver, 1,"Women & Men");
        sex2.click();
        WebElement submit1 = Wait.explicitlyWaitForID(driver, 1, "submit_btn");
        submit1.click();
        WebElement picks = Wait.explicitlyWaitForName(driver, 6, "Picks");
        picks.click();
        WebElement user1 = Wait.explicitlyWaitForID(driver, 3, "user1_header");
        user1.click();
        if(ifExist("Got It")) {
            WebElement element = driver.findElement(By.id("com.mason.wooplus:id/got_it"));
            element.click();
        }
        WebElement poke = Wait.explicitlyWaitForID(driver, 1, "bottom_poke");
        poke.click();
        System.out.println("第一个测试用例完成。。");
        //driver.manage().timeouts().pageLoadTimeout(5000, TimeUnit.MILLISECONDS);
        //driver.getPageSource();//列出界面中的元素
        //driver.findElementByAndroidUIAutomator("new UiSelector().text(\"Add note\")");
        //Assert.assertTrue("Fail", true);
    }


    public static boolean ifExist(String string) { //判断界面中是否存在某个字符串
        return driver.getPageSource().contains(string);
    }

    /*@Ignore
    @Test(timeout = 200)
    public void testOne() {  //测试方法的性能，如果200毫秒不能运行完，则该方法的性能就不达标
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }*/


}
