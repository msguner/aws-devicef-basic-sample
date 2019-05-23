package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSElement;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static io.appium.java_client.touch.offset.PointOption.point;

public class TestUtil {
    private static final Logger LOGGER = Logger.getLogger(TestUtil.class);
    private static final int DEFAULT_WAIT = 15;
    private static AppiumDriver<MobileElement> driver = ThreadLocalDriver.getDriver();
    protected static WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT, 1000);

    //---- APPIUM ELEMENT ACTION METHODS ----
    public static class ElementActions {

        //Element görüntülene kadar beklenir ve return edilir.
        public static MobileElement waitForElement(By el, int seconds, boolean assertion) {
            MobileElement element = null;
            WebDriverWait wait = new WebDriverWait(driver, seconds, 1000);

            if (assertion) {
                try {
                    element = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(el));
                } catch (Exception e) {
                    Assert.fail(el.toString() + " not found! (in " + seconds + " seconds)");
                }
            } else {
                element = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(el));
            }

            return element;
        }

        //Elementler görüntülene kadar beklenir ve liste olarak return edilir.
        public static List<MobileElement> waitForElements(By el, int seconds) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, seconds, 1000);
                List elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(el));
                return elements;
            } catch (Exception e) {
                Assert.fail(el.toString() + " elements not found! (in " + seconds + " seconds)");
            }

            return null;
        }

        //Element görüntülene kadar beklenir ve tıklanır
        public static void waitAndClickElement(By el, int seconds) {
            waitForElement(el, seconds, true).click();
            LOGGER.info("Clicked : " + el.toString());
        }

        //Element görüntülene kadar beklenir ve belirtilen indexde bulunan elemente tıklanır.
        public static void waitAndClickElement(By el, int seconds, int index) {
            try {
                waitForElements(el, seconds).get(index).click();
                LOGGER.info("Clicked[" + index + "] : " + el.toString());
            } catch (Exception e) {
                Assert.fail(el.toString() + "[index='" + index + "']" + " not found! (in " + seconds + " seconds)");
            }
        }

        //Element tıklanabilene kadar beklenir ve tıklanır.
        public static void waitForClickableAndClickElement(By el, int seconds) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, seconds, 1000);
                wait.until(ExpectedConditions.elementToBeClickable(el)).click();
                LOGGER.info("Clicked : " + el.toString());
            } catch (Exception e) {
                Assert.fail("'" + el.toString() + "' element is not clickable. (in " + seconds + " seconds)");
            }
        }

        //Element görüntülene kadar beklenir ve text gönderilir.
        public static void waitElementAndSendKeys(By el, int seconds, String text, boolean hideKeyboard) {
            MobileElement element = waitForElement(el, seconds, true);
            element.click();
            element.clear();
            element.sendKeys(text);
            if (hideKeyboard)
                DeviceUtil.tryHideKeyboard();
            LOGGER.info("SendKey : " + el.toString() + " <- '" + text + "'");
        }

        //Element görüntülene kadar beklenir ve text gönderilir.
        public static void waitElementAndSetValue(By el, int seconds, String text) {
            MobileElement element = waitForElement(el, seconds, true);
//        element.click();
            element.clear();
            element.setValue(text);
            LOGGER.info("SetValue : " + el.toString() + " <- '" + text + "'");
        }

        //Elementin varlığı kontrol edilir
        public static boolean isExist(By el, int seconds) {
            try {
                waitForElement(el, seconds, false);
                LOGGER.info("isExist? : " + el.toString() + " <- true");
                return true;
            } catch (Exception e) {
                LOGGER.info("isExist? : " + el.toString() + " <- false");
                return false;
            }
        }
    }

    //---- SWIPE METHODS ----
    public static class DeviceUtil {

        public static void tryHideKeyboard() {
            if (driver instanceof AndroidDriver) {
                try {
                    LOGGER.info("Hide keyboard");
                    driver.hideKeyboard();
                } catch (Exception e) {
                }
            } else {
                try {
                    IOSElement element = (IOSElement) driver.findElementByClassName("XCUIElementTypeKeyboard");
                    Point keyboardPoint = element.getLocation();
                    tapWithCoordinates(keyboardPoint.getX() + 2, keyboardPoint.getY() - 2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //Tap by coordinates
        public static void tapWithCoordinates(int x, int y) {
            new TouchAction(driver).tap(point(x, y)).perform();
            LOGGER.info("Clicked coordinates : " + "x = " + x + " | y = " + y);
        }
    }

    //---- WAIT METHODS ----
    public static class Waiters {
        //Static bekleme metodu
        public static void waitSeconds(int seconds) {
            try {
                LOGGER.info("Waiting " + seconds + " seconds...");
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}