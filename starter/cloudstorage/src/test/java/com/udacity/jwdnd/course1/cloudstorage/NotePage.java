package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotePage {

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "save-note-button")
    private WebElement saveNoteButton;

    @FindBy(id = "delete-note-button")
    private WebElement deleteNoteButton;

    @FindBy(id = "edit-note-button")
    private WebElement editNoteButton;

    private WebDriver webDriver;

    public NotePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void processCreateNote() {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 2);
        gotoNoteTap(webDriverWait);
        clickBtnAddNote(webDriverWait);
        inputData(webDriverWait);
        clickBtnSaveNote(webDriverWait);
    }

    public Note getFirstNote() {

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 2);
        gotoNoteTap(webDriverWait);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-note-title")));

        String title = webDriver.findElement(By.id("show-note-title")).getText();
        String desc = webDriver.findElement(By.id("show-note-description")).getText();

        Note note = new Note();
        note.setNoteTitle(title);
        note.setNoteDescription(desc);

        return note;
    }

    public void deleteNote() {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 2);
        gotoNoteTap(webDriverWait);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-note-button")));
        deleteNoteButton.click();
    }

    public void processEditNote() {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 2);
        gotoNoteTap(webDriverWait);
        clickBtnEditNote(webDriverWait);
        inputData(webDriverWait);
        clickBtnSaveNote(webDriverWait);
    }

    private void gotoNoteTap(WebDriverWait webDriverWait) {
        webDriver.manage().window().maximize();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        notesTab.click();
    }

    private void clickBtnAddNote(WebDriverWait webDriverWait) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note-button")));
        addNoteButton.click();
    }

    private void inputData(WebDriverWait webDriverWait) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        noteTitle.clear();
        noteTitle.sendKeys("test");
        noteDescription.clear();
        noteDescription.sendKeys("test");
    }

    private void clickBtnSaveNote(WebDriverWait webDriverWait) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note-button")));
        saveNoteButton.click();
    }

    private void clickBtnEditNote(WebDriverWait webDriverWait) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-note-button")));
        editNoteButton.click();
    }

}
