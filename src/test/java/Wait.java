import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2016-11-12 0012.
 */
public class Wait {
    public static WebElement explicitlyWaitForID(WebDriver driver, long time, String idName) { //通过ID显式等待，在一定的时间范围内等待某个元素的出现，
        //直到超时就报出异常
        final String str = idName;
        WebElement element = (new WebDriverWait(driver, time)).until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.id(str));
            }
        });
        return element;
    }

    public static WebElement explicitlyWaitForClassName(WebDriver driver, long time, String className) {//通过ClassName显式等待
        final String str = className;
        WebElement element = (new WebDriverWait(driver, time)).until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.className(str));
            }
        });
        return element;
    }

    public static WebElement explicitlyWaitForName(WebDriver driver, long time, String name) {//通过name显式等待
        final String str = name;
        WebElement element = (new WebDriverWait(driver, time)).until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.name(str));
            }
        });
        return element;
    }

    public static void implicitlyWaitBySeconds(WebDriver driver, long time) { //隐式等待
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }







}
