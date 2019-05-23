package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.testng.Assert;

import static base.TestUtil.ElementActions.*;

public class LoginSteps {

    String baseId = "com.getir.getirtestingcasestudy" + ":id/";

    @And("^Enter username \"([^\"]*)\"$")
    public void enterEmail(String email) {
        waitElementAndSendKeys(By.id(baseId + "email"), 10, email, true);
    }

    @And("^Enter password \"([^\"]*)\"$")
    public void enterPassword(String password) {
        waitElementAndSendKeys(By.id(baseId + "password"), 10, password, true);
    }

    @And("^Click log-in button$")
    public void clickLogInButton() {
        waitAndClickElement(By.id(baseId + "email_sign_in_button"), 10);
    }

    @Given("User is at the login page of the application")
    public void userIsAtTheLoginPageOfTheApplication() {
        Assert.assertTrue(isExist(By.id(baseId + "email"), 10),
                "The login page could not be displayed.");
    }

    @And("Is user at the home page?")
    public void ısUserAtTheHomePage() {
        Assert.assertTrue(isExist(By.id(baseId + "basket"), 10),
                "The home page could not be displayed.");
    }
}