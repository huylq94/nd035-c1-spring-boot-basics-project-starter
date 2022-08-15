package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationFacade;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private static final String RESULT = "result";

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private CredentialService credentialService;

    @PostMapping("/add-credential")
    public String addCredential(Model model, @ModelAttribute("newCredential")CredentialForm newCredential) {
        Authentication authentication = authenticationFacade.getAuthentication();
        User user = userService.getUser(authentication.getName());
        String url = newCredential.getUrl();
        String password = newCredential.getPassword();
        String credentialId = newCredential.getCredentialId();
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptionPassword = encryptionService.encryptValue(password,encodedKey);

        Credentials credentials = Credentials.builder()
                .url(url)
                .username(newCredential.getUsername())
                .userId(user.getUserId())
                .key(encodedKey)
                .password(encryptionPassword)
                .build();

        if(StringUtils.isEmpty(credentialId)){
            credentialService.insert(credentials);
        } else {
            Integer creId = Integer.parseInt(newCredential.getCredentialId());
            credentials.setCredentialId(creId);
            credentialService.update(credentials);
        }

        model.addAttribute("credentials", credentialService.getCredentialsByUserId(user.getUserId()));
        model.addAttribute("result", "success");

        return RESULT;

    }

    @GetMapping("/delete/{credentialId}")
    public String delete(Model model, @PathVariable("credentialId") Integer credentialId) {
        Authentication authentication = authenticationFacade.getAuthentication();
        User user = userService.getUser(authentication.getName());
        credentialService.delete(credentialId);
        model.addAttribute("credentials", credentialService.getCredentialsByUserId(user.getUserId()));
        model.addAttribute("result", "success");

        return RESULT;
    }
}
