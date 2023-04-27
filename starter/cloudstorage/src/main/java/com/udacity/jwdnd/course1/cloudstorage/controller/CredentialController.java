package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("")
    public String credential(Authentication authentication, CredentialForm credentialForm, RedirectAttributes redirectAttributes) {

        if (Objects.isNull(credentialForm.getCredentialId())) {
            credentialService.save(authentication, credentialForm);
        } else {
            credentialService.updateByAuthenticationAndCredentialId(authentication, credentialForm);
        }

        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/result";

    }

    @GetMapping(value = "/delete/{credentialId}")
    public String deleteCredential(Authentication authentication, @PathVariable Integer credentialId, RedirectAttributes redirectAttributes) {
        credentialService.deleteByAuthenticationAndCredentialId(authentication, credentialId);
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/result";
    }


}
