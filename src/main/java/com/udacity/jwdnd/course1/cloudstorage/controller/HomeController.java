package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private static final String HOME_PAGE = "home";

    private final UserService userService;

    private final FileService fileService;

    private final NoteService noteService;

    private final CredentialService credentialService;

    private final AuthenticationFacade authenticationFacade;

    public HomeController(UserService userService, FileService fileService, NoteService noteService,
                          CredentialService credentialService, AuthenticationFacade authenticationFacade) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping()
    public String getViewHome(Model model,
                              @ModelAttribute("newNote") NoteForm newFile,
                              @ModelAttribute("newCredential") CredentialForm newCredential) {
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();
        User user = userService.getUser(username);
        model.addAttribute("files", fileService.getFilesByUserId(user.getUserId()));
        model.addAttribute("notes", noteService.getNotesByUserId(user.getUserId()));
        model.addAttribute("credentials", credentialService.getCredentialsByUserId(user.getUserId()));
        return HOME_PAGE;
    }

}
