package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTests {

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
    public void testCreateNote() {
        signupAndLogin();

        HomePage homePage = new HomePage(driver);
        homePage.processCreateNote();

        driver.get("http://localhost:" + this.port + "/home");

        Note note = homePage.getFirstNote();

        assertEquals("test", note.getNoteTitle());
        assertEquals("test", note.getNoteDescription());

    }

    @Test
    public void testEditNote() {
        signupAndLogin();

        HomePage homePage = new HomePage(driver);
        homePage.processCreateNote();

        driver.get("http://localhost:" + this.port + "/home");
        homePage.processEditNote();

        driver.get("http://localhost:" + this.port + "/home");
        Note note = homePage.getFirstNote();

        assertEquals("test", note.getNoteTitle());
        assertEquals("test", note.getNoteDescription());
    }

    @Test
    public void testDeleteNote() {
        signupAndLogin();

        HomePage homePage = new HomePage(driver);
        homePage.processCreateNote();

        driver.get("http://localhost:" + this.port + "/home");
        homePage.deleteNote();

        try {
            driver.get("http://localhost:" + this.port + "/home");
            Note note = homePage.getFirstNote();
            fail("note can not delete");
        } catch (NoSuchElementException | TimeoutException e) {
            assertTrue(true);
        }

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
