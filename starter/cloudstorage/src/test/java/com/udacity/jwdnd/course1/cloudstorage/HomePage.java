package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "btnLogout")
    private WebElement logoutButton;

    private WebDriver webDriver;

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void logout() {
        logoutButton.click();
    }

    public void processCreateNote() {
        NotePage notePage = new NotePage(webDriver);
        notePage.processCreateNote();
    }

    public Note getFirstNote() {
        NotePage notePage = new NotePage(webDriver);
        return notePage.getFirstNote();
    }

    public void deleteNote() {
        NotePage notePage = new NotePage(webDriver);
        notePage.deleteNote();
    }

    public void processEditNote() {
        NotePage notePage = new NotePage(webDriver);
        notePage.processEditNote();
    }

    public void processCreateCredential() {
        CredentialPage credentialPage = new CredentialPage(webDriver);
        credentialPage.processCreateCredential();
    }

    public Credential getFirstCredential() {
        CredentialPage credentialPage = new CredentialPage(webDriver);
        return credentialPage.getFirstCredential();
    }

    public void deleteCredential() {
        CredentialPage credentialPage = new CredentialPage(webDriver);
        credentialPage.deleteCredential();
    }

    public void processEditCredential() {
        CredentialPage credentialPage = new CredentialPage(webDriver);
        credentialPage.processEditCredential();
    }

}
