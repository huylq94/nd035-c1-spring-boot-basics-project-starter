package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationFacade;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @PostMapping("/add-note")
    public String addNewOrUpdateNote(@ModelAttribute("note") NoteForm noteForm, Model model) {
        Authentication authentication = authenticationFacade.getAuthentication();
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        if (Objects.nonNull(noteForm)) {
            String title = noteForm.getNoteTitle();
            String description = noteForm.getNoteDescription();
            if (StringUtils.isEmpty(noteForm.getNoteId())) {
                noteService.insert(Note.builder()
                        .noteTitle(title)
                        .noteDescription(description)
                        .userId(userId)
                        .build());
            } else {
                noteService.update(Note.builder()
                         .noteId(Integer.parseInt(noteForm.getNoteId()))
                        .noteTitle(title)
                        .noteDescription(description)
                        .userId(userId)
                        .build());
            }
        }
        model.addAttribute("notes", noteService.getNotesByUserId(userId));
        model.addAttribute("result", "success");

        return "result";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Model model) {
        boolean result = noteService.delete(noteId);
        model.addAttribute("result", result ? "success" : "error");
        return "result";
    }

}
