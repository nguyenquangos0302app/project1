package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testCreateCredential() {
        signupAndLogin();

        HomePage homePage = new HomePage(driver);
        homePage.processCreateCredential();

        driver.get("http://localhost:" + this.port + "/home");
        Credential credential = homePage.getFirstCredential();

        assertEquals("test", credential.getUrl());
        assertEquals("test", credential.getUserName());
        assertEquals("test", credential.getPassword());
    }

    @Test
    public void testDeleteCredential() {
        signupAndLogin();

        HomePage homePage = new HomePage(driver);
        homePage.processCreateCredential();

        driver.get("http://localhost:" + this.port + "/home");
        homePage.deleteCredential();

        try {
            driver.get("http://localhost:" + this.port + "/home");
            Credential credential = homePage.getFirstCredential();
            fail("Credential can not delete");
        } catch (NoSuchElementException | TimeoutException e) {
            assertTrue(true);
        }

    }

    @Test
    public void testEditCredential() {
        signupAndLogin();

        HomePage homePage = new HomePage(driver);
        homePage.processCreateCredential();

        driver.get("http://localhost:" + this.port + "/home");
        homePage.processEditCredential();

        driver.get("http://localhost:" + this.port + "/home");
        Credential credential = homePage.getFirstCredential();

        assertEquals("test", credential.getUrl());
        assertEquals("test", credential.getUserName());
        assertEquals("test", credential.getPassword());
    }

    private void signupAndLogin() {
        driver.get("http://localhost:" + this.port + "/signup");
        assertEquals("Sign Up", driver.getTitle());

        SignupPage signupPage = new SignupPage(driver);
        signupPage.signUp("test", "test", "test", "test");

        driver.get("http://localhost:" + this.port + "/login");
        assertEquals("Login", driver.getTitle());

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("test", "test");
    }

}
