package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialPage {

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlText;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameText;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordText;

    @FindBy(id = "credential-save-button")
    private WebElement credentialSaveButton;

    @FindBy(id = "edit-credential-button")
    private WebElement editCredentialButton;

    @FindBy(id = "delete-credential-button")
    private WebElement deleteCredentialButton;

    @FindBy(id="close-credential-edit-button")
    private WebElement closeCredentialEditButton;

    private WebDriver webDriver;

    public CredentialPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void processCreateCredential() {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 2);
        goToCredentialsTab(webDriverWait);
        clickButtonAdd(webDriverWait);
        inputData(webDriverWait);
        clickButtonSave(webDriverWait);
    }

    public Credential getFirstCredential() {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 2);
        goToCredentialsTab(webDriverWait);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-credential-url")));

        String url = webDriver.findElement(By.id("show-credential-url")).getText();
        String username = webDriver.findElement(By.id("show-credential-username")).getText();

        clickEditButton(webDriverWait);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        String password = credentialPasswordText.getAttribute("value");

        Credential credential = new Credential();
        credential.setUrl(url);
        credential.setUserName(username);
        credential.setPassword(password);

        closeCredentialEditButton.click();

        return credential;
    }

    public void deleteCredential() {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 2);
        goToCredentialsTab(webDriverWait);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-credential-button")));
        deleteCredentialButton.click();
    }

    public void processEditCredential() {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 2);
        goToCredentialsTab(webDriverWait);
        clickEditButton(webDriverWait);
        inputData(webDriverWait);
        clickButtonSave(webDriverWait);
    }

    private void goToCredentialsTab(WebDriverWait webDriverWait) {
        webDriver.manage().window().maximize();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        credentialsTab.click();
    }

    private void clickButtonAdd(WebDriverWait webDriverWait) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-credential-button")));
        addCredentialButton.click();
    }

    private void inputData(WebDriverWait webDriverWait) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        credentialUrlText.clear();
        credentialUrlText.sendKeys("test");
        credentialUsernameText.clear();
        credentialUsernameText.sendKeys("test");
        credentialPasswordText.clear();
        credentialPasswordText.sendKeys("test");
    }

    private void clickButtonSave(WebDriverWait webDriverWait) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-save-button")));
        credentialSaveButton.click();
    }

    private void clickEditButton(WebDriverWait webDriverWait) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-credential-button")));
        editCredentialButton.click();
    }

}
