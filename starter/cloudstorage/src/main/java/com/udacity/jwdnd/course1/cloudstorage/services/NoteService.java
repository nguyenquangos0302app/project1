package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    private final UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public List<Note> findByAuthentication(Authentication authentication) {
        Integer userId = userService.findUserIdByAuthentication(authentication);
        return noteMapper.findByUserId(userId);
    }

    public void save(Authentication authentication, NoteForm noteForm) {
        Integer userId = userService.findUserIdByAuthentication(authentication);
        Note note = new Note();
        note.setNoteTitle(noteForm.getNoteTitle());
        note.setNoteDescription(noteForm.getNoteDescription());
        note.setUserId(userId);
        noteMapper.save(note);
    }

    public void updateByAuthentication(Authentication authentication, NoteForm noteForm) {
        Integer userId = userService.findUserIdByAuthentication(authentication);
        noteMapper.updateByNoteIdAndUserId(noteForm.getNoteId(), noteForm.getNoteTitle(), noteForm.getNoteDescription(), userId);
    }

    public void deleteByAuthenticationAndNoteId(Authentication authentication, Integer noteId) {
        Integer userId = userService.findUserIdByAuthentication(authentication);
        noteMapper.deleteNoteByNoteIdAndUserId(noteId, userId);
    }

}
