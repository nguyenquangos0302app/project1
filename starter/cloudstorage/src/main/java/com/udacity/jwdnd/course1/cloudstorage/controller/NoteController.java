package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("")
    public String file(Authentication authentication, NoteForm noteForm, RedirectAttributes redirectAttributes) {

        if (Objects.isNull(noteForm.getNoteId())) {
            noteService.save(authentication, noteForm);
        } else {
            noteService.updateByAuthentication(authentication, noteForm);
        }

        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/result";
    }

    @GetMapping(value = "/delete/{noteId}")
    public String deleteFile(Authentication authentication, @PathVariable Integer noteId, RedirectAttributes redirectAttributes) {
        noteService.deleteByAuthenticationAndNoteId(authentication, noteId);
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/result";
    }

}
