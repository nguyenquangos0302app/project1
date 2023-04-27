package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final FileService fileService;

    private final NoteService noteService;

    private final CredentialService credentialService;

    private final EncryptionService encryptionService;

    public HomeController(FileService fileService,
                          NoteService noteService,
                          CredentialService credentialService,
                          EncryptionService encryptionService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String home(Authentication authentication,
                       FileForm fileForm,
                       NoteForm noteForm,
                       CredentialForm credentialForm,
                       Model model) {
        List<String> fileNames = fileService.findNameFilesByAuthentication(authentication);
        List<Note> notes = noteService.findByAuthentication(authentication);
        List<Credential> credentials = credentialService.findByAuthentication(authentication);

        model.addAttribute("files", fileNames);
        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }

}
