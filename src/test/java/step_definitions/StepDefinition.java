package step_definitions;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit.ScreenShooter;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.CucumberOptions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

@CucumberOptions
public class StepDefinition {

    @Before
    public void clearCache() {
        clearBrowserCookies();
        clearBrowserCache();
    }

    @Given("^open email authorize page$")
    public void openAuthorizePage() {
        open("http://localhost:9001/email");
    }

    @When("^type to input with placeholder \"(.+)\" text:\"(.+)\"$")
    public void input(String elementName, String text) {
        sleep(1_000);
        $(by("placeholder", elementName)).sendKeys(text);
    }

    @When("^press input submit button with value \"(.+)\"$")
    public void submitButton(String buttonName) {
        $(Selectors.byValue(buttonName)).pressEnter();
    }

    @When("^wait until form frame disappears$")
    public void waitFormDisappears() {
        Selenide.$(byCssSelector("form")).waitUntil(Condition.disappears, 5_000);
    }

    @Then("^element with name \"(.+)\" should exist$")
    public boolean exist(String value) {
        return $(byText(value)).exists();
    }

    @When("^go to link \"(.+)\"$")
    public void goToLink(String linkName) {
        $(byText(linkName)).click();
    }

    @When("^press button with text \"(.+)\"")
    public void pressButton(String text) {
        sleep(1_000);
        $(byText(text)).pressEnter();
    }

    @When("^wait until form frame appears$")
    public void waitFormAppears() {
        $(byCssSelector("form")).waitUntil(Condition.appears, 5_000);
    }

    @Then("^element with name \"(.+)\" has value \"(.+)\"$")
    public boolean hasInSite(String elementName, String value) {
        return $(byName(elementName)).getValue().equals(value);
    }

    @Then("^page is authorize page$")
    public boolean isAuthorizePage() {
        return WebDriverRunner.url().equals("http://localhost:9001/email");
    }

    @Test
    public void failed_authorize() {
        openAuthorizePage();
        input("email", "alice@yandex.ru");
        input("password", "Password");
        submitButton("Login");
        waitFormDisappears();
        Assert.assertTrue(exist("Личный кабинет"));
    }

    @Test
    public void failed_add_in_database() {
        openRegistrationPage();
        input("username", "Frank");
        input("email", "frank@microsoft.ru");
        input("password", "FrankPassword");
        submitButton("Sign up");
        waitFormDisappears();
        Assert.assertTrue(exist("Личный кабинет"));
    }

    private void openRegistrationPage() {
        openAuthorizePage();
        goToLink("Sign up");
    }

    @Rule
    public ScreenShooter screenShooter = ScreenShooter.failedTests();

    @Test
    public void success_search_in_site() {
        openAuthorizePage();
        input("email", "bob@gmail.com");
        input("password", "BobPassword");
        submitButton("Login");
        waitFormDisappears();
        pressButton("Личный кабинет");
        waitFormAppears();
        Assert.assertTrue(hasInSite("name", "Bob"));
    }

    @Test
    public void success_exit_from_account() {
        openAuthorizePage();
        input("email", "bob@gmail.com");
        input("password", "BobPassword");
        submitButton("Login");
        waitFormDisappears();
        pressButton("Выход");
        waitFormAppears();
        Assert.assertTrue(isAuthorizePage());
    }
}
