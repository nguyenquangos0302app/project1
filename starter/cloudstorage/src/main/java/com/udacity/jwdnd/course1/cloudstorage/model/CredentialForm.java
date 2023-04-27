package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialForm {

    private Integer credentialId;

    private String userName;

    private String password;

    private String url;

    public CredentialForm() {
    }

    public CredentialForm(Integer credentialId, String userName, String password, String url) {
        this.credentialId = credentialId;
        this.userName = userName;
        this.password = password;
        this.url = url;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
