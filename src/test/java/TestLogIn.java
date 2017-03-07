import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.appium.java_client.android.AndroidKeyCode.KEYCODE_DEL;
import static io.appium.java_client.android.AndroidKeyCode.KEYCODE_MOVE_END;

/**
 * Created by Albert on 2016-11-1 0001.
 * 测试wooplus登录功能，玩card，发moment，上传头像，注册
 */
public class TestLogIn {
    private static AndroidDriver driver;

    @BeforeClass //只需要在所有测试用例执行之前执行一次就可以了
    public static void setUp() throws MalformedURLException { //标有BeforeClass注解的方法必须是static的
        File userPath = new File(System.getProperty("user.dir"));
        File appPath = new File(userPath, "app");
        File app = new File(appPath, "WooPlus280(58_10-25).apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", app.getAbsolutePath());//获取到APP的绝对路径
        //capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("noReset", true);//当app已经被安装是就不会再次被安装，节约时间
        capabilities.setCapability("platformName", "Android");//是启动安卓,还是IOS,还是Firefox ios
        capabilities.setCapability("deviceName", "127.0.0.1:62001");//启动的设备是真机还是模拟器，真机名称可在cmd中使用adb devices查看
        capabilities.setCapability("platformVersion", "4.4.2");//设置安卓系统版本
        capabilities.setCapability("unicodeKeyboard", true);//支持中文输入
        capabilities.setCapability("resetKeyboard", true);//重置为默认的输入法
        capabilities.setCapability("noSign", true);//安装时不对app进行重签名，因为有些app重签名之后可能无法使用
        capabilities.setCapability("appPackage", "com.mason.wooplus");//app的包名
        capabilities.setCapability("appActivity", "com.mason.wooplus.activity.SplashActivity");//入口activity
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        Wait.implicitlyWaitBySeconds(driver,5);
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

    //清空输入框中的文本，只用clear方法是不行的
    public void clearEditText(WebElement et) {
        et.click();
        String string = et.getText();
        if (string != "") {
            driver.pressKeyCode(KEYCODE_MOVE_END);  //将鼠标移到末尾
            for(int i=0; i<string.length(); i++) driver.pressKeyCode(KEYCODE_DEL); //从后往前挨个删除
        } else {
            System.out.println("This edittext is null");
        }
    }

    //@Test(expected = WebDriverException.class)
    @Test
    public void testLogIn() throws Exception { //断言
        if (isElementExist("com.mason.wooplus:id/me")) {  //判断是否已经登陆，若已经登陆了就退出登陆
            driver.findElementById("com.mason.wooplus:id/me").click();
            Wait.explicitlyWaitForID(driver, 1, "right_menu_settings").click();
            List<WebElement> elements = driver.findElements(By.className("android.widget.RelativeLayout"));
            elements.get(3).click();
            Wait.explicitlyWaitForID(driver, 1, "com.mason.wooplus:id/buttonOk").click();
        }
        Wait.explicitlyWaitForID(driver, 2, "com.mason.wooplus:id/sign_in").click();
        List<WebElement> webElements = driver.findElements(By.className("android.widget.EditText"));
        clearEditText(webElements.get(0));
        //输入注册邮箱和密码
        webElements.get(0).sendKeys("m105@gmail.com");
        webElements.get(1).sendKeys("mmmmmm");
        driver.findElementById("com.mason.wooplus:id/submit_btn").click();
        clickPlayNextAround();
        WebElement card1 = Wait.explicitlyWaitForID(driver,12,"item_header");//显示等待
        card1.click();
        WebElement like = driver.findElement(By.id("view_like_userpro"));//profile中点击like按钮
        like.click();
        threadSleep(1000);
//        System.out.println("是否存在提示框：com.mason.wooplus:id/like" + ifExist("com.mason.wooplus:id/like"));
        if(isElementExist("com.mason.wooplus:id/like")) {
            driver.findElementById("com.mason.wooplus:id/like").click();
        }
        threadSleep(1000);
        if(isElementExist("com.mason.wooplus:id/user_profile_btn")) {
            driver.findElement(By.id("com.mason.wooplus:id/cancel")).click();
        }
        clickPlayNextAround();
        WebElement card2 = Wait.explicitlyWaitForID(driver,2,"item_header");
        card2.click();
        WebElement pass = driver.findElement(By.id("view_pass_userpro"));//profile中点击pass按钮
        pass.click();
        threadSleep(1000);
        if(isElementExist("pass")) {
            WebElement pass_btn = driver.findElementById("pass");
            pass_btn.click();
        }
        WebElement like1 = Wait.explicitlyWaitForID(driver, 2, "like_btn");
        like1.click();
        if(isElementExist("com.mason.wooplus:id/user_profile_btn")) {
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
        threadSleep(2000);
        driver.findElementByName("Picks").click();
        WebElement user1 = Wait.explicitlyWaitForID(driver, 8, "user1_header");
        user1.click();
        if(isElementExist("com.mason.wooplus:id/got_it")) {
            WebElement element = driver.findElement(By.id("com.mason.wooplus:id/got_it"));
            element.click();
        }
        WebElement poke = Wait.explicitlyWaitForID(driver, 1, "bottom_poke");
        poke.click();
        Wait.explicitlyWaitForID(driver, 1, "com.mason.wooplus:id/left_btn_icon").click();
        Wait.explicitlyWaitForName(driver, 1, "Match").click();
        int width = (Integer) getWidthAndHeight().get("width"); //获取屏幕宽度
        int height = (Integer) getWidthAndHeight().get("height"); //获取屏幕高度
        swipFromCenterToRight(width, height, 2000); //右滑
        swipFromCenterToLeft(width, height, 2000); //左滑
        snapshot(driver, "card.jpg");
        //driver.manage().timeouts().pageLoadTimeout(5000, TimeUnit.MILLISECONDS);
        //driver.getPageSource();//列出界面中的元素
        //driver.findElementByAndroidUIAutomator("new UiSelector().text(\"Add note\")");
        //Assert.assertTrue("Fail", true);
    }

    @Test
    public void testPlayMomentByCamera() throws Exception{
        Wait.explicitlyWaitForID(driver, 2, "com.mason.wooplus:id/camera").click();
        Wait.explicitlyWaitForID(driver, 1, "com.mason.wooplus:id/moments_post_view").click();
        //点击take photo按钮
        Wait.explicitlyWaitForID(driver, 1, "com.mason.wooplus:id/take_photo").click();
        //点击拍照按钮
        Wait.explicitlyWaitForID(driver, 1, "com.android.camera:id/shutter_button").click();
        //点击选择按钮
        Wait.explicitlyWaitForID(driver, 1, "com.android.camera:id/btn_done").click();
        //在输入框中输入一段文本
        Wait.explicitlyWaitForID(driver, 1, "com.mason.wooplus:id/edit").sendKeys("This is a test.");
        //点击submit按钮
        Wait.explicitlyWaitForID(driver, 1, "com.mason.wooplus:id/submit").click();
        //判断是否发布moment成功（通过是否转到moment界面来判断）
        Assert.assertTrue("Posting moment is failed", Wait.explicitlyWaitForID(driver, 5, "com.mason.wooplus:id/moments_post_view").isDisplayed());
    }

    @Test
    public void testPlayMomentByAlbum() throws Exception{
        Wait.explicitlyWaitForID(driver, 2, "com.mason.wooplus:id/camera").click();
        //点击choose from photos按钮
        Wait.explicitlyWaitForID(driver, 1, "com.mason.wooplus:id/choose_photo").click();
        //在相册中根据像素点来点击第一个相册
        driver.tap(1, 355, 483, 1);
        //在第一个相册中根据像素点来点击第一张图片
        driver.tap(1, 356, 539, 1);
        Wait.explicitlyWaitForID(driver, 1, "com.mason.wooplus:id/submit").click();
        //判断是否发布moment成功（通过是否转到moment界面来判断）
        Assert.assertTrue("Posting moment is failed", Wait.explicitlyWaitForID(driver, 5, "com.mason.wooplus:id/moments_post_view").isDisplayed());
    }

    @Test
    public void testSignUpWithEmail() {
        System.out.println("test two");
    }

    @Ignore
    public void testUpLoadPicture() {

    }

    private static boolean ifExist(String string) { //判断界面中是否存在某个字符串
        return driver.getPageSource().contains(string);
    }

    private static Map getWidthAndHeight() { //获取屏幕的宽度和高度
        Map<String, Integer> map = new HashMap<String, Integer>();//规定key和value的类型，不然会有警告
        map.put("width", driver.manage().window().getSize().width);
        map.put("height", driver.manage().window().getSize().height);
        return map;
    }

    private static void swipFromCenterToRight(int width, int height, int ms) { //从屏幕向右滑动
        int x1 = (int)(width * 0.05);
        int y1 = (int)(height * 0.5);
        int x2 = (int)(width * 0.75);
        driver.swipe(x1, y1, x2, y1, ms);
        Wait.implicitlyWaitBySeconds(driver, 1);
    }

    private static void swipFromCenterToLeft(int width, int height, int ms) { //从屏幕向左滑动
        int x1 = (int)(width * 0.75);
        int y1 = (int)(height * 0.5);
        int x2 = (int)(width * 0.05);
        driver.swipe(x1, y1, x2, y1, ms);
        Wait.implicitlyWaitBySeconds(driver, 1);
    }

    private static boolean isElementExist(String str) { //通过元素ID来判断元素是否存在
        try {
            driver.findElement(By.id(str));
        } catch (org.openqa.selenium.NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static void clickPlayNextAround() { //若页面中检测到有play next around按钮就点击按钮
        String str = "com.mason.wooplus:id/imagebutton_start";
        if (isElementExist(str)) {
            driver.findElementById(str).click();
            Wait.implicitlyWaitBySeconds(driver, 3);
        }
    }

    /**
     * This Method create for take screenshot
     * 捕获截图功能
     * @param drivername
     * @param filename
     * 调用snapshot((TakesScreenshot) driver, "zhihu_showClose.png");
     */
    private static void snapshot(TakesScreenshot drivername, String filename) {
        // this method will take screen shot ,require two parameters ,one is
        // driver name, another is file name

        //get current user dir
        String currentPath = System.getProperty("user.dir");
        //ScreenShot
        File scrFile = drivername.getScreenshotAs(OutputType.FILE);
        // Now you can do whatever you need to do with it, for example copy somewhere

        //create a file path
        File file = new File(currentPath + "\\" + filename);
        if (file.exists()) {
            file.delete();
        }
        try {
            System.out.println("save snapshot path is:" + currentPath + "/"
                    + filename);
            //put the ScreenShut to the create file
            FileUtils.copyFile(scrFile, file);
        } catch (IOException e) {
            System.out.println("Can't save screenshot");
            e.printStackTrace();
        } finally {
            System.out.println("screen shot finished, it's in " + currentPath
                    + " folder");
        }
    }

    //对警告框的处理
    private static void acceptAlert() {
        Alert alert = driver.switchTo().alert();
        alert.accept(); //对警告框接收
//        alert.dismiss(); //对警告框拒绝
    }

    //线程睡觉
    private static void threadSleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
