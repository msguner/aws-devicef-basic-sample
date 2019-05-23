package steps;

import base.ThreadLocalDriver;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ServiceHooks {

    public static AppiumDriver<MobileElement> driver;
    public static String APP_PACKAGE = "com.getir.getirtestingcasestudy";
    public static String APP_ACTIVITY = APP_PACKAGE + ".ui.login.LoginActivity";
    final String Url = "http://127.0.0.1:4723/wd/hub";
    Logger LOGGER = Logger.getLogger(ServiceHooks.class);

    @Before
    public void setUpStep() throws IOException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Amazon device");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        //capabilities.setCapability(AndroidMobileCapabilityType.BROWSER_NAME, MobileBrowserType.CHROME);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
        //capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, true);
        capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, APP_PACKAGE);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, APP_ACTIVITY);
        capabilities.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, false);
        capabilities.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, true);
        capabilities.setCapability("setWebContentsDebuggingEnabled", true);
        capabilities.setCapability("autoAcceptAlerts", true);
        capabilities.setCapability("interKeyDelay", 100);

        driver = new AndroidDriver<MobileElement>(new URL(Url), capabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        ThreadLocalDriver.setDriver(driver);
    }

    @After
    public void teardown() {
        LOGGER.info("After..");
        if (driver != null)
            driver.quit();
    }
}