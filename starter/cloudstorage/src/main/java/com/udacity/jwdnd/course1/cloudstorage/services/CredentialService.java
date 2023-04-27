package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;

    private final UserService userService;

    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    public List<Credential> findByAuthentication(Authentication authentication) {
        Integer userId = userService.findUserIdByAuthentication(authentication);
        return credentialMapper.findByUserId(userId);
    }

    public void save(Authentication authentication, CredentialForm credentialForm) {
        Integer userId = userService.findUserIdByAuthentication(authentication);

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedSalt);

        Credential credential = new Credential();
        credential.setUserName(credentialForm.getUserName());
        credential.setKey(encodedSalt);
        credential.setPassword(hashedPassword);
        credential.setUserid(userId);
        credential.setUrl(credentialForm.getUrl());

        credentialMapper.save(credential);

    }

    public void updateByAuthenticationAndCredentialId(Authentication authentication, CredentialForm credentialForm) {
        Integer userId = userService.findUserIdByAuthentication(authentication);

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedSalt);

        credentialMapper.updateByCredentialIdAndUserId(credentialForm.getCredentialId(), credentialForm.getUserName(), credentialForm.getUrl(), encodedSalt, hashedPassword, userId);

    }

    public void deleteByAuthenticationAndCredentialId(Authentication authentication, Integer credentialId) {
        Integer userId = userService.findUserIdByAuthentication(authentication);
        credentialMapper.deleteByCredentialIdAndUserId(credentialId, userId);
    }

}
